package de.mgi.mms.util.http;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import junit.framework.Assert;

import org.junit.Test;

public class ServletUtilTest {

	/**
	 * This will normalize all parameter names (trim and lower case) and all
	 * parameter values (trim and upper case)
	 * 
	 * e.g.:
	 * 
	 * http://localhost:8080/?caller=de%20&%20caller%20=%20DE&caller=cat&foo=ba
	 * ==> caller=[DE,cat] , foo=[BA]
	 * 
	 * @param h
	 *            .getParameterMap() the request which carries the parameters
	 * 
	 * @return normalized name ==> list of normalized values
	 */
	@Test
	public void getNormalizedParametersTest() {

		Map<String, String[]> parameters = new HashMap<String, String[]>();

		parameters.put("caller", new String[] { " de ", " DE ", });
		parameters.put("CALLER", new String[] { " de ", " DE ", });
		parameters.put("  caller ", new String[] { " CAT ", });
		parameters.put("  foo ", new String[] { " ba ", });

		final Map<String, Set<String>> normalized = ServletUtil.getNormalizedParameters(parameters);

		Assert.assertEquals(2, normalized.keySet().size());
		Assert.assertEquals(2, normalized.get("caller").size());
		Assert.assertEquals(1, normalized.get("foo").size());

		Assert.assertTrue(normalized.get("caller").contains("DE"));

	}
}
