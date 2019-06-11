package menuUser;

import java.io.IOException;
import java.time.LocalDate;

import inscriptions.*;
import java.util.ArrayList;

import hibernate.Passerelle;
import commandLineMenus.*;
import commandLineMenus.rendering.examples.util.InOut;
import static commandLineMenus.rendering.examples.util.InOut.*;


/**
 **** Dialogue utilisateur *****
 ******************************* 
 * Couche : dialogue
 * @author Brahim
 *
 */
public class Dialogue {
	
	
	static Inscriptions inscriptions;
	
	/**
	 * Dialogue
	 * @param inscriptions
	 */
	public Dialogue(Inscriptions inscriptions)
	{
		Dialogue.inscriptions = inscriptions;
		start();
	}
	
	/**
	 * Start
	 */
	public void start()
	{
		menuPrincipal().start();
	}
	
	/**
	 * Menu principal
	 * @return
	 */
	private Menu menuPrincipal()
	{
		Menu menu = new Menu("Gestion des inscriptions sportives");
		menu.add(menuCompetitions());
		menu.add(menuEquipes());
		menu.add(menuPersonnes());
		menu.add(menuQuitter());
		return menu;
	}
	
	/**
	 * Menu Quitter
	 * @return
	 */
	private Menu menuQuitter()
	{
		Menu menu = new Menu("Quitter", "q");
		menu.add(quitterEtEnregistrer());
		menu.add(quitterSansEnregistrer());
		menu.addBack("r");
		return menu;
	}
	
	/**
	 * Menu Competition
	 * @return
	 */
	private Menu menuCompetitions()
	{
		Menu menu = new Menu("Gérer les compétitions", "c");
		menu.add(afficherCompetitions());
		menu.add(ajouterCompetition());
		menu.add(selectionnerCompetition());
		menu.add(sauvegarder());
		menu.addBack("q");
		return menu;
	}
	
	/**
	 * Menu Equipe
	 * @return
	 */
	private Menu menuEquipes()
	{
		Menu menu = new Menu("Gérer les équipes", "e");
		menu.add(afficherEquipes());
		menu.add(ajouterEquipe());
		menu.add(selectionnerEquipe());
		menu.add(sauvegarder());
		menu.addBack("q");
		return menu;
	}
	
	/**
	 * Menu Personne
	 * @return
	 */
	private Menu menuPersonnes()
	{
		Menu menu = new Menu("Gérer les personnes", "p");
		menu.add(afficherPersonnes());
		menu.add(ajouterPersonne());
		menu.add(selectionnerPersonne());
		menu.add(sauvegarder());
		menu.addBack("q");
		return menu;
	}
	
	/**
	 * Affiche les competitions
	 * @return Option
	 */
	private Option afficherCompetitions()
	{
		return new Option("Afficher les compétitions", "l", () -> {
			for(Competition c : Passerelle.getCompetition()) {
				System.out.println(c);
			};
		});
	}
	
	/**
	 * Afficher les equipes
	 * @return
	 */
	private Option afficherEquipes()
	{
		return new Option("Afficher les équipes", "l", () -> {
			for(Equipe e : Passerelle.getEquipe()) {
				System.out.println(e);
			};
		});
	}
	
	/**
	 * Afficher les personnes
	 * @return
	 */
	private Option afficherPersonnes()
	{
		return new Option("Afficher les personnes", "l", () -> {
			for(Personne p : Passerelle.getPersonne()) {
				System.out.println(p);
			};
		});
	}
	
	/**
	 * Ajouter une competition 
	 * @return
	 */
	private Option ajouterCompetition()
	{
		return new Option("Ajouter une compétition", "a", () -> {
			Passerelle.save(inscriptions.createCompetition(getString("Nom : "), 
				LocalDate.of(getInt("Année : "), getInt("Mois : "), getInt("Jour : ")), 
				  InOut.getInt("Choisir :\n0 - Compétition de personnes \n1 - Compétition d'équipes : ")==1));
		});
	}
	
	/**
	 * Ajouter une equipe 
	 * @return
	 */
	private Option ajouterEquipe()
	{
		return new Option("Ajouter une équipe", "a", () -> {
			Passerelle.save(inscriptions.createEquipe(getString("Entrez le nom de votre équipe : ")));});
	}

	/**
	 * Ajouter une personne 
	 * @return
	 */
	private Option ajouterPersonne()
	{
		return new Option("Ajouter une personne", "a", () -> {
			Passerelle.save(inscriptions.createPersonne(getString("Entrez le nom de votre personne : "), 
														getString("Entrez le prenom de votre personne : "), 
														getString("Entrez le mail de votre personne : ")));});
	}

	/**
	 * Sélectionner une compétition
	 * @return
	 */
	private List<Competition> selectionnerCompetition()
	{
		return new List<Competition>("Sélectionner une compétition", "e", 
				() -> new ArrayList<>(Passerelle.getCompetition()),
				(element) -> editerCompetition(element)
				);
	}
	
