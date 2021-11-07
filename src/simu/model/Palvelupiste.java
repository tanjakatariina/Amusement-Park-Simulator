package simu.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import eduni.distributions.ContinuousGenerator;
import simu.framework.Kello;
import simu.framework.Tapahtuma;
import simu.framework.Tapahtumalista;
import simu.framework.Trace;

import javax.persistence.*;

/**
 * Sisältää palvelupisteiden toiminnallisuuden. <b>Palvelupiste</b> huolehtii
 * asiakkaiden lisäämisestä palvelupisteen jonoon, jonosta poistamiseen ja
 * palvelun aloittamisen.
 * 
 * @author Otso Poussa, Tanja Pyykönen ja Tatu Talvikko
 */
@Entity
public class Palvelupiste {

	/**
	 * Palvelupisteen tietokannan id-tunnus.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private int id;

	/**
	 * Palvelupistettä kuvaava nimi.
	 */
	@Column
	private String nimi;

	/**
	 * Palvelupisteeseen saapuneiden asiakkaiden lukumäärä.
	 */
	@Column
	private int saapuneita;

	/**
	 * Palveltujen asiakkaiden lukumäärä.
	 */
	@Column
	private int palveltuja;
	
	/**
	 * Palvelupisteen tekemät tuotot yhteensä kaikilla säillä.
	 */
	@Column
	private double tulotYhteensa;

	/**
	 * Palvelupisteen tekemät tuotot aurinkoisella säällä.
	 */
	@Column
	private double tulotAurinko;

	/**
	 * Palvelupisteen tekemät tuotot sateella.
	 */
	@Column
	private double tulotSade;

	/**
	 * Asiakkaiden palvelemiseen käytetty aika.
	 */
	@Column
	private double aktiiviAika;

	/**
	 * Palvelupisteen käytön suhde kapasiteettiin.
	 */
	@Column
	private double kayttoaste;

	/**
	 * Palvelultujen asiakkaiden lukumäärä per tunti.
	 */
	@Column
	private double suoritusteho;

	/**
	 * Palvelupisteeseen jonottamisajan keskiarvo.
	 */
	@Column
	private double jonotusaikaKeskiarvo;

	/**
	 * Palvelupisteen läpimenoajan keskiarvo.
	 */
	@Column
	private double lapimenoaikaKeskiarvo;

	/**
	 * Palvelupisteen palveluajan keskiarvo.
	 */
	@Column
	private double palveluaikaKeskiarvo;

	/**
	 * Moottori-olio, johon talletetaan <b>OmaMoottorin</b> instanssi.
	 */

	@ManyToOne(cascade = { CascadeType.ALL })
	@JoinColumn(name = "idSim")
	private OmaMoottori omaMoottori;

	/**
	 * LinkedList joka sisältää palvelupisteen jonossa olevat <b>Asiakas</b>-oliot.
	 */
	@Transient
	private LinkedList<Asiakas> jono = new LinkedList<Asiakas>();

	/**
	 * Satunnaisia lukuja tuottava generaattori <code>eduni.distributions</code>
	 * pakkauksesta. Käytetään Sisääänkäynnin ja grillin palveluaikojen arpomiseen.
	 */
	@Transient
	private ContinuousGenerator generator;

	/**
	 * Simuloinnin tapahtumalista, johon lisätään tapahtumia.
	 */
	@Transient
	private Tapahtumalista tapahtumalista;

	/**
	 * Palvelupistekohtaisen tapahtuman tyyppi.
	 */
	@Transient
	private TapahtumanTyyppi skeduloitavanTapahtumanTyyppi;

	/**
	 * Palvelupisteen palveluajan vakio.
	 */
	@Transient
	private double palveluAikaVakio = 0;

	/**
	 * Palvelupisteessä asioimisen hinta.
	 */
	@Transient
	private double hinta = 0;

	/**
	 * HashMap palvelupisteen eri palvelukertojen ajoista. Integer kuvaa asiakkaan
	 * <b>Id</b>:tä
	 */
	@Transient
	private Map<Integer, Double> palveluAjat;

	/**
	 * Lista palvelupisteiden jonotusajoista.
	 */
	@Transient
	private List<Double> jonotusAjat;

	/**
	 * HashMap pisteen eri lapimenoajoista ajoista. Integer kuvaa asiakkaan
	 * <b>Id</b>:tä
	 */
	@Transient
	private Map<Integer, Double> lapimenoAjat;

	/**
	 * Lista asiakkaiden aktiiviajan korottamisajoista. Käytetään Aktiiviajan
	 * hallitsemiseen.
	 */
	@Transient
	private List<Double> korottamisAjat;

