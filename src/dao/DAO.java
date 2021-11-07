package dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import simu.model.Asiakas;
import simu.model.OmaMoottori;
import simu.model.Palvelupiste;

/**
 * Sisältää tietokantaan tallentamista ja tietokannasta hakemista varten
 * tarvittavat metodit. Konstruktorissa luodaan istuntotehdas, joka suljetaan
 * lopuksi destruktorissa.
 * 
 * @author Otso Poussa, Tanja Pyykönen ja Tatu Talvikko
 * @version 1.0
 */
public class DAO implements IDAO {

	/**
	 * Istuntotehdas istuntojen luomisia varten.
	 */
	private SessionFactory istuntotehdas = null;

	/**
	 * Transaktio tietokannan operaatioita varten.
	 */
	private Transaction transaktio = null;

	/**
	 * Luo DAO-olion tietokantaan tallentamista ja tietokannasta hakemista varten,
	 * sekä luodaan istuntotehdas istuntoja varten.
	 */
	public DAO() {
		try {
			istuntotehdas = new Configuration().configure().buildSessionFactory();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @exception Exception jos asiakas-olion tallennuksen aikana tapahtui virhe.
	 */
	@Override
	public void luoAsiakas(Asiakas asiakas) {
		try (Session istunto = istuntotehdas.openSession()) {
			transaktio = istunto.beginTransaction();
			istunto.saveOrUpdate(asiakas);
			transaktio.commit();
		} catch (Exception e) {
			if (transaktio != null) {
				transaktio.rollback();
			}
			System.out.println(e.getMessage());
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @exception Exception jos palvelupiste-olion tallennuksen aikana tapahtui
	 *                      virhe.
	 */
	@Override
	public void luoPalvelupiste(Palvelupiste palvelupiste) {
		try (Session istunto = istuntotehdas.openSession()) {
			transaktio = istunto.beginTransaction();
			istunto.saveOrUpdate(palvelupiste);
			transaktio.commit();
		} catch (Exception e) {
			if (transaktio != null) {
				transaktio.rollback();
			}
			System.out.println(e.getMessage());
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @exception Exception jos omaMoottori-olion tallennuksen aikana tapahtui
	 *                      virhe.
	 */
	@Override
	public void luoOmaMoottori(OmaMoottori omaMoottori) {
		try (Session istunto = istuntotehdas.openSession()) {
			transaktio = istunto.beginTransaction();
			istunto.saveOrUpdate(omaMoottori);
			transaktio.commit();

		} catch (Exception e) {
			if (transaktio != null) {
				transaktio.rollback();
			}
			System.out.println(e.getMessage());
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @exception Exception jos asiakas-olioiden lukemisen aikana tapahtui virhe.
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Asiakas[] lueAsiakkaat() {
		List<Asiakas> asiakkaatList = null;
		Asiakas[] asiakkaat = null;

		try (Session istunto = istuntotehdas.openSession()) {
			transaktio = istunto.beginTransaction();
			asiakkaatList = istunto.createQuery("FROM Asiakas").list();
			asiakkaat = new Asiakas[asiakkaatList.size()];

			for (int i = 0; i < asiakkaatList.size(); i++) {
				asiakkaat[i] = asiakkaatList.get(i);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return asiakkaat;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @exception Exception jos palvelupiste-olioiden lukemisen aikana tapahtui
	 *                      virhe.
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Palvelupiste[] luePalvelupisteet() {
		List<Palvelupiste> palvelupisteetList = null;
		Palvelupiste[] palvelupisteet = null;

		try (Session istunto = istuntotehdas.openSession()) {
			transaktio = istunto.beginTransaction();
			palvelupisteetList = istunto.createQuery("FROM Palvelupiste").list();
			palvelupisteet = new Palvelupiste[palvelupisteetList.size()];
			for (int i = 0; i < palvelupisteetList.size(); i++) {
				palvelupisteet[i] = palvelupisteetList.get(i);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return palvelupisteet;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @exception Exception jos omaMoottori-olioiden lukemisen aikana tapahtui
	 *                      virhe.
	 */
	@SuppressWarnings("unchecked")
	@Override
	public OmaMoottori[] lueOmaMoottorit() {
		List<OmaMoottori> moottoritList = null;
		OmaMoottori[] moottorit = null;

		try (Session istunto = istuntotehdas.openSession()) {
			transaktio = istunto.beginTransaction();
			moottoritList = istunto.createQuery("FROM OmaMoottori").list();
			moottorit = new OmaMoottori[moottoritList.size()];
			for (int i = 0; i < moottoritList.size(); i++) {
				moottorit[i] = moottoritList.get(i);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return moottorit;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @exception Exception jos omaMoottori-olion poistamisessa tapahtui virhe.
	 */
	@Override
	public void poista(int simID) {
		OmaMoottori moottori = null;
		try (Session istunto = istuntotehdas.openSession()) {
			transaktio = istunto.beginTransaction();

			moottori = istunto.get(OmaMoottori.class, simID);
			if (moottori != null) {
				istunto.delete(moottori);
				transaktio.commit();
			}

		} catch (Exception e) {
			if (transaktio != null) {
				transaktio.rollback();
			}
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Destruktori, joka sulkee istuntotehtaan.
	 * 
	 * @exception Exception jos istuntotehtaan sulkemisessa tapahtui virhe.
	 */
	public void finalize() {
		try {
			if (istuntotehdas != null) {
				istuntotehdas.close();
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}