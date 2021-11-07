package view;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import controller.IKontrolleri;
import controller.Kontrolleri;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import simu.framework.Kello;
import simu.model.Asiakas;
import simu.model.OmaMoottori;
import simu.model.Palvelupiste;

/**
 * Sisältää näkymän luontiin tarvittavat komponentit, jossa näytetään
 * simulaation tulokset. Simuloinnin tulokset haetaan tietokannasta
 * <b>Kontrollerin</b> kautta.
 * 
 * @author Otso Poussa, Tanja Pyykönen ja Tatu Talvikko
 * @version 1.0
 */
public class TulosNakyma {
	/**
	 * <b>Kontrolleri</b>, jonka kautta haetaan simuloinnin tulokset tietokannasta.
	 */
	private IKontrolleri kontrolleri;

	/**
	 * Taulukko, johon haetaan <b>omaMoottori</b>-oliot tietokannasta.
	 */
	private OmaMoottori[] moottorit = null;

	/**
	 * Painike tietokannasta poistoa varten.
	 */
	private Button poistaBtn;

	/**
	 * Taulukko, johon haetaan <b>asiakas</b>-oliot tietokannasta.
	 */
	private Asiakas[] asiakkaat = null;

	/**
	 * Taulukko, johon haetaan <b>palvelupiste</b> oliot tietokannasta.
	 */
	private Palvelupiste[] palvelupisteet = null;

	/**
	 * Valintalaatikko, josta voidaan valita simuloinnin ajokerta sen id:n
	 * perusteella.
	 */
	private ChoiceBox<OmaMoottori> simIDValinta;

	/**
	 * Valintalaatikko, josta voidaan valita valitun simulointiajon yksittäinen
	 * <b>asiakas</b>-olio.
	 */
	private ChoiceBox<Asiakas> asiakasIDValinta;

	/**
	 * Valintalaatikko, josta voidaan valita valitun simulointiajon yksittäinen
	 * <b>palvelupiste</b>-olio.
	 */
	private ChoiceBox<Palvelupiste> palvelupisteIDValinta;

	/**
	 * <b>ArrayList</b>, johon lisätään valitun simulointiajon <b>asiakkaat</b>.
	 */
	private ArrayList<Asiakas> listattavatAsiakkaat = new ArrayList<Asiakas>();

	/**
	 * <b>ArrayList</b>, johon lisätään valitun simulointiajon <b>palvelupsiteet</b>
	 */
	private ArrayList<Palvelupiste> listattavatPalvelupsiteet = new ArrayList<Palvelupiste>();

	/**
	 * Pitää sisällään <b>simIDValinta</b> valintalaatikon <b>OmaMoottorin</b>
	 * simulointikerran id:n.
	 */
	private int valitunMoottorinID;

	/**
	 * Taulukko, joka sisältää Labelit simuloinnin tuloksia varten.
	 */
	private Label[] tulokset = new Label[29];

	/**
	 * Taulukko Labeleita varten, joihin sijoitetaan selitykset jokaisesta
	 * tulosteesta.
	 */
	private Label[] tuloksetTekstit = new Label[29];

	/**
	 * Taulukko, joka sisältää selitykset jokaisesta tulosteesta.
	 */
	private String[] tekstit = { "Simuloinnin kokonaisaika (pv hh:mm:ss):", "Tulot yhteensä (\u20ac):",
			"Tulot aurinkoisella säällä (\u20ac): ", "Tulot sateella (\u20ac):", "Sateen kokoaniskesto (pv hh:mm:ss):",
			"Sadekertojen lukumäärä:", "Saapuneiden asiakkaiden lukumäärä:", "Poistuneiden asiakkaiden lukumäärä:",
			"Asiakkaita poistui sateen vuoksi:", "Asiakkaita poistui ruuhkan vuoksi:",
			"Asiakkaiden keskimääräinen läpimenoaika:", "Käytetty rahasumma (\u20ac):",
			"Vietetty aika huvipuistossa (pv hh:mm:ss):", "Haluttujen kohteiden käyntimäärä:",
			"Käytyjen kohteiden lukumäärä:", "Sateen toleranssi (%):", "Poistui aikaisemmin sateen vuoksi:",
			"Poistui aikaisemmin ruuhkan vuoksi:", "Tulot yhteensä (\u20ac):", "Tulot aurinkoisella säällä (\u20ac):",
			"Tulot sateella (\u20ac):", "Saapuneiden asiakkaiden lukumäärä:", "Palveltujen asiakkaiden lukumäärä:",
			"Aktiiviaika (pv hh:mm:ss):", "Keskimääräinen palveluaika (min):", "Keskimääräinen läpimenoaika (min):",
			"Keskimääräinen jonotusaika (min):", "Käyttöaste (%):", "Suoritusteho (palveltuja asiakkaita/h):" };

