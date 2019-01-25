package testJunit;

import org.junit.*;

import static org.junit.Assert.*;

import java.time.LocalDate;

import inscriptions.Competition;
import inscriptions.Equipe;
import inscriptions.Inscriptions;
import inscriptions.Personne;

public class TestCompetition {

	private Inscriptions inscr = Inscriptions.getInscriptions();
	private Personne p1,p2;
	private Competition c1, c2;
	private Equipe e1, e2;
	private LocalDate datecloture = LocalDate.of(2021, 11, 11);
	private LocalDate datecloture2 = LocalDate.of(2018, 04, 29);
	
	@Before
	public void setUp() throws Exception {				
		c1 = inscr.createCompetition("Poker", datecloture, false);
		c2 = inscr.createCompetition("Billard", datecloture2, true);
		p1 = inscr.createPersonne("Robert","DeNiro","deniro.Robert@youtalkingtome.com");
		p2 = inscr.createPersonne("Marlon","Brando","marlon.brando@thgodfhater.com");
		e1 = inscr.createEquipe("Gangs");
		e2 = inscr.createEquipe("Bulls");
	}

	@Test
	public void testSetNom() {
		c2.setNom("Boxe");
		assertEquals("Boxe", c2.getNom());
	}

	@Test
	public void testGetNom() {
		assertEquals("Billard", c2.getNom());
	}
	
	@Test
	public void testEstEnEquipe() {
		assertTrue(c2.estEnEquipe());
		assertFalse(c1.estEnEquipe());	
	}
	
	@Test
	public void testGetDateCloture() {
		assertEquals(datecloture2,c2.getDateCloture());
	}
	
	@Test
	public void testSetDateCloture() {
		c2.setDateCloture(LocalDate.of(2021, 11, 11));
		assertEquals(datecloture,c2.getDateCloture());
	}
	
	@Test
	public void testInscriptionsOuvertes(){
		assertTrue(c1.inscriptionsOuvertes() && !c2.inscriptionsOuvertes());
	}
	
	@Test
	public void TestAddPersonne() {
		assertTrue(c1.add(p1));
		assertTrue(c1.getCandidats().contains(p1));
	}
	
	@Test
	public void TestAddEquipe() {
		assertFalse(c1.getCandidats().contains(e2));
	}
	
	@Test
	public void TestRemoveCandidat() {
		assertTrue(c1.add(p1));
		assertTrue(c1.remove(p1));
		assertFalse(c1.getCandidats().contains(p1));
	}
	
}
