package simu.model;

/**
 * Enumi, joka sisältää B-vaiheen <b>tapahtumien tapahtumatyypit</b>.
 * 
 * @author Otso Poussa, Tanja Pyykönen ja Tatu Talvikko
 * @version 1.0
 */
public enum TapahtumanTyyppi {
	/**
	 * Saapuminen sisäänkäynnin <b>palvelupisteelle</b>.
	 */
	ARRSK, 
	
	/**
	 * Poistuminen sisäänkäynnin <b>palvelupisteeltä</b>.
	 */
	DEPSK, 
	
	/**
	 * Poistuminen grillin <b>palvelupisteeltä</b>.
	 */
	DEPGR, 
	
	/**
	 * Poistuminen maailmanpyörän <b>palvelupisteeltä</b>.
	 */
	DEPMP, 
	
	/**
	 * Poistuminen vuoristoradan <b>palvelupisteeltä</b>.
	 */
	DEPVR, 
	
	/**
	 * Poistuminen karusellin <b>palvelupisteeltä</b>.
	 */
	DEPKS, 
	
	/**
	 * Poistuminen viikinkilaivan <b>palvelupisteeltä</b>.
	 */
	DEPVL, 
	
	/**
	 * Poistuminen kummitusjunan <b>palvelupisteeltä</b>.
	 */
	DEPKJ, 
	
	/**
	 * Sateen saapuminen (alkaminen).
	 */
	ARRSD, 
	
	/**
	 * Sateen poistuminen (loppuminen).
	 */
	DEPSD
}