	/**
	 * Luo <b>TulosNakyma</b> olion, jossa muodostetaan kytkös <b>kontrolleriin</b>
	 * ja alustaa tietokannan poisto painikkeen.
	 */
	public TulosNakyma() {
		kontrolleri = new Kontrolleri();
		poistaBtn = new Button("Poista tietokannasta");
		poistaBtn.setDisable(true);
		if (this.moottorit != null) {
			Arrays.sort(this.moottorit);
		}
	}

	/**
	 * Asettaa asettelupohjat ja kontrollit pääasettelupohjaan ja luo näkymän.
	 * 
	 * @return Tuloksien näkymä.
	 */
	public Scene luoTulosNakyma() {
		for (int i = 0; i < tulokset.length; i++) {
			tulokset[i] = new Label("");
			tulokset[i].setStyle("-fx-font: 14 Verdana;");
		}

		for (int i = 0; i < tuloksetTekstit.length; i++) {
			tuloksetTekstit[i] = new Label(tekstit[i]);
		}

		BorderPane border = new BorderPane();
		Scene scene = new Scene(border);
		scene.getStylesheets().add(getClass().getResource("/css/Styles.css").toExternalForm());

		HBox otsikkoYlos = otsikkoYlos();
		GridPane tuloksetVasen = tuloksetVasen();
		GridPane tuloksetKeski = tuloksetKeski();
		HBox painikeAlas = painikeAlas();

		border.setTop(otsikkoYlos);
		border.setLeft(tuloksetVasen);
		border.setCenter(tuloksetKeski);
		border.setBottom(painikeAlas);

		border.setStyle("-fx-background-color: #fffaee;");

		BorderPane.setMargin(otsikkoYlos, new Insets(5, 10, 0, 10));
		BorderPane.setMargin(tuloksetVasen, new Insets(10, 10, 0, 10));
		BorderPane.setMargin(tuloksetKeski, new Insets(10, 10, 0, 10));
		BorderPane.setMargin(painikeAlas, new Insets(0, 10, 10, 10));

		return scene;
	}

