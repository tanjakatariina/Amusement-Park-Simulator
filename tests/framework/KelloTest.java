package simu.framework;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

/**
 * Sisältää <b>Kello</b>-luokan JUnit testit, joilla testataan Kellon
 * toimivuutta.
 * 
 * @author Tanja Pyykönen
 * @version 1.0
 */
public class KelloTest {

	/**
	 * Testataan onnistuuko Kello-singletonin instanssin haku.
	 */
	@Test
	@Order(1)
	@DisplayName("Saadaanko Kello singletonin instanssi?")
	void testGetInstance() {
		assertNotNull(Kello.getInstance(), "Kello singletonin haku epäonnistui.");
	}

	/**
	 * Testataan toimiiko ajan formatointi muotoon pv hh:mm:ss.
	 */
	@Test
	@Order(2)
	@DisplayName("formatoiKello(): Toimiiko ajan formatointi?")
	void testFormatoiKello() {
		String aika = Kello.getInstance().formatoiKello(86400);
		assertEquals("1pv 00h 00min 00s", aika, "Ajan formatointi muotoon pv hh:mm:ss on muotoiltu väärin.");
	}

	/**
	 * Testataan toimiiko sekuntien muuttaminen minuuteiksi.
	 */
	@Test
	@Order(3)
	@DisplayName("formatoiKelloMin(): Toimiiko sekuntien muuttaminen minuuteiksi?")
	void testFormatoiKelloMin() {
		Double aika = Kello.getInstance().formatoiKelloMin(60);
		assertEquals(1, aika, "sekuntien muuttaminen minuuteiksi ei toimi oikein.");
	}
}