	/**
	 * Samaan aikaan palveltavien asiakkaiden maksimimäärä.
	 */
	@Transient
	private int kapasiteetti;

	/**
	 * Asiakasjonon maksimipituus.
	 */
	@Transient
	private int jononPituus;

	/**
	 * Arvo, joka määrää palvelupisteen suosion aurinkoisella säällä.
	 */
	@Transient
	private int suosioAurinko;

	/**
	 * Arvo, joka määrää palvelupisteen suosion sateella säällä.
	 */
	@Transient
	private int suosioSade;

	/**
	 * Totuusarvo, joka määrää onko palvelupiste varattu.
	 */
	@Transient
	private boolean varattu = false;

	/**
	 * Totuusarvo <b>JUnit</b> testejä varten.
	 */
	@Transient
	private boolean junit = false;

	/**
	 * Numero kuvaamaan palvelupisteen indeksiä <b>palvelupisteet</b>-taulukossa.
	 */
	@Transient
	protected final static int SISAANKAYNTI = 0, GRILLI = 1, MAAILMANPYORA = 2, VUORISTORATA = 3, KARUSELLI = 4,
			VIIKINKILAIVA = 5, KUMMITUSJUNA = 6;

	/**
	 * Tyhjä konstruktori DAO:a varten.
	 */
	public Palvelupiste() {}

	/**
	 * Luo palvelupiste olion, jolle toteutetaan palveluaika jakaumalla ja alustaa
	 * instanssimuuttujille tyhjät arvot. Sisäänkäynnin ja grillin palvelupisteen
	 * instanssit sisältävät <b>ContinuousGenerator</b>-olion.
	 * 
	 * @param tapahtumalista Simuloinnin tapahtumalista, johon lisätään tapahtumia.
	 * @param tyyppi         Käsiteltävän tapahtuman tapahtumantyyppi.
	 * @param nimi           Palvelupisteen nimi.
	 * @param moottori       Omamoottori-olio.
	 */
	public Palvelupiste(Tapahtumalista tapahtumalista, TapahtumanTyyppi tyyppi, String nimi, OmaMoottori moottori) {
		this.tapahtumalista = tapahtumalista;
		this.skeduloitavanTapahtumanTyyppi = tyyppi;
		this.nimi = nimi;
		this.generator = null;
		this.jononPituus = 0;
		this.kapasiteetti = 0;
		this.hinta = 0;
		this.suosioAurinko = 0;
		this.suosioSade = 0;
		this.omaMoottori = moottori;
		this.aktiiviAika = 0;
		this.palveluAjat = new HashMap<Integer, Double>();
		this.lapimenoAjat = new HashMap<Integer, Double>();
		this.jonotusAjat = new ArrayList<Double>();
		this.korottamisAjat = new ArrayList<Double>();
	}

	/**
	 * Luo palvelupiste olion, jolle toteutetaan palveluaika vakioarvolla ja alustaa
	 * instanssimuuttujille tyhjät arvot.
	 * 
	 * @param palveluAikaVakio Palveluajan vakioaika.
	 * @param tapahtumalista   Simuloinnin tapahtumalista, johon lisätään
	 *                         tapahtumia.
	 * @param tyyppi           Käsiteltävän tapahtuman tapahtumantyyppi.
	 * @param nimi             Palvelupisteen nimi.
	 * @param moottori         Omamoottori-olio.
	 */
	public Palvelupiste(double palveluAikaVakio, Tapahtumalista tapahtumalista, TapahtumanTyyppi tyyppi, String nimi,
			OmaMoottori moottori) {
		this.tapahtumalista = tapahtumalista;
		this.palveluAikaVakio = palveluAikaVakio;
		this.skeduloitavanTapahtumanTyyppi = tyyppi;
		this.nimi = nimi;
		this.jononPituus = 0;
		this.kapasiteetti = 0;
		this.hinta = 0;
		this.suosioAurinko = 0;
		this.suosioSade = 0;
		this.omaMoottori = moottori;
		this.aktiiviAika = 0;
		this.palveluAjat = new HashMap<Integer, Double>();
		this.lapimenoAjat = new HashMap<Integer, Double>();
		this.jonotusAjat = new ArrayList<Double>();
		this.korottamisAjat = new ArrayList<Double>();
	}

