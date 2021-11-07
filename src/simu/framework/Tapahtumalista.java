package simu.framework;

import java.util.PriorityQueue;

/**
 * Sisältää PriorityQueue-listan, johon lisätään <b>tapahtumia</b>.
 * Tapahtumalista pitää huolen tapahtuminen lisäämisestä listaan ja tapahtumien
 * poistamisesta listalta oikeassa järjestyksessä.
 * 
 * @author Otso Poussa, Tanja Pyykönen ja Tatu Talvikko
 * @version 1.0
 */
public class Tapahtumalista {
	/**
	 * PriorityQueue-lista, joka pitää sisällään <b>tapahtumia</b>.
	 */
	private PriorityQueue<Tapahtuma> lista = new PriorityQueue<Tapahtuma>();

	/**
	 * Tyhjä konstrukotri, jolla luodaan uusi <b>tapahtumalista</b> olio.
	 */
	public Tapahtumalista() {}

	/**
	 * Hakee ja poistaa listan lopusta <b>tapahtuman</b>.
	 * 
	 * @return Listan remove() metodikutsu.
	 */
	public Tapahtuma poista() {
		//Trace.out(Trace.Level.INFO, "Tapahtumalistasta poisto " + lista.peek().getTyyppi() + " " + lista.peek().getAika());
		return lista.remove();
	}

	/**
	 * Lisää tapahtuman listan loppuun.
	 * 
	 * @param tapahtuma Lisättävä tapahtuma.
	 */
	public void lisaa(Tapahtuma tapahtuma) {
		//Trace.out(Trace.Level.INFO, "Tapahtumalistaan lisätään uusi " + tapahtuma.getTyyppi() + " " + tapahtuma.getAika());
		lista.add(tapahtuma);
	}

	/**
	 * Hakee seuraavaksi vuorossa suoritettavan <b>tapahtuman</b> suoritusajankohdan.
	 * 
	 * @return Listan seuraavaksi suoritettavan tapahtuman ajankohta.
	 */
	public double getSeuraavanAika() {
		return lista.peek().getAika();
	}
}