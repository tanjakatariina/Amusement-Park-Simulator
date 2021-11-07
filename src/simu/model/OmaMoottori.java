package simu.model;

import controller.IKontrolleri;
import dao.IDAO;
import eduni.distributions.Negexp;
import eduni.distributions.Uniform;
import simu.framework.Kello;
import simu.framework.Moottori;
import simu.framework.Saapumisprosessi;
import simu.framework.Tapahtuma;
import simu.framework.Trace;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

/**
 * Huolehtii simuloinnin alustuksista ja simuloinnin tapahtumien
 * suorittamisesta. Käy läpi toteutettavat tapahtumat ja luo uusia tapahtumia.
 * 
 * @author Otso Poussa, Tanja Pyykönen ja Tatu Talvikko
 * @version 1.0
 */
@Entity
public class OmaMoottori extends Moottori implements Comparable<OmaMoottori> {

	/**
	 * Yksilöivä simulointiajon id.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private int idSimulointi;

	/**
	 * Aika kuinka kauan simuloitiin.
	 */
	@Column
	private double simuloinninKesto;

	/**
	 * Huvipuiston tuottamat <b>tulot</b>.
	 */
	@Column
	private double kokoTulot;

	/**
	 * Huvipuiston tuottamat <b>tulot</b> aurinkoisella säällä.
	 */
	@Column
	private double aurinkoTulot;

	/**
	 * Huvipuiston tuottamat <b>tulot</b> sateella.
	 */
	@Column
	private double sadeTulot;

	/**
	 * Kuinka monta <b>asiakasta</b> saapui huvipuistoon.
	 */
	@Column
	private int saapuneet;

	/**
	 * Kuinka monta <b>asiakasta</b> lähti huvipuistosta.
	 */
	@Column
	private int lahteneet;

	/**
	 * Kuinka monta <b>asiakasta</b> poistui huvipuistosta ruuhkan vuoksi.
	 */
	@Column
	private int lahtiRuuhka;

	/**
	 * Kuinka monta <b>asiakasta</b> poistui huvipuistosta sateen vuoksi.
	 */
	@Column
	private int lahtiSade;

	/**
	 * Sadekertojen kestot yhteensä.
	 */
	@Column
	private double sateenKesto;

	/**
	 * Sadekertojen lukumäärä.
	 */
	@Column
	private int sateidenLkm;

	/**
	 * Huvipuiston <b>asiakkaiden</b> keskimääräinen läpimenoaika.
	 */
	@Column
	private double keskLapimenoAika;

	/**
	 * Päivämäärä milloin simulointiajo ajettiin.
	 */
	@Column
	private String paiva;

	/**
	 * Kellonaika milloin simulointiajo ajettiin.
	 */
	@Column
	private String kellonAika;

	/**
	 * Kokoelma <b>asiakkaista</b>, jotka liittyvät kyseiseen <b>omaMoottori</b>
	 * olioon.
	 */
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Asiakas> asiakkaatSet = new HashSet<Asiakas>();

	/**
	 * Kokelma <b>palvelupisteistä</b>, jotka liittyvät kyseiseen <b>omaMoottori</b>
	 * olioon.
	 */
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Palvelupiste> ppSet = new HashSet<Palvelupiste>();

	/**
	 * <b>Asiakaiden</b> saapumisväliaikojen keskiarvo sekunteina.
	 * 
	 * @param aika keskiarvo asiakkaiden saapumisille.
	 */
	@Transient
	private double saapumisVali;

	/**
	 * Sisäänkäynnin saapumisprosessi.
	 */
	@Transient
	private Saapumisprosessi saapumisprosessi;

	/**
	 * Sateen saapumisprosessi.
	 */
	@Transient
	private Saapumisprosessi saapumisprosessiSade;

	/**
	 * Lista, johon kerätään huvipuistosta lähteneet asiakkaat.
	 */
	@Transient
	private ArrayList<Asiakas> lahteneetAsiakkaat;

	/**
	 * DAO tietokantaan tallentamista varten.
	 */
	@Transient
	private IDAO dao;

	/**
	 * Tyhjä konstruktori tietokantaa varten.
	 */
	public OmaMoottori() {}

