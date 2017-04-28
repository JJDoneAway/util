package de.mgi.mms.util.http;

import java.util.Collections;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import de.mgi.mms.util.collection.SortUniqueMap;

public class ServletUtil {

	
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
	public static Map<String, Set<String>> getNormalizedParameters(Map<String, String[]> httpRequestParameters) {
		if (httpRequestParameters == null || httpRequestParameters.entrySet().isEmpty()) {
			return Collections.emptyMap();
		}

		SortUniqueMap<String, String> parametersSorted = new SortUniqueMap<String, String>();

		for (Entry<String, String[]> parameter : httpRequestParameters.entrySet()) {
			
			final String key = parameter.getKey().trim().toLowerCase();
			for (String value : parameter.getValue()) {
				final String normalizedValue = value.trim().toUpperCase();
				if (normalizedValue.length() > 0) {
					parametersSorted.put(key, normalizedValue);
				}
			}

		}

		return Collections.unmodifiableMap(parametersSorted.getContainer());
	}
}