	/**
	 * Tarkistaa, onko palvelupisteen asiakasjonossa tilaa, jolloin lisää asiakkaan
	 * jonoon ja merkitsee asiakkaalle saapumisajan. <br>
	 * Jos jonossa ei ole tilaa, kutsuu asiakkaan lahtiAikaisin metodia ja asettaa
	 * asiakkaalle puistosta poistumiajan.
	 * 
	 * @param a Asiakas-olio
	 */
	public void lisaaJonoon(Asiakas a) { // Jonon ensimmäinen asiakas aina palvelussa
		if (onkoTilaa()) {
			jono.add(a);
			if (!junit) {
				omaMoottori.visuaaliLisaaJonoon(this.getNimi());
			}
			// Trace.out(Trace.Level.INFO, "Jonossa: " + jono.size() + "/" + jononPituus);
			return;
		}
		// Trace.out(Trace.Level.INFO, "Asiakas " + a.getId() + " yritti mennä
		// kohteeseen: " + this.nimi + ", mutta jonossa ei ole tilaa");
		a.lahtiAikaisinRuuhka();
		a.setPoistumisaika(Kello.getInstance().getAika());
		a.raportti();
	}

	/**
	 * Ottaa palvelupisteen <b>asiakasjonon</b> ensimmäisen asiakkaan pois jonosta,
	 * asettaa <b>varattu</b>-booleanin arvoksi <b>false</b> ja nostaa
	 * <b>palveltujen</b> arvoa yhdellä.
	 * 
	 * @return Palauttaa jonon ensimmäisen <b>asiakas</b>-olion.
	 */
	public Asiakas otaJonosta() { // Poistetaan palvelussa ollut
		this.lisaaJonotusAika(jono.peek().getSaapumisaika());
		varattu = false;
		palveltuja++;
		return jono.poll();
	}

	/**
	 * Ottaa jonosta palvelupisteen kapasiteetin verran asiakkaita, ellei jonossa
	 * kapasiteettia vähemmän asiakkaita. Jos on, ottaa kaikki asiakkaat jonosta.
	 * Lisää palveluajan <b>palveluajat</b>-listaan. Arpoo asiakkaille palveluajan
	 * käyttäen <b>generator</b>-luokkaa.
	 * 
	 * @return Palvelun aloittavien asiakkaiden määrä.
	 */
	public int aloitaPalvelu() {
		varattu = true;
		double palveluaika = 0;
		int asiakasMaara = 0;

		if (this.palveluAikaVakio != 0) {
			palveluaika = this.palveluAikaVakio;
		} else {
			palveluaika = generator.sample();
		}

		// Jonossa enemmän kuin kapasiteetin verran -> otetaan jonosta kapasiteetin
		// verran asiakkaita.
		if (jono.size() >= kapasiteetti) {
			this.lisaaAsiakkaanPalveluAika(palveluaika);
			this.lisaaAsiakkaanLapimenoAika(palveluaika);

			for (asiakasMaara = 0; asiakasMaara < kapasiteetti; asiakasMaara++) {
				saapuneita++;
				/*
				 * Trace.out(Trace.Level.INFO, "Aloitetaan uusi palvelu asiakkaalle " +
				 * jono.get(asiakasMaara).getId() + " kohteessa " + this.nimi + ", hän maksoi "
				 * + this.hinta + "\u20ac.");
				 */
				jono.get(asiakasMaara).maksa(this.hinta);

				if (Saa.getInstance().onkoSadeKaynnissa()) {
					this.korotaTulotSade(this.hinta);
				} else {
					this.korotaTulotAurinko(this.hinta);
				}

				tapahtumalista.lisaa(
						new Tapahtuma(skeduloitavanTapahtumanTyyppi, Kello.getInstance().getAika() + palveluaika));
				if (!junit) {
					omaMoottori.visuaaliPoistaJonosta(this.getNimi());
				}
			}
		}
		// Jonossa vähemmän kuin kapasiteetin verran -> otetaan jonosta kaikki
		// asiakkaat.
		else if (jono.size() < kapasiteetti) {
			this.lisaaAsiakkaanPalveluAika(palveluaika);
			this.lisaaAsiakkaanLapimenoAika(palveluaika);

			for (asiakasMaara = 0; asiakasMaara < jono.size(); asiakasMaara++) {
				saapuneita++;
				/*
				 * Trace.out(Trace.Level.INFO, "Aloitetaan uusi palvelu asiakkaalle " +
				 * jono.get(asiakasMaara).getId() + " kohteessa " + this.nimi + ", hän maksoi "
				 * + this.hinta + "\u20ac.");
				 */
				jono.get(asiakasMaara).maksa(hinta);

				if (Saa.getInstance().onkoSadeKaynnissa()) {
					this.korotaTulotSade(this.hinta);
				} else {
					this.korotaTulotAurinko(this.hinta);
				}

				tapahtumalista.lisaa(
						new Tapahtuma(skeduloitavanTapahtumanTyyppi, Kello.getInstance().getAika() + palveluaika));
				if (!junit) {
					omaMoottori.visuaaliPoistaJonosta(this.getNimi());
				}
			}
		}
		if (!junit) {
			omaMoottori.visuaaliPalvelussa(this.getNimi(), asiakasMaara);
		}
		return asiakasMaara;
	}

