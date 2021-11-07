package simu.model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.*;

import eduni.distributions.Uniform;
import simu.framework.Tapahtumalista;
import simu.framework.Trace;
import simu.framework.Trace.Level;
import simu.framework.*;

/**
 * Testataan <b>Palvelupiste</b>-luokan metodeja.
 * 
 * @author Otso Poussa
 * @version 1.0
 */
class PalvelupisteTest {

	/**
	 * Yleinen <b>Palvelupiste</b>-olio testejä varten. Alustetaan ennen jokaista
	 * testiä.
	 */
	private Palvelupiste tp;

	/**
	 * Yleinen <b>OmaMoottori</b>-olio testejä varten.
	 */
	private OmaMoottori om = new OmaMoottori();

	/**
	 * Alustajametodi testeille.
	 */
	@BeforeAll
	public static void init() {
		Trace.setTraceLevel(Level.INFO);

	}

	/**
	 * Alustaa Maailmanpyörä <b>Palvelupiste</b>-olion <b>tp</b>-muuttujaan ja
	 * asettaa pisteen JUnit muuttujaan arvon <b>true</b>. Ajetaan ennen jokaista
	 * testiä.
	 */
	@BeforeEach
	public void setUp() {
		// Luodaan maailmanpyörä palvelupiste.
		tp = new Palvelupiste(400, new Tapahtumalista(), TapahtumanTyyppi.DEPMP, "Maailmanpyörä", new OmaMoottori());
		tp.setJunit(true);
	}

	/**
	 * Testaa Sisäänkäynnin ja Grillin kontruktoria luomalla olion ja testaamalla,
	 * löytyykö kontruktorin asettamat instanssimuuttujat oliosta.
	 */
	@Test
	@DisplayName("Constructor() 1: toimiiko constructor sisäänkäynnille ja grillille?")
	void testPalvelupisteTapahtumalistaTapahtumanTyyppiString() {
		// Luodaan sisäänkäynti
		Palvelupiste palvelu = new Palvelupiste(null, TapahtumanTyyppi.DEPSK, "Sisäänkäynti", new OmaMoottori());
		// Nimen tarkastus
		assertEquals("Sisäänkäynti", palvelu.getNimi(), "Nimi ei asetu oikein");

		// Generaattorin tarkastus
		assertEquals(null, palvelu.getGenerator(), "Generaattori on luotu");

		// Jonon tarkastus
		assertEquals(0, palvelu.getJononPituus(), "Jonon pituutta ei olla asetettu");

		// Hinnan asetus
		assertEquals(0, palvelu.getHinta(), "Hinta on väärin");

		// Auringon suosio
		assertEquals(0, palvelu.getSuosioAurinko(), "Generaattori on luotu");

		// Sateen suosio
		assertEquals(0, palvelu.getSuosioSade(), "Generaattori on luotu");

	}

	/**
	 * Testaa Huvipuistolaitteiden konstruktoria luomalla olion ja testaamalla,
	 * löytyykö kontruktorin asettamat instanssimuuttujat oliosta.
	 */
	@Test
	@DisplayName("Constructor() 2: toimiiko constructor laitteille?")

	void testPalvelupisteDoubleTapahtumalistaTapahtumanTyyppiString() {
		// Luodaan sisäänkäynti
		Palvelupiste palvelu = new Palvelupiste(200, null, TapahtumanTyyppi.DEPSK, "Sisäänkäynti", new OmaMoottori());

		// Nimen tarkastus
		assertEquals("Sisäänkäynti", palvelu.getNimi(), "Nimi ei asetu oikein");

		// Generaattorin tarkastus
		assertEquals(null, palvelu.getGenerator(), "Generaattori on luotu");

		// Jonon tarkastus
		assertEquals(0, palvelu.getJononPituus(), "Jonon pituutta ei olla asetettu");

		// Hinnan asetus
		assertEquals(0, palvelu.getHinta(), "Hinta on väärin");

		// Auringon suosio
		assertEquals(0, palvelu.getSuosioAurinko(), "Generaattori on luotu");

		// Sateen suosio
		assertEquals(0, palvelu.getSuosioSade(), "Generaattori on luotu");

	}

