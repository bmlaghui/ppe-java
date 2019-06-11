package inscriptions;

import java.util.*;

import javax.persistence.*;

/**
 * Représente une personne physique pouvant s'inscrire à une compétition.
 */

@Entity
@Table(name="personne")
public class Personne extends Candidat
{

	@Transient
	private static final long serialVersionUID = 4434646724271327254L;
	
	@Column(name = "prenom")
	private String prenom;

	@Column(name = "mail")
	private String mail;
	
    /*
     * Clés plusieurs à plusieurs sur la table participer
     */
    @ManyToMany(fetch = FetchType.LAZY,
            cascade =
            {
            		CascadeType.DETACH
            })
    @JoinTable(name = "appartenir", joinColumns = {
        @JoinColumn(name = "id_ca")}, inverseJoinColumns = {
        @JoinColumn(name = "id_eq")})
	private Set<Equipe> equipes;
	
     
	Personne(){}
	
	
    /**
     * Constructeur
     * 
     * @param inscriptions
     * @param nom
     * @param prenom
     * @param mail
     */
	Personne(Inscriptions inscriptions, String nom, String prenom, String mail)
	{
		super(inscriptions, nom);
		this.prenom = prenom;
		this.mail = mail;
		equipes = new TreeSet<>();
	}

	/**
	 * Retourne le prénom de la personne.
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
	}

	/**
	 * Retourne l'adresse électronique de la personne.
	 * @return
	 */
	
	public String getMail()
	{
		return mail;
	}

	/**
	 * Modifie l'adresse électronique de la personne.
	 * @param mail
	 */
	
	public void setMail(String mail)
	{
		this.mail = mail;
	}

	/**
	 * Retoure les équipes dont cette personne fait partie.
	 * @return
	 */
	
	public Set<Equipe> getEquipes()
	{
		return Collections.unmodifiableSet(equipes);
	}
	
	boolean add(Equipe equipe)
	{
		equipes.add(equipe);
		//Passerelle.save(equipe);
		return equipes.add(equipe);
	}

	boolean remove(Equipe equipe)
	{
		equipes.remove(equipe);
		//Passerelle.save(equipe);
		return equipes.remove(equipe);
	}
	
	@Override
	public void delete()
	{
		super.delete();
		for (Equipe e : equipes)
			e.remove(this);
	}
	
	@Override
	public String toString()
	{
		return super.toString() + " membre de " + equipes.toString();
	}
}
