package simu.framework;

import controller.IKontrolleri;
import simu.model.Palvelupiste;

/**
 * Simulaattorin pääohjain, joka huolehtii simuloinnin kulusta. Suorittaa
 * simulaattorin A-, B- ja C-vaiheita.
 * 
 * @author Otso Poussa, Tanja Pyykönen ja Tatu Talvikko
 * @version 1.0
 */
public abstract class Moottori extends Thread implements IMoottori {

	/**
	 * Aika kuinka kauan simulointia ajetaan.
	 */
	private double simulointiaika;

	/**
	 * Viiven arvo, joka määrittää moottori säikeen nukkuma-ajan.
	 */
	private long viive = 0;

	/**
	 * Muuttuja, johon sijoitetaan kello singletonin instanssi.
	 */
	private Kello kello;

	/**
	 * Tapahtumalista-olio, joka pitää sisällään B-vaiheen tapahtumia.
	 */
	protected Tapahtumalista tapahtumalista;

	/**
	 * Taulukko, joka pitää sisällään kaikki palvelupisteet C-vaiheen tapahtumia
	 * varten.
	 */
	protected Palvelupiste[] palvelupisteet;

	/**
	 * Kontrolleri, jonka avulla moottori on yhteydessä käyttöliittymään.
	 */
	protected IKontrolleri kontrolleri;

	/**
	 * Tyhjä konstruktori DAO:a varten.
	 */
	public Moottori() {}

	/**
	 * Luo <b>moottori</b> olion, jolla alustetaan muuttujat ja luodaan yhteys
	 * kontrolleriin.
	 * 
	 * @param kontrolleri tietojen hakemisia ja tuomisia varten.
	 */
	public Moottori(IKontrolleri kontrolleri) {
		this.kontrolleri = kontrolleri;
		kello = Kello.getInstance();
		tapahtumalista = new Tapahtumalista();
		simulointiaika = getSimulointiaika();
	}

	/**
	 * <b>Moottori</b> säikeen suoritusmetodi, joka alustaa simulaattorin
	 * tarvittavat alustukset mm. ensimmäisen tapahtuman luonnin ja suorittaa A-,
	 * B-, ja C-vaiheiden toistosilmukkaa. Lopuksi kutsutaan simulaation tuottamia
	 * tuloksia, jotka on määritelty <b>omaMoottorissa</b>.
	 */
	@Override
	public void run() {
		alustukset();
		while (simuloidaan()) {
			viive();
			// Trace.out(Trace.Level.INFO, "\nA-vaihe: kello on " + nykyaika());
			kello.setAika(nykyaika());

			// Trace.out(Trace.Level.INFO, "\nB-vaihe:");
			suoritaBTapahtumat();

			// Trace.out(Trace.Level.INFO, "\nC-vaihe:");
			yritaCTapahtumat();
		}
		tulokset();

	}

	/**
	 * Suorittaa simuloinnin B-vaiheen tapahtumia <b>run</b> metodissa, poistamalla
	 * tapahtumalistasta seuraavaksi vuorossa olevan tapahtuman, kun tapahtuman
	 * skeduloitu aika vastaa simuloinnin nykyistä aikaa.
	 */
	private void suoritaBTapahtumat() {
		while (tapahtumalista.getSeuraavanAika() == kello.getAika()) {
			suoritaTapahtuma(tapahtumalista.poista());
		}
	}

	/**
	 * Suorittaa simuloinnin C-vaiheen tapahtumia <b>run</b> metodissa. Palvelu
	 * aloitetaan asiakkaalle, jos palvelupiste ei ole varattuna ja palvelupisteellä
	 * on asiakkaita jonossa.
	 */
	private void yritaCTapahtumat() {
		for (Palvelupiste p : palvelupisteet) {
			if (!p.onVarattu() && p.onJonossa()) {
				p.aloitaPalvelu();
			}
		}
	}

	/**
	 * Palauttaa simuloinnin nykyajan.
	 * 
	 * @return Nykyinen aika.
	 */
	private double nykyaika() {
		return tapahtumalista.getSeuraavanAika();
	}

	@Override
	public void setSimulointiaika(double aika) {
		simulointiaika = aika;
	}

	/**
	 * Palauttaa ajan kuinka kauan simulointia ajetaan.
	 * 
	 * @return Simuloinnin ajoaika.
	 */
	public double getSimulointiaika() {
		return this.simulointiaika;
	}

	/**
	 * Palauttaa totuusarvon, joka kertoo onko simulointiaikaa vielä jäljellä.
	 * 
	 * @return Simuloinnin tila. Palauttaa <b>True</b> jos simulointiaikaa on vielä
	 *         jäljellä ja <b>False</b> jos simulointiaikaa ei ole enään jäljellä.
	 */
	private boolean simuloidaan() {
		return kello.getAika() < simulointiaika;
	}

	@Override
	public void setViive(long viive) {
		this.viive = viive;
	}

	@Override
	public long getViive() {
		return viive;
	}

	/**
	 * Käynnistää viiveen. Pistää Moottori säikeen nukkumaan <b>viiveen</b> arvon
	 * ajaksi.
	 */
	private void viive() {
		try {
			sleep(viive);
		} catch (InterruptedException e) {
			System.err.println(e.getMessage());
		}
	}

	/**
	 * Määrittelee tarvittavat alustukset simuloinnin suorittamista varten.
	 * Alustuksissa määritellään sisäänkäynti <b>palvelupisteen</b> ja sateen
	 * saapumisprosessit, sekä generoidaan niille ensimmäiset tapahtumat.
	 */
	protected abstract void alustukset();

	/**
	 * Suorittaa simuloinnin B-vaiheen tapahtumia tapahtuman tyypin perusteella.
	 * 
	 * @param tapahtuma Suoritettava tapahtuma.
	 */
	protected abstract void suoritaTapahtuma(Tapahtuma tapahtuma);

	/**
	 * Simulointiajon tuottamien tuloksien asettaminen ja tallentaminen tietokantaan
	 * DAO:n kautta.
	 */
	protected abstract void tulokset();

}