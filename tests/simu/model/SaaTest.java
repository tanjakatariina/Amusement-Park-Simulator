package simu.model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import simu.framework.Kello;

/**
 * Testaa <b>Saa</b>-Singletonia ja se metodeja.
 * 
 * @author Tatu Talvikko
 * @version 1.0
 */
class SaaTest {

	/**
	 * Tarkistetaan toimiiko <b>eiSada()</b> ja <b>sataa()</b> metodit.
	 */
	@Test
	@DisplayName("onkoSadeKaynnissa(): Tarkistaa sateen tilan. (sataako vai ei)")
	public void testOnkoSadeKaynnissa() {
		System.out.println();
		// Nyt ei pitäisi sataa.
		Saa.getInstance().eiSada();
		assertEquals(false, Saa.getInstance().onkoSadeKaynnissa(), "Sään tarkastus ei toimi.");

		// Nyt pitäisi sataa
		Saa.getInstance().sataa();
		assertEquals(true, Saa.getInstance().onkoSadeKaynnissa(), "Sään tarkastus ei toimi.");
	}

	/**
	 * Testataan asettuuko muuttujaan <b>tarkasteluvaliAika</b> arvoja.
	 */
	@Test
	@DisplayName("setTarkasteluvaliAika(): Toimiiko set ja get tarkasteluvaliAika muuttujalle.")
	public void testTarkasteluvaliAika() {
		Saa.getInstance().setTarkasteluvaliAika(666);
		assertEquals(666, Saa.getInstance().getTarkasteluvaliAika(), "Sään tarkasteluväliaika on väärä.");
	}

	/**
	 * Testataan saadaanko yhteys <b>Saa</b>-singleton luokkaan.
	 */
	@Test
	@DisplayName("setGetInstance(): Saadaanko Singletonin instannsi.")
	void testGetInstance() {
		assertNotNull(Saa.getInstance(), "Ei saada Singletonin instanssia");
	}

	/**
	 * Testataan toimiiko <b>sataako()</b> metodi oikein.
	 */
	@Test
	@DisplayName("setSataako(): Antaako metodi true ja false arvot oikein.")
	void testSataako() {
		// Todennäköisyys 0 -> ei pitäisi sataa
		Saa.getInstance().setSateenTodnak(0);
		for (int i = 0; i < 999; i++) {
			assertFalse(Saa.getInstance().sataako(), "ei pitäisi sataa");
		}

		// Todennäköisyys 100 -> pitäisi sataa
		Saa.getInstance().setSateenTodnak(100);
		for (int i = 0; i < 999; i++) {
			assertTrue(Saa.getInstance().sataako(), "pitäisi sataa");
		}
	}

	/**
	 * Testataan <b>set-</b> ja <b>getSateenLoppu</b> luokkia katsoen palauttaako
	 * eri arvon siitä mikä oli laitettu alustuksessa. Luokkaan on hankala testata
	 * sillä arvo tulee <b>Uniform</b> jakauman samplesta.
	 */
	@Test
	@DisplayName("setSetSateenLoppu(): Testataan asettuuko sateenLoppu muuttujaan arvo.")
	public void testSetSateenLoppu() {
		Kello.getInstance().setAika(870.0); // nykyinenAika metodissa
		Saa.getInstance().setSateenLoppu(76000.0); // simAika metodissa
		assertNotEquals(0.0, Saa.getInstance().getSateenLoppu(), "sateenLoppu muuttujan arvo ei muuttunut.");
	}

	/**
	 * Testataan korottaako metodi <b>sadeKerrat</b> muuttujaa.
	 */
	@Test
	@DisplayName("korotaSateenKertoja(): Testataan sadeKerrat muuttujan korotusta.")
	void testKorotaSateenKertoja() {
		Saa.getInstance().korotaSateenKertoja();
		assertEquals(1, Saa.getInstance().getSadeKerrat(), "korotus ei toimi");

	}

	/**
	 * Testataan asettuuko muuttujaan <b>sateenTodnak</b> annettu arvo
	 */
	@Test
	@DisplayName("setSateenTodnak(): Testataan asettuuko sateenTodnak oikein.")
	void testSetSateenTodnak() {
		Saa.getInstance().setSateenTodnak(50);
		assertEquals(50, Saa.getInstance().getSateenTodnak(), "Asetus ei toimi");
	}

	/**
	 * Testataan onko <b>sateenLoppu</b> arvo eri kuin alustuksessa. Luokkaan on
	 * hankala testata koska metodi käyttää sateenLoppu arvoa, joka tulee
	 * <b>Uniform</b> jakauman samplesta.
	 */
	@Test
	@DisplayName("laskeSateenKokoKesto(): Testataan asettuuko sateenKokoKesto muuttujaan arvoa.")
	void testLaskeSateenKokoKesto() {
		Kello.getInstance().setAika(200);
		Saa.getInstance().setSateenLoppu(20000);
		Saa.getInstance().laskeSateenKokoKesto();

		Kello.getInstance().setAika(800);
		assertNotEquals(0.0, Saa.getInstance().getSateenKokoKesto(), "tarkista");
	}

	/**
	 * Testataan asettuuko muuttujaan <b>tarkasteluvaliAika</b> annettua arvoa.
	 */
	@Test
	@DisplayName("setTarkasteluvaliAika(): Testataan asettuuko tarkasteluvaliAika muuttuja oikein")
	void testSetTarkasteluvaliAika() {
		Saa.getInstance().setTarkasteluvaliAika(1000);
		assertEquals(1000, Saa.getInstance().getTarkasteluvaliAika(), "asetus ei toimi");
	}

	/**
	 * Testataan asettuuko <b>onkoSade</b> boolean oikeaan arvoon.
	 */
	@Test
	@DisplayName("eiSada(): Testataan muuttuko onkoSade falseksi.")
	void testEiSada() {
		Saa.getInstance().eiSada();
		assertFalse(Saa.getInstance().onkoSadeKaynnissa(), "Ei asetu falseksi");
	}

	/**
	 * Testataan asettuuko <b>onkoSade</b> boolean oikeaan arvoon.
	 */
	@Test
	@DisplayName("sataa(): Testataan muuttuko onkoSade trueksi.")
	void testSataa() {
		Saa.getInstance().sataa();
		assertTrue(Saa.getInstance().onkoSadeKaynnissa(), "Ei asetu trueksi");
	}
}