	/**
	 * Luo ja asettaa kontrollit <b>GridPane</b> asettelupohjaan, joka asetetaan
	 * <b>tulosNakyma</b> pääasettelupohjan vasemmalle puolelle. Asettelupohja
	 * sisältää simulaation huvipuiston ja yksittäisen asiakkaan tuottamat tulokset
	 * ja niitä kuvaavat tekstit.
	 * 
	 * @exception Exception jos tietojen haku tietokannasta epäonnistuu.
	 * @return <b>GridPane</b> asettelupohja.
	 */
	private GridPane tuloksetVasen() {
		GridPane grid = new GridPane();

		grid.setHgap(20);
		grid.setVgap(7);

		Text huvipuisto = new Text("Huvipuisto");
		huvipuisto.setId("alaOtsikot");
		huvipuisto.setFill(Color.web(Visualisointi.SININEN));

		Label valitseSimAjo = new Label("Valitse simulointikerta (id):");
		simIDValinta = new ChoiceBox<OmaMoottori>();

		try {
			this.lisaaMoottorit();
			Arrays.sort(this.moottorit);
			for (OmaMoottori m : this.moottorit) {
				simIDValinta.getItems().add(m);
			}
		} catch (Exception e) {
			alertinLuonti(AlertType.ERROR, "Tietokanta", "Tietokannan toiminnassa virhe!",
					"Huvipuiston yleisten tuloksien haku tietokannasta epäonnistui.");
		}

		simIDValinta.setOnAction((event) -> {
			poistaBtn.setDisable(false);
			this.tyhjennaTulosteTekstit();

			this.asiakasIDValinta.getItems().clear();
			this.palvelupisteIDValinta.getItems().clear();

			this.listaaMoottorinTulosteet();
			this.paivitaListattavatAsiakkaat();
			this.paivitaListattavatPalvelupisteet();
			for (Asiakas a : this.listattavatAsiakkaat) {
				this.asiakasIDValinta.getItems().add(a);
			}
			for (Palvelupiste p : this.listattavatPalvelupsiteet) {
				this.palvelupisteIDValinta.getItems().add(p);
			}
		});

		// HUVIPUISTO
		grid.add(valitseSimAjo, 0, 0);
		grid.add(simIDValinta, 1, 0);
		grid.add(huvipuisto, 0, 1);

		for (int i = 0, j = 2; i <= 10; i++, j++) {
			grid.add(tuloksetTekstit[i], 0, j);
			grid.add(tulokset[i], 1, j);
		}

		// YKSITTÄINEN ASIAKAS
		Text asiakas = new Text("Yksittäinen asiakas");
		asiakas.setId("alaOtsikot");
		asiakas.setFill(Color.web(Visualisointi.SININEN));
		Label valitseAsiakasId = new Label("Valitse asiakas (id):");
		asiakasIDValinta = new ChoiceBox<>();

		try {
			this.lisaaAsiakkaat();
			this.asiakasIDValinta.setOnAction((event) -> {
				this.listaaAsiakkaanTulosteet();
			});
		} catch (Exception e) {
			alertinLuonti(AlertType.ERROR, "Tietokanta", "Tietokannan toiminnassa virhe!",
					"Yksittäisen asikkaan tuloksien haku tietokannasta epäonnistui.");
		}

		grid.add(asiakas, 0, 14);
		grid.add(valitseAsiakasId, 0, 15);
		grid.add(asiakasIDValinta, 1, 15);

		for (int i = 11, j = 16; i <= 17; i++, j++) {
			grid.add(tuloksetTekstit[i], 0, j);
			grid.add(tulokset[i], 1, j);
		}
		return grid;
	}

	/**
	 * Luo ja asettaa kontrollit <b>GridPane</b> asettelupohjaan, joka asetetaan
	 * <b>tulosNakyma</b> pääasettelupohjan keskelle. Asettelupohja sisältää
	 * simulaation yksittäisen kohteen tuottamat tulokset ja niitä kuvaavat tekstit.
	 * 
	 * @exception Exception jos tietojen haku tietokannasta epäonnistuu.
	 * @return <b>GridPane</b> asettelupohja.
	 */
	private GridPane tuloksetKeski() {
		GridPane grid = new GridPane();

		grid.setHgap(20);
		grid.setVgap(7);

		// YKSITTÄINEN KOHDE
		Text palvelupiste = new Text("Yksittäinen kohde");
		palvelupiste.setId("alaOtsikot");
		palvelupiste.setFill(Color.web(Visualisointi.SININEN));
		Label valitsePalvelupisteId = new Label("Valitse palvelupiste:");
		palvelupisteIDValinta = new ChoiceBox<>();

		try {
			this.lisaaPalvelupisteet();
			this.palvelupisteIDValinta.setOnAction((event) -> {
				this.listaaPalvelupisteenTulosteet();
			});
		} catch (Exception e) {
			alertinLuonti(AlertType.ERROR, "Tietokanta", "Tietokannan toiminnassa virhe!",
					"Yksittäisen kohteen tuloksien haku tietokannasta epäonnistui.");
		}

		grid.add(palvelupiste, 0, 0);
		grid.add(valitsePalvelupisteId, 0, 1);
		grid.add(palvelupisteIDValinta, 1, 1);

		for (int i = 18, j = 2; i <= 28; i++, j++) {
			grid.add(tuloksetTekstit[i], 0, j);
			grid.add(tulokset[i], 1, j);
		}
		return grid;
	}

