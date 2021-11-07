package controller;

import dao.IDAO;
import simu.framework.IMoottori;
import simu.model.Asiakas;
import simu.model.OmaMoottori;
import simu.model.Palvelupiste;

/**
 * Rajapinta, joka sisältää metodien rungot tietojen saamista ja viemistä varten
 * käyttöliittymän ja mallin välillä.
 * 
 * @author Otso Poussa, Tanja Pyykönen ja Tatu Talvikko
 * @version 1.0
 */
public interface IKontrolleri {

	/**
	 * Käynnistää moottori säikeen.
	 */
	public abstract void kaynnistaSimulointi();

	/**
	 * Hakee syötetiedot <b>käyttöliittymästä</b> ja asettaa ne <b>moottorille</b>.
	 */
	public abstract void asetaSyotteet();

	/**
	 * Hakee ja asettaa moottorisäikeelle viiveen, joka nopeuttaa moottorisäikeen
	 * suoritusta.
	 */
	public abstract void nopeuta();

	/**
	 * Hakee ja asettaa moottorisäikeelle viiveen, joka hidastaa moottorisäikeen
	 * suoritusta.
	 */
	public abstract void hidasta();

	/**
	 * Hakee <b>käyttöliittymässä</b> asetetut sisäänkäynti <b>palvelupisteen</b>
	 * syötteet ja asettaa ne <b>moottorille</b>.
	 * 
	 * @param moottori Moottori-olio, jolle syötteet asetetaan.
	 */
	public abstract void syotteetSK(IMoottori moottori);

	/**
	 * Hakee <b>käyttöliittymässä</b> asetetut grilli <b>palvelupisteen</b> syötteet
	 * ja asettaa ne <b>moottorille</b>.
	 * 
	 * @param moottori Moottori-olio, jolle syötteet asetetaan.
	 */
	public abstract void syotteetGR(IMoottori moottori);

	/**
	 * Hakee <b>käyttöliittymässä</b> asetetut maailmanpyörä <b>palvelupisteen</b>
	 * syötteet ja asettaa ne <b>moottorille</b>.
	 * 
	 * @param moottori Moottori-olio, jolle syötteet asetetaan.
	 */
	public abstract void syotteetMP(IMoottori moottori);

	/**
	 * Hakee <b>käyttöliittymässä</b> asetetut vuoristorata <b>palvelupisteen</b>
	 * syötteet ja asettaa ne <b>moottorille</b>.
	 * 
	 * @param moottori Moottori-olio, jolle syötteet asetetaan.
	 */
	public abstract void syotteetVR(IMoottori moottori);

	/**
	 * Hakee <b>käyttöliittymässä</b> asetetut karuselli <b>palvelupisteen</b>
	 * syötteet ja asettaa ne <b>moottorille</b>.
	 * 
	 * @param moottori Moottori-olio, jolle syötteet asetetaan.
	 */
	public abstract void syotteetKS(IMoottori moottori);

	/**
	 * Hakee <b>käyttöliittymässä</b> asetetut viikinkilaiva <b>palvelupisteen</b>
	 * syötteet ja asettaa ne <b>moottorille</b>.
	 * 
	 * @param moottori Moottori-olio, jolle syötteet asetetaan.
	 */
	public abstract void syotteetVL(IMoottori moottori);

	/**
	 * Hakee <b>käyttöliittymässä</b> asetetut kummitusjuna <b>palvelupisteen</b>
	 * syötteet ja asettaa ne <b>moottorille</b>.
	 * 
	 * @param moottori Moottori-olio, jolle syötteet asetetaan.
	 */
	public abstract void syotteetKJ(IMoottori moottori);

	/**
	 * Hakee visualisoinnin <b>käyttöliittymästä</b>, johon visualisointi
	 * <b>asiakkaan</b> jonoon lisäyksestä toteutetaan.
	 * 
	 * @param piste <b>Palvelupiste</b> johon visualisointi toteutetaan.
	 */
	public abstract void visuaaliLisaaJonoon(String piste);

	/**
	 * Hakee visualisoinnin <b>käyttöliittymästä</b>, johon visualisointi
	 * <b>asiakkaan</b> poistamisesta jonosta toteutetaan.
	 * 
	 * @param piste <b>Palvelupiste</b> johon visualisointi toteutetaan.
	 */
	public abstract void visuaaliPoistaJonosta(String piste);

	/**
	 * Hakee visualisoinnin <b>käyttöliittymästä</b>, johon visualisointi
	 * <b>asiakkaan</b> palvelussa olosta toteutetaan.
	 * 
	 * @param piste        <b>Palvelupiste</b> johon visualisointi toteutetaan.
	 * @param asiakasMaara <b>Palvelupisteen</b> asiakasmäärä palvelussa.
	 */
	public abstract void visuaaliPalvelussa(String piste, int asiakasMaara);

	/**
	 * Linkittää simulointi valmis tekstin asetuksen visualisoinnin ja moottorin
	 * välillä.
	 */
	public abstract void asetaSimValmisTeksti();

	/**
	 * Linkitä aurinkoisen sään asetus visualisoinnin ja moottorin välillä.
	 */
	public abstract void asetaAurinko();

	/**
	 * Linkitä sateisen sään asetus visualisoinnin ja moottorin välillä.
	 */
	public abstract void asetaSade();

	/**
	 * Palauttaa kontrollissa luodun DAO-olion tietokantaan tallentamista ja
	 * tietokannasta hakemista varten.
	 * 
	 * @return <b>DAO</b>-olio.
	 */
	public abstract IDAO haeDao();

	/**
	 * Palauttaa tietokannasta luetut <b>omaMoottori</b>-oliot taulukkona.
	 * 
	 * @return Taulukko <b>omaMoottori</b>-olioita.
	 */
	public abstract OmaMoottori[] haeOmaMoottorit();

	/**
	 * Palauttaa tietokannasta luetut <b>asiakas</b>-oliot taulukkona.
	 * 
	 * @return Taulukko <b>asiakas</b>-olioita.
	 */
	public abstract Asiakas[] haeAsiakkaat();

	/**
	 * Palauttaa tietokannasta luetut <b>palvelupiste</b>-oliot taulukkona.
	 * 
	 * @return Taulukko <b>palvelupiste</b>-olioita.
	 */
	public abstract Palvelupiste[] haePalvelupisteet();

	/**
	 * Poistaa tietokannasta simulaatioajoon liittyvät <b>omaMoottori</b>-olion,
	 * <b>asiakas</b>-oliot ja <b>palvelupiste</b>-oliot simulointiajon id:n avulla.
	 * 
	 * @param simID Simulointiajon id-tunniste.
	 */
	public abstract void poista(int simID);
}