	/**
	 * Testaa <b>Asiakas</b>-olion lisäämistä asiakasjonoon. <br>
	 * Asettaa jonolle pituuden, lisää olion ja tarkistaa onko olio jonossa <b>onJonossa</b>-metodilla.
	 */
	@Test
	@DisplayName("lisaaJonoon(): toimiiko jonoon lisääminen?")
	void testLisaaJonoon() {
		tp.setJononPituus(1); 
		Asiakas a = new Asiakas(om);
		tp.lisaaJonoon(a);

		assertEquals(true, tp.onJonossa(), "Jonoon lisäys ei onnistunut");
	}

	/**
	 * Testaa <b>Asiakas</b>-olion ottamista asiakasjonosta. <br>
	 * Asettaa jonolle pituuden, lisää olion, poistaa sen ja tarkistaa, onko olio jonossa
	 * <b>onJonossa</b>-metodilla.
	 */
	@Test
	@DisplayName("OtaJonosta(): Testataan toimiiko jonosta ottaminen")
	void testOtaJonosta() {
		tp.setJononPituus(1); // Asetetaan jonon pituus
		tp.lisaaJonoon(new Asiakas(om));
		assertEquals(true, tp.onJonossa(), "Jonoon lisäys ei onnistunut");

		tp.otaJonosta();
		assertEquals(false, tp.onJonossa(), "Jonosta ottaminen ei onnistunut");

	}

	/**
	 * Testaa palvelun aloittamista lisäämällä <b>Asiakas</b>-olion asiakasjonoon ja
	 * kutsumalla <b>aloitaPalvelu</b>-metodia. <br>
	 * Tarkistaa onko palvelu aloitettu kutsumalla <b>onVarattu</b>-metodia.
	 */
	@Test
	@DisplayName("aloitaPalvelu(): Testataan palvelun aloittamista")
	void testAloitaPalvelu() {
		tp.setJononPituus(5); // Asetetaan jonon pituus
		tp.lisaaJonoon(new Asiakas(null));
		assertEquals(true, tp.onJonossa(), "Jonoon lisäys ei onnistunut");
		tp.aloitaPalvelu();
		assertEquals(true, tp.onVarattu(), "Palvelu ei ole aloitettu");

	}

	/**
	 * Testaa <b>onkoTilaa</b>-metodia asettmalla asiakasjonon pituuden 1 asiakkaan
	 * pituiseksi ja täyttäen sen lisäämällä siihen <b>Asiakas</b>-olion. <br>
	 * Kutsuu sen jälkeen metodia olettaen, että jonossa ei ole tilaa.
	 */
	@Test
	@DisplayName("onkoTilaa(): Testataan onko palvelupisteen jonossa tilaa")
	void testOnkoTilaa() {
		tp.setJononPituus(1); // Asetetaan jonon pituus
		tp.lisaaJonoon(new Asiakas(null));
		assertEquals(true, tp.onJonossa(), "Jonoon lisäys ei onnistunut");

		assertEquals(false, tp.onkoTilaa(), "Jono ei ole täynnä");
	}

	/**
	 * Testaa Palvelupisteen <b>ContinuousGenerator</b>-olion alustamista pisteen
	 * instanssimuuttujaan <b>setGenerator</b>-metodilla.
	 */
	@Test
	@DisplayName("setGenerator(): Testataan asettuukko generaattori.")
	void testSetGenerator() {
		tp.setGenerator(new Uniform(1, 300));
		assertNotNull(tp.getGenerator(), "Generaattorin asetus on väärin");
	}

	/**
	 * Testaa palvelunhinnan asettamista.
	 */
	@Test
	@DisplayName("setHinta(): Testataan hinnan asettamista")
	void testSetHinta() {
		tp.setHinta(10.50);
		assertEquals(10.50, tp.getHinta(), "Hinta on väärin");
	}

	/**
	 * Testaa palvelupisteen palvelukapasiteetin asettamista.
	 */
	@Test
	@DisplayName("setKapasiteetti(): Asettaako oikean laitteen kapasiteetin?")
	public void testSetKapasiteetti() {
		tp.setKapasiteetti(100);
		assertEquals(100, tp.getKapasiteetti(), "Kapasiteetin asetus virheellinen");

	}

	/**
	 * Testaa palvelupisteen nimen hakemista. <br>
	 * Alustaa palvelupisteen nimellä "kummitusjuna" ja testaa, että palauttaako
	 * <b>getNimi</b>-metodi oikean nimen.
	 */
	@Test
	@DisplayName("getNimi(): Onko palvelupisteen nimi asetettu oikein?")
	void testGetNimi() {
		// Palvelupisteen nimi asetetaan luonnissa
		Palvelupiste testi = new Palvelupiste(0.0, null, null, "kummitusjuna", new OmaMoottori());
		assertEquals("kummitusjuna", testi.getNimi(), "Nimen asettaminen ei toimi constructorissa");
	}

