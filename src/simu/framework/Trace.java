package simu.framework;

/**
 * Ohjelman konsolitulosteita varten.
 * 
 * @author Otso Poussa, Tanja Pyykönen ja Tatu Talvikko
 * @version 1.0
 */
public class Trace {

	/**
	 * Enumi, joka sisältää vaihtoehdot konsolitulosteen tasolle.
	 */
	public enum Level {
		/**
		 * Tietoviesti, joka korostaa sovelluksen tilaa.
		 */
		INFO,

		/**
		 * Mahdollinen haitallinen tilanne.
		 */
		WAR,

		/**
		 * Ei-kohtalokas virheilmoitus.
		 */
		ERR
	}

	/**
	 * Konsolitulosteen taso.
	 */
	private static Level traceLevel;

	/**
	 * Asetetaan konsolitulosteen taso.
	 * 
	 * @param lvl Konsolitulosteen taso.
	 */
	public static void setTraceLevel(Level lvl) {
		traceLevel = lvl;
	}

	/**
	 * Kirjoittaa konsolitulosteen.
	 * 
	 * @param lvl Konsolitulosteen taso.
	 * @param txt Konsolitulosteen teksti.
	 */
	public static void out(Level lvl, String txt) {
		if (lvl.ordinal() >= traceLevel.ordinal()) {
			System.out.println(txt);
		}
	}
}