	/**
	 * Luodaan omaMoottori olio, jossa alustetaan muuttujat ja luodaan huvipuiston
	 * palvelupisteet.
	 * 
	 * @param kontrolleri Luodaan yhteys kontrolleriin.
	 */
	public OmaMoottori(IKontrolleri kontrolleri) {
		super(kontrolleri);
		dao = kontrolleri.haeDao();

		palvelupisteet = new Palvelupiste[7];
		palvelupisteet[Palvelupiste.SISAANKAYNTI] = new Palvelupiste(tapahtumalista, TapahtumanTyyppi.DEPSK,
				"Sisäänkäynti", this);
		palvelupisteet[Palvelupiste.GRILLI] = new Palvelupiste(tapahtumalista, TapahtumanTyyppi.DEPGR, "Grilli", this);
		palvelupisteet[Palvelupiste.MAAILMANPYORA] = new Palvelupiste(480, tapahtumalista, TapahtumanTyyppi.DEPMP,
				"Maailmanpyörä", this);
		palvelupisteet[Palvelupiste.VUORISTORATA] = new Palvelupiste(120, tapahtumalista, TapahtumanTyyppi.DEPVR,
				"Vuoristorata", this);
		palvelupisteet[Palvelupiste.KARUSELLI] = new Palvelupiste(300, tapahtumalista, TapahtumanTyyppi.DEPKS,
				"Karuselli", this);
		palvelupisteet[Palvelupiste.VIIKINKILAIVA] = new Palvelupiste(300, tapahtumalista, TapahtumanTyyppi.DEPVL,
				"Viikinkilaiva", this);
		palvelupisteet[Palvelupiste.KUMMITUSJUNA] = new Palvelupiste(180, tapahtumalista, TapahtumanTyyppi.DEPKJ,
				"Kummitusjuna", this);

		this.simuloinninKesto = 0;
		this.kokoTulot = 0;
		this.aurinkoTulot = 0;
		this.sadeTulot = 0;
		this.lahteneet = 0;
		this.saapuneet = 0;
		this.lahtiRuuhka = 0;
		this.lahtiSade = 0;
		this.keskLapimenoAika = 0;
		this.sateenKesto = 0;
		this.sateidenLkm = 0;
		this.paiva = "";
		this.kellonAika = "";
		this.lahteneetAsiakkaat = new ArrayList<Asiakas>();
	}

	@Override
	protected void alustukset() {
		palvelupisteet[Palvelupiste.SISAANKAYNTI].setKapasiteetti(1);
		palvelupisteet[Palvelupiste.GRILLI].setKapasiteetti(1);

		// SATEEN SAAPUMISPROSESSI
		//Trace.out(Trace.Level.INFO, "Sateen tarkasteluväliaika: " + Saa.getInstance().getTarkasteluvaliAika());
		if (Saa.getInstance().getSateenTodnak() != 0) {
			saapumisprosessiSade = new Saapumisprosessi(new Negexp(Saa.getInstance().getTarkasteluvaliAika(), 5),
					tapahtumalista, TapahtumanTyyppi.ARRSD);
			saapumisprosessiSade.generoiSeuraava();
		}
		asetaAurinko();

		// ASIAKKAAN SAAPUMISPROSESSI
		//Trace.out(Trace.Level.INFO, "Asiakkaiden saapumisväli keskiarvo: " + this.getSaapumisVali() + " s");
		saapumisprosessi = new Saapumisprosessi(new Negexp(this.getSaapumisVali(), 5), tapahtumalista,
				TapahtumanTyyppi.ARRSK);
		saapumisprosessi.generoiSeuraava();
	}