	/**
	 * Palauttaa tilan, josta selviää onko palvelupiste varattu.
	 * 
	 * @return <b>true</b>, jos palvelupiste on varattu.
	 */
	public boolean onVarattu() {
		return varattu;
	}

	/**
	 * Palauttaa palvelupisteen jonon tilan, josta selviää onko asiakkaita jonossa.
	 * 
	 * @return <b>true</b>, jos <b>asiakasjonossa</b> on asiakkaita.
	 */
	public boolean onJonossa() {
		return jono.size() != 0;
	}

	/**
	 * Palauttaa palvelupisteen jonon tilan, josta selviää onko jonossa tilaa
	 * uusille asiakkaille.
	 * 
	 * @return <b>true</b>, jos <b>asiakasjonossa</b> on tilaa.
	 */
	public boolean onkoTilaa() {
		if ((jono.size() + 1) <= jononPituus) {
			return true;
		}
		return false;
	}

	/**
	 * Palauttaa luokan <b>ContinousGenerator</b>-olion, jolla arvotaan
	 * sisäänkäynnin ja grillin palveluajat.
	 * 
	 * @return generaattori jakaumia varten.
	 */
	public ContinuousGenerator getGenerator() {
		return generator;
	}

	/**
	 * Asettaa parametrin luokan <b>ContinousGenerator</b>-olioksi, jolla arvotaan
	 * sisäänkäynnin ja grillin palveluajat.
	 * 
	 * @return Satunnaislukuja tuottavat generaattori.
	 */
	public void setGenerator(ContinuousGenerator generator) {
		this.generator = generator;
	}

	/**
	 * Asettaa palvelupisteelle hinnan, jonka asiakas maksaa palvelusta.
	 * 
	 * @param hinta Maksettava hinta.
	 */
	public void setHinta(double hinta) {
		this.hinta = hinta;
	}

	/**
	 * Palauttaa palvelupisteelle määritellyn hinnan, joka maksetaan palvelusta.
	 * 
	 * @return Maksettava hinta.
	 */
	public double getHinta() {
		return this.hinta;
	}

	/**
	 * Asettaa palvelupisteen kapasiteetin (maksimiarvon).
	 * 
	 * @param kapasiteetti Kapasiteetin maksimiarvo.
	 */
	public void setKapasiteetti(int kapasiteetti) {
		this.kapasiteetti = kapasiteetti;
	}

	/**
	 * Palauttaa palvelupisteen kapasiteetin (maksimiarvon).
	 * 
	 * @return Kapasiteetin maksimiarvo.
	 */
	public int getKapasiteetti() {
		return this.kapasiteetti;
	}

	/**
	 * Asettaa palvelupisteen jonon maksimipituuden.
	 * 
	 * @param pituus Jonon maksimipituus.
	 */
	public void setJononPituus(int pituus) {
		this.jononPituus = pituus;
	}

	/**
	 * Palauttaa tämänhetkisen asiakasjonon pituuden.
	 * 
	 * @return Jonon pituus.
	 */
	public int getJononPituus() {
		return this.jononPituus;
	}

	/**
	 * Palauttaa palvelupisteen suosion määrän aurinkoisella säällä prosentteina.
	 * 
	 * @return Suosion arvo.
	 */
	public int getSuosioAurinko() {
		return this.suosioAurinko;
	}

	/**
	 * Asettaa palvelupisteen suosion prosenttimäärän aurinkoisella säällä.
	 * 
	 * @param suosioA Suosion arvo.
	 */
	public void setSuosioAurinko(int suosioA) {
		this.suosioAurinko = suosioA;
	}

	/**
	 * Palauttaa palvelupisteen suosion määrän sateella prosentteina.
	 * 
	 * @return Suosion arvo.
	 */
	public int getSuosioSade() {
		return this.suosioSade;
	}

	/**
	 * Asettaa palvelupisteen suosion prosenttimäärän sateella.
	 * 
	 * @param suosioS Suosion arvo.
	 */
	public void setSuosioSade(int suosioS) {
		this.suosioSade = suosioS;
	}

