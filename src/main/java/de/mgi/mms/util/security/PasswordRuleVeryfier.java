package de.mgi.mms.util.security;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The METRO Systems got the following rules for a proper password:
 * 
 * 1) At least 8 characters
 * 
 * 2) At least one digit
 * 
 * @author Johannes.Hoehne
 * 
 */
public class PasswordRuleVeryfier {
	private static Pattern pattern;
	private static Matcher matcher;

	static {
		pattern = Pattern.compile("((?=.*\\d)(?=.*\\D).{8,})");
	}

	/**
	 * Validate password with regular expression
	 * 
	 * @param password
	 *            password for validation
	 * @return true valid password, false invalid password
	 */
	public static boolean validate(final String password) {

		if (password == null || password.trim().equals("")) {
			return false;
		}
		matcher = pattern.matcher(password);
		return matcher.matches();

	}
}
