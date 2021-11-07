package simu.model;

import simu.framework.Kello;
import simu.framework.Trace;

import javax.persistence.*;

import org.hibernate.annotations.Type;

import eduni.distributions.Uniform;

/**
 * Sisältää yksittäisen asiakkaan huvipuistossa asioimiseen liittyvät tiedot.
 * Asiakas liikkuu huvipuiston systeemissä palvelupisteiden välillä.
 * 
 * @author Otso Poussa, Tanja Pyykönen ja Tatu Talvikko
 * @version 1.0
 */
@Entity
public class Asiakas implements Comparable<Asiakas> {
	/**
	 * Asiakkaan <b>tietokannan id</b>, joka toimii asiakastietueen yksilöllisenä
	 * pääavaimena.
	 */
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private int idTietokanta;

	/**
	 * Asiakkaan id numero.
	 */
	@Column
	private int id = 0;

	/**
	 * <b>omaMoottori</b>-olio, johon asiakas on liitoksissa.
	 */
	@ManyToOne
	@JoinColumn(name = "idSim")
	private OmaMoottori omaMoottori;

	/**
	 * Asiakkaan huvipuistossa käyttämä rahamäärä.
	 */
	@Column
	private double kaytettyRaha;

	/**
	 * Asiakkaan haluttujen kohteissa käymisien lukumäärä kertoo, kuinka monessa
	 * laitteessa asiakas pyrkii käymään huvipuistovierailunsa aikana. (Arvotaan
	 * <b>uniform</b>-jakaumalla.)
	 */
	@Column
	private int haluttuKyytiLkm;

	/**
	 * Asiakkaan käytyjen kyytien lukumäärä kertoo, kuinka monessa kohteessa asiakas
	 * on käynyt vierailunsa aikana.
	 */
	@Column
	private int kaydytKyydit;

	/**
	 * <b>Sateen toleranssi</b> on kokonaisluku 0-100 väliltä, joka kertoo kuinka
	 * suurella todennäköisyydellä asiakas lähtee puistosta sateen alkaessa.
	 * (Arvotaan <b>uniform</b>-jakaumalla.)
	 */
	@Column
	private int sateenToleranssi;

	/**
	 * Totuusarvo, joka kertoo lähtikö asiakas aikaisemmin huvipuistosta sateen
	 * alkamisen vuoksi.
	 */
	@Type(type = "true_false")
	@Column
	private boolean lahtiAikaisinSade;

	/**
	 * Totuusarvo, joka kertoo lähtikö asiakas huvipuistosta aikaisemmin ruuhkan
	 * vuoksi.
	 */
	@Type(type = "true_false")
	@Column
	private boolean lahtiAikaisinRuuhka;

	/**
	 * Asikkaan vietetty aika huvipuistossa.
	 */
	@Column
	private double vietettyAika = 0;

	/**
	 * Staattinen kokonaisluku, josta asetetaan asiakkaan <b>id</b>. Lukua
	 * korotetaan aina, kun se asetetaan uuden asiakkaan id:ksi.
	 */
	@Transient
	private static int i = 1;

	/**
	 * Aika milloin asiakas saapui huvipuistoon. Lasketaan <b>Kello</b>-singletonin
	 * avulla.
	 */
	@Transient
	private double saapumisaika;

	/**
	 * Aika milloin asiakas poistui huvipuistosta. Lasketaan
	 * <b>Kello</b>-singletonin avulla.
	 */
	@Transient
	private double poistumisaika;

	/**
	 * Tyhjä konstruktori DAO varten.
	 */
	public Asiakas() {
	}

	/**
	 * Luo <b>Asiakas</b>-olion alustaen sille kuuluvat muuttujat.
	 * 
	 * @param moottori Asiakkaaseen liittyvä <b>omaMoottori</b>-olio.
	 */
	public Asiakas(OmaMoottori moottori) {
		this.kaydytKyydit = 0;
		this.id = i++;
		this.saapumisaika = Kello.getInstance().getAika();
		this.haluttuKyytiLkm = (int) new Uniform(1, 16).sample();
		this.sateenToleranssi = (int) new Uniform(0, 101).sample();
		this.lahtiAikaisinSade = false;
		this.lahtiAikaisinRuuhka = false;
		this.omaMoottori = moottori;
		this.kaytettyRaha = 0;
		this.vietettyAika = 0.0;

		// Trace.out(Trace.Level.INFO, "Uusi asiakas nro " + this.id + " saapui klo " +
		// this.saapumisaika);
	}

	/**
	 * Palauttaa ajan milloin asiakas poistui huvipuistosta.
	 * 
	 * @return Asiakkaan poistumisaika.
	 */
	public double getPoistumisaika() {
		return this.poistumisaika;
	}

	/**
	 * Asettaa asiakkaalle poistumisajan.
	 * 
	 * @param poistumisaika Asiakkaan poistumisaika.
	 */
	public void setPoistumisaika(double poistumisaika) {
		this.poistumisaika = poistumisaika;
	}

	/**
	 * Palauttaa ajan milloin asiakas saapui huvipuistoon.
	 * 
	 * @return Asiakkaan saapumisaika.
	 */
	public double getSaapumisaika() {
		return this.saapumisaika;
	}

