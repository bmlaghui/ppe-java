package inscriptions;

import java.util.*;

import javax.persistence.*;

/**
 * Représente une Equipe. C'est-à-dire un ensemble de personnes pouvant 
 * s'inscrire à une compétition.
 * 
 */

@Entity
@Table(name="equipe")
public class Equipe extends Candidat
{
	@Transient
	private static final long serialVersionUID = 4147819927233466035L;
	
    /*
     * Clés plusieurs à plusieurs sur la table participer
     */
	@ManyToMany(fetch = FetchType.LAZY,
            cascade =
            {
            		CascadeType.DETACH
            })
    @JoinTable(name = "appartenir", joinColumns = {
        @JoinColumn(name = "id_eq")}, inverseJoinColumns = {
        @JoinColumn(name = "id_ca")})
    @OrderBy("id_ca ASC")
	private SortedSet<Personne> membres = new TreeSet<>();
	
	public Equipe(Inscriptions inscriptions, String nom)
	{
		super(inscriptions, nom);
	}
	
    public Equipe() {}

	/**
	 * Retourne l'ensemble des personnes formant l'équipe.
	 */
	public SortedSet<Personne> getMembres()
	{
		return Collections.unmodifiableSortedSet(membres);
	}
	public SortedSet<Personne> getMembres(Equipe equipe)
	{
		return Collections.unmodifiableSortedSet(equipe.getMembres());
	}
	
	/**
	 * Ajoute une personne dans l'équipe.
	 * @param membre
	 * @return
	 */

	public boolean add(Personne membre)
	{
		membre.add(this);
		return membres.add(membre);
	}

	/**
	 * Supprime une personne de l'équipe. 
	 * @param membre
	 * @return
	 */
	
	public boolean remove(Personne membre)
	{
		membre.remove(this);
		return membres.remove(membre);
	}

	/**
	 * Retourne les personnes que l'on peut ajouter dans cette équipe.
	 * @return les personnes que l'on peut ajouter dans cette équipe.
	 */
	
	public Set<Personne> getPersonnesAAjouter()
	{
		// retourner les personnes que l'on peut ajouter dans cette équipe.
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
	}
		
	@Override
	public String toString()
	{
		return "Equipe " + super.toString();
	}
}
