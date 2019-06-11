package inscriptions;

import java.io.Serializable;
import java.util.Collections;
import java.time.LocalDate;
import java.util.Set;
import java.util.TreeSet;
import javax.persistence.*;

import inscriptions.Inscriptions;

/**
 * Couche métier des compétitions;
 * Représente une compétition, c'est-à-dire un ensemble de candidats 
 * inscrits à un événement, les inscriptions sont closes à la date dateCloture.
 *
 */

@Entity
@Table(name = "competition")
public class Competition implements Comparable<Competition>, Serializable
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_co")
    private int id_co;
    
    @Transient
	private static final long serialVersionUID = -2882150118573759729L;
	
	@Transient
	private Inscriptions inscriptions;
	
	@Column( name ="nom")
	private String nom;
	
	 @ManyToMany(fetch = FetchType.LAZY,
	            cascade
	            = {
	                CascadeType.DETACH
	            })
	    @JoinTable(name = "inscrire", joinColumns = {
	        @JoinColumn(name = "id_co")}, inverseJoinColumns = {
	        @JoinColumn(name = "id_ca")})
	private Set<Candidat> candidats;
	
	@Column(name = "date_cloture")
	private LocalDate dateCloture;
	
	@Column(name="en_equipe")
	private boolean enEquipe = false;

	Competition(){}
	
	Competition(Inscriptions inscriptions, String nom, LocalDate dateCloture, boolean enEquipe)
	{
		this.enEquipe = enEquipe;
		this.inscriptions = inscriptions;
		this.nom = nom;
		this.dateCloture = dateCloture;
		candidats = new TreeSet<>();
	}
	
	/**
	 * Retourne le nom de la compétition.
	 * @return
	 */
	
	public String getNom()
	{
		return nom;
	}
	
	/**
	 * Modifie le nom de la compétition.
	 */
	
	public void setNom(String nom)
	{
		this.nom = nom ;
	}
	
	/**
	 * Retourne vrai si les inscriptions sont encore ouvertes, 
	 * faux si les inscriptions sont closes.
	 * @return
	 */
	
	public boolean inscriptionsOuvertes()
	{
		// retourner vrai si et seulement si la date système est antérieure à la date de clôture.
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
	 * Est vrai si et seulement si les inscriptions sont réservées aux équipes.
	 * @return
	 */
	
	public boolean estEnEquipe()
	{
		return enEquipe;
	}
	
	/**
	 * 
	 * @param enEquipe
	 */
	public void setEstEnEquipe(boolean enEquipe) {
		if(getCandidats().isEmpty())
			this.enEquipe = enEquipe;
		else
			
			throw new RuntimeException("Erreur set En equipe: ");
			
	}
	
	
	/**
	 * Modifie la date de cloture des inscriptions. Il est possible de la reculer 
	 * mais pas de l'avancer.
	 * @param dateCloture
	 */
	
	public void setDateCloture(LocalDate dateCloture)
	{
		// vérifier que l'on avance pas la date.
		if (dateCloture.isAfter(getDateCloture()) || dateCloture.equals(getDateCloture()))
			this.dateCloture = dateCloture;
		else
			throw new RuntimeException("Impossible de changer la date de cloture.");
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
	 * Inscrit un candidat de type Personne à la compétition. Provoque une
	 * exception si la compétition est réservée aux équipes ou que les 
	 * inscriptions sont closes.
	 * @param personne
	 * @return
	 */
	
	public boolean add(Personne personne)
	{
		// vérifier que la date de clôture n'est pas passée
		if (enEquipe || ! inscriptionsOuvertes())
			throw new RuntimeException("Impossbile d'ajouter une personne.");
		personne.add(this);
		return candidats.add(personne);
	}

	/**
	 * Inscrit un candidat de type Equipe à la compétition. Provoque une
	 * exception si la compétition est réservée aux personnes ou que 
	 * les inscriptions sont closes.
	 * @param personne
	 * @return
	 */

	public boolean add(Equipe equipe)
	{
		// vérifier que la date de clôture n'est pas passée
		if (!enEquipe || !inscriptionsOuvertes())
			throw new RuntimeException("Impossible d'ajouter une equipe.");
		equipe.add(this);
		//Passerelle.save(equipe);
		return candidats.add(equipe);
	}
	
	/**
	 * Retourne les Candidats que l'on peut inscrire à cette competition.
	 * @return les candidats que l'on peut inscrire à cette compétition.
	 */
	
	public Set<Candidat> getCandidatsAInscrire()
	{
		// les candidats que l'on peut inscrire à cette compétition.
		Set<Candidat> CandidatsAInscrire = new TreeSet<>();
		for (Candidat candidats : inscriptions.getCandidats())
			if (!(getCandidats()).contains(candidats))
				CandidatsAInscrire.add(candidats);
		return CandidatsAInscrire;
	}
	
	/**
	 * Desinscrit un candidat.
	 * @param candidat
	 * @return
	 */
	
	public boolean remove(Candidat candidat)
	{
		candidat.remove(this);
		//Passerelle.delete(candidat);
		return candidats.remove(candidat);
	}
	
	public void remove() {
		while(!getCandidats().isEmpty())
			remove(getCandidats().iterator().next());
	}
	
	/**
	 * Supprime la competition de l'application.
	 */
	
	public void delete()
	{
		for (Candidat candidat : candidats)
			candidat.remove(this);
		//Passerelle.delete(this);
		inscriptions.delete(this);	
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
	
	public int getId() {
		return id_co;
	}
}
