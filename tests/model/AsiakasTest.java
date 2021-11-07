package simu.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;

import simu.framework.Trace;
import simu.framework.Trace.Level;

/**
 * Testataan asiakas-luokan metodeja.
 * 
 * @author Otso Poussa, Tanja Pyykönen ja Tatu Talvikko
 * @version 1.0
 */
class AsiakasTest {

	/**
	 * Asiakas-olio testauksia varten.
	 */
	private Asiakas a;

	/**
	 * Alustajametodi. <b>Trace</b> pitää olla asetettuna jotta testit voivat suorittaa.
	 */
	@BeforeAll
	public static void alustus() {
		Trace.setTraceLevel(Level.INFO);
	}

	/**
	 * Jokaiselle testille luodaan uusi <b>asiakas</b>-olio.
	 */
	@BeforeEach
	public void asiakasLuonti() {
		a = new Asiakas(null);
	}

	/**
	 * Tässä testataan <b>Asiakas</b>-luokan konstruktoria ja sen asettamia arvoja.
	 * Huom. osa testeista on assertNotNull, koska jos ajaa kaikki <b>tests</b>
	 * kansion testit niin asiakkaan id ja saapumisaika voi vaihdella.
	 */
	@Test
	@DisplayName("Asiakas(): Testataan onko constructorissa annetut arvot asetettu")
	void testAsiakas() {
		Asiakas asiakas = new Asiakas(null);
		assertEquals(0, asiakas.getKaydytKyydit(), "Kaydyt kyydit pitäisi olla 0 alustus vaiheessa");
		// Asiakkaan Id:tä tarkistetaan not nullilla, siltä varalta jos ajaa kaikki
		// testit niin sitä on vaikea arvioida mikä sen id on
		assertNotNull(asiakas.getId(), "Id:n pitäisi olla asetettu");
		// Sama syy kuin id
		assertNotNull(asiakas.getSaapumisaika(), "Saapumisaika ei ole asetettu");
		assertNotNull(a.getSateenToleranssi());
		assertNotNull(a.getHaluttuKyytiLkm());
		assertFalse(asiakas.isLahtiAikaisinSade());
		assertFalse(asiakas.isLahtiAikaisinRuuhka());
	}

	/**
	 * Tässä testataan asettuukko asiakkaalle annettu <b>poistumisaika</b>.
	 */
	@Test
	@DisplayName("testGetPoistumisaika(): Toimiiko set ja get?")
	void testGetPoistumisaika() {
		a.setPoistumisaika(1413.0);
		assertEquals(1413.0, a.getPoistumisaika(), "Poistumisaika asetettu väärin");
	}

	/**
	 * Tässä testataan asettuuko asiakkaalle annettu <b>saapumisaika</b>
	 */
	@Test
	@DisplayName("testGetSaapumisaika(): Toimiiko set ja get?")
	void testGetSaapumisaika() {
		a.setSaapumisaika(432.0);
		assertEquals(432.0, a.getSaapumisaika(), "Saapumisaika asetettu väärin");
	}

	/**
	 * Tässä testataan onko <b>Asiakaalla id:tä</b>. Huom. assertNotNull.
	 */
	@Test
	@DisplayName("getId(): Asettuuko id oikein?")
	void testGetId() {
		assertNotNull(a.getId(), "ID ei asetu oikein");
	}

	/**
	 * Testataan onko <b>asiakkaalla</b> asettunut <b>haluttuKyytiLkm</b> Tätäkään
	 * ei voi testata kunnolla koska <b>haluttuKyytiLkm</b> tulee <b>Uniform</b>
	 * jakauman samplesta.
	 */
	@Test
	@DisplayName("getHaluttuKyytiLkm(): Asetetaanko haluttujen kyytien määrä oikein")
	void testGetHaluttuKyytiLkm() {
		assertNotNull(a.getHaluttuKyytiLkm(), "Haluttujen kyytien määrä ei ole asetettu oikein.");
	}

