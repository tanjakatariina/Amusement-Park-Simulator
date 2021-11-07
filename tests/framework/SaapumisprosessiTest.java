package simu.framework;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;

import eduni.distributions.Negexp;
import simu.framework.Trace.Level;
import simu.model.TapahtumanTyyppi;

/**
 * Ttestataan <b>Saapumisprosessi</b>-luokan metodeja.
 * 
 * @author Tatu Talvikko
 * @version 1.0
 */
class SaapumisprosessiTest {

	/**
	 * Saapumisprosessi, jolla generoidaan uusia saapumisia.
	 */
	private Saapumisprosessi saapumisprosessiX;

	/**
	 * Tapahtumalista, johon saapumisprosessin tuottamat tapahtumat lisätään.
	 */
	private Tapahtumalista tapahtumalista;

	/**
	 * Testataanko toimiiko <b>Saapumisprosessin</b> konstruktori.
	 */
	@Test
	@Order(1)
	@DisplayName("Saapumisprosessi(): Toimiiko konstruktori?")
	void testSaapumisprosessi() {
		Negexp generaattori = new Negexp(1, 5);
		tapahtumalista = new Tapahtumalista();
		saapumisprosessiX = new Saapumisprosessi(generaattori, tapahtumalista, TapahtumanTyyppi.ARRSD);

		assertNotNull(saapumisprosessiX, "Konstruktorin asettaminen ei onnistunut.");
	}

	/**
	 * Testataan toimiiko metodi <b>generoiSeuraava()</b>.
	 */
	@Test
	@Order(2)
	@DisplayName("generoiSeuraava(): Testataan luoko metodi tapahtuman ja viekö se sen tapahtumalistaan")
	void testGeneroiSeuraava() {
		Trace.setTraceLevel(Level.INFO);
		Negexp generaattori = new Negexp(1, 5);
		tapahtumalista = new Tapahtumalista();
		saapumisprosessiX = new Saapumisprosessi(generaattori, tapahtumalista, TapahtumanTyyppi.ARRSD);

		Kello.getInstance().setAika(123.0);
		saapumisprosessiX.generoiSeuraava();
		assertNotNull(tapahtumalista.getSeuraavanAika(), "tapahtumaa ei lisätty tapahtumalistaan");
	}
}