	/**
	 * Testaa palvelupisteen aurinkoisen sään aikaisen suosion asettamista.
	 */
	@Test
	@DisplayName("setSuosioAurinko(): Asettaako oikean auringon suosion?")
	void testSetSuosioAurinko() {
		tp.setSuosioAurinko(120);
		assertEquals(120, tp.getSuosioAurinko(), "Suosio jotain");
	}

	/**
	 * Testaa palvelupisteen sateisen sään aikaisen suosion asettamista.
	 */
	@Test
	@DisplayName("setSuosioSade(): Asettaako oikean sateen suosion?")
	void testSetSuosioSade() {
		tp.setSuosioSade(120);
		assertEquals(120, tp.getSuosioSade(), "Suosio jotain");
	}

	/**
	 * Testaa palveltujen asiakkaiden määrän laskemista. <br>
	 * Lisää jonoon asiakkaita ja ottaa niitä pois tarkistaen palveltujen määrän
	 * metodilla.
	 */
	@Test
	@DisplayName("testGetPalveltuja(): Toimiiko palveltujen asiakkaiden palauttaminen?")
	void testGetPalveltuja() {
		tp.setJononPituus(10); // Asetetaan jonon pituus
		for (int i = 0; i < 5; i++) {
			tp.lisaaJonoon(new Asiakas(null));
		}
		assertEquals(true, tp.onJonossa(), "Jonoon lisäys ei onnistunut");

		tp.otaJonosta();
		assertEquals(true, tp.onJonossa(),
				"Jonosta ottaminen ei onnistunut: Jono tyhjentyi yhden asiakkaan pois ottamisen jälkeen.");
		assertEquals(1, tp.getPalveltuja(), "Palveltujen korotus on väärin");

		for (int i = 0; i < 4; i++) {
			tp.otaJonosta();
		}
		assertEquals(5, tp.getPalveltuja(), "Palveltujen korotus on väärin ");

	}

	/**
	 * Testaa asiakkaiden lisäämistä asiakasjonoon. <br>
	 * Asettaa asiakasjonon pituudeksi ja palvelupisteen kapasiteetiksi 5, täyttää
	 * jonon asiakkailla ja aloittaa palvelun. Testaa sen jälkeen, onko saapuneita
	 * 5.
	 */
	@DisplayName("testGetSaapuneita(): Lisätäänkö asiakkaat jonoon?")
	@Test
	void testGetSaapuneita() {
		tp.setJononPituus(5); // Asetetaan jonon pituus
		tp.setKapasiteetti(5);
		for (int i = 0; i < 5; i++) {
			tp.lisaaJonoon(new Asiakas(null));
		}
		assertEquals(true, tp.onJonossa(), "Jonoon lisäys ei onnistunut");

		tp.aloitaPalvelu();
		assertEquals(true, tp.onVarattu(), "Palvelu ei ole aloitettu");
		assertEquals(5, tp.getSaapuneita(), "Saapuneiden korotus on väärin");

	}

	/**
	 * Testaa sateisen sään tulojen korottamista. <br>
	 * korottaa tuloja eri määrillä ja testaa palautuuko yhteen laskettu määrä.
	 */
	@Test
	@DisplayName("laskeTulotSade(): Testataan toimiiko sateen tulojen korotus.")
	void testLaskeTulotSade() {
		tp.korotaTulotSade(500.50);
		tp.korotaTulotSade(150.20);
		tp.korotaTulotSade(1000.30);
		assertEquals(1651.0, tp.getTulotSade(), "Sateen tulot ovat väärin");
	}

	/**
	 * Testaa aurinkoisen sään tulojen korottamista. <br>
	 * korottaa tuloja eri määrillä ja testaa palautuuko yhteen laskettu määrä.
	 */
	@Test
	@DisplayName("laskeTulotAurinko(): Testataan toimiiko auringon tulojen korotus.")
	void testLaskeTulotAurinko() {
		tp.korotaTulotAurinko(1500.0);
		tp.korotaTulotAurinko(1000.0);
		tp.korotaTulotAurinko(2000.0);
		assertEquals(4500.0, tp.getTulotAurinko(), "Auringon tulot ovat väärin");
	}