	/**
	 * Sélectionner une equipe
	 * @return
	 */
	private List<Equipe> selectionnerEquipe()
	{
		return new List<Equipe>("Sélectionner une équipe", "e", 
				() -> new ArrayList<>(Passerelle.getEquipe()),
				(element) -> editerEquipe(element)
				);
	}
	
	/**
	 * Sélectionner une personne
	 * @return
	 */
	private List<Personne> selectionnerPersonne()
	{
		return new List<Personne>("Sélectionner une personne", "e", 
				() -> new ArrayList<>(Passerelle.getPersonne()),
				(element) -> editerPersonne(element)
				);
	}
	
	/**
	 * Menu Edition competition
	 * @param competition
	 * @return
	 */
	private Menu editerCompetition(Competition competition)
    {
        Menu menu = new Menu("Editer " + competition.getNom() + ((competition.inscriptionsOuvertes()) ? "" : " Inscriptions closes !"), competition.getNom(), "");
        menu.add(afficherCompetition(competition));
        menu.add(afficherCandidats(competition));
        
        if(competition.inscriptionsOuvertes()) {
	        if (!competition.estEnEquipe()) {
	        	menu.add(ajouterPersonneCompetition(competition));
	        }
	        else {
	        	menu.add(ajouterEquipeCompetition(competition)); 
	        }
        } 
        	
        menu.add(supprimerCandidat(competition));
        menu.add(modifierNomCompetition(competition));
        menu.add(modifierDateCompetition(competition));
        menu.add(modifierEnEquipe(competition));
        menu.add(supprimerCompetition(competition));
        menu.add(sauvegarder());
        menu.addBack("q");
        return menu;
    }
	
	/**
	 * Afficher les details de la competition sélectionnée
	 * @param competition
	 * @return
	 */
	private Option afficherCompetition(final Competition competition)
	{
		return new Option("Afficher les details de la compétition", "lc", 
				() -> 
				{	
					for(Competition c : Passerelle.getCompetition()) {
						
						if(c.getId()==competition.getId()) 
							if(c.estEnEquipe())
							{
							System.out.print(c.getNom()+" "+c.getDateCloture()+", En équipes ");
							}
							else
							{
							System.out.print(c.getNom()+" "+c.getDateCloture()+", En personnes ");

							}
			}
				}
		);
	}
	
	/**
	 * Afficher les candidats de la compétition
	 * @param competition
	 * @return
	 */
	private Option afficherCandidats(final Competition competition)
	{
		return new Option("Afficher les candidats", "a", 
				() -> 
				{
					for(Competition c : Passerelle.getCompetition()) {
						if (c.getId()==competition.getId())
							System.out.print(c.getCandidats());
					}
				}
		);
	}
	
	/**
	 * Ajouter une personne dans la compétition
	 * @param competition
	 * @return
	 */
	private List<Candidat> ajouterPersonneCompetition(final Competition competition)
	{
		return new List<>("Ajouter un candidat dans la compétition", "m", 
				() -> new ArrayList<>(inscriptions.getPersonnes()),
				(index, element) -> {
					competition.add((Personne) element);
					Passerelle.save(competition);}
				);
	}
	
	/**
	 * Ajouter une equipe dans la compétition
	 * @param competition
	 * @return
	 */
	private List<Candidat> ajouterEquipeCompetition(final Competition competition)
	{
		return new List<>("Ajouter une équipe dans la compétition", "e", 
				() -> new ArrayList<>(inscriptions.getEquipes()),
				(index, element) -> {competition.add((Equipe) element);
				Passerelle.save(competition);
				}
				);
	}
	
	/**
	 * Supprimer un candidat de la compétition
	 * @param competition
	 * @return
	 */
	private List<Candidat> supprimerCandidat(final Competition competition)
	{
		return new List<>("Supprimer un candidat", "s", 
				() -> new ArrayList<>(competition.getCandidats()),
				(index, element) -> {competition.remove(element);
				Passerelle.save(competition);}
				);
	}
	
	/**
	 * Modifier le nom de la compétition
	 * @param competition
	 * @return
	 */
	private Option modifierNomCompetition(final Competition competition)
	{
		return new Option("modifier le nom de la compétition", "c", 
				() -> 
				{
					competition.setNom(getString("Nouveau nom : \n"));
					Passerelle.save(competition);
				}
		);
	}
	
	/**
	 * Modifier la date de la compétition
	 * @param competition
	 * @return
	 */
	private Option modifierDateCompetition(final Competition competition)
	{
		return new Option("modifier la date de la compétition", "md", 
				() -> {
					competition.setDateCloture(LocalDate.of(getInt("Année : "), getInt("Mois : "), getInt("Jour : ")));
					Passerelle.save(competition);
					});
	}
	
	/**
	 * Supprimer une compétition
	 * @param competition
	 * @return
	 */
	private Option supprimerCompetition(final Competition competition)
	{
		return new Option("Supprimer la compétition", "d", 
				() -> 
				{
					competition.delete();
					Passerelle.delete(competition);;
				}
		);
	}
	
