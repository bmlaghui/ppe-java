package menuUser;

import java.util.ArrayList;
import java.util.Scanner;

import commandLineMenus.*;
import commandLineMenus.rendering.examples.util.InOut;
import inscriptions.Inscriptions;
import inscriptions.*;


public class MainUser {

	public void start()
	{
		menuPrincipal().start();
	}
	
	private Menu menuPrincipal()
	{
		Menu menu = new Menu(".:: Inscriptions Sportives .:: ");
		menu.add(menuInscriptions());
		menu.add(menuCandidats());
		menu.add(menuCompetitions());
		return menu;
	}
	
	private Menu menuInscriptions() {
		Menu menu = new Menu("Inscriptions", "i");
		menu.add(creerEquipe());
		menu.add(creerPersonne());
		menu.addBack("r");
		return menu; 		}
	
	private Menu menuCandidats() {
		Menu menu = new Menu("Candidat", "c");
		menu.add(menuEquipe());
		menu.add(menuPersonne());
		menu.addBack("r");
		return menu;
	}
	
	private Menu menuCompetitions() {
		Menu menu = new Menu("Competitions", "n");
		menu.add(listCompetitions());
		menu.addBack("r");
		return menu;
	}
	
	private List<Competition> selectionCompetitions()
	{
		return new List<Competition>("Selectionner une competitions", "s",
				() -> new ArrayList<>(Inscriptions.getInscriptions().getCompetitions()),
				(element) -> menuSelectCompetition(element)
				);
	}
	
	private Menu menuSelectCompetition(Competition c) {
		Menu menu = new Menu ("Competition: ", c.getNom());
		//menu.add(inscriptionEquipe(c));
	//	menu.add(inscriptionPersonne(c));
//		menu.add(supprimerCompetition());
		menu.addBack("r");
		return menu;
	}
	
	private Option inscriptionEquipe(Equipe e) {
		return new Option("Inscription Equipe", "e", () -> {
					selectEquipe();
			}
		);
	}
	
	private List<Equipe> selectEquipe()
	{
		return new List<Equipe>("Selectionner une equipe", "s",
				() -> new ArrayList<>(Inscriptions.getInscriptions().getEquipes()),
				(element) -> inscriptionEquipe(element) );
	}
	
	private Option listCompetitions() {
		return new Option("Liste des competitions", "l", () -> { System.out.println(Inscriptions.getInscriptions().getCompetitions());});
	}

	
	private Option creerEquipe() { 
		return new Option("Creer un candidat equipe", "e", () ->  { 
			Scanner sc = new Scanner(System.in);
			String nom = sc.next();
			Inscriptions.getInscriptions().createEquipe(nom);
			}
		);
	}

	private Option creerPersonne() {
		
		return new Option("Creer un candidat personne", "p", () -> {
			Scanner sc = new Scanner(System.in);
			String nom = sc.next();
			String prenom = sc.next();
			String mail = sc.next();
			Inscriptions.getInscriptions().createPersonne(nom, prenom, mail);
			}
		);
	}
	
	
	
	private Menu menuEquipe() {
		Menu menu = new Menu ("Equipe", "e");
		menu.add(creerEquipe());
		menu.add(ListEquipe());
		menu.add(selectionEquipe());
		menu.addBack("r");
		return menu;
	}
	
	private Option ListEquipe() {
		return new Option("Liste des equipes", "l", () -> { System.out.println(Inscriptions.getInscriptions().getEquipes());});
	}
	
	private Option ListPersonne() {
		return new Option("Liste des personnes", "l", () -> { System.out.println(Inscriptions.getInscriptions().getPersonnes());});
	}
	
	private List<Equipe> selectionEquipe()
	{
		return new List<Equipe>("Selectionner une equipe", "s",
				() -> new ArrayList<>(Inscriptions.getInscriptions().getEquipes()),
				(element) -> menuSelectEquipe(element)
				);
	}
	
	private Menu menuSelectEquipe(Equipe e) {
		Menu menu = new Menu ("Equipe: ", e.getNom());
		menu.add(listMembres(e));
		menu.add(supprimerEquipe(e));
		menu.addBack("r");
		return menu;
	}
	
	private Option supprimerEquipe(Equipe e) {
		return new Option ("Supprimer cette equipe", "s", () -> { e.delete();}); 
	}
	
	private Option listMembres(Equipe e) {
		return new Option ("Liste des membres", "m", () ->  { System.out.println(e.getMembres());});
	}
	
	private Menu menuPersonne() {
		Menu menu = new Menu ("Personne", "p");
		menu.add(creerPersonne());
		menu.add(ListPersonne());
		menu.add(selectionPersonne());
		menu.addBack("r");
		return menu;
	}
	
	private List<Personne> selectionPersonne()
	{
		return new List<Personne>("Selectionner une personne", "s",
				() -> new ArrayList<>(Inscriptions.getInscriptions().getPersonnes()),
				(element) -> menuSelectPersonne(element)
				);
	}
	
	private Menu menuSelectPersonne(Personne p) {
		Menu menu = new Menu (p.getPrenom() + " " + p.getNom());
		menu.add(modifNom(p));
		menu.add(modifPrenom(p));
		menu.add(modifMail(p));
		menu.add(supprimer(p));
		menu.addBack("r");
		return menu;
	}
	
	private Option modifNom(Personne p) {
		return new Option ("Modifier le nom", "n", () -> { String nvxNom = InOut.getString("Entrer le nouveau nom "); 
		p.setNom(nvxNom);
		}
		);
	}

	private Option modifPrenom(Personne p) {
		return new Option ("Modifier le prenom", "p", () -> { String nvxPrenom = InOut.getString("Entrer le nouveau prenom"); 
		p.setPrenom(nvxPrenom);
		}
		);
	}
	
	private Option modifMail(Personne p) {
		return new Option ("Modifier l'adresse mail", "m", () -> { String nvxMail = InOut.getString("Entrer le nouveau mail");
		p.setMail(nvxMail);
		}
		);
	} 
	
	private Option supprimer(Personne p) {
		return new Option ("Supprimer cette personne", "s", () -> { p.delete();});
	}
	
	
	
//	private Option selectPersonne() {
//		
//	}
//	
//	private Option selectEquipe() {
//		
//	}
//	
//	private Option selectCompetition() {
//		
//	}
//	

	public static void main(String[] args)
	{
		MainUser menu_user = new MainUser();
		menu_user.start();
		
	}
}
