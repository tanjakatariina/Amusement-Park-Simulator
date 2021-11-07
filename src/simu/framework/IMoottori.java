package simu.framework;

/**
 * Rajapinta, jolla asetetaan ja haetaan tietoja kontrollerin kautta.
 * 
 * @author Otso Poussa, Tanja Pyykönen ja Tatu Talvikko
 * @version 1.0
 */
public interface IMoottori {

	/**
	 * Asettaa simuloinnin keston.
	 * 
	 * @param aika Simuloinnin kesto.
	 */
	public abstract void setSimulointiaika(double aika);

	/**
	 * Asettaa asiakkaille <b>saapumisväliajan</b> keskiarvon, kuvaamaan kuinka
	 * usein asiakkaita saapuu sisäänkäynnin palvelupisteeseen.
	 * 
	 * @param aika Keskiarvo asiakkaiden saapumisille.
	 */
	public abstract void setSaapumisVali(double aika);

	/**
	 * Asettaa <b>palvelupisteiden</b> jonojen maksimi pituudet (kapasiteetit).
	 * 
	 * @param pituus Asetettava jonon kapasiteetti.
	 * @param piste  Palvelupiste, jolle asetetaan jonon kapasiteeti.
	 */
	public abstract void setJpituus(int pituus, int piste);

	/**
	 * Asettaa <b>palvelupisteiden</b> palveluiden hinnat.
	 * 
	 * @param hinta Asiakkaan maksettava euromäärä palvelusta.
	 * @param piste Palvelupiste, jolle hinta asetetaan.
	 */
	public abstract void setHinta(double hinta, int piste);

	/**
	 * Asettaa <b>palvelupisteiden</b> suosiot aurinkoisella säällä.
	 * 
	 * @param suosio Palvelupisteen suosio aurinkoisella säällä.
	 * @param piste  Palvelupiste, jolle asetetaan aurinkoisen sään suosio.
	 */
	public abstract void setSuosioA(int suosio, int piste);

	/**
	 * Asettaa <b>palvelupisteiden</b> suosiot sateella.
	 * 
	 * @param suosio Palvelupisteen suosio sateella.
	 * @param piste  Palvelupiste, jolle asetetaan sateen suosio.
	 */
	public abstract void setSuosioS(int suosio, int pPiste);

	/**
	 * Asettaa <b>palvelupisteille</b> yhden ajokerran maksimi asiakasmäärän
	 * (kapasiteetin).
	 * 
	 * @param kapasiteetti Palvelupisteen kapasiteetti.
	 * @param piste        Palvelupiste, jolle asetetaan laitteen kapasiteetti.
	 */
	public abstract void setlaiteKapasiteetti(int kapasiteetti, int piste);

	/**
	 * Asettaa maksimiajan <b>palvelupisteen</b> <code>Uniform</code> jakaumalle.
	 * 
	 * @param max   <code>Uniform</code> jakauman maksimiarvo.
	 * @param piste Palvelupiste, jolle asetetaan <code>Uniform</code> jakauman
	 *              maksimiarvo.
	 */
	public abstract void setGenerator(double max, int piste);

	/**
	 * Asettaa viiveen, jolla voidaan hidastaa ja nopeuttaa simuloinnin kulkua.
	 * 
	 * @param aika Viiveen arvo.
	 */
	public abstract void setViive(long aika);

	/**
	 * Palauttaa simuloinnille asetetun viiveen.
	 * 
	 * @return Viiveen arvo.
	 */
	public abstract long getViive();

	/**
	 * Määrittää minkä <b>palvelupisteen</b> jonoon <b>asiakas</b> piirretään
	 * <b>visualisoinnissa</b>.
	 * 
	 * @param piste Palvelupiste, jonka jonoon asikas lisätään.
	 */
	public abstract void visuaaliLisaaJonoon(String piste);

	/**
	 * Määrittää minkä <b>palvelupisteen</b> jonosta <b>asiakas</b> poistetaan
	 * <b>visualisoinnissa</b>.
	 * 
	 * @param piste Palvelupiste, jonka jonosta asiakas poistetaan.
	 */
	public abstract void visuaaliPoistaJonosta(String piste);

	/**
	 * Määrittää <b>asiakkaiden</b> visualisoinnin, kun asiakkaita palvellaan
	 * <b>palvelupisteellä</b>.
	 * 
	 * @param piste        Palvelupiste, jossa asiakasta visualisoidaan.
	 * @param asiakasMaara Palvelupisteen tämänhetkinen asiakasmäärä.
	 */
	public abstract void visuaaliPalvelussa(String piste, int asiakasMaara);

	/**
	 * Määrittelee visualisointiin simulointi valmis tekstin.
	 */
	public abstract void asetaSimValmisTeksti();

	/**
	 * Määrittelee visualisointiin aurinkoisen sään.
	 */
	public abstract void asetaAurinko();

	/**
	 * Määrittelee visualisointiin sateisen sään.
	 */
	public abstract void asetaSade();
}