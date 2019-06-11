package inscriptions;

import java.io.Serializable;
import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;

import javax.persistence.*;

/**
 * Candidat à un événement sportif, soit une personne physique, soit une équipe.
 *
 */

@Entity
@Table(name = "candidat")
@Inheritance(strategy=InheritanceType.JOINED) //gere l'heritage
public abstract class Candidat implements Comparable<Candidat>, Serializable
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_ca", nullable = false)
    private int id_ca;

	@Transient
	private static final long serialVersionUID = -6035399822298694746L;
	
	@Transient
	private Inscriptions inscriptions;
	
	@Column (name = "nom")
	private String nom;
	
	
    /**
     * Clés plusieurs à plusieurs sur la table inscrire
     **/
	@ManyToMany(fetch = FetchType.LAZY,
	            cascade
	            = {
	                CascadeType.DETACH
	            })
	    @JoinTable(name = "inscrire", joinColumns = {
	        @JoinColumn(name = "id_ca")}, inverseJoinColumns = {
	        @JoinColumn(name = "id_co")})
	/**
     * Crée une liste de toutes les compétitions auxquelles le candidat est inscrit
     **/
	 private Set<Competition> competitions;
	
	
	Candidat(Inscriptions inscriptions, String nom)
	{
		this.inscriptions = inscriptions;	
		this.nom = nom;
		competitions = new TreeSet<>();
	}
	
    Candidat(){}

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
	}

	/**
	 * Retourne toutes les compétitions auxquelles ce candidat est inscrit.s
	 * @return
	 */

	public Set<Competition> getCompetitions()
	{
		return Collections.unmodifiableSet(competitions);
	}
	
	boolean add(Competition competition)
	{
		return competitions.add(competition);
	}

	boolean remove(Competition competition)
	{
		return competitions.remove(competition);
	}

	/**
	 * Supprime un candidat de l'application.
	 */
	
	public void delete()
	{
		for (Competition c : competitions)
			c.remove(this);
		//inscriptions.delete(this);
	}
	
	@Override
	public int compareTo(Candidat o)
	{
		return getNom().compareTo(o.getNom());
	}
	
	@Override
	public String toString()
	{
		return getNom() + " -> inscrit à " + getCompetitions();
	}
	
	public int getId() {
		return id_ca;
	}
	
}
