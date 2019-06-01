package com.hibernate.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Transient;

import com.hibernate.connect.Passerelle;

/**
 * Repr�sente une comp�tition, c'est-�-dire un ensemble de candidats 
 * inscrits � un �v�nement, les inscriptions sont closes � la date dateCloture.
 *
 */
@Entity(name = "competition")
public class Competition implements Comparable<Competition>, Serializable
{
	private static final long serialVersionUID = -2882150118573759729L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	private String nom;
	private LocalDate dateCloture;
	private boolean enEquipe = false;
	
	@Transient
	private Inscriptions inscriptions;
	@ManyToMany(fetch = FetchType.EAGER, mappedBy = "competitions")
	private Set<Candidat> candidats;
	
	protected Competition() {}

	Competition(Inscriptions inscriptions, String nom, LocalDate dateCloture, boolean enEquipe)
	{
		this.enEquipe = enEquipe;
		this.inscriptions = inscriptions;
		this.nom = nom;
		this.dateCloture = dateCloture;
		candidats = new TreeSet<>();
		Passerelle.save(this);
	}
	
	/**
	 * Retourne le nom de la comp�tition.
	 * @return
	 */
	
	public String getNom()
	{
		return nom;
	}
	
	/**
	 * Modifie le nom de la comp�tition.
	 */
	
	public void setNom(String nom)
	{
		this.nom = nom ;
		Passerelle.save(this);
	}
	
	/**
	 * Retourne vrai si les inscriptions sont encore ouvertes, 
	 * faux si les inscriptions sont closes.
	 * @return
	 */
	
	public boolean inscriptionsOuvertes()
	{
		// TODO retourner vrai si et seulement si la date syst�me est ant�rieure � la date de cl�ture.
		
		return getDateCloture().isAfter(LocalDate.now());
		
	}
	
	/**
	 * Retourne la date de cloture des inscriptions.
	 * @return
	 */
	
	public LocalDate getDateCloture()
	{
		return dateCloture;
	}
	
	/**
	 * Est vrai si et seulement si les inscriptions sont r�serv�es aux �quipes.
	 * @return
	 */
	
	public boolean estEnEquipe()
	{
		return enEquipe;
	}
	
	/**
	 * Modifie la date de cloture des inscriptions. Il est possible de la reculer 
	 * mais pas de l'avancer.
	 * @param dateCloture
	 */
	
	public void setDateCloture(LocalDate dateCloture)
	{
		// TODO v�rifier que l'on avance pas la date.
		if (getDateCloture().isBefore(dateCloture))
			this.dateCloture = dateCloture;
		else
			throw new RuntimeException("Impossible de modifier la date.");
		Passerelle.save(this);
	}
	
	/**
	 * Retourne l'ensemble des candidats inscrits.
	 * @return
	 */
	
	public Set<Candidat> getCandidats()
	{
		return Collections.unmodifiableSet(candidats);
	}
	
	/**
	 * Inscrit un candidat de type Personne � la comp�tition. Provoque une
	 * exception si la comp�tition est r�serv�e aux �quipes ou que les 
	 * inscriptions sont closes.
	 * @param personne
	 * @return
	 */
	
	public boolean add(Personne personne)
	{
		// TODO v�rifier que la date de cl�ture n'est pas pass�e
		if (enEquipe || ! inscriptionsOuvertes())
			throw new RuntimeException("Impossbile d'ajouter une personne.");
		personne.add(this);
		boolean toReturn = candidats.add(personne);
		Passerelle.save(this);
		
		return toReturn;
	}

	/**
	 * Inscrit un candidat de type Equipe � la comp�tition. Provoque une
	 * exception si la comp�tition est r�serv�e aux personnes ou que 
	 * les inscriptions sont closes.
	 * @param personne
	 * @return
	 */

	public boolean add(Equipe equipe)
	{
		// TODO v�rifier que la date de cl�ture n'est pas pass�e
		if (!enEquipe || !inscriptionsOuvertes())
			throw new RuntimeException("Impossible d'ajouter une equipe.");
		equipe.add(this);
		boolean toReturn = candidats.add(equipe);
		Passerelle.save(this);
		
		return toReturn;
	}
	
	/**
	 * Retourne les Candidats que l'on peut inscrire � cette competition.
	 * @return les candidats que l'on peut inscrire � cette comp�tition.
	 */
	
	public Set<Candidat> getCandidatsAInscrire()
	{
		// TODO les candidats que l'on peut inscrire � cette comp�tition.
		Set<Candidat> CandidatsAInscrire = new TreeSet<>();
		for (Candidat candidats : inscriptions.getCandidats())
			if (!(getCandidats()).contains(candidats))
				CandidatsAInscrire.add(candidats);
		return CandidatsAInscrire;
	}
	
	/**
	 * D�sinscrit un candidat.
	 * @param candidat
	 * @return
	 */
	
	public boolean remove(Candidat candidat)
	{
		candidat.remove(this);
		boolean toReturn = candidats.remove(candidat);
		Passerelle.save(this);
		
		return toReturn;
	}
	
	/**
	 * Supprime la comp�tition de l'application.
	 */
	
	public void delete()
	{
		for (Candidat candidat : candidats)
			remove(candidat);
		inscriptions.delete(this);
		Passerelle.delete(this);
	}
	
	@Override
	public int compareTo(Competition o)
	{
		return getNom().compareTo(o.getNom());
	}
	
	@Override
	public String toString()
	{
		return getNom();
	}
}