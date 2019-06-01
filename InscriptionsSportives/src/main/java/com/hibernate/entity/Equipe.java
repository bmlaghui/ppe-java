package com.hibernate.entity;

import java.util.Collections;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.JoinColumn;

import org.hibernate.annotations.Sort;
import org.hibernate.annotations.SortType;

import com.hibernate.connect.Passerelle;

/**
 * Repr�sente une Equipe. C'est-�-dire un ensemble de personnes pouvant 
 * s'inscrire � une comp�tition.
 * 
 */
@Entity(name ="equipe")
public class Equipe extends Candidat
{
	private static final long serialVersionUID = 4147819927233466035L;
	
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(name = "equipe_personne", 
	joinColumns = {@JoinColumn(name = "EQUIPE_ID", nullable = false, updatable = false) }, 
	inverseJoinColumns = { @JoinColumn(name = "PERSONNE_ID",nullable = false, updatable = false) })
	@Sort(type = SortType.NATURAL)
	private SortedSet<Personne> membres = new TreeSet<>();
	
	protected Equipe() {}
	
	Equipe(Inscriptions inscriptions, String nom)
	{
		super(inscriptions, nom);
	}

	/**
	 * Retourne l'ensemble des personnes formant l'�quipe.
	 */
	
	public SortedSet<Personne> getMembres()
	{
		return Collections.unmodifiableSortedSet(membres);
	}
	
	/**
	 * Ajoute une personne dans l'�quipe.
	 * @param membre
	 * @return
	 */

	public boolean add(Personne membre)
	{
		membre.add(this);
		boolean toReturn = membres.add(membre);
		Passerelle.save(this);
		
		return toReturn;
	}

	/**
	 * Supprime une personne de l'�quipe. 
	 * @param membre
	 * @return
	 */
	
	public boolean remove(Personne membre)
	{
		membre.remove(this);
		boolean toReturn = membres.remove(membre);
		Passerelle.save(this);
		
		return toReturn;
	}

	/**
	 * Retourne les personnes que l'on peut ajouter dans cette �quipe.
	 * @return les personnes que l'on peut ajouter dans cette �quipe.
	 */
	
	public Set<Personne> getPersonnesAAjouter()
	{
		// TODO retourner les personnes que l'on peut ajouter dans cette �quipe.
		Set<Personne> PersonneAAjouter = new TreeSet<>();
		for (Personne personnes : Inscriptions.getInscriptions().getPersonnes())
			if (!getMembres().contains(personnes))
				PersonneAAjouter.add(personnes);
		return PersonneAAjouter;
	}
	
	@Override
	public void delete()
	{
		super.delete();
		Passerelle.delete(this);
	}
	
	@Override
	public String toString()
	{
		return "Equipe " + super.toString();
	}
}