	@Override
	protected void suoritaTapahtuma(Tapahtuma tapahtuma) {
		Asiakas a;
		Palvelupiste pp;
		switch (tapahtuma.getTyyppi()) {
		case ARRSK:
			if (palvelupisteet[Palvelupiste.SISAANKAYNTI].onkoTilaa()) {
				palvelupisteet[Palvelupiste.SISAANKAYNTI].lisaaJonoon(new Asiakas(this));
				saapumisprosessi.generoiSeuraava();
				break;
			}
			saapumisprosessi.generoiSeuraava();
			break;
		case DEPSK:
			pp = palvelupisteet[Palvelupiste.SISAANKAYNTI];
			a = pp.otaJonosta();
			pp.korotaAktiiviAika(a.getId());
			palvelupisteet[jononTarkastus()].lisaaJonoon(a);
			this.visuaaliPalvelussa("Sisäänkäynti", 0);
			saapuneet++;
			break;
		case DEPGR:
			pp = palvelupisteet[Palvelupiste.GRILLI];
			a = pp.otaJonosta();
			pp.korotaAktiiviAika(a.getId());
			toteutaTapahtuma(a, Palvelupiste.GRILLI);
			this.visuaaliPalvelussa("Grilli", 0);
			break;
		case DEPMP:
			pp = palvelupisteet[Palvelupiste.MAAILMANPYORA];
			a = pp.otaJonosta();
			pp.korotaAktiiviAika(a.getId());
			toteutaTapahtuma(a, Palvelupiste.MAAILMANPYORA);
			this.visuaaliPalvelussa("Maailmanpyörä", 0);
			break;
		case DEPVR:
			pp = palvelupisteet[Palvelupiste.VUORISTORATA];
			a = pp.otaJonosta();
			pp.korotaAktiiviAika(a.getId());
			toteutaTapahtuma(a, Palvelupiste.VUORISTORATA);
			this.visuaaliPalvelussa("Vuoristorata", 0);
			break;
		case DEPKS:
			pp = palvelupisteet[Palvelupiste.KARUSELLI];
			a = pp.otaJonosta();
			pp.korotaAktiiviAika(a.getId());
			toteutaTapahtuma(a, Palvelupiste.KARUSELLI);
			this.visuaaliPalvelussa("Karuselli", 0);
			break;
		case DEPVL:
			pp = palvelupisteet[Palvelupiste.VIIKINKILAIVA];
			a = pp.otaJonosta();
			pp.korotaAktiiviAika(a.getId());
			toteutaTapahtuma(a, Palvelupiste.VIIKINKILAIVA);
			this.visuaaliPalvelussa("Viikinkilaiva", 0);
			break;
		case DEPKJ:
			pp = palvelupisteet[Palvelupiste.KUMMITUSJUNA];
			a = pp.otaJonosta();
			pp.korotaAktiiviAika(a.getId());
			toteutaTapahtuma(a, Palvelupiste.KUMMITUSJUNA);
			this.visuaaliPalvelussa("Kummitusjuna", 0);
			break;
		case ARRSD:
			//Trace.out(Trace.Level.INFO, "Sateen todennäköisyys: " + Saa.getInstance().getSateenTodnak());
			if (Saa.getInstance().sataako()) {
				if (Kello.getInstance().getAika() >= this.getSimulointiaika()) {
					break;
				}
				Saa.getInstance().sataa();
				Saa.getInstance().korotaSateenKertoja();
				Saa.getInstance().setSateenLoppu(this.getSimulointiaika());
				Saa.getInstance().laskeSateenKokoKesto();
				//Trace.out(Trace.Level.INFO, "Sateen loppumisen ajankohta: " + Saa.getInstance().getSateenLoppu());
				tapahtumalista.lisaa(new Tapahtuma(TapahtumanTyyppi.DEPSD, Saa.getInstance().getSateenLoppu()));
				asetaSade();
			} else {
				Saa.getInstance().eiSada();
				saapumisprosessiSade.generoiSeuraava();
				asetaAurinko();
			}
			break;
		case DEPSD:
			saapumisprosessiSade.generoiSeuraava();
			break;
		}
	}

	/**
	 * Siirretään asiakas palvelupisteen jonoon. Tarkistetaan ensin poistetaanko
	 * asiakas aikaisemmin sateen vuoksi tai jos asiakas on käynyt jo haluamisensa
	 * kohteiden käyntimäärän.
	 * 
	 * @param a     Asiakas-olio.
	 * @param piste Palvelupiste, jossa tapahtuma toteutetaan.
	 */
	public void toteutaTapahtuma(Asiakas a, int piste) {
		a.korotaKyyteja();
		if (a.onValmis()) {
			a.setPoistumisaika(Kello.getInstance().getAika());
			a.raportti();
			return;
		}
		if (Saa.getInstance().onkoSadeKaynnissa()) {
			if ((int) new Uniform(0, 100).sample() >= a.getSateenToleranssi()) {
				a.lahtiAikaisinSade();
				a.setPoistumisaika(Kello.getInstance().getAika());
				a.raportti();
				return;
			}
		}
		palvelupisteet[jononTarkastus()].lisaaJonoon(a);
	}

