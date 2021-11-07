package view;

import java.util.ArrayList;

import controller.IKontrolleri;
import controller.Kontrolleri;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * Simulaation <b>käyttöliittymä</b>, joka sisältää simulaation visualisoinnin
 * ja syötteiden asetukset. Tästä pääikkunasta avataan näkymät
 * <b>InfoNakymalle</b> ja <b>TulosNakymalle</b>.
 * 
 * @author Otso Poussa, Tanja Pyykönen ja Tatu Talvikko
 * @version 1.0
 */
public class SimulaattorinGUI extends Application implements ISimulaattorinUI {
	/**
	 * <b>Kontrolleri</b>, jonka avulla viedään syötteitä <b>TextField</b>
	 * kontrollien kentistä <b>OmaMoottoriin</b>.
	 */
	private IKontrolleri kontrolleri;

	/**
	 * <b>visualisoinnin</b> kanvas-olio, johon visualisoidaan simulaation kulku.
	 */
	private IVisualisointi naytto;

	/**
	 * <b>Viive</b>, jonka avulla säädellään simulaation kulun nopeutta.
	 */
	private Long viive;

	/**
	 * <b>infoNakyma</b> hakee <b>InfoNakyma</b>-luokassa luodun näkymän.
	 */
	private InfoNakyma infoNakyma;

	/**
	 * <b>tuloksetNakyma</b> hakee <b>TulosNakyma</b>-luokassa luodun näkymän.
	 */
	private TulosNakyma tuloksetNakyma;

	/**
	 * <b>TextField</b> kontrolli simulaation syötteiden asettamiseksi.
	 */
	private TextField simKestoTF, sadeKeskiarvoTF, sadeTodnakTF, skKeskiarvoTF, skJonoKTF, skPAikaTF, skHintaTF,
			grSuosioATF, grSuosioSTF, grJonoKTF, grPAikaTF, grHintaTF, mpSuosioATF, mpSuosioSTF, mpJonoKTF, mpLaiteKTF,
			mpHintaTF, vrSuosioATF, vrSuosioSTF, vrJonoKTF, vrLaiteKTF, vrHintaTF, ksSuosioATF, ksSuosioSTF, ksJonoKTF,
			ksLaiteKTF, ksHintaTF, vkSuosioATF, vkSuosioSTF, vkJonoKTF, vkLaiteKTF, vkHintaTF, kjSuosioATF, kjSuosioSTF,
			kjJonoKTF, kjLaiteKTF, kjHintaTF;

	/**
	 * Taulukko, joka sisältää sisäänkäynti <b>palvelupisteen</b> syötteiden
	 * <b>TextField</b> kentät.
	 */
	private TextField[] skTF = { skKeskiarvoTF, skJonoKTF, skPAikaTF, skHintaTF };

	/**
	 * Taulukko, joka sisältää grilli <b>palvelupisteen</b> syötteiden
	 * <b>TextField</b> kentät.
	 */
	private TextField[] grilliTF = { grSuosioATF, grSuosioSTF, grJonoKTF, grPAikaTF, grHintaTF };

	/**
	 * Taulukko, joka sisältää maailmanpyörä <b>palvelupisteen</b> syötteiden
	 * <b>TextField</b> kentät.
	 */
	private TextField[] mpLaitteetTF = { mpSuosioATF, mpSuosioSTF, mpJonoKTF, mpLaiteKTF, mpHintaTF };

	/**
	 * Taulukko, joka sisältää vuoristorata <b>palvelupisteen</b> syötteiden
	 * <b>TextField</b> kentät.
	 */
	private TextField[] vrLaitteetTF = { vrSuosioATF, vrSuosioSTF, vrJonoKTF, vrLaiteKTF, vrHintaTF };

	/**
	 * Taulukko, joka sisältää karuselli <b>palvelupisteen</b> syötteiden
	 * <b>TextField</b> kentät.
	 */
	private TextField[] ksLaitteetTF = { ksSuosioATF, ksSuosioSTF, ksJonoKTF, ksLaiteKTF, ksHintaTF };

	/**
	 * Taulukko, joka sisältää viikinkilaiva <b>palvelupisteen</b> syötteiden
	 * <b>TextField</b> kentät.
	 */
	private TextField[] vkLaitteetTF = { vkSuosioATF, vkSuosioSTF, vkJonoKTF, vkLaiteKTF, vkHintaTF };

	/**
	 * Taulukko, joka sisältää kummitusjuna <b>palvelupisteen</b> syötteiden
	 * <b>TextField</b> kentät.
	 */
	private TextField[] kjLaitteetTF = { kjSuosioATF, kjSuosioSTF, kjJonoKTF, kjLaiteKTF, kjHintaTF };

	/**
	 * Taulukko, joka sisältää sisäänkäynti <b>palvelupisteen</b> syötteitä kuvaavat
	 * tekstit.
	 */
	private String[] tekstitSisaankayntiL = { "Saapumisväliaikojen keskiarvo (min):", "Jonon kapasiteetti:",
			"Palveluaika korkeintaan (min):", "Hinta (\u20ac):" };

	/**
	 * Taulukko, joka sisältää grilli <b>palvelupisteen</b> syötteitä kuvaavat
	 * tekstit.
	 */
	private String[] tekstitGrillilleL = { "Suosio aurinkoisella säällä (\u0025):", "Suosio sateella (\u0025):",
			"Jonon kapasiteetti:", "Palveluaika korkeintaan (min):", "Hinta (\u20ac):" };

	/**
	 * Taulukko, joka sisältää maailmanpyörä, vuoristorata, karuselli, viikinkilaiva
	 * ja kummitusjuna <b>palvelupisteiden</b> syötteitä kuvaavat tekstit.
	 */
	private String[] tekstitLaitteilleL = { "Suosio aurinkoisella säällä (\u0025):", "Suosio sateella (\u0025):",
			"Jonon kapasiteetti:", "Laitteen kapasiteetti:", "Hinta (\u20ac):" };

	/**
	 * Lista, johon kerätään mahdollisten virheellisten syötteiden virhetekstit.
	 */
	private ArrayList<String> virheTekstit = new ArrayList<String>();

	/**
	 * Valintanappi, jolla valitaan simulaation keston yksikkö.
	 */
	private RadioButton yksikkoPaivaRB, yksikkoTuntiRB, yksikkoMinRB;

	/**
	 * Totuusarvo, joka kertoo onko syötteiden asettamisessa tapahtunut virheitä.
	 * Alustetaan arvoksi <b>False</b>.
	 */
	private boolean onkoVirheita = false;