	/**
	 * Testaa aurinkoisen ja sateisen sään tulojen yhteen laskevaa metodia. <br>
	 * Korottaa aurinkoisen ja sateisen sään tuloja ja kutsuu sen jälkeen
	 * <b>laskeTulotYhteensa</b>-metodia ja tarkistaa onko tulot laskettu oikein.
	 */
	@Test
	@DisplayName("getTulotYhteensa(): Saadanko kaikki palvelupisteen tulot? (sade+aurinko)")
	void testGetTulotYhteensa() {
		// Lisätään tuloja
		tp.korotaTulotAurinko(1500.0);
		tp.korotaTulotAurinko(1000.0);
		tp.korotaTulotAurinko(2000.0);

		tp.korotaTulotSade(500.50);
		tp.korotaTulotSade(150.20);
		tp.korotaTulotSade(1000.30);

		tp.laskeTulotYhteensa();
		assertEquals(6151.0, tp.getTulotYhteensa(), "Tulot ovat väärin");
	}

	/**
	 * Testaa palveluajan keskiarvon laskevaa metodia. <br>
	 * Lisää 3 palveluaikaa palveluaika-HashMappiin ja kutsuu sen jälkeen aikojen
	 * keskiarvoa laskevaa metodia.
	 */
	@Test
	@DisplayName("testPalveluAjanKeskiarvo(): Lasketaanko tallennettujen palveluaikojen keskiarvo oikein?")
	void testPalveluAjanKeskiarvo() {
		tp.lisaaPalveluAika(1, 5);
		tp.lisaaPalveluAika(2, 5);
		tp.lisaaPalveluAika(3, 5);

		tp.laskePalveluaikaKeskiarvo();
		assertEquals(5.0, tp.getPalveluaikaKeskiarvo(), "Keskiarvo laskettu väärin!");
	}

	/**
	 * Testaa jonotusaikoja lisäävää metodia. <br>
	 * Asettaa Kelloajan 100 sekuntiin ja lisää 4 ja 6 sekunnnin kohdalla alkaneet
	 * jonotusajat. Alustaa <b>jonotusAjat</b>-listan muuttujaan ja tarkistaa, että
	 * listassa oleva aika on oikein.
	 */
	@Test
	@DisplayName("testLisaaJonotusAika(): Lisätäänkö jonotusajat oikein")
	void testLisaaJonotusAika() {
		Kello.getInstance().setAika(100);
		tp.lisaaJonotusAika(4);

		List<Double> jonotusAjat = tp.getJonotusAjat();
		assertEquals(96, jonotusAjat.get(0), "Jonotusaikojen laskeminen ei toimi.");

		tp.lisaaJonotusAika(6);
		jonotusAjat = tp.getJonotusAjat();
		assertEquals(94, jonotusAjat.get(1), "Jonotusaikojen laskeminen ei toimi.");
	}

	/**
	 * Testaa jonotusajan keskiarvon laskevaa metodia. <br>
	 * Asettaa Kelloajan 10 sekuntiin ja lisää eri sekuntien kohdilla alkaneita
	 * jonotusaikoja. Kutsuu keskiarvoa laskevaa metodia ja tarkistaa, että
	 * keskiarvo on laskettu oikein.
	 */
	@Test
	@DisplayName("testJonotusAjanKeskiarvo(): Lasketaanko jonotusajan keskiarvo oikein?")
	void testJonotusAjanKeskiarvo() {
		Kello.getInstance().setAika(10);

		tp.lisaaJonotusAika(5);
		tp.lisaaJonotusAika(4);
		tp.laskeJonotusaikaKeskiarvo();
		assertEquals(5.5, tp.getJonotusaikaKeskiarvo(), "Jonotusaikojen keskiarvon laskeminen ei toimi.");

		tp.lisaaJonotusAika(6);
		tp.laskeJonotusaikaKeskiarvo();
		assertEquals(5, tp.getJonotusaikaKeskiarvo(), "Jonotusaikojen keskiarvon laskeminen ei toimi.");
	}

