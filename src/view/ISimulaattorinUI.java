package view;

/**
 * Rajapinta, jossa määritellään metodit, joilla viedään tietoja
 * käyttöliittymästä kontrollerille.
 * 
 * @author Otso Poussa, Tanja Pyykönen ja Tatu Talvikko
 * @version 1.0
 */
public interface ISimulaattorinUI {

	/**
	 * Palauttaa simuloinnin tämänhetkisen <b>viiveen</b>.
	 * 
	 * @return Simuloinnin <b>viive</b>.
	 */
	public abstract long getViive();

	/**
	 * Palauttaa simuloinnin keston, joka on muutettu sekunneiksi annetun
	 * simuloinnin keston yksiköstä.
	 * 
	 * @return Simuloinnin kesto.
	 */
	public abstract double getAika();

	/**
	 * Palauttaa sateen tarkasteluvälin keskiarvon, joka on muutettu sekunneiksi.
	 * 
	 * @return Sateen tarkasteluvälin keskiarvo.
	 */
	public abstract double getSaaTarkastelu();

	/**
	 * Palauttaa sateen todennäköisyyden. Jos sateen todennäköisyys on yhtäsuuri
	 * kuin nolla, niin kytke <b>sään tarkasteluvälin keskiarvo</b> <b>TextField</b>
	 * kontrolli pois päältä.
	 * 
	 * @return Sateen todennäköisyys.
	 */
	public abstract int getSadeTodNak();

	/**
	 * Palauttaa sisäänkäynti <b>palvelupisteen</b> asiakkaiden saapumisväliaikojen
	 * keskiarvon, joka on muunnettu minuuteista sekunneiksi.
	 * 
	 * @return Sisäänkäynnin asiakkaiden saapumisväliaikojen keskiarvo.
	 */
	public abstract double getSaapumisVali();

	/**
	 * Palauttaa sisäänkäynti <b>palvelupisteen</b> jonon maksimi pituuden
	 * (kapasiteetin).
	 * 
	 * @return Sisäänkäynnin jonon kapasiteetti.
	 */
	public abstract int getSKJpituus();

	/**
	 * Palauttaa grilli <b>palvelupisteen</b> jonon maksimi pituuden (kapasiteetin).
	 * 
	 * @return Grillin jonon kapasiteetti.
	 */
	public abstract int getGRJpituus();

	/**
	 * Palauttaa maailmanpyörä <b>palvelupisteen</b> jonon maksimi pituuden
	 * (kapasiteetin).
	 * 
	 * @return Maailmanpyörän jonon kapasiteetti.
	 */
	public abstract int getMPJpituus();

	/**
	 * Palauttaa vuoristorata <b>palvelupisteen</b> jonon maksimi pituuden
	 * (kapasiteetin).
	 * 
	 * @return Vuoristoradan jonon kapasiteetti.
	 */
	public abstract int getVRJpituus();

	/**
	 * Palauttaa karuselli <b>palvelupisteen</b> jonon maksimi pituuden
	 * (kapasiteetin).
	 * 
	 * @return Karusellin jonon kapasiteetti.
	 */
	public abstract int getKSJpituus();

	/**
	 * Palauttaa viikinkilaiva <b>palvelupisteen</b> jonon maksimi pituuden
	 * (kapasiteetin).
	 * 
	 * @return Viikinkilaivan jonon kapasiteetti.
	 */
	public abstract int getVLJpituus();

	/**
	 * Palauttaa kummitusjuna <b>palvelupisteen</b> jonon maksimi pituuden
	 * (kapasiteetin).
	 * 
	 * @return Kummitusjunan jonon kapasiteetti.
	 */
	public abstract int getKJJpituus();

	/**
	 * Palauttaa sisäänkäynti <b>palvelupisteen</b> maksimi palveluajan, joka on
	 * muunnettu minuuteista sekunneiksi.
	 * 
	 * @return Sisäänkäynnin palveluajan maksimiaika.
	 */
	public abstract double getSKaikaMax();

	/**
	 * Palauttaa grilli <b>palvelupisteen</b> maksimi palveluajan, joka on muunnettu
	 * minuuteista sekunneiksi.
	 * 
	 * @return Grillin palveluajan maksimiaika.
	 */
	public abstract double getGRaikaMax();

	/**
	 * Palauttaa sisäänkäynti <b>palvelupisteen</b> hinnan, jonka <b>asiakas</b>
	 * maksaa palvelusta.
	 * 
	 * @return Sisäänkäynnin hinta.
	 */
	public abstract double getSKHinta();

	/**
	 * Palauttaa grilli <b>palvelupisteen</b> hinnan, jonka <b>asiakas</b> maksaa
	 * palvelusta.
	 * 
	 * @return Grillin hinta.
	 */
	public abstract double getGRHinta();

	/**
	 * Palauttaa maailmanpyörä <b>palvelupisteen</b> hinnan, jonka <b>asiakas</b>
	 * maksaa palvelusta.
	 * 
	 * @return Maailmanpyörän hinta.
	 */
	public abstract double getMPHinta();

	/**
	 * Palauttaa vuoristorata <b>palvelupisteen</b> hinnan, jonka <b>asiakas</b>
	 * maksaa palvelusta.
	 * 
	 * @return Vuoristoradan hinta.
	 */
	public abstract double getVRHinta();