	/**
	 * Painike viiveen hidastusta varten.
	 */
	private Button hidastaBtn;

	/**
	 * Painike viiveen nopeutusta varten.
	 */
	private Button nopeutaBtn;

	/**
	 * Käyttöliittymän alustaja, jossa luodaan kytkökset <b>kontrolleriin</b>,
	 * <b>infoNakymaan</b> ja <b>tuloksetNakymaan</b>. Simuloinnin viiveen
	 * alustusarvo asetetaan, sekä viiveen hidasta ja nopeuta painikkeet luodaan ja
	 * kytketään pois päältä.
	 */
	@Override
	public void init() {
		viive = 150L;
		kontrolleri = new Kontrolleri(this);
		infoNakyma = new InfoNakyma();
		tuloksetNakyma = new TulosNakyma();
		hidastaBtn = new Button("<");
		hidastaBtn.setDisable(true);
		nopeutaBtn = new Button(">");
		nopeutaBtn.setDisable(true);
	}

	/**
	 * Asettaa asettelupohjat ja kontrollit pääasettelupohjaan, sekä luo ja asettaa
	 * näkymän pääikkunaan. Lopuksi näytetään pääikkuna käyttäjälle.
	 * 
	 * @param primaryStage Pääikkuna.
	 */
	@Override
	public void start(Stage primaryStage) {
		try {
			primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
				@Override
				public void handle(WindowEvent t) {
					Platform.exit();
					System.exit(0);
				}
			});

			BorderPane border = new BorderPane();
			Scene scene = new Scene(border);
			border.setPrefSize(600, 600);

			HBox hBoxYlos = otsikkoYlos();
			border.setTop(hBoxYlos);

			GridPane gridSyote = syotteetGrid();
			ScrollPane scrollPaneSyote = new ScrollPane();
			scrollPaneSyote.setContent(gridSyote);

			scrollPaneSyote.setFitToHeight(true);
			scrollPaneSyote.setPrefWidth(509);
			border.setLeft(scrollPaneSyote);

			naytto = new Visualisointi(600, 600);
			border.setCenter((Canvas) naytto);

			HBox hBoxAlas = alaosaHBox();
			border.setBottom(hBoxAlas);

			border.setStyle("-fx-background-color: #fffaee;");
			gridSyote.setStyle("-fx-background-color: #fffaee;");

			primaryStage.setMinHeight(720);
			primaryStage.setMinWidth(1165);

			BorderPane.setMargin(hBoxYlos, new Insets(5, 10, 0, 10));
			BorderPane.setMargin(scrollPaneSyote, new Insets(5, 10, 10, 10));
			BorderPane.setMargin(hBoxAlas, new Insets(0, 10, 10, 10));
			BorderPane.setMargin((Canvas) naytto, new Insets(5, 10, 10, 10));

			primaryStage.setTitle("Huvipuistosimulaattori");
			primaryStage.getIcons().add(new Image("/images/icon.png"));
			scene.getStylesheets().add(getClass().getResource("/css/Styles.css").toExternalForm());