	/**
	 * Palauttaa palvelupistettä kuvaavan nimen.
	 * 
	 * @return Palvelupisteen nimi.
	 */
	public String getNimi() {
		return this.nimi;
	}

	/**
	 * Asettaa palvelupisteelle nimen.
	 * 
	 * @param nimi Palvelupisteen nimi.
	 */
	public void setNimi(String nimi) {
		this.nimi = nimi;
	}

	/**
	 * Palauttaa palvelupisteen kokonaistuotot.
	 * 
	 * @return Palvelupisteen kokonaistuotot.
	 */
	public double getTulotYhteensa() {
		return this.tulotYhteensa;
	}

	/**
	 * Asettaa palvelupisteen kokonaistuotot.
	 * 
	 * @param tulotYhteensa Palvelupisteen kokonaistuotot.
	 */
	public void setTulotYhteensa(double tulotYhteensa) {
		this.tulotYhteensa = tulotYhteensa;
	}

	/**
	 * Laskee palvelupisteen tuottamat tulot aurinkoiselta ja sateiselta säältä
	 * yhteen.
	 */
	public void laskeTulotYhteensa() {
		this.tulotYhteensa = this.tulotSade + this.tulotAurinko;
	}

	/**
	 * Palauttaa palvelupisteen tuottamat tulot aurinkoisella säällä.
	 * 
	 * @return Tulon määrä.
	 */
	public double getTulotAurinko() {
		return this.tulotAurinko;
	}

	/**
	 * Asettaa palvelupisteen tuottamat tulot aurinkoisella säällä.
	 * 
	 * @param tulotAurinko Tulon määrä.
	 */
	public void setTulotAurinko(double tulotAurinko) {
		this.tulotAurinko = tulotAurinko;
	}

	/**
	 * Korottaa aurinkoisen sään tulojen määrää.
	 * 
	 * @param hinta Korotettava hintamäärä.
	 */
	public void korotaTulotAurinko(double hinta) {
		this.tulotAurinko += hinta;
	}

	/**
	 * Palauttaa palvelupisteen tuottamat tulot sateiselta säältä.
	 * 
	 * @return Tulon määrä.
	 */
	public double getTulotSade() {
		return this.tulotSade;
	}

	/**
	 * Asettaa palvelupisteen tuottamat tulot sateella.
	 * 
	 * @param tulotSade Tulon määrä.
	 */
	public void setTulotSade(double tulotSade) {
		this.tulotSade = tulotSade;
	}

	/**
	 * Korottaa sateisen sään tulojen määrää.
	 * 
	 * @param hinta Tulon määrä.
	 */
	public void korotaTulotSade(double hinta) {
		this.tulotSade += hinta;
	}

	/**
	 * Palauttaa saapuneiden asiakkaiden lukumäärän.
	 * 
	 * @return Saapuneiden asiakkaiden määrä.
	 */
	public int getSaapuneita() {
		return this.saapuneita;
	}

	/**
	 * Asettaa saapuneiden asiakkaiden lukumäärän.
	 *
	 * @param saapuneita Saapuneiden asiakkaiden määrää.
	 */
	public void setSaapuneita(int saapuneita) {
		this.saapuneita = saapuneita;
	}

	/**
	 * Palauttaa palveltujen asiakkaiden lukumäärän.
	 * 
	 * @return Palveltujen asiakkaiden määrän.
	 */
	public int getPalveltuja() {
		return this.palveltuja;
	}

	/**
	 * Asettaa palveltujen asiakkaiden lukumäärän.
	 * 
	 * @param palveltuja Palveltujen asiakkaiden määrä.
	 */
	public void setPalveltuja(int palveltuja) {
		this.palveltuja = palveltuja;
	}

	/**
	 * Palauttaa palvelupisteen aktiiviajan.
	 * 
	 * @return Aktiiviaika.
	 */
	public double getAktiiviAika() {
		return this.aktiiviAika;
	}

	/**
	 * Asettaa palvelupisteen aktiiviajan.
	 * 
	 * @param aktiiviAika Palvelupisteen aktiiviaika.
	 */
	public void setAktiiviAika(double aktiiviAika) {
		this.aktiiviAika = aktiiviAika;
	}

	/**
	 * Palauttaa palvelupisteen käyttöasteen.
	 * 
	 * @return Palvelupisteen käyttöaste.
	 */
	public double getKayttoaste() {
		return this.kayttoaste;
	}