	/**
	 * Tarkistaa onko kohteen jonossa tilaa, jos kohteessa ei ole tilaa niin
	 * arvotaan seuraava kohde. Uusia kohteita arvotaan maksimissaan kolme.
	 * 
	 * @return <b>Palvelupisteet</b> taulukon indeksin, johon <b>asiakas</b>
	 *         siirretään.
	 */
	private int jononTarkastus() {
		int jono = 0;
		for (int i = 0; i < 3; i++) {
			jono = jononArvottuNro();
			if (palvelupisteet[jono].onkoTilaa()) {
				break;
			}
			//Trace.out(Trace.Level.INFO, "Palvelupisteessä " + palvelupisteet[jono].getNimi() + " ei ollut tilaa.");																											// Poista
		}
		return jono;
	}

	/**
	 * Arpoo <b>palvelupisteiden</b> suosioiden perusteella mihin jonoon asiakas
	 * siirtyy. Suosioita käytetään sääolosuhteen mukaan, joko palvelupisteiden
	 * suosioita aurinkoisella säällä tai sateella.
	 * 
	 * @return <b>Palvelupisteet</b> taulukon indeksi.
	 */
	private int jononArvottuNro() {
		Uniform uni = new Uniform(0, 100, System.nanoTime());
		int arvottuLuku = Math.abs((int) uni.sample());
		int kokonaisSuosio = 0, suosioGR = 0, suosioMP = 0, suosioVR = 0, suosioKS = 0, suosioVL = 0, suosioKJ = 0;

		if (Saa.getInstance().onkoSadeKaynnissa()) {
			suosioGR = palvelupisteet[Palvelupiste.GRILLI].getSuosioSade();
			suosioMP = palvelupisteet[Palvelupiste.MAAILMANPYORA].getSuosioSade();
			suosioVR = palvelupisteet[Palvelupiste.VUORISTORATA].getSuosioSade();
			suosioKS = palvelupisteet[Palvelupiste.KARUSELLI].getSuosioSade();
			suosioVL = palvelupisteet[Palvelupiste.VIIKINKILAIVA].getSuosioSade();
			suosioKJ = palvelupisteet[Palvelupiste.KUMMITUSJUNA].getSuosioSade();
		} else {
			suosioGR = palvelupisteet[Palvelupiste.GRILLI].getSuosioAurinko();
			suosioMP = palvelupisteet[Palvelupiste.MAAILMANPYORA].getSuosioAurinko();
			suosioVR = palvelupisteet[Palvelupiste.VUORISTORATA].getSuosioAurinko();
			suosioKS = palvelupisteet[Palvelupiste.KARUSELLI].getSuosioAurinko();
			suosioVL = palvelupisteet[Palvelupiste.VIIKINKILAIVA].getSuosioAurinko();
			suosioKJ = palvelupisteet[Palvelupiste.KUMMITUSJUNA].getSuosioAurinko();
		}

		kokonaisSuosio += suosioGR - 1;
		if (arvottuLuku <= kokonaisSuosio) {
			return Palvelupiste.GRILLI;
		}
		kokonaisSuosio += suosioMP;
		if (arvottuLuku <= kokonaisSuosio) {
			return Palvelupiste.MAAILMANPYORA;
		}
		kokonaisSuosio += suosioVR;
		if (arvottuLuku <= kokonaisSuosio) {
			return Palvelupiste.VUORISTORATA;
		}
		kokonaisSuosio += suosioKS;
		if (arvottuLuku <= kokonaisSuosio) {
			return Palvelupiste.KARUSELLI;
		}
		kokonaisSuosio += suosioVL;
		if (arvottuLuku <= kokonaisSuosio) {
			return Palvelupiste.VIIKINKILAIVA;
		}
		kokonaisSuosio += suosioKJ;
		if (arvottuLuku <= kokonaisSuosio) {
			return Palvelupiste.KUMMITUSJUNA;
		}
		return -1;
	}