	/**
	 * Testaa läpimenoaikoja lisäävää metodia. <br>
	 * Lisää jonotus- sekä palveluaikoja <b>lisaaJonotusAika</b> ja
	 * <b>lisaaLapimenoAika</b> metodilla. Alustaa läpimenoajat sisältävän HashMapin
	 * muuttujaan ja tarkistaa HashMapin alkioista onko ajat lisätty listaan.
	 */
	@Test
	@DisplayName("testLisaaLapimenoAika(): Lisätäänkö oikein laskettu läpimenoaika HashMappiin?")
	void testLisaaLapimenoAika() {
		Kello.getInstance().setAika(10);
		tp.lisaaJonotusAika(5);
		tp.lisaaLapimenoAika(1, 5);

		Map<Integer, Double> lapimenot = tp.getLapimenoajat();
		assertEquals(10.0, lapimenot.get(1), "Läpimenoajan lisäys ei toimi!");

		tp.lisaaLapimenoAika(2, 10);

		lapimenot = tp.getLapimenoajat();
		assertEquals(15.0, lapimenot.get(2), "Läpimenoajan lisäys ei toimi!");
	}

	/**
	 * Testaa läpimenoaikojen keskiarvon laskevaa metodia. <br>
	 * Asettaa kellon ajan 10 sekuntiin ja lisää jonotus- sekä palveluaikoja
	 * <b>lisaaJonotusAika</b> ja <b>lisaaLapimenoAika</b> metodilla. Kutsuu sen
	 * jälkeen keskiarvon laskevaa <b>laskeLapimenoaikaKeskiarvo</b>-metodia ja
	 * tarkistaa onko keskiarvo laskettu oikein.
	 */
	@Test
	@DisplayName("testLapimenoAjanKeskiarvo(): Lasketaanko läpimenoajan keskiarvo oikein?")
	void testLapimenoAjanKeskiarvo() {
		Kello.getInstance().setAika(10);
		tp.lisaaJonotusAika(0);
		tp.lisaaLapimenoAika(1, 5);
		tp.lisaaJonotusAika(5);
		tp.lisaaLapimenoAika(2, 10);
		tp.laskeLapimenoaikaKeskiarvo();

		assertEquals(15, tp.getLapimenoaikaKeskiarvo(), "Läpimenoaikojen keskiarvon laskeminen ei toimi!");
	}

	/**
	 * Testaa suoritustehon laskevaa metodia. <br>
	 * Alustaa palvelupisteen <b>OmaMoottori</b>-olion muuttujaan ja asettaa sille
	 * 14400 sekunnin simulointiajan. Sen jälkeen asettaa palvelupisteen palveltujen
	 * asiakkaiden määrän 2000 asiakkaaseen ja kutsuu
	 * <b>laskeSuoritusteho</b>-metodia. Tarkistaa sen jälkeen, onko suoritusteho
	 * laskettu oikein.
	 */
	@Test
	@DisplayName("testSuoritusTeho(): Lasketaanko suoritusteho oikein?")
	void testSuoritusTeho() {
		om = tp.getOmaMoottori();
		om.setSimulointiaika(14400);
		tp.setPalveltuja(2000);
		tp.laskeSuoritusteho();

		assertEquals(500, tp.getSuoritusteho(), "Suoritustehon laskeminen ei toimi.");
	}

	/**
	 * Testaa käyttöasteen laskevaa metodia. <br>
	 * Alustaa palvelupisteen <b>OmaMoottori</b>-olion muuttujaan ja asettaa sille
	 * 500 sekunnin simulointiajan. Sen jälkeen asettaa palvelupisteen aktiiviajaksi
	 * 100 sekuntia ja kutsuu aktiiviajan laskevaa <b>laskeKayttoAste</b>-metodia ja
	 * tarkistaa, onko käyttöaste laskettu oikein.
	 */
	@Test
	@DisplayName("testKayttoAste(): Lasketaanko käyttöaste oikein?")
	void testKayttoAste() {
		om = tp.getOmaMoottori();
		om.setSimulointiaika(500);
		tp.setAktiiviAika(100);

		tp.laskeKayttoaste();
		assertEquals(20, tp.getKayttoaste(), "Käyttöasteen laskeminen ei toimi.");
	}

	/**
	 * Testaa jonon pituuden asettavaa metodia. Asettaa palvelupisteen jonon
	 * pituudeksi 300 asiakasta ja testaa onko jono sen pituinen.
	 */
	@Test
	@DisplayName("testSetJononPituus(): Testataan palvelupisteen jonon pituuden asettamista.")
	void testSetJononPituus() {
		tp.setJononPituus(300);
		assertEquals(300, tp.getJononPituus(), "Jonon pituuden asettamisessa on virheitä");
	}
}