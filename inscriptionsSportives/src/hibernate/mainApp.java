package hibernate;

import menuUser.Dialogue;


/**
 ******* Classe principale *****
 ******************************* 
 * @author Ihcen
 *
 */
public class mainApp{ 
	
	/**
	 * Methode principale
	 * Point d'entree
	 * @param args
	 */
	public static void main(String[] args) {
	Passerelle.open();
	
	Dialogue.main();    
	
	Passerelle.close();
	System.out.println("Application fermée.");
	}

}