	@Override
	protected void tulokset() {		
		/*Trace.out(Trace.Level.INFO, "\n\t---" + "Huvipuiston yleiset tulokset" + "---");
		Trace.out(Trace.Level.INFO, "Simuloinnin kokonaiskesto: " + Kello.getInstance().formatoiKello(Kello.getInstance().getAika()));
		Trace.out(Trace.Level.INFO, "Simuloinnin tulot: " + getKokonaistuotot() + "€");
		Trace.out(Trace.Level.INFO, "Simuloinnin tulot sateisella säällä: " + getSateenTulot() + "€");
		Trace.out(Trace.Level.INFO, "Simuloinnin tulot aurinkoisella säällä: " + getAurinkoisenTulot() + "€");
		Trace.out(Trace.Level.INFO, "Simuloinnin saapuneiden asiakkaiden määrä: " + this.saapuneet);
		Trace.out(Trace.Level.INFO, "Simuloinnin lähteneiden asiakkaiden määrä: " + this.lahteneet);
		Trace.out(Trace.Level.INFO, "Simuloinnin aikaisin lähteneiden asiakkaiden määrä (ruuhka): " + this.lahtiRuuhka);
		Trace.out(Trace.Level.INFO, "Simuloinnin aikaisin lähteneiden asiakkaiden määrä (sade): " + this.lahtiSade);
		Trace.out(Trace.Level.INFO, "Simuloinnin palveltujen asiakkaiden määrä palvelupisteissä: " + getPalveltujeAsiakkaidenLkm());
		
		Saa.getInstance().raportti(); */
		
		asetaSimValmisTeksti();
		setPaiva();
		setKello();
		
		this.simuloinninKesto = Kello.getInstance().getAika();
		this.kokoTulot = getKokonaistuotot();
		this.aurinkoTulot = getAurinkoisenTulot();
		this.sadeTulot = getSateenTulot();
		this.sateenKesto = Saa.getInstance().getSateenKokoKesto();
		this.sateidenLkm = Saa.getInstance().getSadeKerrat();
		this.lahteneet = this.lahteneetAsiakkaat.size();

		double ajat = 0.0;
		for (Asiakas a : lahteneetAsiakkaat) {
			this.asiakkaatSet.add(a);
			if (a.isLahtiAikaisinRuuhka()) {
				this.lahtiRuuhka++;
			}
			if (a.isLahtiAikaisinSade()) {
				this.lahtiSade++;
			}
			ajat += a.getVietettyAika();
		}

		
		this.keskLapimenoAika = ajat / this.lahteneetAsiakkaat.size();
		
		// Jos keskLapimenoAika = NaN niin aseta arvoksi 0
		if(Double.isNaN(this.keskLapimenoAika)) {
			this.keskLapimenoAika = 0.0;
		}
		
		for (Palvelupiste p : palvelupisteet) {
			p.raportti();
			this.ppSet.add(p);
		}
		this.dao.luoOmaMoottori(this);
	}

	@Override
	public void setSaapumisVali(double aika) {
		this.saapumisVali = aika;
	}

	/**
	 * Palauttaa <b>asiakkaiden</b> saapumisväliaikojen keskiarvon.
	 * 
	 * @return Keskiarvo asiakkaiden saapumisille.
	 */
	public double getSaapumisVali() {
		return this.saapumisVali;
	}

	@Override
	public void setJpituus(int pituus, int piste) {
		this.palvelupisteet[piste].setJononPituus(pituus);
	}

	@Override
	public void setHinta(double hinta, int piste) {
		this.palvelupisteet[piste].setHinta(hinta);
	}

	@Override
	public void setSuosioA(int suosio, int piste) {
		this.palvelupisteet[piste].setSuosioAurinko(suosio);
	}

	@Override
	public void setSuosioS(int suosio, int piste) {
		this.palvelupisteet[piste].setSuosioSade(suosio);
	}

	@Override
	public void setlaiteKapasiteetti(int kapasiteetti, int piste) {
		this.palvelupisteet[piste].setKapasiteetti(kapasiteetti);
	}

	@Override
	public void setGenerator(double max, int piste) {
		this.palvelupisteet[piste].setGenerator(new Uniform(60, max + 1));
	}

	@Override
	public void visuaaliLisaaJonoon(String piste) {
		this.kontrolleri.visuaaliLisaaJonoon(piste);
	}