	/**
	 * Testataan asettuuko annettu arvo muuttujaan <b>haluttuKyytiLkm</b>
	 */
	@Test
	@DisplayName("setHaluttuKyytiLkm(): Testataah asettaako set arvon oikein")
	void testSetHaluttuKyytiLkm() {
		a.setHaluttuKyytiLkm(17);
		assertNotEquals(17, a.getHaluttuKyytiLkm(), "luku yli rajan(onko rajaa?)");

		a.setHaluttuKyytiLkm(8);
		assertNotNull(a.getHaluttuKyytiLkm(), "KyytienLkm ei asetu annettuun arvoon");
	}

	/**
	 * Testataan korottuuko <b>kaytettyRaha</b> muuttuja, kun kutsutaan
	 * <b>maksa()</b> metodia.
	 */
	@Test
	@DisplayName("maksa(): Suureneeko kaytettyRaha summa")
	void testMaksa() {
		a.maksa(5);
		assertEquals(5, a.getKaytettyRaha(), "Käytetty raha ei vaihdu");

		a.maksa(5);
		a.maksa(5);
		a.maksa(5);
		assertEquals(20, a.getKaytettyRaha(), "Käytetty raha ei korotu");
	}

	/**
	 * Testataan korottuuko <b>kaydytKyydit</b> muuttuja, kun kutsutaan
	 * <b>korotaKyyteja()</b> metodia.
	 */
	@Test
	@DisplayName("korotaKyyteja(): Testataan korottuuko kaydytKyydit")
	void testKorotaKyyteja() {
		a.korotaKyyteja();
		assertEquals(1, a.getKaydytKyydit(), "Kyytien korotus ei toimi");
	}

	/**
	 * Testataan toimiiko <b>onValmis()</b> metodi oikein.
	 */
	@Test
	@DisplayName("onValmis(): Palauttaako onValmis oikean boolean arvon?")
	void testOnValmis() {
		// Asiakas ei ole käynyt kaikissa laitteissa, joten ei ole valmis
		assertFalse(a.onValmis(), "Asiakkaan ei pitäisi olla valmis");

		// Asetetaan halutut laitteiden määrä ja korotetaan käynnit
		a.setHaluttuKyytiLkm(16);
		for (int i = 0; i < a.getHaluttuKyytiLkm(); i++) {
			a.korotaKyyteja();
		}

		// Asiakkaan pitäisi olla valmis nyt
		assertTrue(a.onValmis(), "Asiakkaan pitäisi olla valmis");
	}

	/**
	 * Testaan asetaako <b>lahtiAikaisinSade()</b> metodi <b>isLahtiAikaisinSade</b>
	 * muuttujan trueksi.
	 */
	@Test
	@DisplayName("lahtiAikaisinSade(): Testataan asettuuko lahtiAikaisinSade trueksi?")
	void testLahtiAikaisinSade() {
		assertFalse(a.isLahtiAikaisinSade(), "Pitäisi olla false");

		a.lahtiAikaisinSade();

		assertTrue(a.isLahtiAikaisinSade(), "lahtiAikaisinSade pitäisi olla true");

	}

	/**
	 * Testaan asetaako <b>lahtiAikaisinRuuhka()</b> metodi
	 * <b>isLahtiAikaisinRuuhka</b> muuttujan trueksi.
	 */
	@Test
	@DisplayName("lahtiAikaisinRuuhka(): Testataan asettuuko lahtiAikaisinRuuhka trueksi")
	void testLahtiAikaisinRuuhka() {
		assertFalse(a.isLahtiAikaisinRuuhka(), "Pitäisi olla false");

		a.lahtiAikaisinRuuhka();

		assertTrue(a.isLahtiAikaisinRuuhka(), "lahtiAikaisinRuuhka pitäisi olla true");
	}

	/**
	 * Testataan onko <b>sateenTolerannsi</b> asetettu constructorissa Tämäkin
	 * muuttuja on saanut arvona <b>Uniform</b> jakauman samplesta, joten tarkan
	 * arvon testaus olisi hankalaa.
	 */
	@Test
	@DisplayName("getSateenToleranssi(): Testataan onko luku asetettu")
	void testGetSateenToleranssi() {
		assertNotNull(a.getSateenToleranssi(), "Toleranssi on null");
	}
}