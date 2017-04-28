package de.mgi.mms.location;

import static org.junit.Assert.*;

import org.junit.Test;

import de.mgi.mms.util.location.Locations;
import de.mgi.mms.util.location.Locations.Location;

public class LocationsTest {

	/**
	 * new Location("GERMANY", "DE", "DEU", "276")
	 * 
	 * @throws Exception
	 */
	@Test
	public void testGoodOne() throws Exception {
		try {
			long begin = System.currentTimeMillis();
			Location loc = Locations.getByIsoAlpha2Code("DE");
			long first = System.currentTimeMillis() - begin;
			loc = Locations.getByIsoAlpha2Code("DE");
			long second = System.currentTimeMillis() - begin - first;

			System.out.println(first + " | " + second + " " + 100.00 / first * second + "%");
			// we test the init
			assertTrue("first >= second but was " + first + " / " + second, first >= second);

			assertEquals("GERMANY", loc.getCountryName());
			assertEquals("DE", loc.getIsoAlpha2Code());
			assertEquals("DEU", loc.getIsoAlpha3Code());
			assertEquals("276", loc.getIsoNumericalCode());

			assertEquals(loc, Locations.getByIsoAlpha3Code("DEU"));
			assertEquals(loc, Locations.getByIsoNumericalCode("276"));

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
}