	@Override
	public void visuaaliPoistaJonosta(String piste) {
		this.kontrolleri.visuaaliPoistaJonosta(piste);
	}

	@Override
	public void visuaaliPalvelussa(String piste, int asiakasMaara) {
		this.kontrolleri.visuaaliPalvelussa(piste, asiakasMaara);
	}

	@Override
	public void asetaAurinko() {
		this.kontrolleri.asetaAurinko();
	}

	@Override
	public void asetaSade() {
		this.kontrolleri.asetaSade();
	}

	@Override
	public void asetaSimValmisTeksti() {
		this.kontrolleri.asetaSimValmisTeksti();
	}

	/**
	 * Asettaa päivämäärän simulointiajolle formatoituna muotoon dd/MM/yyyy.
	 */
	public void setPaiva() {
		SimpleDateFormat muunnaPaiva = new SimpleDateFormat("dd/MM/yyyy");
		this.paiva = muunnaPaiva.format(new Date());
	}

	/**
	 * Palauttaa simulointiajon päivämään merkkijonona.
	 * 
	 * @return Simulointiajon päivämäärä
	 */
	public String getPaiva() {
		return this.paiva;
	}

	/**
	 * Asettaa kellonajan simulointiajolle formatoituna muotoon HH:mm.
	 */
	public void setKello() {
		SimpleDateFormat muunnaKello = new SimpleDateFormat("HH:mm");
		this.kellonAika = muunnaKello.format(new Date(System.currentTimeMillis()));
	}

	/**
	 * Palauttaa simulointiajon kellonajan merkkijonona.
	 * 
	 * @return Simulointiajon kellonaika.
	 */
	public String getKellonAika() {
		return this.kellonAika;
	}

	/**
	 * Palauttaa kuinka paljon asiakkaita saapui huvipuistoon.
	 * 
	 * @return Saapuneiden asiakkaiden lukumäärä.
	 */
	public int getSaapuneet() {
		return this.saapuneet;
	}

	/**
	 * Palauttaa simulointiajon yksilöivän id:n.
	 * 
	 * @return Simulointiajon id.
	 */
	public int getIdSimulointi() {
		return this.idSimulointi;
	}

	/**
	 * Palauttaa huvipuiston asiakkaiden keskimääräisen läpimenoajan.
	 * 
	 * @return Asiakkaiden keskimääräinen läpimenoaika.
	 */
	public double getKeskLapimenoAika() {
		return this.keskLapimenoAika;
	}

	/**
	 * Palauttaa ajan kuinka kauan simulointia ajettiin.
	 * 
	 * @return Simuloinnin kesto.
	 */
	public double getSimuloinninKesto() {
		return this.simuloinninKesto;
	}

	/**
	 * Palauttaa huvipuiston tuottamat tulot.
	 * 
	 * @return Kokonaistulo.
	 */
	public double getKokoTulot() {
		return this.kokoTulot;
	}

	/**
	 * Palauttaa huvipuiston tuottamat tulot aurinkoisella säällä.
	 * 
	 * @return Tulot aurinkoisella säällä.
	 */
	public double getAurinkoTulot() {
		return this.aurinkoTulot;
	}

	/**
	 * Palauttaa huvipuiston tuottamat tulot sateella.
	 * 
	 * @return Tulot sateella.
	 */
	public double getSadeTulot() {
		return this.sadeTulot;
	}

	/**
	 * Palauttaa huvipuistosta poistuneiden asiakkaiden lukumäärän.
	 * 
	 * @return Poistuneiden asiakkaiden lukumäärä.
	 */
	public int getLahteneet() {
		return this.lahteneet;
	}

	/**
	 * Palauttaa asiakkaiden lukumäärän, jotka poistuivat huvipuistosta ruuhkien
	 * vuoksi.
	 * 
	 * @return Poistuneiden asiakkaiden lukumäärä.
	 */
	public int getLahtiRuuhka() {
		return this.lahtiRuuhka;
	}

	/**
	 * Palauttaa asiakkaiden lukumäärän, jotka poistuivat huvipuistosta sateen
	 * vuoksi.
	 * 
	 * @return Poistuneiden asiakkaiden lukumäärä.
	 */
	public int getLahtiSade() {
		return this.lahtiSade;
	}

