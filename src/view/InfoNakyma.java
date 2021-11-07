package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

/**
 * Luo info-näkymän. Asetellaan info-näkymän kontrollit ja asettelupohja. Näkymä
 * sisältää simulaattorin käyttöohjeet, sekä ohjelman tekijät.
 * 
 * @author Otso Poussa, Tanja Pyykönen ja Tatu Talvikko
 * @version 1.0
 */
public class InfoNakyma {

	/**
	 * Tekstien rivitykseen käytettävä vakioarvo.
	 */
	private final int RIVITYSARVO = 900;

	/**
	 * Tyhjä konstruktori, jolla luodaan uusi <b>InfoNakyma</b>-olio.
	 */
	public InfoNakyma() {}

	/**
	 * Luo <b>GridPane</b> asettelupohjan, johon asetetaan kontrollit. kontrollit
	 * sisältävät tekstit simulaattorin ja syötteiden käyttöohjeille, sekä
	 * ohjelmiston tekijöille.
	 * 
	 * @return Infon näkymä.
	 */
	public Scene setInfoNakyma() {
		GridPane grid = new GridPane();

		grid.setHgap(20);
		grid.setVgap(10);
		grid.setPadding(new Insets(10, 10, 10, 10));
		grid.setAlignment(Pos.TOP_CENTER);

		Text simKayttoOtsikko = new Text("Simulaattorin käyttö ja syötteet");
		simKayttoOtsikko.setId("otsikot");
		simKayttoOtsikko.setFill(Color.web(Visualisointi.SININEN));

		Text simKayttoT = new Text(
				"Simulaattorin päätarkoituksena on tutkia sääolosuhteiden vaikutusta huvipuiston kohteiden kävijämääriin, mahdollisiin ruuhkautumisiin, sekä miten sääolosuhteet vaikuttavat huvipuiston tuoton määrään. "
						+ "Simulaatio sisältää valmiina oletussyötteet, joita voidaan halutessa vaihtaa. "
						+ "Painikkeiden < ja > avulla voidaan hidastaa tai nopeuttaa simuloinnin kulkua. "
						+ "Tuloksissa voidaan tutkia simulointikertojen tuloksia. Tulokset sisältävät kolme kategoriaa: Huvipuiston yleiset tulokset, yksittäisen asiakkaan tulokset, sekä yksittäisen kohteen tulokset. "
						+ "Kun simulointikerta on valittu valintalaatikosta, voidaan sen simulointikerran tulokset poistaa tietokannasta painamalla poista tietokannasta painiketta.");
		simKayttoT.setWrappingWidth(RIVITYSARVO);
		simKayttoT.setId("infoTeksti");

		Text infoTeksti = new Text(
				"\u2022 Simuloinnin kestolla asetetaan simuloinnin kokoanisaika. Sallittuja arvoja ovat reaaliluvut, joiden täytyy olla vähintään 1 tunti.\n"
						+ "\u2022 Yksikkö asettaa valitun simuloinnin keston aikayksikön.\n"
						+ "\u2022 Sateen tarkasteluvälin keskiarvo minuutteina kuvaa kuinka usein sadetta tarkastellaan simuloinnin aikana. Sallittuja arvoja ovat reaaliluvut, jotka ovat suurempia kuin nolla.\n"
						+ "\u2022 Sateen todennäköisyys prosentteina kertoo millä todennäköisyydellä sää muuttuu sateiseksi, kun sadetta tarkastellaan. Salillitut arvot ovat kokonaisluvut väliltä 0-100.\n"
						+ "\u2022 Sisäänkäynnin saapumisväliaikojen keskiarvo minuutteina kertoo, kuinka usein asiakkaita saapuu sisäänkäynnille. Sallittuja arvoja ovat reaaliluvut, jotka ovat suurempi kuin nolla.\n"
						+ "\u2022 Sisäänkäynnin ja grillin palveluaika korkeintaan minuutteina, kuvaa kuinka kauan asiakasta voidaan maksimissaan palvellaan kohteessa. Simulaatio arpoo palveluajan yhden minuutin ja syötetyn minuutti arvon väliltä. Sallittuja arvoja ovat reaaliluvut, jotka ovat suurempia kuin 1.0.\n"
						+ "\u2022 Jonojen kapasiteetit kertovat, kuinka monta asiakasta mahtuu jonottamaan tiettyyn kohteeseen kerrallaan. Sallittuja arvoja ovat kokonaisluvut, jotka ovat suurempia kuin nolla.\n"
						+ "\u2022 Hinnat euroina kuvaavat, kuinka paljon asiakkaan täytyy maksaa palvelusta. Sallitut arvot ovat reaaliluvut nollasta ylöspäin.\n"
						+ "\u2022 Suosiot aurinkoisella säällä prosentteina, kertovat kuinka suurella todennäköisyydellä asiakas haluaa mennä kohteeseen kun sääolosuhde on aurinkoinen. Sallitut arvot ovat kokonaisluvut väliltä 0-100. Kaikkien kohteiden aurinkoiset suosiot yhteensä eivät saa ylittää 100 prosenttia.\n"
						+ "\u2022 Suosiot sateella prosentteina, kertovat kuinka suurella todennäköisyydellä asiakas haluaa mennä kohteeseen kun sääolosuhde on sateinen. Sallitut arvot ovat kokonaisluvut väliltä 0-100. Kaikkien kohteiden sade suosiot yhteensä eivät saa ylittää 100 prosenttia.\n"
						+ "\u2022 Laitteen kapasiteetit, kertovat kuinka monta asiakasta voidaan palvella samanaikaisesti kohteessa. Sallittuja arvoja ovat kokonaisluvut, jotka ovat suurempia kuin nolla.");
		infoTeksti.setWrappingWidth(RIVITYSARVO);
		infoTeksti.setId("infoTeksti");

		Text ryhma = new Text("Ryhmä 11 - Otso Poussa, Tanja Pyykönen, Tatu Talvikko");
		ryhma.setWrappingWidth(RIVITYSARVO);
		ryhma.setId("infoTeksti");

		grid.add(simKayttoOtsikko, 0, 0);
		grid.add(simKayttoT, 0, 1);
		grid.add(infoTeksti, 0, 2);
		grid.add(ryhma, 0, 3);

		grid.setStyle("-fx-background-color: #fffaee;");
		
		Scene scene = new Scene(grid);
		scene.getStylesheets().add(getClass().getResource("/css/Styles.css").toExternalForm());
		return scene;
	}
}