	/**
	 * Luo ja asettaa <b>HBox</b> asettelupohjaan. Asettelupohja asetetaan
	 * <b>tulosNakyma</b> pääasettelupohjan yläosaan. Asettelupohja sisältää näkymän
	 * otsikon.
	 * 
	 * @return <b>HBox</b> asettelupohja.
	 */
	private HBox otsikkoYlos() {
		HBox otsikkoBox = new HBox();

		Text otsikko = new Text("Simuloinnin tulokset");
		otsikko.setId("otsikot");
		otsikko.setFill(Color.web(Visualisointi.SININEN));

		otsikkoBox.getChildren().add(otsikko);
		return otsikkoBox;
	}

	/**
	 * Luo asettelupohjan, johon liitetään tietokannan poisto painike. Kun
	 * tietokannasta poistetaan, tässä metodissa huolehditaan
	 * <b>omaMottori</b>-olioiden uudelleen listaus valintalaatikkoon ja
	 * tyhjennetään yksittäisen asiakkaan ja yksittäisen kohteen valintalaatikoiden
	 * valinnat. Asettelupohja asetetaan <b>tulosNakyma</b> pääasettelupohjan
	 * alaosaan.
	 * 
	 * @exception Exception jos tietojen haku tietokannasta epäonnistuu.
	 * @return <b>HBox</b> asettelupohja.
	 */
	private HBox painikeAlas() {
		HBox painikeBox = new HBox();
		painikeBox.setSpacing(20);

		poistaBtn.setMaxWidth(Double.MAX_VALUE);
		poistaBtn.setOnAction((event) -> {
			try {
				kontrolleri.poista(valitunMoottorinID);
			} catch (Exception e) {
				alertinLuonti(AlertType.ERROR, "Tietokanta", "Tietokannan toiminnassa virhe!",
						"Tietokannan poistossa tapahtui odottamaton virhe.");
			}

			this.tyhjennaTulosteTekstit();
			this.simIDValinta.setValue(null);
			this.asiakasIDValinta.getItems().clear();
			this.palvelupisteIDValinta.getItems().clear();
			simIDValinta.getItems().clear();

			try {
				this.lisaaMoottorit();
				for (int i = 0; i <= 10; i++) {
					tulokset[i].setText("");
				}
				Arrays.sort(this.moottorit);
				for (OmaMoottori m : this.moottorit) {
					simIDValinta.getItems().add(m);
				}
			} catch (Exception e) {
				alertinLuonti(AlertType.ERROR, "Tietokanta", "Tietokannan toiminnassa virhe!",
						"Huvipuiston yleisten tuloksien haku tietokannasta epäonnistui.");
			}
			poistaBtn.setDisable(true);

		});

		Pane vali = new Pane();
		HBox.setHgrow(vali, Priority.ALWAYS);
		vali.setMinSize(10, 1);

		painikeBox.getChildren().addAll(vali, poistaBtn);

		return painikeBox;
	}

	/**
	 * Hakee <b>omaMoottori</b>-oliot tietokannasta <b>kontrollerin</b> avulla.
	 */
	private void lisaaMoottorit() {
		this.moottorit = kontrolleri.haeOmaMoottorit();
	}

	/**
	 * Hakee <b>asiakas</b>-oliot tietokannasta <b>kontrollerin</b> avulla.
	 */
	private void lisaaAsiakkaat() {
		this.asiakkaat = kontrolleri.haeAsiakkaat();
	}

	/**
	 * Hakee <b>palvelupiste</b>-oliot tietokannasta <b>kontrollerin</b> avulla.
	 */
	private void lisaaPalvelupisteet() {
		this.palvelupisteet = kontrolleri.haePalvelupisteet();
	}

