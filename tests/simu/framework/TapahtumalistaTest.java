package simu.framework;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;

import simu.framework.Trace.Level;
import simu.model.TapahtumanTyyppi;

/**
 * Testataan <b>Tapahtumalista</b>-luokan metodeja.
 * 
 * @author Tatu Talvikko
 * @version 1.0
 */
class TapahtumalistaTest {

	/**
	 * <b>Tapahtumalista</b>-olio, johon tapahtumia lisätään.
	 */
	private Tapahtumalista tLista;

	/**
	 * Alustajametodi testeille, jossa asetetaan Trace-luokka konsolitulosteita
	 * varten.
	 */
	@BeforeAll
	public static void init() {
		Trace.setTraceLevel(Level.INFO);
	}

	/**
	 * Tässä metodissa testataan meneekö <b>Tapahtuma Tapahtumalistaan</b>.
	 */
	@Test
	@Order(1)
	@DisplayName("lisaa(): Toimiiko lisaaminen listaan")
	void testLisaa() {
		tLista = new Tapahtumalista();
		tLista.lisaa(new Tapahtuma(TapahtumanTyyppi.ARRSD, 15.00));

		assertEquals(15.00, tLista.getSeuraavanAika(), "Tapahtumaa ei lisätty listaan");
	}

	/**
	 * Tässä metodissa testaan poisuuko <b>Tapahtuma Tapahtumalista listasta</b>
	 */
	@Test
	@Order(2)
	@DisplayName("poista(): Poistuuko tapahtuma listasta?")
	void testPoista() {
		tLista = new Tapahtumalista();
		// Lisätään tapahtumat
		tLista.lisaa(new Tapahtuma(TapahtumanTyyppi.ARRSD, 15.00)); // Tapahtuma nro.1
		tLista.lisaa(new Tapahtuma(TapahtumanTyyppi.ARRSD, 25.00)); // Tapahtuma nro.2
		assertEquals(15.00, tLista.getSeuraavanAika(), "Tapahtumia ei lisätty");

		// Poistetaan 1. tapahtuma
		tLista.poista();
		assertEquals(25.00, tLista.getSeuraavanAika(), "Tapahtuman poistossa on ongelmia");
	}
}