	/**
	 * Asettaa asiakkaalle saapumisajan.
	 * 
	 * @param saapumisaika Asiakkaan saapumisaika.
	 */
	public void setSaapumisaika(double saapumisaika) {
		this.saapumisaika = saapumisaika;
	}

	/**
	 * Arpoo ja asettaa asiakkaan halutun kyytimäärän <b>uniform</b>-jakaumalla.
	 * 
	 * @param maksimi Kyytien suurin mahdollinen lukumäärä.
	 */
	public void setHaluttuKyytiLkm(int maksimi) {
		Uniform uni = new Uniform(1, maksimi + 1);
		this.haluttuKyytiLkm = (int) uni.sample();
	}

	/**
	 * Kasvattaa asiakkaan puistossa käytetyn rahan määrää parametrin verran.
	 * 
	 * @param hinta Maksettava rahamäärä.
	 */
	public void maksa(double hinta) {
		this.kaytettyRaha += hinta;
	}

	/**
	 * Korottaa käytyjen kyytien lukumäärää yhdellä.
	 */
	public void korotaKyyteja() {
		this.kaydytKyydit++;
	}

	/**
	 * Palauttaa asiakkaan käymien kohteiden lukumäärän.
	 * 
	 * @return Käytyjen kohteiden lukumäärä.
	 */
	public int getKaydytKyydit() {
		return this.kaydytKyydit;
	}

	/**
	 * Tarkistaa onko asiakas käynyt haluamansa kyytien lukumäärän verran.
	 * 
	 * @return Palauttaa <b>true</b>, jos käytyjen kyytien lukumäärä on suurempi tai
	 *         yhtäsuuri kuin haluttujen kyytien määrä.
	 */
	public boolean onValmis() {
		if (this.kaydytKyydit >= this.haluttuKyytiLkm) {
			return true;
		}
		return false;
	}

	/**
	 * Asettaa asiakkaan <b>lahtiAikaisinSade</b> totuusarvon todeksi.
	 */
	public void lahtiAikaisinSade() {
		this.lahtiAikaisinSade = true;
	}

	/**
	 * Asettaa asiakkaan <b>lahtiAikaisinRuuhka</b> totuusarvon todeksi.
	 */
	public void lahtiAikaisinRuuhka() {
		this.lahtiAikaisinRuuhka = true;
	}

	/**
	 * Palauttaa asiakkaan sateen toleranssiluvun.
	 * 
	 * @return Sateen toleranssi.
	 */
	public int getSateenToleranssi() {
		return this.sateenToleranssi;
	}

	/**
	 * Palauttaa asiakkaan käytetyn rahamäärän.
	 * 
	 * @return Käytetty rahamäärä.
	 */
	public double getKaytettyRaha() {
		return this.kaytettyRaha;
	}

	/**
	 * Palauttaa totuusarvon lähtikö asiakas aikaisemmin sateen vuoksi.
	 * 
	 * @return <b>true</b>, jos asiakas lähti aikaisin sateen vuoksi.
	 */
	public boolean isLahtiAikaisinSade() {
		return lahtiAikaisinSade;
	}

	/**
	 * Palauttaa totuusarvon lähtikö asiakas aikaisemmin ruuhkan vuoksi.
	 * 
	 * @return <b>true</b>, jos asiakas lähti aikaisin ruuhkan vuoksi.
	 */
	public boolean isLahtiAikaisinRuuhka() {
		return lahtiAikaisinRuuhka;
	}

	/**
	 * Palauttaa <b>asiakkaan</b> vietetyn ajan huvipuistossa.
	 * 
	 * @return Vietetty aika.
	 */
	public double getVietettyAika() {
		return this.vietettyAika;
	}

	/**
	 * Hakee <b>omaMoottori</b>-olion simuloinnin id:n.
	 * 
	 * @return omaMoottorin simuloinnin id-tunniste.
	 */
	public int getOmaMoottoriId() {
		return omaMoottori.getIdSimulointi();
	}

	/**
	 * Palauttaa <b>asiakkaan</b> id numeron.
	 * 
	 * @return Id numero.
	 */
	public int getId() {
		return this.id;
	}

	/**
	 * Palauttaa asiakkaan haluttujen kohteiden käymisen lukumäärän.
	 * 
	 * @return Haluttujen kohteiden lukumäärä.
	 */
	public int getHaluttuKyytiLkm() {
		return this.haluttuKyytiLkm;
	}

	/**
	 * Asettaa asiakkaan tietokannan id:n.
	 * 
	 * @param idTietokanta Tietokannan id-tunniste.
	 */
	public void setIdTietokanta(int idTietokanta) {
		this.idTietokanta = idTietokanta;
	}

	/**
	 * Palauttaa asiakkaan tietokannan id:n.
	 * 
	 * @return Tietokannan id.
	 */
	public int getIdTietokanta() {
		return idTietokanta;
	}

