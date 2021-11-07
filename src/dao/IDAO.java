package dao;

import simu.model.Asiakas;
import simu.model.OmaMoottori;
import simu.model.Palvelupiste;

/**
 * Rajapinta, joka määrittelee metodit tietokannasta hakemiseen, tietokantaan
 * tallentamiseen ja tietokannasta poistoon.
 * 
 * @author Otso Poussa, Tanja Pyykönen ja Tatu Talvikko
 * @version 1.0
 */
public interface IDAO {

	/**
	 * Tallenna <b>asiakas</b>-olio tietokantaan.
	 * 
	 * @param asiakas Tallennettava asiakas.
	 */
	public abstract void luoAsiakas(Asiakas asiakas);

	/**
	 * Tallenna <b>palvelupiste</b>-olio tietokantaan.
	 * 
	 * @param palvelupiste Tallennettava palvelupiste.
	 */
	public abstract void luoPalvelupiste(Palvelupiste palvelupiste);

	/**
	 * Tallenna <b>omaMoottori</b>-olio tietokantaan.
	 * 
	 * @param omaMoottori Tallennettava omaMoottori.
	 */
	public abstract void luoOmaMoottori(OmaMoottori omaMoottori);

	/**
	 * Lue asiakas-oliot listaan tietokannasta ja siirrä listalla olevat
	 * asiakas-oliot taulukkoon.
	 * 
	 * @return Asiakkaita sisältä taulukko.
	 */
	public abstract Asiakas[] lueAsiakkaat();

	/**
	 * Lue palvelupiste-oliot listaan tietokannasta ja siirrä listalla olevat
	 * palvelupiste oliot taulukkoon.
	 * 
	 * @return Palvelupisteitä sisältä taulukko.
	 */
	public abstract Palvelupiste[] luePalvelupisteet();

	/**
	 * Lue omaMoottori-oliot listaan tietokannasta ja siirrä listalla olevat
	 * omaMoottori oliot taulukkoon.
	 * 
	 * @return OmaMoottoreita sisältä taulukko.
	 */
	public abstract OmaMoottori[] lueOmaMoottorit();

	/**
	 * Hakee ja poistaa tietokannasta omaMoottori-olion, jonka <b>idSimulointi</b>
	 * on parametrina annettu simID. Poistaa myös automaattisesti kaikki asiakas-
	 * ja palvelupiste-oliot, jotka ovat liitoksissa kyseiseen omaMoottori-olioon.
	 * 
	 * @param simID Poistettavan simulointiajan id.
	 */
	public abstract void poista(int simID);
}