package view;

/**
 * Rajapinta, joka sisältää visualisointiin tarvittavat metodit. Metodeilla
 * haetaan visualisointiin liittyvät toimenpiteet.
 * 
 * @author Otso Poussa, Tanja Pyykönen ja Tatu Talvikko
 * @version 1.0
 */
public interface IVisualisointi {

	/**
	 * Tyhjentää visualisoinnin ja asettaa palvelupisteet paikoilleen.
	 */
	public abstract void tyhjennaNaytto();

	/**
	 * Asettaa kuvat palvelupisteille ja piirtää ne koordinaattien kohdille.
	 */
	public abstract void luoPalveluPisteet();

	/**
	 * Lisää asiakkaan palvelupisteen jonon perälle. <br>
	 * <br>
	 * Muuntaa parametrina saadun palvelupisteen nimen sitä vastaavaksi numeroksi
	 * <b>palvelupisteNumeroksi</b>-metodilla ja hakee jonon pituutta kuvaavan
	 * tekstin kordinaatit. Tarkistaa <b>jonoKordinaatit</b>-taulukosta, onko jonon
	 * viimeinen asiakas jonon X-askelilla alueen reunalla. Jos viimeinen asiakas on
	 * jonon reunalla, kordinaatteja siirretään eteenpäin Y-akselilla. <br>
	 * Jos jonossa on enemmän asiakkaita, kuin jonon alueelle mahtuu, jonon pituutta
	 * kuvaavaa numeron arvoa nostetaan yhdellä.
	 * 
	 * @param piste Palvelupisteen nimi.
	 */
	public abstract void visuaaliLisaaJonoon(String piste);

	/**
	 * Liikuttaa palvelupisteen jonoa eteenpäin poistamalla asiakkaan jonon perältä.
	 * <br>
	 * <br>
	 * Muuntaa parametrina saadun palvelupisteen nimen sitä vastaavaksi numeroksi
	 * <b>palvelupisteNumeroksi</b>-metodilla ja hakee jonon pituutta kuvaavan
	 * tekstin kordinaatit. Tarkistaa <b>jonoKordinaatit</b>-taulukosta, onko
	 * poistettava asiakas X-akselilla jonon reunalla. Jos Asiakas on jonon
	 * reunalla, Y-kordinaatteja siirretään ylöspäin.<br>
	 * Jos jonossa on enemmän asiakkaita, kuin jonon alueelle mahtuu, jonon pituutta
	 * kuvaavan numeron arvoa lasketaan yhdellä.
	 * 
	 * @param piste Palvelupisteen nimi
	 */
	public abstract void visuaaliPoistaJonosta(String piste);

	/**
	 * Esittää palvelupisteessä olevien asiakkaiden määrän numerona kanvaksella
	 * palvelupisteen vieressä. <br>
	 * Pyyhkii aiemman numeron ja kirjoittaa uuden numeron parametrin palvelupisteen
	 * kordinaatteihin.
	 * 
	 * @param piste        Palvelupisteen nimi.
	 * @param asiakasMaara Palveltavien asiakkaiden lukumäärä.
	 */
	public abstract void visuaaliPalvelussa(String piste, int asiakasMaara);

	/**
	 * Asettaa simulointiin tekstin, joka ilmoittaa simuloinnin päättyneen.
	 */
	public abstract void asetaSimValmisTeksti();

	/**
	 * Aseta simulointiin sateinen sää.
	 */
	public abstract void asetaSade();

	/**
	 * Aseta simulointiin aurinkoinen sää.
	 */
	public abstract void asetaAurinko();
}