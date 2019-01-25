package testJunit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import java.util.*;

import org.junit.*;

import inscriptions.Equipe;
import inscriptions.Inscriptions;
import inscriptions.Personne;

public class TestEquipe {
	
	private Inscriptions inscr = Inscriptions.getInscriptions();; 
	private Personne p1, p2;
	private Equipe equipe1;

	
	@Before // avec cette annotation, cette méthode sera appelée avant chaque test
	public void setUp() throws Exception {
		 p1 = inscr.createPersonne("Mlaghui", "Brahim", "bra@gmail.com");
		 p2 = inscr.createPersonne("Borgi", "Ihcen", "ihcen@gmail.com");
		 equipe1 = inscr.createEquipe("Equipe de Poker");
	}
	
	@Test
	public void testGetMembres() {
		Set<Personne> listeMembres = equipe1.getMembres();
		assertTrue(equipe1.add(p1));
		assertTrue(equipe1.add(p2));
		assertEquals(listeMembres, equipe1.getMembres());
	}
	
	
	@Test
	public void testAdd() {
		Set<Personne> listeMembres = equipe1.getMembres();
		assertTrue(equipe1.add(p1));
		assertTrue(listeMembres.contains(p1));
		assertFalse(listeMembres.contains(p2));
	}
	@Test
	public void testRemove() {
		Set<Personne> listeMembres = equipe1.getMembres();
		assertTrue(equipe1.add(p1));
		assertTrue(equipe1.remove(p1));
		assertFalse(listeMembres.contains(p1));
	}
}