	/**
	 * Palauttaa simuloinnin aikana sataneiden sadekertojen kokonaisajan.
	 * 
	 * @return Sateiden kestot yhteensä.
	 */
	public double getSateenKesto() {
		return this.sateenKesto;
	}

	/**
	 * Palauttaa sadekertojen lukumäärän.
	 * 
	 * @return Sadekerrat.
	 */
	public int getSateidenLkm() {
		return this.sateidenLkm;
	}

	/**
	 * <b>Asiakas</b>-olio lisätään <b>lahteneetAsiakkaat</b>-listaan.
	 * 
	 * @param a Huvipuistosta lähtenyt <b>asiakas</b>-olio.
	 */
	public void lisaaLahtenyt(Asiakas a) {
		this.lahteneetAsiakkaat.add(a);
	}

	/**
	 * Palauttaa DAO-olion.
	 * 
	 * @return DAO-olio.
	 */
	public IDAO getDAO() {
		return this.dao;
	}

	/**
	 * Palauttaa kokoelman asiakkaita.
	 * 
	 * @return Kokoelma asiakkaita.
	 */
	public Set<Asiakas> getAsiakkaatSet() {
		return asiakkaatSet;
	}

	/**
	 * Asettaa kokoelman asiakkaita.
	 * 
	 * @param asiakkaatSet Kokoelma asiakkaita.
	 */
	public void setAsiakkaatSet(Set<Asiakas> asiakkaatSet) {
		this.asiakkaatSet = asiakkaatSet;
	}

	/**
	 * Palauttaa kokoelman palvelupisteitä.
	 * 
	 * @return Kokoelma palvelupisteitä.
	 */
	public Set<Palvelupiste> getPpSet() {
		return ppSet;
	}

	/**
	 * Asettaa kokoelman palvelupisteitä.
	 * 
	 * @param ppSet Kokoelma palvelupisteitä.
	 */
	public void setPpSet(Set<Palvelupiste> ppSet) {
		this.ppSet = ppSet;
	}

	/**
	 * Asettaa simulointiajon id:n.
	 * 
	 * @param idSimulointi Simulointiajon id.
	 */
	public void setIdSimulointi(int idSimulointi) {
		this.idSimulointi = idSimulointi;
	}

	/**
	 * Asettaa simuloinnin keston.
	 * 
	 * @param simuloinninKesto Simuloinnin kesto.
	 */
	public void setSimuloinninKesto(double simuloinninKesto) {
		this.simuloinninKesto = simuloinninKesto;
	}

	/**
	 * Asettaa huvipuiston tuottamat kokonaistulot.
	 * 
	 * @param kokoTulot Tuottamat tulot.
	 */
	public void setKokoTulot(double kokoTulot) {
		this.kokoTulot = kokoTulot;
	}

	/**
	 * Asettaa huvipuiston tuottamat tulot aurinkoisella säällä.
	 * 
	 * @param aurinkoTulot Tuotetut tulot aurinkoisella säällä.
	 */
	public void setAurinkoTulot(double aurinkoTulot) {
		this.aurinkoTulot = aurinkoTulot;
	}

	/**
	 * Asettaa huvipuiston tuottamat tulot sateella.
	 * 
	 * @param sadeTulot Tuotetut tulot sateella.
	 */
	public void setSadeTulot(double sadeTulot) {
		this.sadeTulot = sadeTulot;
	}

	/**
	 * Asettaa huvipuistoon saapuneiden asiakkaiden lukumäärän.
	 * 
	 * @param saapuneet Lukumäärä saapuneista asiakkaista.
	 */
	public void setSaapuneet(int saapuneet) {
		this.saapuneet = saapuneet;
	}

	/**
	 * Asettaa huvipuistosta poistuneiden asiakkaiden lukumäärän.
	 * 
	 * @param lahteneet Lukumäärä poistuneista asiakkaista.
	 */
	public void setLahteneet(int lahteneet) {
		this.lahteneet = lahteneet;
	}

	/**
	 * Asettaa lukumäärän, kuinka monta asiakasta poistui huvipuistosta ruuhkan
	 * vuoksi.
	 * 
	 * @param lahtiRuuhka Lukumäärä poistuneista asiakkaista.
	 */
	public void setLahtiRuuhka(int lahtiRuuhka) {
		this.lahtiRuuhka = lahtiRuuhka;
	}

