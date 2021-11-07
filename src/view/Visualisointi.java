package view;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 * Sisältää simulaation visualisointiin tarvittavan toiminnallisuuden.
 * Visualisoinnissa luodaan canvas-olio, johon piirretään palvelupisteet ja
 * niiden jonotusalueet. Asiakkaiden kulkua visualisoidaan piirtämällä niitä
 * palvelupisteiden jonoihin, poistamalla asiakkaita jonoista ja näyttämällä
 * numeerisella arvolla, kuinka monta asiakasta on palveltavana
 * palvelupisteessä.
 * 
 * @author Otso Poussa, Tanja Pyykönen ja Tatu Talvikko
 * @version 1.0
 */
public class Visualisointi extends Canvas implements IVisualisointi {
	/**
	 * Labelit taulukoiden minimi ja maksimi arvoille, palvelupisteille sekä X- ja
	 * Y-kordinaateille.
	 */
	private final static int MIN = 0, MAX = 1, X = 0, Y = 1, SK = 0, GR = 1, MP = 2, VR = 3, KR = 4, VL = 5, KJ = 6;

	/**
	 * Visualisointiin käytettävä <b>GraphicsContext</b>-olio.
	 */
	private GraphicsContext gc;

	/**
	 * Kanvaksessa käytetty väri.
	 */
	protected final static String SININEN = "311D76", TUMMAKELTAINEN = "FFE7B2", KELTAINEN = "FFECC2",
			PUNAINEN = "D70008";
	
	/**
	 * Taulukko jonka avulla mitataan palvelupisteiden jonojen pituutta. Alkiot
	 * vastaavat eri palvelupisteitä.
	 */
	private int jonossa[] = new int[7];

	/**
	 * Kaksisuuntainen taulukko, jossa pidetään jonon viimeisen asiakkaan sijaintia
	 * eri palvelupisteiden jonoissa. <br>
	 * Ensimmäinen taulukon suunta viittaa palvelupisteeseen ja toinen X tai Y
	 * kordinaattiin.
	 */
	private double jonoKordinaatit[][] = new double[7][2];

	/**
	 * <b>minMaxX</b>- ja <b>minMaxY</b>-taulukoihin määritetään eri
	 * palvelupisteiden jonojen rajat kanvaksen kordinaateissa. Asiakkaat piirretään
	 * palvelupisteiden jonoissa rajojen sisään.
	 */
	private final double minMaxX[][] = new double[7][4], minMaxY[][] = new double[7][4];

