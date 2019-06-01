package com.hibernate.entity;

import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import org.hibernate.annotations.CascadeType;

import com.hibernate.connect.Passerelle;

import org.hibernate.annotations.Cascade;
/**
 * Repr�sente une personne physique pouvant s'inscrire � une comp�tition.
 */
@Entity(name = "personne")
public class Personne extends Candidat
{
	private static final long serialVersionUID = 4434646724271327254L;
	
	
	private String prenom;
	

	private String mail;
	
	@ManyToMany(fetch = FetchType.EAGER, mappedBy = "membres")
	private Set<Equipe> equipes;
	
	protected Personne() {};
	
	Personne(Inscriptions inscriptions, String nom, String prenom, String mail)
	{
		super(inscriptions, nom);
		this.prenom = prenom;
		this.mail = mail;
		equipes = new TreeSet<>();
		Passerelle.save(this);
	}

	/**
	 * Retourne le pr�nom de la personne.
	 * @return
	 */
	
	public String getPrenom()
	{
		return prenom;
	}

	/**
	 * Modifie le prénom de la personne.
	 * @param prenom
	 */
	
	public void setPrenom(String prenom)
	{
		this.prenom = prenom;
		Passerelle.save(this);
	}

	/**
	 * Retourne l'adresse �lectronique de la personne.
	 * @return
	 */
	
	public String getMail()
	{
		return mail;
	}

	/**
	 * Modifie l'adresse �lectronique de la personne.
	 * @param mail
	 */
	
	public void setMail(String mail)
	{
		this.mail = mail;
		Passerelle.save(this);
	}

	/**
	 * Retoure les �quipes dont cette personne fait partie.
	 * @return
	 */
	
	public Set<Equipe> getEquipes()
	{
		return Collections.unmodifiableSet(equipes);
	}
	
	boolean add(Equipe equipe)
	{
		
		boolean toReturn = equipes.add(equipe);
		Passerelle.save(this);
		
		return toReturn;
	}

	boolean remove(Equipe equipe)
	{
		boolean toReturn = equipes.remove(equipe);
		Passerelle.save(this);
		
		return toReturn;
	}
	
	@Override
	public void delete()
	{
		super.delete();
		for (Equipe e : equipes)
			e.remove(this);
		Passerelle.delete(this);
	}
	
	@Override
	public String toString()
	{
		return super.toString() + " membre de " + equipes.toString();
	}
}