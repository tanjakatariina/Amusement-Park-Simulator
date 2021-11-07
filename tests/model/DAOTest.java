package simu.model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.*;

import controller.IKontrolleri;
import controller.Kontrolleri;
import dao.IDAO;
import simu.framework.Trace;
import simu.framework.Trace.Level;

/**
 * Testataan DAO-luokan metodeja tietokantaan tallentamista ja lukemista varten.
 * 
 * @author Otso Poussa, Tanja Pyykönen ja Tatu Talvikko
 * @version 1.0
 */
class DAOTest {

	/**
	 * DAO:n olio.
	 */
	private IDAO dao;

	/**
	 * Kontrolleri, jonka kautta DAO haetaan.
	 */
	private IKontrolleri kont = new Kontrolleri();

	/**
	 * OmaMoottori-olio testausta varten.
	 */
	private OmaMoottori om;

	/**
	 * Palvelupiste-olio testausta varten.
	 */
	private Palvelupiste pp;

	/**
	 * Asiakas-olio testausta varten.
	 */
	private Asiakas as;

	/**
	 * Suoritetaan ennen jokaisen testin aloitusta. Haetaan dao ja luodaan
	 * omaMoottori-, asiakas- ja palvelupiste-olio testausta varten.
	 */
	@BeforeEach
	public void alkutoimet() {
		Trace.setTraceLevel(Level.INFO);
		dao = kont.haeDao();

		Set<Asiakas> asiakkaatSet = new HashSet<Asiakas>();
		Set<Palvelupiste> ppSet = new HashSet<Palvelupiste>();

		om = new OmaMoottori();
		om.setIdSimulointi(0); // Tietokanta huolhtii id numeroinnista.
		om.setSimuloinninKesto(500);
		om.setKokoTulot(1500);
		om.setAurinkoTulot(1000);
		om.setSadeTulot(500);
		om.setSaapuneet(100);
		om.setLahteneet(99);
		om.setLahtiRuuhka(10);
		om.setLahtiSade(20);
		om.setSateenKesto(100);
		om.setSateidenLkm(1);
		om.setKeskLapimenoAika(100);
		om.setPaiva();
		om.setKello();

		pp = new Palvelupiste();
		pp.setId(0); // Tietokanta huolehtii id numeroinnista.
		pp.setNimi("Sisäänkäynti");
		pp.setSaapuneita(100);
		pp.setPalveltuja(99);
		pp.setTulotYhteensa(1000);
		pp.setTulotAurinko(500);
		pp.setTulotSade(500);
		pp.setAktiiviAika(450);
		pp.setKayttoaste(90);
		pp.setSuoritusteho(80);
		pp.setJonotusaikaKeskiarvo(300);
		pp.setLapimenoaikaKeskiarvo(200);
		pp.setPalveluaikaKeskiarvo(100);
		pp.setOmaMoottori(om);
		ppSet.add(pp);

		as = new Asiakas();
		as.setIdTietokanta(0); // Tietokanta huolehtii id numeroinnista.
		as.setId(1);
		as.setOmaMoottori(om);
		as.setKaytettyRaha(50);
		as.setKaydytKyydit(5);
		as.setSateenToleranssi(50);
		as.setLahtiAikaisinRuuhka(false);
		as.setLahtiAikaisinSade(true);
		as.setVietettyAika(400);
		as.setHaluttuKyytiLkm(15);
		asiakkaatSet.add(as);

		om.setAsiakkaatSet(asiakkaatSet);
		om.setPpSet(ppSet);
	}

	/**
	 * Poistetaan tietokannannasta taulut jokaisen testin jälkeen omaMoottori-olion
	 * simulointi id:n avulla.
	 */
	@AfterEach
	public void lopputoimet() {
		dao.poista(om.getIdSimulointi());
	}

	/**
	 * Testataan toimiiko omaMoottori-olion tallennus ja haku tietokannasta.
	 */
	@Test
	@DisplayName("testLuoJaLueOmaMoottori(): Onnistuuko omaMoottorin tallennus ja lukeminen?")
	void testLuoJaLueOmaMoottori() {
		dao.luoOmaMoottori(om);

		boolean loytyi = false;

		for (OmaMoottori m : dao.lueOmaMoottorit()) {
			if (m.getIdSimulointi() == om.getIdSimulointi()) {
				loytyi = true;
			}
		}
		assertTrue(loytyi, "omaMoottorin tallennus ja lukeminen epäonnistui.");
	}

	/**
	 * Testataan toimiiko asiakas-olion tallennus ja haku tietokannasta.
	 */
	@Test
	@DisplayName("testLuoJaLueAsiakas(): Onnistuuko asiakkaan tallennus ja lukeminen?")
	void testLuoJaLueAsiakas() {
		dao.luoOmaMoottori(om);
		dao.luoAsiakas(as);

		boolean loytyi = false;

		for (Asiakas a : dao.lueAsiakkaat()) {
			if (a.getIdTietokanta() == as.getIdTietokanta()) {
				loytyi = true;
			}
		}
		assertTrue(loytyi, "Asiakkaan tallennus ja lukeminen epäonnistui.");
	}

	/**
	 * Testataan toimiiko palvelupiste-olion tallennus ja haku tietokannasta.
	 */
	@Test
	@DisplayName("testLuoJaLuePalvelupiste(): Onnistuuko palvelupisteen tallennus ja lukeminen?")
	void testLuoJaLuePalvelupiste() {
		dao.luoOmaMoottori(om);
		dao.luoPalvelupiste(pp);

		boolean loytyi = false;

		for (Palvelupiste p : dao.luePalvelupisteet()) {
			if (p.getId() == pp.getId()) {
				loytyi = true;
			}
		}
		assertTrue(loytyi, "Palvelupisteen tallennus ja lukeminen epäonnistui.");
	}
}