	/**
	 * Asettaa palvelupisteen käyttöasteen.
	 * 
	 * @param kayttoaste Palvelupisteen käyttöaste.
	 */
	public void setKayttoaste(double kayttoaste) {
		this.kayttoaste = kayttoaste;
	}

	/**
	 * Laskee palvelupisteen käyttöasteen jakamalla aktiiviajan <b>omamoottorin</b>
	 * simulointiajalla. Simulointi aika kuvataan minuutteina.
	 */
	public void laskeKayttoaste() {
		this.kayttoaste = ((this.aktiiviAika / (omaMoottori.getSimulointiaika() / 100)));
	}

	/**
	 * Palauttaa palvelupisteen suoritustehon.
	 * 
	 * @return Palvelupisteen suoritusteho.
	 */
	public double getSuoritusteho() {
		return this.suoritusteho;
	}

	/**
	 * Asettaa palvelupisteen suoritustehon.
	 * 
	 * @param suoritusteho Palvelupisteen suoritusteho.
	 */
	public void setSuoritusteho(double suoritusteho) {
		this.suoritusteho = suoritusteho;
	}

	/**
	 * Laskee palvelupisteen suoritustehon jakamala palveltujen määrän
	 * <b>omamoottorin</b> simulointiajalla. Simulointi aika kuvataan minuutteina.
	 */
	public void laskeSuoritusteho() {
		this.suoritusteho = this.palveltuja / (omaMoottori.getSimulointiaika() / 3600);
	}

	/**
	 * Palauttaa palveluajan keskiarvon. (Pitää laskea ensin metodissa
	 * <b>laskePalveluaikaKeskiarvo()</b>).
	 * 
	 * @return Palveluajan keskiarvo.
	 */
	public double getPalveluaikaKeskiarvo() {
		return this.palveluaikaKeskiarvo;
	}

	/**
	 * Asettaa palveluajan keskiarvon.
	 * 
	 * @param palveluaikaKeskiarvo Palveluajan keskiarvo.
	 */
	public void setPalveluaikaKeskiarvo(double palveluaikaKeskiarvo) {
		this.palveluaikaKeskiarvo = palveluaikaKeskiarvo;
	}

	/**
	 * Laskee palveluajan keskivarvon summaamalla <b>palveluAjat</b>-listan sisällön
	 * ja jakamalla sen sitten listan sisällön koolla.
	 */
	public void laskePalveluaikaKeskiarvo() {
		double summa = 0;
		for (Map.Entry<Integer, Double> aika : palveluAjat.entrySet()) {
			summa += aika.getValue();
		}
		if (this.palveluAjat.size() == 0) {
			this.palveluaikaKeskiarvo = 0;

		} else {
			this.palveluaikaKeskiarvo = (summa / (double) this.palveluAjat.size());

		}
	}

	/**
	 * Laskee jonotus ajan <b>jonotusAjat</b>-listaan vähentämällä parametrin arvon
	 * tämän hetkisestä kelloajasta.
	 * 
	 * @param saapumisAika Saapumisaika.
	 */
	public void lisaaJonotusAika(double saapumisAika) {
		this.jonotusAjat.add(Kello.getInstance().getAika() - saapumisAika);
	}

	/**
	 * Palauttaa palvelupisteen jonotusajat sisältävän listan.
	 * 
	 * @return jonotusAjat Jonotusaikojen lista.
	 */
	public List<Double> getJonotusAjat() {
		return this.jonotusAjat;
	}

	/**
	 * Palauttaa palvelupisteen jonotusajan keskiarvon. (Pitää ensin laskea
	 * metodilla <b>laskeJonotusaikaKeskiarvo()</b>).
	 * 
	 * @return Jonotusajan keskiarvo.
	 */
	public double getJonotusaikaKeskiarvo() {
		return this.jonotusaikaKeskiarvo;
	}

	/**
	 * Asettaa palvelupisteen jonotusajan keskiarvon.
	 * 
	 * @param jonotusaikaKeskiarvo Jonotusajan keskiarvo.
	 */
	public void setJonotusaikaKeskiarvo(double jonotusaikaKeskiarvo) {
		this.jonotusaikaKeskiarvo = jonotusaikaKeskiarvo;
	}

	/**
	 * Laskee jonotusajankeskiarvon summaamalla jonotusajat yhteen ja jakamalla ne
	 * niiden lukumäärällä.
	 */
	public void laskeJonotusaikaKeskiarvo() {
		double summa = 0;
		for (double d : this.jonotusAjat) {
			summa += d;
		}
		if (this.jonotusAjat.size() == 0) {
			this.jonotusaikaKeskiarvo = 0;
		} else {
			this.jonotusaikaKeskiarvo = (summa / (double) this.jonotusAjat.size());
		}
	}

