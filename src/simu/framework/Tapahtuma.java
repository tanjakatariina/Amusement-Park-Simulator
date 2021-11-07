package simu.framework;

import simu.model.TapahtumanTyyppi;

/**
 * Pitää sisällään simulaatiossa käsiteltävät B-vaiheiden tapahtumien luonnit,
 * joilla on tapahtuman suoritusajankohta ja tapahtumaa kuvaava tyyppitunnus.
 * 
 * @author Otso Poussa, Tanja Pyykönen ja Tatu Talvikko
 * @version 1.0
 */
public class Tapahtuma implements Comparable<Tapahtuma> {

	/**
	 * Tapahtumaa kuvaava tyyppi.
	 */
	private TapahtumanTyyppi tyyppi;

	/**
	 * Tapahtuman suoritusajankohta.
	 */
	private double aika;

	/**
	 * Luo <b>tapahtuma</b>-olion, jolla on <b>tyyppi</b> ja tapahtuma <b>aika</b>,
	 * joka kuvaa milloin tapahtuma suoritetaan.
	 * 
	 * @param tyyppi Tapahtuman tyyppi.
	 * @param aika   Tapahtuman suoritusajankohta.
	 */
	public Tapahtuma(TapahtumanTyyppi tyyppi, double aika) {
		this.tyyppi = tyyppi;
		this.aika = aika;
	}

	/**
	 * Palauttaa <b>tapahtuman</b> tyypin.
	 * 
	 * @return Tapahtuman tyyppi.
	 */
	public TapahtumanTyyppi getTyyppi() {
		return tyyppi;
	}

	/**
	 * Palauttaa <b>tapahtuman</b> suoritusajankohdan.
	 * 
	 * @return Tapahtuman suoritusaika.
	 */
	public double getAika() {
		return aika;
	}

	/**
	 * Comparable-rajapinnan ylikirjoitettu metodi, jonka avulla voidaan verrata
	 * tapahtuma-olioita niiden <b>suoritusajankohtien</b> perusteella.
	 * 
	 * @param t Tapahtuma-olio, jota verrataan.
	 * @return Palauttaa -1, jos olio on vertailujärjestyksessä ennen parametrina
	 *         saatavaa olioata. Palauttaa 1, jos olio on järjestyksessä parametrina
	 *         saatavan olion jälkeen. Muulloin palautetaan 0.
	 */
	@Override
	public int compareTo(Tapahtuma t) {
		if (this.aika < t.aika) {
			return -1;
		} else if (this.aika > t.aika) {
			return 1;
		}
		return 0;
	}
}