	/**
	 * Asettaa asiakkaan yksilöivän id:n.
	 * 
	 * @param id Asiakkaan id numero.
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Asettaa asiakkaalle omaMoottori-olion.
	 * 
	 * @param omaMoottori Liitettävä omaMoottori-olio.
	 */
	public void setOmaMoottori(OmaMoottori omaMoottori) {
		this.omaMoottori = omaMoottori;
	}

	/**
	 * Asettaa asiakkaan käytetyn rahamäärän.
	 * 
	 * @param kaytettyRaha Käytetty rahamäärä.
	 */
	public void setKaytettyRaha(double kaytettyRaha) {
		this.kaytettyRaha = kaytettyRaha;
	}

	/**
	 * Asettaa asiakkaan käytyjen kyytien lukumäärän.
	 * 
	 * @param kaydytKyydit Käydyt kyydit.
	 */
	public void setKaydytKyydit(int kaydytKyydit) {
		this.kaydytKyydit = kaydytKyydit;
	}

	/**
	 * Asettaa asiakkaan sateen toleranssin.
	 * 
	 * @param sateenToleranssi Sateen toleranssi.
	 */
	public void setSateenToleranssi(int sateenToleranssi) {
		this.sateenToleranssi = sateenToleranssi;
	}

	/**
	 * Asettaa lähtikö asiakas aikaisin sateen vuoksi.
	 * 
	 * @param lahtiAikaisinSade Lähtikö asiakas aikaisin sateen vuoksi.
	 */
	public void setLahtiAikaisinSade(boolean lahtiAikaisinSade) {
		this.lahtiAikaisinSade = lahtiAikaisinSade;
	}

	/**
	 * Asettaa lähtikö asiakas aikaisin ruuhkan vuoksi.
	 * 
	 * @param lahtiAikaisinRuuhka Lähtikö asiakas aikaisin ruuhkan vuoksi.
	 */
	public void setLahtiAikaisinRuuhka(boolean lahtiAikaisinRuuhka) {
		this.lahtiAikaisinRuuhka = lahtiAikaisinRuuhka;
	}

	/**
	 * Asettaa asiakkaalle huvipuistossa vietetyn ajan.
	 * 
	 * @param vietettyAika Vietetty aika.
	 */
	public void setVietettyAika(double vietettyAika) {
		this.vietettyAika = vietettyAika;
	}

	/**
	 * Laskee asiakkaan vietetyn ajan huvipuistossa ja lisää asikkaan
	 * omaMoottori-olion lisaaLahtenyt-listaan. Tulostaa konsoliin raportin asiakkaan
	 * eri instanssimuuttujista.
	 */
	public void raportti() {
		this.omaMoottori.lisaaLahtenyt(this);
		this.vietettyAika = this.poistumisaika - this.saapumisaika;

		/*
		 * Trace.out(Trace.Level.INFO, "\nAsiakas " + id + " valmis! ");
		 * Trace.out(Trace.Level.INFO, "Asiakas " + id +
		 * " lähti aikaisin sateen vuoksi? " + this.lahtiAikaisinSade);
		 * Trace.out(Trace.Level.INFO, "Asiakas " + id +
		 * " lähti aikaisin ruuhkan vuoksi? " + this.lahtiAikaisinRuuhka);
		 * Trace.out(Trace.Level.INFO, "Asiakas " + id + " sateen toleranssi? " +
		 * this.sateenToleranssi); Trace.out(Trace.Level.INFO, "Asiakas " + id +
		 * " saapui: " + this.saapumisaika); Trace.out(Trace.Level.INFO, "Asiakas " + id
		 * + " poistui: " + this.poistumisaika); Trace.out(Trace.Level.INFO, "Asiakas "
		 * + id + " viipyi: " + (this.poistumisaika - this.saapumisaika));
		 * Trace.out(Trace.Level.INFO, "Asiakas " + id + " käytti rahaa: " +
		 * this.kaytettyRaha + "\u20ac."); Trace.out(Trace.Level.INFO, "Asiakas " + id +
		 * " halusi käydä: " + this.haluttuKyytiLkm + " kohteessa.");
		 * Trace.out(Trace.Level.INFO, "Asiakas " + id + " kävi: " + this.kaydytKyydit +
		 * " kohteessa.");
		 */
	}

	/**
	 * Ylikirjoitettu metodi, jolla asetetaan merkkijono asiakas-oliolle. Merkkijono
	 * sisältää asiakkaan id numeron.
	 * 
	 * @return Merkkijono asiakkaan id numerosta.
	 */
	@Override
	public String toString() {
		return "" + this.id;
	}

	/**
	 * Comparable-rajapinnan ylikirjoitettu metodi, jonka avulla voidaan järjestää
	 * asiakas-oliot niiden id-tunnusten perusteilla.
	 * 
	 * @param a Asiakas-olio, jota verrataan.
	 * @return Palauttaa -1, jos olio on vertailujärjestyksessä ennen parametrina
	 *         saatavaa olioata. Palauttaa 1, jos olio on järjestyksessä parametrina
	 *         saatavan olion jälkeen. Muulloin palautetaan 0.
	 */
	@Override
	public int compareTo(Asiakas a) {
		if (this.id < a.id) {
			return -1;
		} else if (this.id > a.id) {
			return 1;
		}
		return 0;
	}
}