	/**
	 * Lisää parametrin ja viimeisimmän jonotusajan summan jonon ensimmäisen
	 * asiakkaan läpimenoajaksi. <br>
	 * Arvo lisätään <b>lapimenoAjat</b>-HashMappiin. Avaimena jonon ensimmäisen
	 * asiakkaan <b>ID</b>.
	 * 
	 * @param palveluaika Palveluaika.
	 */
	public void lisaaAsiakkaanLapimenoAika(double palveluaika) {
		if (jonotusAjat.size() > 0) {
			this.lapimenoAjat.put(jono.peek().getId(), palveluaika + this.jonotusAjat.get(jonotusAjat.size() - 1));
		}
	}

	/**
	 * Lisää parametrin arvon ja <b>jonotusAjat</b>-listan ensimmäisen jonotusajan
	 * summan (läpimenoajan) <b>lapimenoAjat</b>-listaan.
	 * 
	 * @param avain       Asiakkaan id.
	 * @param palveluaika Palveluaika.
	 */
	public void lisaaLapimenoAika(int avain, double palveluaika) {
		this.lapimenoAjat.put(avain, palveluaika + this.jonotusAjat.get(jonotusAjat.size() - 1));
	}

	/**
	 * Palauttaa palvelupisteen läpimenoajan keskiarvon.
	 * 
	 * @return Läpimenoajan keskiarvo.
	 */
	public double getLapimenoaikaKeskiarvo() {
		return this.lapimenoaikaKeskiarvo;
	}

	/**
	 * Palauttaa palvelupisteen läpimenoajat sisältävän HashMapin.
	 * 
	 * @return Läpimenoaikojen lista.
	 */
	public Map<Integer, Double> getLapimenoajat() {
		return this.lapimenoAjat;
	}

	/**
	 * Asettaa palvelupisteen asiakkaiden läpimenoaikojen keskiarvon.
	 * 
	 * @param lapimenoaikaKeskiarvo Läpimenoajan keskiarvo.
	 */
	public void setLapimenoaikaKeskiarvo(double lapimenoaikaKeskiarvo) {
		this.lapimenoaikaKeskiarvo = lapimenoaikaKeskiarvo;
	}

	/**
	 * Laskee palvelupisteen läpimenoajan keskiarvon summaamalla läpimenoajat yhteen
	 * ja jakamalla summan niiden lukumäärällä.
	 */
	public void laskeLapimenoaikaKeskiarvo() {
		double summa = 0;
		for (Map.Entry<Integer, Double> aika : this.lapimenoAjat.entrySet()) {
			summa += aika.getValue();
		}
		if (this.lapimenoAjat.size() == 0) {
			this.lapimenoaikaKeskiarvo = 0;
		} else {
			this.lapimenoaikaKeskiarvo = (summa / (double) this.lapimenoAjat.size());
		}
	}

	/**
	 * Korottaa palvelupisteen aktiiviaikaa parametrin Asiakas <b>id</b>:n
	 * osoittaman asiakkaan palveluajalla. Palveluaika haetaan
	 * <b>palveluAjat</b>-HashMapista. Metodi tarkistaa myös, onko tällä
	 * ajanhektellä korotettu aktiiviaikaa jotta samoja laitteen ajoja ei lisättäisi
	 * erikseen. Tarkistus tehdään tarkistamalla löytyykö aikaa
	 * <b>lisaamisAjat</b>-listasta.
	 * 
	 * @param asiakkaanID Asiakkaan id.
	 */
	public void korotaAktiiviAika(int asiakkaanID) {
		if (palveluAjat.get(asiakkaanID) != null) {
			if (!this.korottamisAjat.contains(Kello.getInstance().getAika())) {
				this.aktiiviAika += palveluAjat.get(asiakkaanID);
				this.korottamisAjat.add(Kello.getInstance().getAika());
			}
		}
	}

	/**
	 * lisää parametrin rvon palveluajaksi <b>palveluAjat</b> HashMappiin jonon
	 * ensimmäisen asiakkaan <b>ID</b>:n kohdalle.
	 * 
	 * @param aika Palveluaika.
	 */
	public void lisaaAsiakkaanPalveluAika(double aika) {
		this.palveluAjat.put(jono.peek().getId(), aika);
	}

