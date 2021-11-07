package simu.framework;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import simu.model.TapahtumanTyyppi;

/**
 * Testaan Tapahtuma-luokkaa ja sen metodeja. Tarkoituksena on varmistaa että
 * konstruktori asettaa annetut parametrit paikoilleen luokan muuttujiin.
 * 
 * @author Tatu Talvikko
 * @version 1.0
 */
class TapahtumaTest {

	/**
	 * Testattava tapahtuma.
	 */
	private Tapahtuma t;

	/**
	 * Testataan onko <b>TapahtumanTyyppi</b> asetettu konstruktorissa ja
	 * palauttaako get-metodi sen.
	 */
	@Test
	@DisplayName("getTyyppi(): Onko tapahtuman tyyppi asetettu oikein?")
	void testGetTyyppi() {
		t = new Tapahtuma(TapahtumanTyyppi.ARRSD, 35.00);
		assertEquals(TapahtumanTyyppi.ARRSD, t.getTyyppi(), "Tapahtuman tyyppi ei ole asetettu.");
	}

	/**
	 * Testataan onko <b>aika</b> muuttuja asetettu konstruktorissa ja palauttaako
	 * se get-metodi sen.
	 */
	@Test
	@DisplayName("getAika(): Onko tapahtuman aika asetettu oikein?")
	void testGetAika() {
		t = new Tapahtuma(TapahtumanTyyppi.ARRSD, 35.00);
		assertEquals(35.00, t.getAika(), "Aika ei ole asetettu oikein.");
	}
}