	/**
	 * Luo <b>Canvas</b>-olion yliluokan konstruktorilla ja asettaa
	 * <b>GraphicsContext2D</b>-kontekstin instanssimuuttujaan. <br>
	 * Kutsuu yliluokan konstruktoria ja asettaa kordinaatit eri palvelupisteille
	 * kanvaksella. Asettaa myös jokaiselle palvelupisteiden jonoihin rajat
	 * asiakkaiden liikkumiselle, sekä alustaa asiakkaiden kordinaatit alkamaan
	 * jonon kärjestä. Asettaa myös <br>
	 * <br>
	 * Palvelupisteiden jonot ovat suorakulmion muotoisia ja asiakkaat liikkuvat
	 * niissä oikealta vasemmalle ja alhaalta ylös.
	 * 
	 * @param w Kanvaksen leveys.
	 * @param h Kanvaksen korkeus.
	 */
	public Visualisointi(int w, int h) {
		super(w, h);
		gc = this.getGraphicsContext2D();
		tyhjennaNaytto();

		// Palvelupiste 1 (Sisäänkäynti)
		minMaxX[SK][MIN] = 10;
		minMaxX[SK][MAX] = 10 + 580;
		minMaxY[SK][MIN] = 530;
		minMaxY[SK][MAX] = 530 + 50;

		jonoKordinaatit[SK][X] = minMaxX[SK][MIN] - 10;
		jonoKordinaatit[SK][Y] = minMaxY[SK][MIN];

		// Palvelupiste 2 (Grilli)
		minMaxX[GR][MIN] = 10;
		minMaxX[GR][MAX] = 10 + 150;
		minMaxY[GR][MIN] = 100;
		minMaxY[GR][MAX] = 100 + 90;

		jonoKordinaatit[GR][X] = minMaxX[GR][MIN] - 10;
		jonoKordinaatit[GR][Y] = minMaxY[GR][MIN];

		// Palvelupiste 3 (Maailmanpyörä)
		minMaxX[MP][MIN] = 225;
		minMaxX[MP][MAX] = 225 + 150;
		minMaxY[MP][MIN] = 100;
		minMaxY[MP][MAX] = 100 + 90;

		jonoKordinaatit[MP][X] = minMaxX[MP][MIN] - 10;
		jonoKordinaatit[MP][Y] = minMaxY[MP][MIN];

		// Palvelupiste 4 (Vuoristorata)
		minMaxX[VR][MIN] = 440;
		minMaxX[VR][MAX] = 440 + 150;
		minMaxY[VR][MIN] = 100;
		minMaxY[VR][MAX] = 100 + 90;

		jonoKordinaatit[VR][X] = minMaxX[VR][MIN] - 10;
		jonoKordinaatit[VR][Y] = minMaxY[VR][MIN];

		// Palvelupiste 5 (Karuselli)
		minMaxX[KR][MIN] = 10;
		minMaxX[KR][MAX] = 10 + 150;
		minMaxY[KR][MIN] = 330;
		minMaxY[KR][MAX] = 330 + 90;

		jonoKordinaatit[KR][X] = minMaxX[KR][MIN] - 10;
		jonoKordinaatit[KR][Y] = minMaxY[KR][MIN];

		// Palvelupiste 6 (Viikinkilaiva)
		minMaxX[VL][MIN] = 225;
		minMaxX[VL][MAX] = 225 + 150;
		minMaxY[VL][MIN] = 330;
		minMaxY[VL][MAX] = 330 + 90;

		jonoKordinaatit[VL][X] = minMaxX[VL][MIN] - 10;
		jonoKordinaatit[VL][Y] = minMaxY[VL][MIN];

		// Palvelupiste 7 (Kummitusjuna)
		minMaxX[KJ][MIN] = 440;
		minMaxX[KJ][MAX] = 440 + 150;
		minMaxY[KJ][MIN] = 330;
		minMaxY[KJ][MAX] = 330 + 90;

		jonoKordinaatit[KJ][X] = minMaxX[KJ][MIN] - 10;
		jonoKordinaatit[KJ][Y] = minMaxY[KJ][MIN];

		for (int x = 0; x < 7; x++) {
			jonossa[x] = 0;
		}
	}

	@Override
	public void tyhjennaNaytto() {
		gc.setFill(Color.web(TUMMAKELTAINEN));
		gc.fillRect(0, 0, this.getWidth(), this.getHeight());
		luoPalveluPisteet();
	}

	@Override
	public void luoPalveluPisteet() {
		gc.setFont(Font.font("Verdana", 15));

		// Grilli
		Image grKuva = new Image("/images/grilli.png");
		gc.drawImage(grKuva, 10, 10, 60, 90);

		gc.setFill(Color.web(KELTAINEN));
		gc.fillRect(10, 100, 150, 90);

		// Maailmanpyörä
		Image mpKuva = new Image("/images/maailmanpyora.png");
		gc.drawImage(mpKuva, 213, 10, 80, 90);

		gc.setFill(Color.web(KELTAINEN));
		gc.fillRect(225, 100, 150, 90);

		// Vuoristorata
		Image vrKuva = new Image("/images/vuoristorata.png");
		gc.drawImage(vrKuva, 440, 40, 120, 60);

		gc.setFill(Color.web(KELTAINEN));
		gc.fillRect(440, 100, 150, 90);

		// Karuselli
		Image ksKuva = new Image("/images/karuselli.png");
		gc.drawImage(ksKuva, 10, 230, 90, 100);

		gc.setFill(Color.web(KELTAINEN));
		gc.fillRect(10, 330, 150, 90);

		// Viikinkilaiva
		Image vlKuva = new Image("/images/viikinkilaiva.png");
		gc.drawImage(vlKuva, 225, 240, 100, 90);

		gc.setFill(Color.web(KELTAINEN));
		gc.fillRect(225, 330, 150, 90);

		// Kummitusjuna
		Image kjKuva = new Image("/images/kummitusjuna.png");
		gc.drawImage(kjKuva, 440, 270, 110, 60);

		gc.setFill(Color.web(KELTAINEN));
		gc.fillRect(440, 330, 150, 90);

		// Sisäänkäynti
		Image skKuva = new Image("/images/sisaankaynti.png");
		gc.drawImage(skKuva, 10, 445, 105, 85);

		gc.setFill(Color.web(KELTAINEN));
		gc.fillRect(10, 530, 580, 50);
	}