	/**
	 * Asettaa <b>omaMoottorin</b> tuloksien arvot <b>simIDValinta</b>
	 * valintalaatikosta, sen perusteella mikä simulointiajo on valittu. Tulokset
	 * formatoidaan oikeisiin muotoihin tuloksien asetusten yhteydessä.
	 */
	private void listaaMoottorinTulosteet() {
		OmaMoottori valittuMoottori = this.simIDValinta.getSelectionModel().getSelectedItem();
		this.valitunMoottorinID = valittuMoottori.getIdSimulointi();

		for (OmaMoottori m : moottorit) {
			if (valitunMoottorinID == m.getIdSimulointi()) {
				tulokset[0].setText(String.valueOf(Kello.getInstance().formatoiKello(m.getSimuloinninKesto())));
				tulokset[1].setText(String.valueOf(Math.round(m.getKokoTulot()) + "\u20ac"));
				tulokset[2].setText(String.valueOf(Math.round(m.getAurinkoTulot()) + "\u20ac"));
				tulokset[3].setText(String.valueOf(Math.round(m.getSadeTulot()) + "\u20ac"));
				tulokset[4].setText(String.valueOf(Kello.getInstance().formatoiKello(m.getSateenKesto())));
				tulokset[5].setText(String.valueOf(m.getSateidenLkm()));
				tulokset[6].setText(String.valueOf(m.getSaapuneet()));
				tulokset[7].setText(String.valueOf(m.getLahteneet()));
				tulokset[8].setText(String.valueOf(m.getLahtiSade()));
				tulokset[9].setText(String.valueOf(m.getLahtiRuuhka()));
				tulokset[10].setText(String
						.valueOf(Math.round(Kello.getInstance().formatoiKelloMin(m.getKeskLapimenoAika())) + "min"));
			}
		}
	}

	/**
	 * Asettaa <b>asiakkaiden</b> tuloksien arvot, sen perusteella mikä
	 * <b>asiakas</b> on valittu <b>asikasIDValinta</b> valintalaatikosta. Tulokset
	 * formatoidaan oikeisiin muotoihin tuloksien asetusten yhteydessä.
	 */
	private void listaaAsiakkaanTulosteet() {
		Asiakas a = this.asiakasIDValinta.getSelectionModel().getSelectedItem();
		tulokset[11].setText(String.valueOf(Math.round(a.getKaytettyRaha()) + "\u20ac"));
		tulokset[12].setText(String.valueOf(Kello.getInstance().formatoiKello(a.getVietettyAika())));
		tulokset[13].setText(String.valueOf(a.getHaluttuKyytiLkm()));
		tulokset[14].setText(String.valueOf(a.getKaydytKyydit()));
		tulokset[15].setText(String.valueOf(a.getSateenToleranssi() + "%"));
		if (a.isLahtiAikaisinSade()) {
			tulokset[16].setText("Kyllä");
		} else {
			tulokset[16].setText("Ei");
		}
		
		if (a.isLahtiAikaisinRuuhka()) {
			tulokset[17].setText("Kyllä");
		} else {
			tulokset[17].setText("Ei");
		}
	}

	/**
	 * Asettaa <b>palvelupisteiden</b> tuloksien arvot, sen perusteella mikä
	 * <b>palvelupiste</b> on valittu <b>palvelupisteIDValinta</b>
	 * valintalaatikosta. Tulokset formatoidaan oikeisiin muotoihin tuloksien
	 * asetusten yhteydessä.
	 */
	private void listaaPalvelupisteenTulosteet() {
		Palvelupiste p = this.palvelupisteIDValinta.getSelectionModel().getSelectedItem();
		tulokset[18].setText(String.valueOf(Math.round(p.getTulotYhteensa()) + "\u20ac"));
		tulokset[19].setText(String.valueOf(Math.round(p.getTulotAurinko()) + "\u20ac"));
		tulokset[20].setText(String.valueOf(Math.round(p.getTulotSade()) + "\u20ac"));
		tulokset[21].setText(String.valueOf(p.getSaapuneita()));
		tulokset[22].setText(String.valueOf(p.getPalveltuja()));
		tulokset[23].setText(String.valueOf(Kello.getInstance().formatoiKello(p.getAktiiviAika())));
		tulokset[24].setText(
				String.valueOf(Math.round(Kello.getInstance().formatoiKelloMin(p.getPalveluaikaKeskiarvo())) + "min"));
		tulokset[25].setText(
				String.valueOf(Math.round(Kello.getInstance().formatoiKelloMin(p.getLapimenoaikaKeskiarvo())) + "min"));
		tulokset[26].setText(
				String.valueOf(Math.round(Kello.getInstance().formatoiKelloMin(p.getJonotusaikaKeskiarvo())) + "min"));
		tulokset[27].setText(String.valueOf(Math.round(p.getKayttoaste()) + "%"));
		tulokset[28].setText(String.valueOf(Math.round(p.getSuoritusteho()) + " asiakasta/h"));
	}