	/**
	 * Asettaa lukumäärän, kuinka monta asiakasta poistui huvipuistosta sateen
	 * vuoksi.
	 * 
	 * @param lahtiSade Lukumäärä poistuneista asiakkaista.
	 */
	public void setLahtiSade(int lahtiSade) {
		this.lahtiSade = lahtiSade;
	}

	/**
	 * Asettaa ajan, kuinka kauan simuloinnin aikana satoi.
	 * 
	 * @param sateenKesto Sateen kesto.
	 */
	public void setSateenKesto(double sateenKesto) {
		this.sateenKesto = sateenKesto;
	}

	/**
	 * Asettaa sateiden lukumäärän.
	 * 
	 * @param sateidenLkm Sateiden lukumäärä.
	 */
	public void setSateidenLkm(int sateidenLkm) {
		this.sateidenLkm = sateidenLkm;
	}

	/**
	 * Asettaa huvipuiston asiakkaiden keskimääräisen läpimenoajan.
	 * 
	 * @param keskLapimenoAika Keskimääräinen läpimenoaika.
	 */
	public void setKeskLapimenoAika(double keskLapimenoAika) {
		this.keskLapimenoAika = keskLapimenoAika;
	}

	/**
	 * Laskee yhteen kaikkien palvelupisteiden tulot sateella ja aurinkoisella
	 * säällä ja palauttaa kaikkien palvelupisteiden kokonaistulot yhteensä.
	 * 
	 * @return Palvelupisteiden kokonaistulot.
	 */
	private double getKokonaistuotot() {
		double tulot = 0.0;
		for (Palvelupiste piste : palvelupisteet) {
			tulot += piste.getTulotAurinko() + piste.getTulotSade();
		}
		return tulot;
	}

	/**
	 * Laskee yhteen ja palauttaa kaikkien <b>palvelupisteiden</b> saadut tulot
	 * sateella.
	 * 
	 * @return Palvelupisteiden kokonaistulot sateella.
	 */
	private double getSateenTulot() {
		double tulot = 0.0;
		for (Palvelupiste piste : palvelupisteet) {
			tulot += piste.getTulotSade();
		}
		return tulot;
	}

	/**
	 * Laskee yhteen ja palauttaa kaikkien <b>palvelupisteiden</b> saadut tulot
	 * aurinkoisella säällä.
	 * 
	 * @return Palvelupisteiden kokonaistulot aurinkoisella säällä.
	 */
	private double getAurinkoisenTulot() {
		double tulot = 0.0;
		for (Palvelupiste piste : palvelupisteet) {
			tulot += piste.getTulotAurinko();
		}
		return tulot;
	}

	/**
	 * Laskee yhteen ja palauttaa kaikkien <b>palvelupisteiden</b> palvelutujen
	 * asiakkaiden lukumäärän.
	 * 
	 * @return Lukumäärä palvelupisteiden palvelluista asiakkaista.
	 */
	private int getPalveltujeAsiakkaidenLkm() {
		int palveltujenLkm = 0;
		for (Palvelupiste piste : palvelupisteet) {
			palveltujenLkm += piste.getPalveltuja();
		}
		return palveltujenLkm;
	}

	/**
	 * Comparable<> rajapinnan ylikirjoitettu metodi, jonka avulla voidaan järjestää
	 * omaMoottori-oliot niiden simulointi id numeroiden perusteella.
	 */
	@Override
	public int compareTo(OmaMoottori o) {
		if (this.idSimulointi < o.idSimulointi) {
			return -1;
		} else if (this.idSimulointi > o.idSimulointi) {
			return 1;
		}
		return 0;
	}

	/**
	 * Ylikirjoitettu metodi, jolla asetetaan merkkijono omaMoottori-oliolle.
	 * Merkkijono sisältää omaMoottoria kuvavaan simulointiajon id:n, sekä päivän ja
	 * kellon ajan milloin omaMoottori olio tallennettiin tietokantaan.
	 * 
	 * @return merkkijono omaMoottori-olion tilasta.
	 */
	@Override
	public String toString() {
		return this.idSimulointi + " (" + this.paiva + " - " + this.kellonAika + ")";
	}
}