	@Override
	public void visuaaliPoistaJonosta(String piste) {
		int i = this.palvelupisteNumeroksi(piste);
		double[] jonoTekstiKordinaatit = this.haeTekstinKordinaatit(i);

		gc.setFill(Color.web(TUMMAKELTAINEN));
		gc.fillRect(jonoTekstiKordinaatit[X], jonoTekstiKordinaatit[Y] - 15, 100, 20);

		if (jonoKordinaatit[i][X] < minMaxX[i][MIN] && !((jonoKordinaatit[i][Y] - 10) < minMaxY[i][MIN])) {
			jonoKordinaatit[i][Y] -= 10;
			jonoKordinaatit[i][X] = minMaxX[i][MAX] - 10;
		}

		if (i != SK && jonossa[i] <= 135 || i == 0 && jonossa[i] <= 290) {
			gc.setFill(Color.web(KELTAINEN));
			gc.fillRect(jonoKordinaatit[i][X], jonoKordinaatit[i][Y], 10, 10);
			jonoKordinaatit[i][X] -= 10;
		} else {
			gc.setFill(Color.BLACK);
			gc.fillText("Jonossa: " + String.valueOf(jonossa[i]), jonoTekstiKordinaatit[X], jonoTekstiKordinaatit[Y]);
		}
		jonossa[i]--;

	}

	@Override
	public void visuaaliLisaaJonoon(String piste) {
		int i = this.palvelupisteNumeroksi(piste);
		double[] jonoTekstiKordinaatit = this.haeTekstinKordinaatit(i);

		gc.setFill(Color.web(TUMMAKELTAINEN));
		gc.fillRect(jonoTekstiKordinaatit[X], jonoTekstiKordinaatit[Y] - 15, 100, 20);

		if (i != SK && jonossa[i] >= 135 || i == 0 && jonossa[i] >= 290) {
			jonossa[i]++;
			gc.setFill(Color.BLACK);
			gc.fillText("Jonossa: " + String.valueOf(jonossa[i]), jonoTekstiKordinaatit[X], jonoTekstiKordinaatit[Y]);
			return;
		}

		gc.setFill(Color.web(SININEN));
		if (jonoKordinaatit[i][X] + 20 > minMaxX[i][MAX]) {
			jonoKordinaatit[i][Y] += 10;
			jonoKordinaatit[i][X] = minMaxX[i][MIN] - 10;
		}

		jonossa[i]++;
		jonoKordinaatit[i][X] += 10;
		gc.fillOval(jonoKordinaatit[i][X], jonoKordinaatit[i][Y], 10, 10);
	}

	@Override
	public void asetaSade() {
		gc.setFill(Color.web(TUMMAKELTAINEN));
		gc.fillRect(550, 5, 40, 40);
		Image sadeKuva = new Image("/images/sade.png");
		gc.drawImage(sadeKuva, 550, 5, 40, 40);

	}

	@Override
	public void asetaAurinko() {
		gc.setFill(Color.web(TUMMAKELTAINEN));
		gc.fillRect(550, 5, 40, 40);
		Image aurinkoKuva = new Image("/images/aurinko.png");
		gc.drawImage(aurinkoKuva, 550, 5, 40, 40);

	}

	public void asetaSimValmisTeksti() {
		gc.setFill(Color.web(PUNAINEN));
		gc.fillText("Simulointi valmis!", 456, 595);
	}