	/**
	 * Päivittää <b>listattavatAsikkaat</b> ArrayListan <b>asiakkaat</b>, jos
	 * <b>simIDValinta</b> valintalaatikosta vaihdetaan toinen simulointiajo
	 * tarkasteltavaksi ja tämän jälkeen jäjestää listan asiakkaat.
	 */
	private void paivitaListattavatAsiakkaat() {
		this.listattavatAsiakkaat.clear();
		if (this.asiakkaat == null) {
			alertinLuonti(AlertType.ERROR, "Tietokanta", "Tietokannan toiminnassa virhe!",
					"Asiakkaiden haku tietokannasta epäonnistui!");
		}
		for (Asiakas a : asiakkaat) {
			if (a.getOmaMoottoriId() == this.valitunMoottorinID) {
				this.listattavatAsiakkaat.add(a);
			}
		}
		Collections.sort(this.listattavatAsiakkaat);
	}

	/**
	 * Päivittää <b>listattavatPalvelupisteet</b> ArrayListan <b>palvelupisteet</b>,
	 * jos <b>simIDValinta</b> valintalaatikosta vaihdetaan toinen simulointiajo
	 * tarkasteltavaksi.
	 */
	private void paivitaListattavatPalvelupisteet() {
		this.listattavatPalvelupsiteet.clear();
		if (this.palvelupisteet == null) {
			alertinLuonti(AlertType.ERROR, "Tietokanta", "Tietokannan toiminnassa virhe!",
					"Palvelupisteiden haku tietokannasta epäonnistui!");
		}
		for (Palvelupiste p : palvelupisteet) {
			if (p.getOmaMoottoriId() == this.valitunMoottorinID) {
				this.listattavatPalvelupsiteet.add(p);
			}
		}
	}

	/**
	 * Tyhjentää <b>asiakasIDValinta</b> ja <b>palvelupisteIDValinta</b>
	 * valintalaatikoiden nykyisen valinnan ja niiden tulokset kun
	 * <b>simIDValinta</b> valintalaatikosta vaihdetaan toinen simulointiajo
	 * tarkasteltavaksi tai tietokannasta poistetaan simulointikerran tulokset.
	 */
	private void tyhjennaTulosteTekstit() {
		this.asiakasIDValinta.setValue(null);
		this.palvelupisteIDValinta.setValue(null);

		for (int i = 11; i <= 28; i++) {
			tulokset[i].setText("");
		}
	}

	/**
	 * Luo ja näyttää <b>alert</b> ikkunan käyttäjälle virhetilanteessa.
	 * 
	 * @param tyyppi      Alertin tyyppi.
	 * @param otsikko     Alertin otsikko.
	 * @param ylatunniste Alertin ylätunniste.
	 * @param sisalto     Alertin sisältö.
	 */
	private void alertinLuonti(AlertType tyyppi, String otsikko, String ylatunniste, String sisalto) {
		Alert alert = new Alert(tyyppi);
		alert.setTitle(otsikko);
		alert.setHeaderText(ylatunniste);
		alert.setContentText(sisalto);
		alert.showAndWait();
	}
}