	/**
	 * Lisää parametrin arvon palveluajan <b>palveluAjat</b>-HashMappiin ja asettaa
	 * avaimeksi asiakas ID:n.
	 * 
	 * @param avain Asiakkaan id.
	 * @param aika  Palveluaika.
	 */
	public void lisaaPalveluAika(int avain, double aika) {
		this.palveluAjat.put(avain, aika);
	}

	/**
	 * Palauttaa palvelupisteen tietokannan id:n
	 * 
	 * @return Tietokannan id.
	 */
	public int getId() {
		return id;
	}

	/**
	 * Asettaa palvelupisteelle tietokannan id:n.
	 * 
	 * @param id Tietokannan id.
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Palauttaa <b>omaMoottori</b>-olion simuloinnin id:n.
	 * 
	 * @return OmoMoottori-olion simulointi id:n.
	 */
	public int getOmaMoottoriId() {
		return this.omaMoottori.getIdSimulointi();
	}

	/**
	 * Palauttaa <b>OmaMoottori</b>-olion.
	 * 
	 * @return OmaMoottori-olion.
	 */
	public OmaMoottori getOmaMoottori() {
		return omaMoottori;
	}

	/**
	 * Laskee palvelupisteen tulostukset kutsumalla niiden metodeja ja tulostaa
	 * konsoliin raportin palvelupisteen eri instanssimuuttujista.
	 */
	public void raportti() {
		this.laskeTulotYhteensa();
		this.laskeSuoritusteho();
		this.laskeKayttoaste();
		this.laskePalveluaikaKeskiarvo();
		this.laskeJonotusaikaKeskiarvo();
		this.laskeLapimenoaikaKeskiarvo();

		/*
		 * Trace.out(Trace.Level.INFO, "\n\t---" + this.nimi.toUpperCase() + "---");
		 * Trace.out(Trace.Level.INFO, this.nimi + " palvelupisteeseen saapui " +
		 * this.saapuneita + " asiakasta."); Trace.out(Trace.Level.INFO, this.nimi +
		 * " palvelupiste palveli " + this.palveltuja + " asiakasta.");
		 * Trace.out(Trace.Level.INFO, this.nimi + " palvelupiste teki " +
		 * this.tulotSade + " \u20ac tuloa aurinkoisella säällä.");
		 * Trace.out(Trace.Level.INFO, this.nimi + " palvelupiste teki " +
		 * this.tulotAurinko + " \u20ac tuloa sateella."); Trace.out(Trace.Level.INFO,
		 * this.nimi + " palvelupiste teki " + this.getTulotYhteensa() +
		 * " \u20ac tuloa."); Trace.out(Trace.Level.INFO, this.nimi +
		 * " palvelupisteen aktiiviaika oli " + this.getAktiiviAika());
		 * Trace.out(Trace.Level.INFO, this.nimi +
		 * " palvelupisteen keskimääräinen palveluaika oli " +
		 * this.getPalveluaikaKeskiarvo() + " s"); Trace.out(Trace.Level.INFO, this.nimi
		 * + " palvelupisteen keskimääräinen jonotusaika oli " +
		 * this.getJonotusaikaKeskiarvo() + " s"); Trace.out(Trace.Level.INFO, this.nimi
		 * + " palvelupisteen keskimääräinen läpimenoaika oli " +
		 * this.getLapimenoaikaKeskiarvo() + " s"); Trace.out(Trace.Level.INFO,
		 * this.nimi + " palvelupisteen keskimääräinen suoritusteho oli " +
		 * this.getSuoritusteho() + " asiakasta tuntia kohden");
		 * Trace.out(Trace.Level.INFO, this.nimi +
		 * " palvelupisteen käyttöaste simulaation aikana oli " + +this.getKayttoaste()
		 * + " %");
		 */
	}

	/**
	 * Asettaa parametrina annetun <b>OmaMoottori</b>-olion palvelupisteen
	 * <b>omaMoottori</b>-muuttujaan.
	 * 
	 * @param omaMoottori-olio
	 */
	public void setOmaMoottori(OmaMoottori omaMoottori) {
		this.omaMoottori = omaMoottori;
	}

	/**
	 * Ylikirjoitettu metodi, jolla asetetaan merkkijono palvelupiste-oliolle.
	 * Merkkijono sisältää palvelupistettä kuvaavan nimen.
	 * 
	 * @return Merkkijono palvelupisteen nimestä.
	 */
	@Override
	public String toString() {
		return "" + this.nimi;
	}

	/**
	 * Asettaa parametrin arvon <b>JUnit</b>-booleaniin.
	 * 
	 * @param junit Asetettava totuusarvo JUnitille.
	 */
	public void setJunit(boolean junit) {
		this.junit = junit;
	}
}