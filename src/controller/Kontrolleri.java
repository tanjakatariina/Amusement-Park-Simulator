package controller;

import dao.DAO;
import dao.IDAO;
import javafx.application.Platform;
import simu.framework.IMoottori;
import simu.model.Asiakas;
import simu.model.OmaMoottori;
import simu.model.Palvelupiste;
import simu.model.Saa;
import view.ISimulaattorinUI;

/**
 * Kontrolleriohjain käyttöliittymän ja mallin välillä. Kontrollerin avulla
 * käyttöliittymä pääse toimimaan mallin kanssa ja malli käyttöliittymän kanssa.
 * Kontrollerissa luodaan DAO yhteys, jota käyttöliittymä ja malli voivat
 * käyttää.
 * 
 * @author Otso Poussa, Tanja Pyykönen ja Tatu Talvikko
 * @version 1.0
 */
public class Kontrolleri implements IKontrolleri {

	/**
	 * Yhteys DAO-olioon.
	 */
	private IDAO dao = new DAO();

	/**
	 * <b>Moottori</b>-olio, tietojen hakuun ja viemiseen.
	 */
	private IMoottori moottori;

	/**
	 * <b>SimulaattorinGUI-olio</b>, tietojen hakemista varten.
	 */
	private ISimulaattorinUI ui;

	/**
	 * Konstruktori, jolla saadaan yhteys <b>käyttöliittymään</b>.
	 * 
	 * @param ui SimulaattorinGUI-olio.
	 */
	public Kontrolleri(ISimulaattorinUI ui) {
		this.ui = ui;
	}

	/**
	 * Tyhjä konstruktori JUnitteja ja tulosNakyma-luokkaa varten.
	 * 
	 */
	public Kontrolleri() {}

	@Override
	public void kaynnistaSimulointi() {
		((Thread) moottori).start();
	}

	@Override
	public void asetaSyotteet() {
		moottori = new OmaMoottori(this);
		moottori.setViive(ui.getViive());
		moottori.setSimulointiaika(ui.getAika());
		Saa.getInstance().setTarkasteluvaliAika(ui.getSaaTarkastelu());
		Saa.getInstance().setSateenTodnak(ui.getSadeTodNak());
		this.syotteetSK(moottori);
		this.syotteetGR(moottori);
		this.syotteetMP(moottori);
		this.syotteetVL(moottori);
		this.syotteetKS(moottori);
		this.syotteetVR(moottori);
		this.syotteetKJ(moottori);
	}

	@Override
	public void hidasta() {
		moottori.setViive((long) (moottori.getViive() * 1.10));
	}

	@Override
	public void nopeuta() {
		moottori.setViive((long) (moottori.getViive() * 0.9));
	}

	@Override
	public void syotteetSK(IMoottori moottori) {
		moottori.setSaapumisVali(ui.getSaapumisVali());
		moottori.setJpituus(ui.getSKJpituus(), 0);
		moottori.setGenerator(ui.getSKaikaMax(), 0);
		moottori.setHinta(ui.getSKHinta(), 0);
	}

	@Override
	public void syotteetGR(IMoottori moottori) {
		moottori.setJpituus(ui.getGRJpituus(), 1);
		moottori.setGenerator(ui.getGRaikaMax(), 1);
		moottori.setHinta(ui.getGRHinta(), 1);
		moottori.setSuosioA(ui.getGRsuosioA(), 1);
		moottori.setSuosioS(ui.getGRsuosioS(), 1);
	}

	@Override
	public void syotteetMP(IMoottori moottori) {
		moottori.setJpituus(ui.getMPJpituus(), 2);
		moottori.setHinta(ui.getMPHinta(), 2);
		moottori.setSuosioA(ui.getMPsuosioA(), 2);
		moottori.setSuosioS(ui.getMPsuosioS(), 2);
		moottori.setlaiteKapasiteetti(ui.getMPkapasiteetti(), 2);
	}

	@Override
	public void syotteetVR(IMoottori moottori) {
		moottori.setJpituus(ui.getVRJpituus(), 3);
		moottori.setHinta(ui.getVRHinta(), 3);
		moottori.setSuosioA(ui.getVRsuosioA(), 3);
		moottori.setSuosioS(ui.getVRsuosioS(), 3);
		moottori.setlaiteKapasiteetti(ui.getVRkapasiteetti(), 3);
	}

	@Override
	public void syotteetKS(IMoottori moottori) {
		moottori.setJpituus(ui.getKSJpituus(), 4);
		moottori.setHinta(ui.getKSHinta(), 4);
		moottori.setSuosioA(ui.getKSsuosioA(), 4);
		moottori.setSuosioS(ui.getKSsuosioS(), 4);
		moottori.setlaiteKapasiteetti(ui.getKSkapasiteetti(), 4);
	}

	@Override
	public void syotteetVL(IMoottori moottori) {
		moottori.setJpituus(ui.getVLJpituus(), 5);
		moottori.setHinta(ui.getVLHinta(), 5);
		moottori.setSuosioA(ui.getVLsuosioA(), 5);
		moottori.setSuosioS(ui.getVLsuosioS(), 5);
		moottori.setlaiteKapasiteetti(ui.getVLkapasiteetti(), 5);
	}

	@Override
	public void syotteetKJ(IMoottori moottori) {
		moottori.setJpituus(ui.getKJJpituus(), 6);
		moottori.setHinta(ui.getKJHinta(), 6);
		moottori.setSuosioA(ui.getKJsuosioA(), 6);
		moottori.setSuosioS(ui.getKJsuosioS(), 6);
		moottori.setlaiteKapasiteetti(ui.getKJkapasiteetti(), 6);
	}

	@Override
	public void visuaaliLisaaJonoon(String piste) {
		Platform.runLater(new Runnable() {
			public void run() {
				ui.getVisualisointi().visuaaliLisaaJonoon(piste);
			}
		});
	}

	@Override
	public void visuaaliPoistaJonosta(String piste) {
		Platform.runLater(new Runnable() {
			public void run() {
				ui.getVisualisointi().visuaaliPoistaJonosta(piste);
			}
		});
	}

	@Override
	public void visuaaliPalvelussa(String piste, int asiakasMaara) {
		Platform.runLater(new Runnable() {
			public void run() {
				ui.getVisualisointi().visuaaliPalvelussa(piste, asiakasMaara);
			}
		});
	}

	@Override
	public void asetaSimValmisTeksti() {
		Platform.runLater(new Runnable() {
			public void run() {
				ui.getVisualisointi().asetaSimValmisTeksti();
			}
		});
	}

	@Override
	public void asetaAurinko() {
		Platform.runLater(new Runnable() {
			public void run() {
				ui.getVisualisointi().asetaAurinko();
			}
		});
	}

	@Override
	public void asetaSade() {
		Platform.runLater(new Runnable() {
			public void run() {
				ui.getVisualisointi().asetaSade();
			}
		});
	}

	@Override
	public IDAO haeDao() {
		return this.dao;
	}

	@Override
	public OmaMoottori[] haeOmaMoottorit() {
		return dao.lueOmaMoottorit();
	}

	@Override
	public Asiakas[] haeAsiakkaat() {
		return dao.lueAsiakkaat();
	}

	@Override
	public Palvelupiste[] haePalvelupisteet() {
		return dao.luePalvelupisteet();
	}

	@Override
	public void poista(int simID) {
		dao.poista(simID);
	}
}
