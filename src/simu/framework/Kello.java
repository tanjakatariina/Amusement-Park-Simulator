package simu.framework;

/**
 * Pitää huolen simuloinnin ajan asettamisesta ja tarvittavista ajan
 * formatoinneista ja muunnoksista.
 * 
 * @author Otso Poussa, Tanja Pyykönen ja Tatu Talvikko
 */
public class Kello {

	/**
	 * Simuloinnin aika.
	 */
	private double aika;

	/**
	 * Singletonin instanssi.
	 */
	private static Kello instanssi;

	/**
	 * Yksityinen konstruktori, joka alustaa <b>ajan</b> nollaksi.
	 */
	private Kello() {
		aika = 0;
	}

	/**
	 * Luo ja/tai palauttaa olemassa olevan singleton instanssin.
	 * 
	 * @return Kello-olion instanssi.
	 */
	public static Kello getInstance() {
		if (instanssi == null) {
			instanssi = new Kello();
		}
		return instanssi;
	}

	/**
	 * Asettaa simuloinnille ajan.
	 * 
	 * @param aika Asetettava aika.
	 */
	public void setAika(double aika) {
		this.aika = aika;
	}

	/**
	 * Palauttaa simuloinnin ajan.
	 * 
	 * @return Ajan arvo.
	 */
	public double getAika() {
		return aika;
	}

	/**
	 * Formatoi parametrina syötetyn sekunti <b>ajan</b> muotoon 0pv 00h 00min 00s.
	 * 
	 * @param aika sekunteina, joka halutaan formatoida.
	 * @return Formatoitu aika muodossa: 0pv 00h 00min 00s.
	 */
	public String formatoiKello(double aika) {
		int aikaMuunnos = (int) Math.round(aika);
		int pv = aikaMuunnos / 86400;
		int h = (aikaMuunnos % 86400) / 3600;
		int min = ((aikaMuunnos % 86400) % 3600) / 60;
		int s = ((aikaMuunnos % 86400) % 3600) % 60;

		String hFormat = String.format("%02d", h);
		String minFormat = String.format("%02d", min);
		String sFormat = String.format("%02d", s);

		return pv + "pv " + hFormat + "h " + minFormat + "min " + sFormat + "s";
	}

	/**
	 * Muuttaa parametrina syötetyn sekunti <b>ajan</b> minuuteiksi.
	 * 
	 * @param aika sekunteina, joka halutaan formatoida.
	 * @return Formatoitu aika minuutteina.
	 */
	public Double formatoiKelloMin(double aika) {
		return aika / 60;
	}
}