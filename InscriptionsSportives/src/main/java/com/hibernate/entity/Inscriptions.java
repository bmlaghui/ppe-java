package com.hibernate.entity;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.*; 

import javax.persistence.Entity;

import com.hibernate.connect.Passerelle;

/**
 * Point d'entrée dans l'application, un seul objet de type Inscription
 * permet de gérer les compétitions, candidats (de type equipe ou personne)
 * ainsi que d'inscrire des candidats à des compétition.
 */


public class Inscriptions implements Serializable
{
	private static final long serialVersionUID = -3095339436048473524L;
	private static final String FILE_NAME = "Inscriptions.srz";
	private static Inscriptions inscriptions;
	
	private SortedSet<Competition> competitions = new TreeSet<>();
	private SortedSet<Candidat> candidats = new TreeSet<>();

	private Inscriptions()
	{
	}
	
	/**
	 * Retourne les compétitions.
	 * @return
	 */
	
	public SortedSet<Competition> getCompetitions()
	{
		return convertListToSet(Passerelle.getData("Competition"));
	}
	
	/**
	 * Retourne tous les candidats (personnes et équipes confondues).
	 * @return
	 */
	
	public SortedSet<Candidat> getCandidats()
	{
		return convertListToSet(Passerelle.getData("Candidat"));
	}

	/**
	 * Retourne toutes les personnes.
	 * @return
	 */
	
	public SortedSet<Personne> getPersonnes()
	{
		return convertListToSet(Passerelle.getData("Personne"));
	}

	/**
	 * Retourne toutes les équipes.
	 * @return
	 */
	
	public SortedSet<Equipe> getEquipes()
	{
		return convertListToSet(Passerelle.getData("Equipe"));
	}
	
    public <T> SortedSet<T> convertListToSet(List<T> list) 
    { 
    	SortedSet<T> set = new TreeSet<>(); 
        for (T t : list) 
            set.add(t); 
  
        return set; 
    } 

	/**
	 * Créée une compétition. Ceci est le seul moyen, il n'y a pas
	 * de constructeur public dans {@link Competition}.
	 * @param nom
	 * @param dateCloture
	 * @param enEquipe
	 * @return
	 */
	
	public Competition createCompetition(String nom, 
			LocalDate dateCloture, boolean enEquipe)
	{
		Competition competition = new Competition(this, nom, dateCloture, enEquipe);
		competitions.add(competition);
		return competition;
	}

	/**
	 * Créée un Candidat de type Personne. Ceci est le seul moyen, il n'y a pas
	 * de constructeur public dans {@link Personne}.

	 * @param nom
	 * @param prenom
	 * @param mail
	 * @return
	 */
	
	public Personne createPersonne(String nom, String prenom, String mail)
	{
		Personne personne = new Personne(this, nom, prenom, mail);
		candidats.add(personne);
		return personne;
	}
	
	/**
	 * Créée un Candidat de type équipe. Ceci est le seul moyen, il n'y a pas
	 * de constructeur public dans {@link Equipe}.
	 * @param nom
	 * @param prenom
	 * @param mail
	 * @return
	 */
	
	public Equipe createEquipe(String nom)
	{
		Equipe equipe = new Equipe(this, nom);
		candidats.add(equipe);
		return equipe;
	}
	
	void delete(Competition competition)
	{
		competitions.remove(competition);
	}
	
	void delete(Candidat candidat)
	{
		candidats.remove(candidat);
	}
	
	/**
	 * Retourne l'unique instance de cette classe.
	 * Crée cet objet s'il n'existe déjà.
	 * @return l'unique objet de type {@link Inscriptions}.
	 */
	
	public static Inscriptions getInscriptions()
	{
		
		if (inscriptions == null)
		{
			inscriptions = readObject();
			if (inscriptions == null)
				inscriptions = new Inscriptions();
		}
		return inscriptions;
	}

	/**
	 * Retourne un object inscriptions vide. Ne modifie pas les compétitions
	 * et candidats déjà existants.
	 */
	
	public Inscriptions reinitialiser()
	{
		inscriptions = new Inscriptions();
		return getInscriptions();
	}

	/**
	 * Efface toutes les modifications sur Inscriptions depuis la dernière sauvegarde.
	 * Ne modifie pas les compétitions et candidats déjà existants.
	 */
	
	public Inscriptions recharger()
	{
		inscriptions = null;
		return getInscriptions();
	}
	
	private static Inscriptions readObject()
	{
		ObjectInputStream ois = null;
		try
		{
			FileInputStream fis = new FileInputStream(FILE_NAME);
			ois = new ObjectInputStream(fis);
			return (Inscriptions)(ois.readObject());
		}
		catch (IOException | ClassNotFoundException e)
		{
			return null;
		}
		finally
		{
				try
				{
					if (ois != null)
						ois.close();
				} 
				catch (IOException e){}
		}	
	}
	
	/**
	 * Sauvegarde le gestionnaire pour qu'il soit ouvert automatiquement 
	 * lors d'une exécution ultérieure du programme.
	 * @throws IOException 
	 */
	
	public void sauvegarder() throws IOException
	{
		ObjectOutputStream oos = null;
		try
		{
			FileOutputStream fis = new FileOutputStream(FILE_NAME);
			oos = new ObjectOutputStream(fis);
			oos.writeObject(this);
		}
		catch (IOException e)
		{
			throw e;
		}
		finally
		{
			try
			{
				if (oos != null)
					oos.close();
			} 
			catch (IOException e){}
		}
	}
	
	@Override
	public String toString()
	{
		return "Candidats : " + getCandidats().toString()
			+ "\nCompetitions  " + getCompetitions().toString();
	}
	
	public static void main(String[] args)
	{
		Inscriptions inscriptions = Inscriptions.getInscriptions();
		Competition flechettes = inscriptions.createCompetition("Mondial de fléchettes", LocalDate.of(2019, 12, 12), false);
		Personne tony = inscriptions.createPersonne("Tony", "Dent de plomb", "azerty"), 
				 boris = inscriptions.createPersonne("Boris", "le Hachoir", "ytreza"),
				 brahim = inscriptions.createPersonne("Brahim", "Mlaghui", "brah"),
				 benoit = inscriptions.createPersonne("Benoit", "Valle", "ben");
		flechettes.add(benoit);
		flechettes.add(tony);
		flechettes.add(brahim);
		Equipe lesManouches = inscriptions.createEquipe("Les Manouches");
		lesManouches.add(boris);
		lesManouches.add(tony);
		//System.out.println("Inscription ouverte ? "+ flechettes.inscriptionsOuvertes());
		//System.out.println("Date cloture: "+flechettes.getDateCloture());
		//System.out.println("Date Systeme: "+LocalDate.now());
		//System.out.println(inscriptions);
		//flechettes.setDateCloture(LocalDate.of(2020,12, 12));
		//System.out.println("Candidat d�j� inscris:"+flechettes.getCandidats());
		//System.out.println("Candidat � inscrire:"+flechettes.getCandidatsAInscrire());
		
	//	System.out.println(flechettes.getDateCloture());
			lesManouches.delete();
		//	System.out.println(inscriptions);
			try
			{
				inscriptions.sauvegarder();
			} 
			catch (IOException e)
			{
				System.out.println("Sauvegarde impossible." + e);
			}
			
		}
}