	/**
	 * Palauttaa karuselli <b>palvelupisteen</b> hinnan, jonka <b>asiakas</b> maksaa
	 * palvelusta.
	 * 
	 * @return Karusellin hinta.
	 */
	public abstract double getKSHinta();

	/**
	 * Palauttaa viikinkilaiva <b>palvelupisteen</b> hinnan, jonka <b>asiakas</b>
	 * maksaa palvelusta.
	 * 
	 * @return Viikinkilaivan hinta.
	 */
	public abstract double getVLHinta();

	/**
	 * Palauttaa kummitusjuna <b>palvelupisteen</b> hinnan, jonka <b>asiakas</b>
	 * maksaa palvelusta.
	 * 
	 * @return Kummitusjunan hinta.
	 */
	public abstract double getKJHinta();

	/**
	 * Palauttaa grilli <b>palvelupisteen</b> suosion aurinkoisella säällä.
	 * Tarkistaa myös kaikkien palvelupisteiden suosioiden summan aurinkoisella
	 * säällä.
	 * 
	 * @return Grillin suosio aurinkoisella säällä.
	 */
	public abstract int getGRsuosioA();

	/**
	 * Palauttaa maailmanpyörä <b>palvelupisteen</b> suosion aurinkoisella säällä.
	 * 
	 * @return Maailmanpyörä suosio aurinkoisella säällä.
	 */
	public abstract int getMPsuosioA();

	/**
	 * Palauttaa vuoristorata <b>palvelupisteen</b> suosion aurinkoisella säällä.
	 * 
	 * @return Vuoristoradan suosio aurinkoisella säällä.
	 */
	public abstract int getVRsuosioA();

	/**
	 * Palauttaa karuselli <b>palvelupisteen</b> suosion aurinkoisella säällä.
	 * 
	 * @return Karusellin suosio aurinkoisella säällä.
	 */
	public abstract int getKSsuosioA();

	/**
	 * Palauttaa viikinkilaiva <b>palvelupisteen</b> suosion aurinkoisella säällä.
	 * 
	 * @return Viikinkilaivan suosio aurinkoisella säällä.
	 */
	public abstract int getVLsuosioA();

	/**
	 * Palauttaa kummitusjuna <b>palvelupisteen</b> suosion aurinkoisella säällä.
	 * 
	 * @return Kummitusjunan suosio aurinkoisella säällä.
	 */
	public abstract int getKJsuosioA();

	/**
	 * Palauttaa grilli <b>palvelupisteen</b> suosion sateella. Tarkistaa myös
	 * kaikkien palvelupisteiden suosioiden summan sateella.
	 * 
	 * @return Grillin suosio sateella.
	 */
	public abstract int getGRsuosioS();

	/**
	 * Palauttaa maailmanpyörä <b>palvelupisteen</b> suosion sateella.
	 * 
	 * @return Maailmanpyörän suosio sateella.
	 */
	public abstract int getMPsuosioS();

	/**
	 * Palauttaa vuoristorata <b>palvelupisteen</b> suosion sateella.
	 * 
	 * @return Vuoristoradan suosio sateella.
	 */
	public abstract int getVRsuosioS();

	/**
	 * Palauttaa karuselli <b>palvelupisteen</b> suosion sateella.
	 * 
	 * @return Karusellin suosio sateella.
	 */
	public abstract int getKSsuosioS();

	/**
	 * Palauttaa viikinkilaiva <b>palvelupisteen</b> suosion sateella.
	 * 
	 * @return Viikinkilaivan suosio sateella.
	 */
	public abstract int getVLsuosioS();

	/**
	 * Palauttaa kummitusjuna <b>palvelupisteen</b> suosion sateella.
	 * 
	 * @return Kummitusjunan suosio sateella.
	 */
	public abstract int getKJsuosioS();

	/**
	 * Palauttaa maailmanpyörä <b>palvelupisteen</b> ajokerran maksimi kapasiteetin.
	 * 
	 * @return Maailmanpyörän laitteen kapasiteetti.
	 */
	public abstract int getMPkapasiteetti();

	/**
	 * Palauttaa vuoristorata <b>palvelupisteen</b> ajokerran maksimi kapasiteetin.
	 * 
	 * @return Vuoristoradan laitteen kapasiteetti.
	 */
	public abstract int getVRkapasiteetti();

	/**
	 * Palauttaa karuselli <b>palvelupisteen</b> ajokerran maksimi kapasiteetin.
	 * 
	 * @return Karusellin laitteen kapasiteetti.
	 */
	public abstract int getKSkapasiteetti();

	/**
	 * Palauttaa viikinkilaiva <b>palvelupisteen</b> ajokerran maksimi kapasiteetin.
	 * 
	 * @return Viikinkilaivan laitteen kapasiteetti.
	 */
	public abstract int getVLkapasiteetti();

	/**
	 * Palauttaa kummitusjuna <b>palvelupisteen</b> ajokerran maksimi kapasiteetin.
	 * 
	 * @return Kummitusjunan laitteen kapasiteetti.
	 */
	public abstract int getKJkapasiteetti();

	/**
	 * Hakee simulaation visualisoinnin.
	 * 
	 * @return Visualisoinnin kanvas-olio.
	 */
	public IVisualisointi getVisualisointi();
}
