package simu.model;

import javax.persistence.Entity;

import eduni.distributions.Uniform;
import simu.framework.Kello;
import simu.framework.Trace;

/**
 * Luo sää-singletonin, joka ohjaa sään muuttumista simuloinnin aikana.
 * 
 * @author Otso Poussa, Tanja Pyykönen ja Tatu Talvikko
 * @version 1.0
 */
@Entity
public class Saa {
	/**
	 * Saa-singletonin instanssi.
	 */
	private static Saa instanssi;

	/**
	 * Kokonaisluku 1-100 väliltä, joka määrää sateen todennäköisyyden.
	 */
	private int sateenTodnak;

	/**
	 * Sateiden lukumäärä, joka kertoo kuinka montaa kertaa satoi yhteensä
	 * simuloinnin aikana.
	 */
	private int sadeKerrat;

	/**
	 * Aika, milloin sade loppui.
	 */
	private double sateenLoppu;

	/**
	 * Sateen kesto, joka kestoo kuinka simuloinnin aikana yhteensä satoi.
	 */
	private double sateenKokoKesto;

	/**
	 * Aika, joka kuvaa kuinka usein sadetta tarkastellaan simuloinnin aikana.
	 */
	private double tarkasteluvaliAika;

	/**
	 * Totuusarvo kuvaamaan sateen tilaa.
	 */
	private boolean onkoSade;

	/**
	 * Yksityinen konstruktori, joka alustaa instanssimuuttujat nolliksi.
	 */
	private Saa() {
		this.sateenTodnak = 0;
		this.tarkasteluvaliAika = 0.0;
		this.sateenLoppu = 0.0;
		this.sateenKokoKesto = 0.0;
		this.sadeKerrat = 0;
	}

	/**
	 * Luo ja/tai palauttaa olemassa olevan singleton instanssin.
	 * 
	 * @return saa-olion instanssi.
	 */
	public static Saa getInstance() {
		if (instanssi == null) {
			instanssi = new Saa();
		}
		return instanssi;
	}

	/**
	 * Vertaa arvoSade-metodissa <b>uniform</b>-jakaumalla arvottua lukua
	 * <b>sateenTodnak</b> -muuttujaan.
	 * 
	 * @return <b>true</b>, jos arvottu luku on pienempi tai yhtäsuuri.
	 */
	public boolean sataako() {
		if (this.arvoLuku() <= this.sateenTodnak - 1) {
			return true;
		}
		return false;
	}

	/**
	 * Arpoo luvun 0-100 <b>uniform</b>-jakaumalla.
	 * 
	 * @return Arvottu luku.
	 */
	private int arvoLuku() {
		Uniform uni = new Uniform(0, 100, System.nanoTime());
		int arvottuNum = Math.abs((int) uni.sample());
		return arvottuNum;
	}

	/**
	 * Arpoo jäljellä olevasta simulaatio ajasta sateen lopetusajan. Hyödyntää
	 * <b>uniform</b>-jakaumaa.
	 * 
	 * @param simAika Simuloinnin kokonaisaika.
	 */
	public void setSateenLoppu(double simAika) {
		Double nykyinenAika = Kello.getInstance().getAika();
		Uniform uni = new Uniform(nykyinenAika, simAika);
		this.sateenLoppu = Math.abs(uni.sample());
	}

	/**
	 * Palauttaa sateen loppumisajan.
	 * 
	 * @return Sateen loppumisen aika.
	 */
	public double getSateenLoppu() {
		return this.sateenLoppu;
	}

	/**
	 * Korottaa sadekertoja määräävää muuttujaa yhdellä.
	 */
	public void korotaSateenKertoja() {
		this.sadeKerrat++;
	}

	/**
	 * Palauttaa sadekertojen määrät simuloinnin ajalta.
	 * 
	 * @return Sateiden sade kerrat.
	 */
	public int getSadeKerrat() {
		return this.sadeKerrat;
	}

	/**
	 * Asettaa sateen todennäköisyyden.
	 * 
	 * @param sateenTodnak Sateen todennäköisyys.
	 */
	public void setSateenTodnak(int sateenTodnak) {
		this.sateenTodnak = sateenTodnak;
	}

	/**
	 * Palauttaa sateen todennäköisyyden.
	 * 
	 * @return Sateen todennäköisyys.
	 */
	public int getSateenTodnak() {
		return this.sateenTodnak;
	}

	/**
	 * Laskee sadekerran keston nykyisen kellonajan ja määritetyn sateenloppumisajan
	 * väliltä instanssimuuttujaan <b>sateenKokoKesto</b>.
	 */
	public void laskeSateenKokoKesto() {
		this.sateenKokoKesto += (this.sateenLoppu - Kello.getInstance().getAika());
	}

	/**
	 * Palauttaa sadekertojen kokonaiskeston.
	 * 
	 * @return Sateen kesto.
	 */
	public double getSateenKokoKesto() {
		return this.sateenKokoKesto;
	}

	/**
	 * Asettaa sateen arpomisväliajan annetun keskiarvon mukaan instanssimuuttujaan
	 * <b>tarkasteluvaliAika</b>.
	 * 
	 * @param keskiarvo Tarkasteluvälin keskiarvo.
	 */
	public void setTarkasteluvaliAika(double keskiarvo) {
		this.tarkasteluvaliAika = keskiarvo;
	}

	/**
	 * Palauttaa sateen tarkasteluväliajan.
	 * 
	 * @return Sateen tarkasteluväliaika.
	 */
	public double getTarkasteluvaliAika() {
		return this.tarkasteluvaliAika;
	}

	/**
	 * Asettaa satamisen booleaniin arvoon <b>false</b>, joka kuvaa, että tällä
	 * hetkellä ei sada.
	 */
	public void eiSada() {
		this.onkoSade = false;
	}

	/**
	 * Asettaa satamisen booleaniin arvon <b>true</b>, joka kuvaa, että tällä
	 * hetkellä sataa.
	 */
	public void sataa() {
		this.onkoSade = true;
	}

	/**
	 * Palauttaa satamisen booleaniin arvon.
	 * 
	 * @return Simuloinnin sääolosuhteen tilan. Palauttaa <b>true</b>, jos tällä
	 *         hetkellä sataa.
	 */
	public boolean onkoSadeKaynnissa() {
		return this.onkoSade;
	}

	/**
	 * Tulostaa konsoliin raportin sateen kokonaiskestosta ja sadekertojen lukumäärästä.
	 */
	public void raportti() {
		// Trace.out(Trace.Level.INFO, "Sateiden kokonaiskesto: " +
		// Kello.getInstance().formatoiKello(this.getSateenKokoKesto()));
		// Trace.out(Trace.Level.INFO, "Satoi " + this.getSadeKerrat() + " kertaa.");
	}
}