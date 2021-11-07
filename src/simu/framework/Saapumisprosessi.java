package simu.framework;

import eduni.distributions.*;
import simu.model.TapahtumanTyyppi;

/**
 * Luo ja generoi saapumisprosesseja. Saapumisprosesseilla luodaan aina uusia
 * saapumisia systeemiin.
 * 
 * @author Otso Poussa, Tanja Pyykönen ja Tatu Talvikko
 * @version 1.0
 */
public class Saapumisprosessi {

	/**
	 * Generaattorilla tuotetaan arvoja tietyn jakauman pohjalta
	 * <code>eduni.distributions</code> pakkauksesta.
	 */
	private ContinuousGenerator generaattori;

	/**
	 * Tapahtumalista, johon saapumisprosessin tapahtumia lisätään.
	 */
	private Tapahtumalista tapahtumalista;

	/**
	 * Saapumisprosessin tapahtuman tyyppi.
	 */
	private TapahtumanTyyppi tyyppi;

	/**
	 * Luo ja alustaa saapumisprosessi olion annetuilla parametreilla.
	 * 
	 * @param generaattori Saapumisprosessin generaattori
	 *                     <code>eduni.distributions</code> pakkauksesta.
	 * @param lista        Tapahtumalista, johon saapumisprosessin tapahtumat
	 *                     lisätään.
	 * @param tyyppi       Saapumisprosessin tapahtuman tyyppi.
	 */
	public Saapumisprosessi(ContinuousGenerator generaattori, Tapahtumalista lista, TapahtumanTyyppi tyyppi) {
		this.generaattori = generaattori;
		this.tapahtumalista = lista;
		this.tyyppi = tyyppi;
	}

	/**
	 * Luo uuden tapahtuman generaattorin jakauman avulla ja lisää tapahtuman
	 * tapahtumalistaan.
	 */
	public void generoiSeuraava() {
		Tapahtuma t = new Tapahtuma(tyyppi, Kello.getInstance().getAika() + generaattori.sample());
		tapahtumalista.lisaa(t);
	}
}