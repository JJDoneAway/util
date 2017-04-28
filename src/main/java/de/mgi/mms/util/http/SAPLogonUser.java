package de.mgi.mms.util.http;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import javax.servlet.http.Cookie;

import org.apache.log4j.Logger;

import de.mgi.common.sapticket.SAPTicketInfo;
import de.mgi.common.sapticket.SAPTicketVerifier;
import de.mgi.common.sapticket.TicketVerifierException;

public class SAPLogonUser {

	private static Logger logger = Logger.getLogger(SAPLogonUser.class);

	private static final String backDoorKey = "machesohneportal";

	/**
	 * This method will do the whole MGI SAP portal stuff.
	 * 
	 * It will check if there is a valid portal cooky
	 * 
	 * Afterwards it will be encrypted and the mail address of the user will be
	 * returned.
	 * 
	 * 
	 * 
	 * @param cookies
	 * @param requestRarameter
	 * @return the mail address of the portal - user
	 * 
	 * @throws IllegalArgumentException
	 *             if anything is wrong
	 * 
	 */
	public static String getUserName(Cookie[] cookies) {
		// do back door stuff by sysproperty
		final String backDoorProperty = System.getProperty(backDoorKey);
		if (backDoorProperty != null && !"".equals(backDoorProperty.trim())) {
			return backDoorProperty.toLowerCase();
		}

		final String cookyName = "MYSAPSSO2";
		final String keyStoreLocation = "/oracle/projects/services/jks/current/networking.jks";
		final String keyStorePassword = "portal1234";

		SAPTicketVerifier verifier;
		try {
			verifier = SAPTicketVerifier.getInstance(keyStoreLocation, keyStorePassword);
			logger.debug("We where able to open the portals key store");
		} catch (TicketVerifierException e) {
			throw new IllegalArgumentException("It was not possible to read the key store of the portal. there are some configuration problems. We expect the key store in the following file: " + keyStoreLocation, e);
		}

		if (cookies != null) {

			for (Cookie cooky : cookies) {
				logger.debug("Found a cooky with the name: " + cooky.getName());

				if (cooky.getName().trim().equalsIgnoreCase(cookyName)) {
					logger.debug("Found " + cookyName + " cooky");

					final String MYSAPSSO = cooky.getValue();

					String cookieValue;
					try {
						cookieValue = URLDecoder.decode(MYSAPSSO, "UTF-8");
					} catch (UnsupportedEncodingException e1) {
						throw new IllegalArgumentException("Your " + cookyName + " cooky is not encoded with UTF-8 but should be", e1);
					}
					cookieValue = cookieValue.replace(' ', '+');
					System.out.println(cookieValue);

					SAPTicketInfo info;
					try {
						info = verifier.verifyTicket(cookieValue);
					} catch (TicketVerifierException e) {
						throw new IllegalArgumentException("We are not able to verify your authorisation. So maybe you are not allowed to access this site.", e);
					}

					return info.getPortalUser();

				}
			}

		}
		throw new IllegalArgumentException("We found no cooky with your authentication stuff. We expect a cooky with the name " + cookyName);

	}

}
