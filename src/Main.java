import simu.framework.Trace;
import simu.framework.Trace.Level;

/**
 * Pääluokka ohjelman käynnistämistä varten.
 * 
 * @author Otso Poussa, Tanja Pyykönen ja Tatu Talvikko
 * @version 1.0
 */
public class Main {

	/**
	 * Kutsuu simulaation pääkäyttöliittymän käynnistysmetodia.
	 * 
	 * @param args Komentoriviargumentti.
	 */
	public static void main(String[] args) {
		Trace.setTraceLevel(Level.INFO);
		view.SimulaattorinGUI.main(args);
	}
}