	@Override
	public void visuaaliPalvelussa(String piste, int asiakasMaara) {
		int i = this.palvelupisteNumeroksi(piste);

		switch (i) {
		case SK:
			gc.setFill(Color.web(TUMMAKELTAINEN));
			gc.fillRect(130, 513, 45, 15);
			gc.setFill(Color.BLACK);
			gc.fillText(String.valueOf(asiakasMaara), 130, 528);
			break;
		case GR:
			gc.setFill(Color.web(TUMMAKELTAINEN));
			gc.fillRect(75, 83, 45, 15);
			gc.setFill(Color.BLACK);
			gc.fillText(String.valueOf(asiakasMaara), 75, 98);
			break;
		case MP:
			gc.setFill(Color.web(TUMMAKELTAINEN));
			gc.fillRect(290, 80, 45, 18);
			gc.setFill(Color.BLACK);
			gc.fillText(String.valueOf(asiakasMaara), 290, 98);
			break;
		case VR:
			gc.setFill(Color.web(TUMMAKELTAINEN));
			gc.fillRect(568, 83, 45, 15);
			gc.setFill(Color.BLACK);
			gc.fillText(String.valueOf(asiakasMaara), 568, 98);
			break;
		case KR:
			gc.setFill(Color.web(TUMMAKELTAINEN));
			gc.fillRect(100, 314, 45, 15);
			gc.setFill(Color.BLACK);
			gc.fillText(String.valueOf(asiakasMaara), 100, 329);
			break;
		case VL:
			gc.setFill(Color.web(TUMMAKELTAINEN));
			gc.fillRect(335, 314, 45, 15);
			gc.setFill(Color.BLACK);
			gc.fillText(String.valueOf(asiakasMaara), 335, 329);
			break;
		case KJ:
			gc.setFill(Color.web(TUMMAKELTAINEN));
			gc.fillRect(560, 314, 45, 15);
			gc.setFill(Color.BLACK);
			gc.fillText(String.valueOf(asiakasMaara), 560, 329);
			break;
		}

	}

	/**
	 * Muuntaa parametrin palvelupisteen nimen sitä vastaavaksi numeroksi (0-6).
	 * 
	 * @param piste Palvelupisteen nimi.
	 * @return Palvelupistettä vastaavan numeron.
	 */
	private int palvelupisteNumeroksi(String piste) {
		int i = 99;
		switch (piste) {
		case "Sisäänkäynti":
			i = 0;
			break;
		case "Grilli":
			i = 1;
			break;
		case "Maailmanpyörä":
			i = 2;
			break;
		case "Vuoristorata":
			i = 3;
			break;
		case "Karuselli":
			i = 4;
			break;
		case "Viikinkilaiva":
			i = 5;
			break;
		case "Kummitusjuna":
			i = 6;
			break;

		}
		return i;
	}

	/**
	 * Hakee pisteen jonon pituutta kuvaavan tekstin kordinaatit parametrin
	 * palvelupisteen persuteella.
	 * 
	 * @param piste Palvelupisteen numero.
	 * @return Taulukon, jossa tekstin X- ja Y-kordinaatit.
	 */
	private double[] haeTekstinKordinaatit(int piste) {
		double[] kordinaatit = new double[2];
		switch (piste) {
		case 0:
			kordinaatit[X] = 10;
			kordinaatit[Y] = 595;
			break;
		case 1:
			kordinaatit[X] = 10;
			kordinaatit[Y] = 205;
			break;
		case 2:
			kordinaatit[X] = 225;
			kordinaatit[Y] = 205;
			break;
		case 3:
			kordinaatit[X] = 440;
			kordinaatit[Y] = 205;
			break;
		case 4:
			kordinaatit[X] = 10;
			kordinaatit[Y] = 435;
			break;
		case 5:
			kordinaatit[X] = 225;
			kordinaatit[Y] = 435;
			break;
		case 6:
			kordinaatit[X] = 440;
			kordinaatit[Y] = 435;
			break;
		}
		return kordinaatit;
	}
}