			primaryStage.setScene(scene);
			primaryStage.show();

		} catch (Exception e) {
			System.err.println(e.getMessage());
		}

	}

	/**
	 * Luo ja asettaa kontrollit <b>HBox</b> asettelupohjaan, joka asetetaan
	 * pääasettelupohjan yläosaan. Asettelupohja sisältää simulaattorin pääotsikon.
	 * 
	 * @return <b>HBox</b> asettelupohja.
	 */
	private HBox otsikkoYlos() {
		HBox otsikkoBox = new HBox();

		Text otsikko = new Text("Huvipuistosimulaattori");
		otsikko.setId("otsikot");
		otsikko.setFill(Color.web(Visualisointi.SININEN));

		otsikkoBox.getChildren().add(otsikko);
		return otsikkoBox;
	}

	/**
	 * Luo ja asettaa kontrollit <b>GridPane</b> asettelupohjaan, joka asetetaan
	 * pääasettelupohjan vasemmalle puolelle. Asettelupohja sisältää simulaattorin
	 * syötteet ja niitä kuvaavat tekstit.
	 * 
	 * @return <b>GridPane</b> asettelupohja.
	 */
	private GridPane syotteetGrid() {
		GridPane grid = new GridPane();
		grid.setHgap(20);
		grid.setVgap(5);

		// SIMULOINTI
		Text simOtsikko = new Text("Simulointi");
		simOtsikko.setId("alaOtsikot");
		simOtsikko.setFill(Color.web(Visualisointi.SININEN));
		Label simKestoL = new Label("Simuloinnin kesto:");
		simKestoTF = new TextField(String.valueOf(1.0));
		Label simYksikkoL = new Label("Yksikkö:");
		HBox yksikot = yksikkoRB();

		// SADE
		Text sadeOtsikko = new Text("Sade");
		sadeOtsikko.setId("alaOtsikot");
		sadeOtsikko.setFill(Color.web(Visualisointi.SININEN));
		Label sadeKeskiarvoL = new Label("Tarkasteluvälin keskiarvo (min):");
		sadeKeskiarvoTF = new TextField(String.valueOf(5.0));
		Label sadeTodnakL = new Label("Sateen todennäköisyys (%):");
		sadeTodnakTF = new TextField(String.valueOf(20));

		// SISÄÄNKÄYNTI
		Label skKeskiarvoL = null, skJonoKL = null, skPAikaL = null, skHintaL = null;
		Label[] skL = { skKeskiarvoL, skJonoKL, skPAikaL, skHintaL };

		Text skOtsikko = new Text("Sisäänkäynti");
		skOtsikko.setId("alaOtsikot");
		skOtsikko.setFill(Color.web(Visualisointi.SININEN));
		for (int i = 0; i < tekstitSisaankayntiL.length; i++) {
			skL[i] = new Label(tekstitSisaankayntiL[i]);
		}

		skTF[0] = new TextField(String.valueOf(5.0));
		skTF[1] = new TextField(String.valueOf(300));
		skTF[2] = new TextField(String.valueOf(5.0));
		skTF[3] = new TextField(String.valueOf(2.0));

		// GRILLI
		Label grSuosioAL = null, grSuosioSL = null, grJonoKL = null, grPAikaL = null, grHintaL = null;
		Label[] grilliL = { grSuosioAL, grSuosioSL, grJonoKL, grPAikaL, grHintaL };

		Text grOtsikko = new Text("Grilli");
		grOtsikko.setId("alaOtsikot");
		grOtsikko.setFill(Color.web(Visualisointi.SININEN));
		for (int i = 0; i < tekstitGrillilleL.length; i++) {
			grilliL[i] = new Label(tekstitGrillilleL[i]);
		}

		grilliTF[0] = new TextField(String.valueOf(20));
		grilliTF[1] = new TextField(String.valueOf(50));
		grilliTF[2] = new TextField(String.valueOf(200));
		grilliTF[3] = new TextField(String.valueOf(10));
		grilliTF[4] = new TextField(String.valueOf(15.0));

		// MAAILMANPYÖRÄ
		Label mpSuosioAL = null, mpSuosioSL = null, mpJonoKL = null, mpLaiteKL = null, mpHintaL = null;
		Label[] mpLaitteetL = { mpSuosioAL, mpSuosioSL, mpJonoKL, mpLaiteKL, mpHintaL };

		Text mpOtsikko = new Text("Maailmanpyörä");
		mpOtsikko.setId("alaOtsikot");
		mpOtsikko.setFill(Color.web(Visualisointi.SININEN));
		for (int i = 0; i < tekstitLaitteilleL.length; i++) {
			mpLaitteetL[i] = new Label(tekstitLaitteilleL[i]);
		}

		mpLaitteetTF[0] = new TextField(String.valueOf(40));
		mpLaitteetTF[1] = new TextField(String.valueOf(10));
		mpLaitteetTF[2] = new TextField(String.valueOf(100));
		mpLaitteetTF[3] = new TextField(String.valueOf(40));
		mpLaitteetTF[4] = new TextField(String.valueOf(10.0));

		// VUORISTORATA
		Label vrSuosioAL = null, vrSuosioSL = null, vrJonoKL = null, vrLaiteKL = null, vrHintaL = null;
		Label[] vrLaitteetL = { vrSuosioAL, vrSuosioSL, vrJonoKL, vrLaiteKL, vrHintaL };

		Text vrOtsikko = new Text("Vuoristorata");
		vrOtsikko.setId("alaOtsikot");
		vrOtsikko.setFill(Color.web(Visualisointi.SININEN));
		for (int i = 0; i < tekstitLaitteilleL.length; i++) {
			vrLaitteetL[i] = new Label(tekstitLaitteilleL[i]);
		}

		vrLaitteetTF[0] = new TextField(String.valueOf(15));
		vrLaitteetTF[1] = new TextField(String.valueOf(5));
		vrLaitteetTF[2] = new TextField(String.valueOf(100));
		vrLaitteetTF[3] = new TextField(String.valueOf(10));
		vrLaitteetTF[4] = new TextField(String.valueOf(10.0));

		// KARUSELLI
		Label ksSuosioAL = null, ksSuosioSL = null, ksJonoKL = null, ksLaiteKL = null, ksHintaL = null;
		Label[] ksLaitteetL = { ksSuosioAL, ksSuosioSL, ksJonoKL, ksLaiteKL, ksHintaL };

		Text ksOtsikko = new Text("Karuselli");
		ksOtsikko.setId("alaOtsikot");
		ksOtsikko.setFill(Color.web(Visualisointi.SININEN));
		for (int i = 0; i < tekstitLaitteilleL.length; i++) {
			ksLaitteetL[i] = new Label(tekstitLaitteilleL[i]);
		}

		ksLaitteetTF[0] = new TextField(String.valueOf(10));
		ksLaitteetTF[1] = new TextField(String.valueOf(15));
		ksLaitteetTF[2] = new TextField(String.valueOf(40));
		ksLaitteetTF[3] = new TextField(String.valueOf(20));
		ksLaitteetTF[4] = new TextField(String.valueOf(5.0));

		// VIIKINKILAIVA
		Label vkSuosioAL = null, vkSuosioSL = null, vkJonoKL = null, vkLaiteKL = null, vkHintaL = null;
		Label[] vkLaitteetL = { vkSuosioAL, vkSuosioSL, vkJonoKL, vkLaiteKL, vkHintaL };

		Text vkOtsikko = new Text("Viikinkilaiva");
		vkOtsikko.setId("alaOtsikot");
		vkOtsikko.setFill(Color.web(Visualisointi.SININEN));
		for (int i = 0; i < tekstitLaitteilleL.length; i++) {
			vkLaitteetL[i] = new Label(tekstitLaitteilleL[i]);
		}

		vkLaitteetTF[0] = new TextField(String.valueOf(10));
		vkLaitteetTF[1] = new TextField(String.valueOf(5));
		vkLaitteetTF[2] = new TextField(String.valueOf(50));
		vkLaitteetTF[3] = new TextField(String.valueOf(30));
		vkLaitteetTF[4] = new TextField(String.valueOf(5.0));

		// KUMMITUSJUNA
		Label kjSuosioAL = null, kjSuosioSL = null, kjJonoKL = null, kjLaiteKL = null, kjHintaL = null;
		Label[] kjLaitteetL = { kjSuosioAL, kjSuosioSL, kjJonoKL, kjLaiteKL, kjHintaL };

		Text kjOtsikko = new Text("Kummitusjuna");
		kjOtsikko.setId("alaOtsikot");
		kjOtsikko.setFill(Color.web(Visualisointi.SININEN));
		for (int i = 0; i < tekstitLaitteilleL.length; i++) {
			kjLaitteetL[i] = new Label(tekstitLaitteilleL[i]);
		}

		kjLaitteetTF[0] = new TextField(String.valueOf(5));
		kjLaitteetTF[1] = new TextField(String.valueOf(15));
		kjLaitteetTF[2] = new TextField(String.valueOf(80));
		kjLaitteetTF[3] = new TextField(String.valueOf(10));
		kjLaitteetTF[4] = new TextField(String.valueOf(5.0));

		// ELEMENTTIEN LISÄYS
		grid.add(simOtsikko, 0, 0);
		grid.add(simKestoL, 0, 1);
		grid.add(simKestoTF, 1, 1);
		grid.add(simYksikkoL, 0, 2);
		grid.add(yksikot, 1, 2);
		grid.add(sadeOtsikko, 0, 3);
		grid.add(sadeKeskiarvoL, 0, 4);
		grid.add(sadeKeskiarvoTF, 1, 4);
		grid.add(sadeTodnakL, 0, 5);
		grid.add(sadeTodnakTF, 1, 5);

		grid.add(skOtsikko, 0, 6);
		for (int i = 0, j = 7; i < skL.length; i++, j++) {
			grid.add(skL[i], 0, j);
			grid.add(skTF[i], 1, j);
		}

		grid.add(grOtsikko, 0, 12);
		for (int i = 0, j = 13; i < grilliL.length; i++, j++) {
			grid.add(grilliL[i], 0, j);
			grid.add(grilliTF[i], 1, j);
		}

		grid.add(mpOtsikko, 0, 18);
		for (int i = 0, j = 19; i < mpLaitteetL.length; i++, j++) {
			grid.add(mpLaitteetL[i], 0, j);
			grid.add(mpLaitteetTF[i], 1, j);
		}

		grid.add(vrOtsikko, 0, 24);
		for (int i = 0, j = 25; i < vrLaitteetL.length; i++, j++) {
			grid.add(vrLaitteetL[i], 0, j);
			grid.add(vrLaitteetTF[i], 1, j);
		}

		grid.add(ksOtsikko, 0, 30);
		for (int i = 0, j = 31; i < ksLaitteetL.length; i++, j++) {
			grid.add(ksLaitteetL[i], 0, j);
			grid.add(ksLaitteetTF[i], 1, j);
		}

		grid.add(vkOtsikko, 0, 36);
		for (int i = 0, j = 37; i < vkLaitteetL.length; i++, j++) {
			grid.add(vkLaitteetL[i], 0, j);
			grid.add(vkLaitteetTF[i], 1, j);
		}

		grid.add(kjOtsikko, 0, 42);
		for (int i = 0, j = 43; i < kjLaitteetL.length; i++, j++) {
			grid.add(kjLaitteetL[i], 0, j);
			grid.add(kjLaitteetTF[i], 1, j);
		}
		return grid;
	}

	/**
	 * Luo ja asettaa valintanappi kontrollit <b>HBox</b> asettelupohjaan, joka
	 * asetetaan <b>syotteetGrid</b> asettelupohjan osaksi. Valintanapilla valitaan
	 * simuloinnin keston yksikkö. Valittavia yksiköitä ovat päivä, tunti tai
	 * minuutti.
	 * 
	 * @return <b>HBox</b> asettelupohja.
	 */
	private HBox yksikkoRB() {
		HBox yksikkoBox = new HBox();
		yksikkoBox.setSpacing(4);
		yksikkoPaivaRB = new RadioButton("Päivä");
		yksikkoTuntiRB = new RadioButton("Tunti");
		yksikkoMinRB = new RadioButton("Minuutti");

		ToggleGroup yksikkoRyhmaTG = new ToggleGroup();
		yksikkoPaivaRB.setToggleGroup(yksikkoRyhmaTG);
		yksikkoTuntiRB.setToggleGroup(yksikkoRyhmaTG);
		yksikkoMinRB.setToggleGroup(yksikkoRyhmaTG);

		yksikkoPaivaRB.setSelected(true);

		yksikkoBox.getChildren().addAll(yksikkoPaivaRB, yksikkoTuntiRB, yksikkoMinRB);

		return yksikkoBox;
	}

	/**
	 * Luo ja asettaa kontrollit <b>HBox</b> asettelupohjaan, joka asetetaan
	 * pääasettelupohjan alaosaan. Asettelupohja sisältää painikkeet
	 * <b>InfoNakyman</b>, <b>TulosNakyman</b> avaukselle, sekä painikkeet
	 * simulaation käynnistykselle, hidastukselle ja nopeutukselle.
	 * 
	 * @return <b>HBox</b> asettelupohja.
	 */
	private HBox alaosaHBox() {
		HBox painikkeetBox = new HBox();
		painikkeetBox.setSpacing(20);

		// INFO IKKUNA
		Button paaNakymaInfoBtn = new Button("?");
		paaNakymaInfoBtn.setMaxWidth(Double.MAX_VALUE);
		paaNakymaInfoBtn.setOnAction((event) -> {
			Stage stage = new Stage();

			stage.setTitle("Simulaattorin käyttö ja syötteet");
			stage.getIcons().add(new Image("/images/icon.png"));
			stage.setHeight(640);
			stage.setWidth(100);
			stage.setMinHeight(640);
			stage.setMinWidth(1000);

			stage.setScene(infoNakyma.setInfoNakyma());
			stage.show();
		});

		// TULOS IKKUNA
		Button tuloksetBtn = new Button("Tulokset");
		tuloksetBtn.setMaxWidth(Double.MAX_VALUE);
		tuloksetBtn.setOnAction((event) -> {
			Stage stage = new Stage();

			stage.setTitle("Simuloinnin tulokset");
			stage.getIcons().add(new Image("/images/icon.png"));
			stage.setHeight(700);
			stage.setWidth(1100);
			stage.setMinHeight(700);
			stage.setMinWidth(1100);

			stage.setScene(tuloksetNakyma.luoTulosNakyma());
			stage.show();
		});

		// KÄYNNISTYS
		Button kaynnistaBtn = new Button("Käynnistä");
		kaynnistaBtn.setMaxWidth(Double.MAX_VALUE);
		kaynnistaBtn.setOnAction((event) -> {
			kontrolleri.asetaSyotteet();
			if (this.onkoVirheita) {
				naytaVirheet();
			} else {
				hidastaBtn.setDisable(false);
				nopeutaBtn.setDisable(false);
				kontrolleri.kaynnistaSimulointi();
				kaynnistaBtn.setDisable(true);
			}
		});

		// HIDASTUS
		hidastaBtn.setMaxWidth(Double.MAX_VALUE);
		hidastaBtn.setOnAction((event) -> {
			kontrolleri.hidasta();
		});

		// NOPEUTUS
		nopeutaBtn.setMaxWidth(Double.MAX_VALUE);
		nopeutaBtn.setOnAction((event) -> {
			kontrolleri.nopeuta();
		});

		paaNakymaInfoBtn.setPrefWidth(30);
		kaynnistaBtn.setPrefWidth(95);
		hidastaBtn.setPrefWidth(30);
		nopeutaBtn.setPrefWidth(30);
		tuloksetBtn.setPrefWidth(80);

		Pane vali = new Pane();
		HBox.setHgrow(vali, Priority.ALWAYS);
		vali.setMinSize(10, 1);

		painikkeetBox.getChildren().addAll(paaNakymaInfoBtn, kaynnistaBtn, vali, hidastaBtn, nopeutaBtn, tuloksetBtn);

		return painikkeetBox;
	}

	/**
	 * Luo <b>Alert</b> ikkunan, joka näytetään käyttäjälle, jos syötteiden
	 * asettamisessa on tapahtunut virheitä. <b>Alerttiin</b> kerätään syötteiden
	 * yksilölliset virhetekstit, jotka näytetään käyttäjälle, jos virheellisiä
	 * arvoja on syötetty syötteiden <b>TextField</b> kenttiin.
	 */
	private void naytaVirheet() {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Syötteiden tarkistus");
		alert.setHeaderText("Syötteissä virhe!");
		alert.setContentText("Simulaatiota ei voitu käynnistää virheellisten syötteiden vuoksi.");

		String virheTeksti = "";

		for (String teksti : this.virheTekstit) {
			virheTeksti += teksti + "\n";
		}

		Label label = new Label("Tarkista seuraavat syötteet: ");
		TextArea tekstiKentta = new TextArea(virheTeksti);
		tekstiKentta.setEditable(false);
		tekstiKentta.setWrapText(true);

		tekstiKentta.setMaxWidth(Double.MAX_VALUE);
		tekstiKentta.setMaxHeight(Double.MAX_VALUE);

		GridPane sisalto = new GridPane();
		GridPane.setVgrow(tekstiKentta, Priority.ALWAYS);
		GridPane.setHgrow(tekstiKentta, Priority.ALWAYS);

		sisalto.setMaxWidth(Double.MAX_VALUE);
		sisalto.add(label, 0, 0);
		sisalto.add(tekstiKentta, 0, 1);

		alert.getDialogPane().setExpandableContent(sisalto);

		alert.showAndWait();
		onkoVirheita = false;
		virheTeksti = "";
		this.virheTekstit.clear();
	}

	/**
	 * Kerää syötteiden yksilölliset virhetekstit <b>virheTekstit</b> listaan, jotka
	 * annetaan parametrina metodille. Asettaa <b>onkoVirheita</b> totuusarvon
	 * arvoksi <b>True</b>.
	 * 
	 * @param teksti Lisättävä virheteksti.
	 */
	private void asetaVirheTeksti(String teksti) {
		if (!this.virheTekstit.contains(teksti)) {
			this.virheTekstit.add(teksti);
		}
		this.onkoVirheita = true;
	}

	/**
	 * Tarkistaa <b>Palvelupisteiden</b> suosiot aurinkoisella säällä. Suosiot
	 * aurinkoisella säällä täytyy olla väliltä 0-100. Jos suosioiden summa on
	 * erisuuri kuin 100, asetetaan virheteksti <b>VirheTekstit</b> listaan.
	 * 
	 * @exception Exception jos aurinkoisten säiden suosiot summa on erisuuri kuin
	 *                      100.
	 */
	private void tarkistaKokonaisSuosioA() {
		try {
			int summa = Integer.parseInt(grilliTF[0].getText()) + Integer.parseInt(mpLaitteetTF[0].getText())
					+ Integer.parseInt(vrLaitteetTF[0].getText()) + Integer.parseInt(ksLaitteetTF[0].getText())
					+ Integer.parseInt(vkLaitteetTF[0].getText()) + Integer.parseInt(kjLaitteetTF[0].getText());
			if (summa != 100)
				throw new Exception();
		} catch (Exception e) {
			asetaVirheTeksti("Kaikkien kohteiden suosiot aurinkoisella säällä täytyy olla yhteensä 100%");
		}
	}

	/**
	 * Tarkistaa <b>Palvelupisteiden</b> suosiot sateella. Suosiot sateella täytyy
	 * olla väliltä 0-100. Jos suosioiden summa on erisuuri kuin 100, asetetaan
	 * virheteksti <b>VirheTekstit</b> listaan.
	 * 
	 * @exception Exception jos sateiden suosioiden summa on erisuuri kuin 100.
	 */
	private void tarkistaKokonaisSuosioS() {
		try {
			int summa = Integer.parseInt(grilliTF[1].getText()) + Integer.parseInt(mpLaitteetTF[1].getText())
					+ Integer.parseInt(vrLaitteetTF[1].getText()) + Integer.parseInt(ksLaitteetTF[1].getText())
					+ Integer.parseInt(vkLaitteetTF[1].getText()) + Integer.parseInt(kjLaitteetTF[1].getText());
			if (summa != 100)
				throw new Exception();
		} catch (Exception e) {
			asetaVirheTeksti("Kaikkien kohteiden suosiot sateella täytyy olla yhteensä 100%");
		}
	}

	@Override
	public long getViive() {
		return this.viive;
	}

	@Override
	public IVisualisointi getVisualisointi() {
		return naytto;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @exception Exception jos syötetty arvo ei ole reaaliluku, joka on vähintään
	 *                      yhden tunnin.
	 */
	@Override
	public double getAika() {
		double palautus = 0.0;
		try {
			if (yksikkoPaivaRB.isSelected()) {
				palautus = Double.parseDouble(simKestoTF.getText()) * 86400;
				if (palautus < 0.04) {
					throw new Exception();
				}

			} else if (yksikkoTuntiRB.isSelected()) {
				palautus = Double.parseDouble(simKestoTF.getText()) * 3600;
				if (palautus < 1.0) {
					throw new Exception();
				}
			} else {
				palautus = Double.parseDouble(simKestoTF.getText()) * 60;
				if (palautus < 60) {
					throw new Exception();
				}
			}
		} catch (Exception e) {
			asetaVirheTeksti("Simulointi - Simuloinnin kesto: Reaaliluku, joka on vähintään yksi tunti.");
		}
		return palautus;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @exception Exception jos syötetty arvo ei ole reaaliluku, joka on suurempi
	 *                      kuin nolla.
	 */
	@Override
	public double getSaaTarkastelu() {
		double palautus = 0.0;
		try {
			palautus = Double.parseDouble(sadeKeskiarvoTF.getText()) * 60;
			if (palautus <= 0)
				throw new Exception();
		} catch (Exception e) {
			asetaVirheTeksti("Sade - Tarkasteluvälin keskiarvo (min): Reaaliluku, joka on suurempi kuin 0.");
		}
		return palautus;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @exception Exception jos syötetty arvo ei ole kokonaisluku, joka on väliltä
	 *                      0-100.
	 */
	@Override
	public int getSadeTodNak() {
		int palautus = 0;
		try {
			palautus = Integer.parseInt(sadeTodnakTF.getText());
			if (palautus < 0 || palautus > 100)
				throw new Exception();
			if (palautus == 0) {
				sadeKeskiarvoTF.setDisable(true);
			}
		} catch (Exception e) {
			asetaVirheTeksti("Sade - Sateen todennäköisyys (%): Kokonaisluku, joka on väliltä 0-100.");
		}
		return palautus;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @exception Exception jos syötetty arvo ei ole reaaliluku, joka on suurempi
	 *                      kuin nolla.
	 */
	@Override
	public double getSaapumisVali() {
		double palautus = 0.0;
		try {
			palautus = Double.parseDouble(skTF[0].getText()) * 60;
			if (palautus <= 0)
				throw new Exception();
		} catch (Exception e) {
			asetaVirheTeksti(
					"Sisäänkäynti - Saapumisväliaikojen keskiarvo (min): Reaaliluku, joka on suurempi kuin 0.");
		}
		return palautus;

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @exception Exception jos syötetty arvo ei ole kokonaisluku, joka on suurempi
	 *                      kuin nolla.
	 */
	@Override
	public int getSKJpituus() {
		int palautus = 0;
		try {
			palautus = Integer.parseInt(skTF[1].getText());
			if (palautus <= 0)
				throw new Exception();
		} catch (Exception e) {
			asetaVirheTeksti("Sisäänkäynti - Jonon kapasiteetti: Kokonaisluku, joka on suurempi kuin 0.");
		}
		return palautus;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @exception Exception jos syötetty arvo ei ole kokonaisluku, joka on suurempi
	 *                      kuin nolla.
	 */
	@Override
	public int getGRJpituus() {
		int palautus = 0;
		try {
			palautus = Integer.parseInt(grilliTF[2].getText());
			if (palautus <= 0)
				throw new Exception();
		} catch (Exception e) {
			asetaVirheTeksti("Grilli - Jonon kapasiteetti: Kokonaisluku, joka on suurempi kuin 0.");
		}
		return palautus;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @exception Exception jos syötetty arvo ei ole kokonaisluku, joka on suurempi
	 *                      kuin nolla.
	 */
	@Override
	public int getMPJpituus() {
		int palautus = 0;
		try {
			palautus = Integer.parseInt(mpLaitteetTF[2].getText());
			if (palautus <= 0)
				throw new Exception();
		} catch (Exception e) {
			asetaVirheTeksti("Maailmanpyörä - Jonon kapasiteetti: Kokonaisluku, joka on suurempi kuin 0.");
		}
		return palautus;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @exception Exception jos syötetty arvo ei ole kokonaisluku, joka on suurempi
	 *                      kuin nolla.
	 */
	@Override
	public int getVRJpituus() {
		int palautus = 0;
		try {
			palautus = Integer.parseInt(vrLaitteetTF[2].getText());
			if (palautus <= 0)
				throw new Exception();
		} catch (Exception e) {
			asetaVirheTeksti("Vuoristorata - Jonon kapasiteetti: Kokonaisluku, joka on suurempi kuin 0.");
		}
		return palautus;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @exception Exception jos syötetty arvo ei ole kokonaisluku, joka on suurempi
	 *                      kuin nolla.
	 */
	@Override
	public int getKSJpituus() {
		int palautus = 0;
		try {
			palautus = Integer.parseInt(ksLaitteetTF[2].getText());
			if (palautus <= 0)
				throw new Exception();
		} catch (Exception e) {
			asetaVirheTeksti("Karuselli - Jonon kapasiteetti: Kokonaisluku, joka on suurempi kuin 0.");
		}
		return palautus;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @exception Exception jos syötetty arvo ei ole kokonaisluku, joka on suurempi
	 *                      kuin nolla.
	 */
	@Override
	public int getVLJpituus() {
		int palautus = 0;
		try {
			palautus = Integer.parseInt(vkLaitteetTF[2].getText());
			if (palautus <= 0)
				throw new Exception();
		} catch (Exception e) {
			asetaVirheTeksti("Viikinlilaiva - Jonon kapasiteetti: Kokonaisluku, joka on suurempi kuin 0.");
		}
		return palautus;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @exception Exception jos syötetty arvo ei ole kokonaisluku, joka on suurempi
	 *                      kuin nolla.
	 */
	@Override
	public int getKJJpituus() {
		int palautus = 0;
		try {
			palautus = Integer.parseInt(kjLaitteetTF[2].getText());
			if (palautus <= 0)
				throw new Exception();
		} catch (Exception e) {
			asetaVirheTeksti("Kummitusjuna - Jonon kapasiteetti: Kokonaisluku, joka on suurempi kuin 0.");
		}
		return palautus;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @exception Exception jos syötetty arvo ei ole reaaliluku, joka on suurempi
	 *                      kuin yksi minuutti.
	 */
	@Override
	public double getSKaikaMax() {
		double palautus = 0.0;
		try {
			palautus = Double.parseDouble(skTF[2].getText()) * 60;
			if (palautus <= 60)
				throw new Exception();
		} catch (Exception e) {
			asetaVirheTeksti("Sisäänkäynti - Palveluaika korkeintaan (min): Reaaliluku, joka on suurempi kuin 1.");
		}
		return palautus;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @exception Exception jos syötetty arvo ei ole reaaliluku, joka on suurempi
	 *                      kuin yksi minuutti.
	 */
	@Override
	public double getGRaikaMax() {
		double palautus = 0.0;
		try {
			palautus = Double.parseDouble(grilliTF[3].getText()) * 60;
			if (palautus <= 60)
				throw new Exception();
		} catch (Exception e) {
			asetaVirheTeksti("Grilli - Palveluaika korkeintaan (min): Reaaliluku, joka on suurempi kuin 1.");
		}
		return palautus;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @exception Exception jos syötetty arvo ei ole reaaliluku, joka on nolla tai
	 *                      suurempi kuin nolla.
	 */
	@Override
	public double getSKHinta() {
		double palautus = 0.0;
		try {
			palautus = Double.parseDouble(skTF[3].getText());
			if (palautus < 0)
				throw new Exception();
		} catch (Exception e) {
			asetaVirheTeksti("Sisäänkäynti - Hinta (\u20ac): Reaaliluku, joka on 0 tai suurempi kuin 0.");
		}
		return palautus;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @exception Exception jos syötetty arvo ei ole reaaliluku, joka on nolla tai
	 *                      suurempi kuin nolla.
	 */
	@Override
	public double getGRHinta() {
		double palautus = 0.0;
		try {
			palautus = Double.parseDouble(grilliTF[4].getText());
			if (palautus < 0)
				throw new Exception();
		} catch (Exception e) {
			asetaVirheTeksti("Grilli - Hinta (\u20ac): Reaaliluku, joka on 0 tai suurempi kuin 0.");
		}
		return palautus;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @exception Exception jos syötetty arvo ei ole reaaliluku, joka on nolla tai
	 *                      suurempi kuin nolla.
	 */
	@Override
	public double getMPHinta() {
		double palautus = 0.0;
		try {
			palautus = Double.parseDouble(mpLaitteetTF[4].getText());
			if (palautus < 0)
				throw new Exception();
		} catch (Exception e) {
			asetaVirheTeksti("Maailmanpyörä - Hinta (\u20ac): Reaaliluku, joka on 0 tai suurempi kuin 0.");
		}
		return palautus;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @exception Exception jos syötetty arvo ei ole reaaliluku, joka on nolla tai
	 *                      suurempi kuin nolla.
	 */
	@Override
	public double getVRHinta() {
		double palautus = 0.0;
		try {
			palautus = Double.parseDouble(vrLaitteetTF[4].getText());
			if (palautus < 0)
				throw new Exception();
		} catch (Exception e) {
			asetaVirheTeksti("Vuoristorata - Hinta (\u20ac): Reaaliluku, joka on 0 tai suurempi kuin 0.");
		}
		return palautus;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @exception Exception jos syötetty arvo ei ole reaaliluku, joka on nolla tai
	 *                      suurempi kuin nolla.
	 */
	@Override
	public double getKSHinta() {
		double palautus = 0.0;
		try {
			palautus = Double.parseDouble(ksLaitteetTF[4].getText());
			if (palautus < 0)
				throw new Exception();
		} catch (Exception e) {
			asetaVirheTeksti("Karuselli - Hinta (\u20ac): Reaaliluku, joka on 0 tai suurempi kuin 0.");
		}
		return palautus;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @exception Exception jos syötetty arvo ei ole reaaliluku, joka on nolla tai
	 *                      suurempi kuin nolla.
	 */
	@Override
	public double getVLHinta() {
		double palautus = 0.0;
		try {
			palautus = Double.parseDouble(vkLaitteetTF[4].getText());
			if (palautus < 0)
				throw new Exception();
		} catch (Exception e) {
			asetaVirheTeksti("Viikinkilaiva - Hinta (\u20ac): Reaaliluku, joka on 0 tai suurempi kuin 0.");
		}
		return palautus;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @exception Exception jos syötetty arvo ei ole reaaliluku, joka on nolla tai
	 *                      suurempi kuin nolla.
	 */
	@Override
	public double getKJHinta() {
		double palautus = 0.0;
		try {
			palautus = Double.parseDouble(kjLaitteetTF[4].getText());
			if (palautus < 0)
				throw new Exception();
		} catch (Exception e) {
			asetaVirheTeksti("Kummitusjuna - Hinta (\u20ac): Reaaliluku, joka on 0 tai suurempi kuin 0.");
		}
		return palautus;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @exception Exception jos syötetty arvo ei ole kokonaisluku, joka on väliltä
	 *                      0-100.
	 */
	@Override
	public int getGRsuosioA() {
		int palautus = 0;
		try {
			palautus = Integer.parseInt(grilliTF[0].getText());
			if (palautus < 0 || palautus > 100) {
				throw new Exception();
			} else {
				tarkistaKokonaisSuosioA(); // Riittää tarkistaa vain yhdessä metodissa aurinkoisten suosioiden summa.
			}
		} catch (Exception e) {
			asetaVirheTeksti("Grilli - Suosio aurinkoisella säällä (%): Kokonaisluku, joka on väliltä 0-100.");
		}
		return palautus;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @exception Exception jos syötetty arvo ei ole kokonaisluku, joka on väliltä
	 *                      0-100.
	 */
	@Override
	public int getMPsuosioA() {
		int palautus = 0;
		try {
			palautus = Integer.parseInt(mpLaitteetTF[0].getText());
			if (palautus < 0 || palautus > 100) {
				throw new Exception();
			}
		} catch (Exception e) {
			asetaVirheTeksti("Maailmanpyörä - Suosio aurinkoisella säällä (%): Kokonaisluku, joka on väliltä 0-100.");
		}
		return palautus;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @exception Exception jos syötetty arvo ei ole kokonaisluku, joka on väliltä
	 *                      0-100.
	 */
	@Override
	public int getVRsuosioA() {
		int palautus = 0;
		try {
			palautus = Integer.parseInt(vrLaitteetTF[0].getText());
			if (palautus < 0 || palautus > 100) {
				throw new Exception();
			}
		} catch (Exception e) {
			asetaVirheTeksti("Vuoristorata - Suosio aurinkoisella säällä (%): Kokonaisluku, joka on väliltä 0-100.");
		}
		return palautus;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @exception Exception jos syötetty arvo ei ole kokonaisluku, joka on väliltä
	 *                      0-100.
	 */
	@Override
	public int getKSsuosioA() {
		int palautus = 0;
		try {
			palautus = Integer.parseInt(ksLaitteetTF[0].getText());
			if (palautus < 0 || palautus > 100) {
				throw new Exception();
			}
		} catch (Exception e) {
			asetaVirheTeksti("Karuselli - Suosio aurinkoisella säällä (%): Kokonaisluku, joka on väliltä 0-100.");
		}
		return palautus;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @exception Exception jos syötetty arvo ei ole kokonaisluku, joka on väliltä
	 *                      0-100.
	 */
	@Override
	public int getVLsuosioA() {
		int palautus = 0;
		try {
			palautus = Integer.parseInt(vkLaitteetTF[0].getText());
			if (palautus < 0 || palautus > 100) {
				throw new Exception();
			}
		} catch (Exception e) {
			asetaVirheTeksti("Viikinkilaiva - Suosio aurinkoisella säällä (%): Kokonaisluku, joka on väliltä 0-100.");
		}
		return palautus;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @exception Exception jos syötetty arvo ei ole kokonaisluku, joka on väliltä
	 *                      0-100.
	 */
	@Override
	public int getKJsuosioA() {
		int palautus = 0;
		try {
			palautus = Integer.parseInt(kjLaitteetTF[0].getText());
			if (palautus < 0 || palautus > 100) {
				throw new Exception();
			}
		} catch (Exception e) {
			asetaVirheTeksti("Kummitusjuna - Suosio aurinkoisella säällä (%): Kokonaisluku, joka on väliltä 0-100.");
		}
		return palautus;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @exception Exception jos syötetty arvo ei ole kokonaisluku, joka on väliltä
	 *                      0-100.
	 */
	@Override
	public int getGRsuosioS() {
		int palautus = 0;
		try {
			palautus = Integer.parseInt(grilliTF[1].getText());
			if (palautus < 0 || palautus > 100) {
				throw new Exception();
			} else {
				tarkistaKokonaisSuosioS(); // Riittää tarkistaa vain yhdessä metodissa sateiden summa
			}
		} catch (Exception e) {
			asetaVirheTeksti("Grilli - Suosio sateella (%): Kokonaisluku, joka on väliltä 0-100.");
		}
		return palautus;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @exception Exception jos syötetty arvo ei ole kokonaisluku, joka on väliltä
	 *                      0-100.
	 */
	@Override
	public int getMPsuosioS() {
		int palautus = 0;
		try {
			palautus = Integer.parseInt(mpLaitteetTF[1].getText());
			if (palautus < 0 || palautus > 100) {
				throw new Exception();
			}
		} catch (Exception e) {
			asetaVirheTeksti("Maailmanpyörä - Suosio sateella (%): Kokonaisluku, joka on väliltä 0-100.");
		}
		return palautus;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @exception Exception jos syötetty arvo ei ole kokonaisluku, joka on väliltä
	 *                      0-100.
	 */
	@Override
	public int getVRsuosioS() {
		int palautus = 0;
		try {
			palautus = Integer.parseInt(vrLaitteetTF[1].getText());
			if (palautus < 0 || palautus > 100) {
				throw new Exception();
			}
		} catch (Exception e) {
			asetaVirheTeksti("Vuoristorata - Suosio sateella (%): Kokonaisluku, joka on väliltä 0-100.");
		}
		return palautus;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @exception Exception jos syötetty arvo ei ole kokonaisluku, joka on väliltä
	 *                      0-100.
	 */
	@Override
	public int getKSsuosioS() {
		int palautus = 0;
		try {
			palautus = Integer.parseInt(ksLaitteetTF[1].getText());
			if (palautus < 0 || palautus > 100) {
				throw new Exception();
			}
		} catch (Exception e) {
			asetaVirheTeksti("Karuselli - Suosio sateella (%): Kokonaisluku, joka on väliltä 0-100.");
		}
		return palautus;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @exception Exception jos syötetty arvo ei ole kokonaisluku, joka on väliltä
	 *                      0-100.
	 */
	@Override
	public int getVLsuosioS() {
		int palautus = 0;
		try {
			palautus = Integer.parseInt(vkLaitteetTF[1].getText());
			if (palautus < 0 || palautus > 100) {
				throw new Exception();
			}
		} catch (Exception e) {
			asetaVirheTeksti("Viikinkilaiva - Suosio sateella (%): Kokonaisluku, joka on väliltä 0-100.");
		}
		return palautus;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @exception Exception jos syötetty arvo ei ole kokonaisluku, joka on väliltä
	 *                      0-100.
	 */
	@Override
	public int getKJsuosioS() {
		int palautus = 0;
		try {
			palautus = Integer.parseInt(kjLaitteetTF[1].getText());
			if (palautus < 0 || palautus > 100) {
				throw new Exception();
			}
		} catch (Exception e) {
			asetaVirheTeksti("Kummitusjuna - Suosio sateella (%): Kokonaisluku, joka on väliltä 0-100.");
		}
		return palautus;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @exception Exception jos syötetty arvo ei ole kokonaisluku, joka on suurempi
	 *                      kuin nolla.
	 */
	@Override
	public int getMPkapasiteetti() {
		int palautus = 0;
		try {
			palautus = Integer.parseInt(mpLaitteetTF[3].getText());
			if (palautus <= 0) {
				throw new Exception();
			}
		} catch (Exception e) {
			asetaVirheTeksti("Maailmanpyörä - Laitteen kapasiteetti: Kokonaisluku, joka on suurempi kuin 0.");
		}
		return palautus;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @exception Exception jos syötetty arvo ei ole kokonaisluku, joka on suurempi
	 *                      kuin nolla.
	 */
	@Override
	public int getVRkapasiteetti() {
		int palautus = 0;
		try {
			palautus = Integer.parseInt(vrLaitteetTF[3].getText());
			if (palautus <= 0) {
				throw new Exception();
			}
		} catch (Exception e) {
			asetaVirheTeksti("Vuoristorata - Laitteen kapasiteetti: Kokonaisluku, joka on suurempi kuin 0.");
		}
		return palautus;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @exception Exception jos syötetty arvo ei ole kokonaisluku, joka on suurempi
	 *                      kuin nolla.
	 */
	@Override
	public int getKSkapasiteetti() {
		int palautus = 0;
		try {
			palautus = Integer.parseInt(ksLaitteetTF[3].getText());
			if (palautus <= 0) {
				throw new Exception();
			}
		} catch (Exception e) {
			asetaVirheTeksti("Karuselli - Laitteen kapasiteetti: Kokonaisluku, joka on suurempi kuin 0.");
		}
		return palautus;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @exception Exception jos syötetty arvo ei ole kokonaisluku, joka on suurempi
	 *                      kuin nolla.
	 */
	@Override
	public int getVLkapasiteetti() {
		int palautus = 0;
		try {
			palautus = Integer.parseInt(vkLaitteetTF[3].getText());
			if (palautus <= 0) {
				throw new Exception();
			}
		} catch (Exception e) {
			asetaVirheTeksti("Viikinkilaiva - Laitteen kapasiteetti: Kokonaisluku, joka on suurempi kuin 0.");
		}
		return palautus;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @exception Exception jos syötetty arvo ei ole kokonaisluku, joka on suurempi
	 *                      kuin nolla.
	 */
	@Override
	public int getKJkapasiteetti() {
		int palautus = 0;
		try {
			palautus = Integer.parseInt(kjLaitteetTF[3].getText());
			if (palautus <= 0) {
				throw new Exception();
			}
		} catch (Exception e) {
			asetaVirheTeksti("Kummitusjuna - Laitteen kapasiteetti: Kokonaisluku, joka on suurempi kuin 0.");
		}
		return palautus;
	}

	/**
	 * Käynnistää JavaFX-sovelluksen (käyttöliittymän).
	 * 
	 * @param args komentoriviargumentti.
	 */
	public static void main(String[] args) {
		launch(args);
	}

}