	/**
	 * Modifier l'état en Equipe de la compétition
	 * @param competition
	 * @return
	 */
	private Option modifierEnEquipe(final Competition competition)
	{
		return new Option("Modifier l'état de l'equipe", "me", 
				() -> {
						if(!competition.getCandidats().isEmpty()) {
							 int a = getInt("Voulez-vous supprimer tous les candidats ?\n"
									 +"0 - Non\n"
									 +"1 - Oui\n");
							 if(a == 1) {
								 competition.remove();
								 competition.setEstEnEquipe(!competition.estEnEquipe());
								 
							 }
						}else {
							competition.setEstEnEquipe(!competition.estEnEquipe());
						}
							
				});
		
	}
			
	/**
	 * Menu Edition Equipe
	 * @param equipe
	 * @return
	 */
	private Menu editerEquipe(Equipe equipe)
    {
        Menu menu = new Menu("Editer " + equipe.getNom());
        menu.add(afficherMembres(equipe));
        menu.add(ajouterMembre(equipe));
        menu.add(supprimerMembre(equipe));
        menu.add(supprimerEquipe(equipe));
        menu.add(sauvegarder());
        menu.addBack("q");
        return menu;
    }
	
	/**
	 * Afficher les membres de l'équipe
	 * @param equipe
	 * @return
	 */
	private Option afficherMembres(final Equipe equipe)
	{
		return new Option("Afficher les membres", "a", 
				() -> 
				{
					System.out.println(equipe.getMembres());
					//Passerelle.getPersonne(equipe);
				}
		);
	}
	
	/**
	 * Ajouter un membre dans l'équipe
	 * @param equipe
	 * @return
	 */
	private List<Personne> ajouterMembre(final Equipe equipe)
	{
		return new List<>("Ajouter un membre", "m", 
				() -> new ArrayList<>(Passerelle.getPersonne()),
				(index, element) -> {equipe.add(element);
				//Passerelle.save(equipe);
				}
				);
	}
	
	/**
	 * Supprimer un membre de l'équipe
	 * @param equipe
	 * @return
	 */
	private List<Personne> supprimerMembre(final Equipe equipe)
	{
		return new List<>("Supprimer un membre", "s", 
				() -> new ArrayList<>(equipe.getMembres()),
				(index, element) -> {equipe.remove(element);
				//Passerelle.delete(equipe);
				}
				);
	}
	
	/**
	 * Supprimer une équipe
	 * @param equipe
	 * @return
	 */
	private Option supprimerEquipe(final Equipe equipe)
	{
		return new Option("Supprimer l'équipe", "d", 
				() -> 
				{
					equipe.delete();
					Passerelle.delete(equipe);
				}
		);
	}	
	
	/**
	 * Edition de Personne
	 * @param personne
	 * @return
	 */
	private Menu editerPersonne(Personne personne)
	{
	    Menu menu = new Menu("Editer " + personne.getNom());
	    menu.add(modifierPersonne(personne));
        menu.add(supprimerPersonne(personne));
        menu.add(sauvegarder());
	    menu.addBack("q");
	    return menu;
	}
	
	/**
	 * Modifier une personne (nom, prénom, mail)
	 * @param personne
	 * @return
	 */
	private Option modifierPersonne(Personne personne)
	{
		return new Option("Modifier une personne", "a", () -> {
			
			personne.setNom(getString("Nouveau nom : "));
			personne.setPrenom(getString("Nouveau prenom : "));
			personne.setMail(getString("Nouveau mail : "));
			Passerelle.save(personne);
			
		});
	}
	
	/**
	 * Supprimer une personne 
	 * @param personne
	 * @return
	 */
	private Option supprimerPersonne(Personne personne)
	{
		return new Option("Supprimer la personne", "b", () -> {personne.delete();
		Passerelle.delete(personne);
		});
	}
	
	/**
	 * Quitter le dialogue et enregistrer les données dans Inscriptions.srz
	 * @return
	 */
	private Option quitterEtEnregistrer()
    {
        return new Option("Quitter et sauvegarder", "q", 
                () -> 
                {
                    try
                    {
                        inscriptions.sauvegarder();
                        Action.QUIT.optionSelected();
                    } 
                    catch (IOException e)
                    {
                        System.out.println("Impossible d'effectuer la sauvegarde");
                    }
                }
            );
    }
	
	/**
	 * Quitter le dialogue sans sauvegarder les données 
	 * @return
	 */
	private Option quitterSansEnregistrer()
	{
		return new Option("Quitter sans sauvegarder", "a", Action.QUIT);
	}
	
	/**
	 * Sauvegarder les données dans Inscriptions.srz
	 * @return
	 */
	private Option sauvegarder()
    {
        return new Option("Sauvegarder", "x", 
                () -> 
                {
                    try
                    {
    					System.out.print("Sauvegarde en cours...");
                        inscriptions.sauvegarder();
    					System.out.println("réussie.");

                    } 
                    catch (IOException e)
                    {
                        System.out.println("Impossible d'effectuer une sauvegarde");
                    }
                }
            );
    }
	
	/**
	 * Fonction Main
	 */
	public static void main() {
		new Dialogue(Inscriptions.getInscriptions());
	}
		
}
