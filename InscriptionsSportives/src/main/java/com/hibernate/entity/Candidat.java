package com.hibernate.entity;

import java.io.Serializable;
import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.hibernate.connect.Passerelle;

/**
 * Candidat � un �v�nement sportif, soit une personne physique, soit une �quipe.
 *
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "candidat")
public abstract class Candidat implements Comparable<Candidat>, Serializable
{
	private static final long serialVersionUID = -6035399822298694746L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	private String nom;
	
	@Transient
	private Inscriptions inscriptions;
	
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(name = "CANDIDAT_COMPETITION", 
	joinColumns = {@JoinColumn(name = "CANDIDAT_ID", nullable = false, updatable = false) }, 
	inverseJoinColumns = { @JoinColumn(name = "COMPETITION_ID",nullable = false, updatable = false) })
	private Set<Competition> competitions;
	
	protected Candidat() {};
	
	Candidat(Inscriptions inscriptions, String nom)
	{
		this.inscriptions = inscriptions;	
		this.nom = nom;
		competitions = new TreeSet<>();
		Passerelle.save(this);
	}

	/**
	 * Retourne le nom du candidat.
	 * @return
	 */	
	public String getNom()
	{
		return nom;
	}

	/**
	 * Modifie le nom du candidat.
	 * @param nom
	 */	
	public void setNom(String nom)
	{
		this.nom = nom;
		Passerelle.save(this);
	}

	/**
	 * Retourne toutes les comp�titions auxquelles ce candidat est inscrit.s
	 * @return
	 */
	public Set<Competition> getCompetitions()
	{
		return Collections.unmodifiableSet(competitions);
	}
	
	boolean add(Competition competition)
	{
		boolean toReturn = competitions.add(competition);
		Passerelle.save(this);
		
		return toReturn;
	}

	boolean remove(Competition competition)
	{
		boolean toReturn = competitions.remove(competition);
		Passerelle.save(this);
		
		return toReturn;
	}

	/**
	 * Supprime un candidat de l'application.
	 */	
	public void delete()
	{
		for (Competition c : competitions)
			c.remove(this);
		inscriptions.delete(this);
	}
	
	@Override
	public int compareTo(Candidat o)
	{
		return getNom().compareTo(o.getNom());
	}
	
	@Override
	public String toString()
	{
		return "\n" + getNom() + " -> inscrit � " + getCompetitions();
	}
}
