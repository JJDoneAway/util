package de.mgi.mms.util.security;

import java.security.MessageDigest;

/**
 * In here you got the possibility to encrypt passords so that you can store it
 * in an DB
 * 
 * If you intend to do you user authentication via TOMCAT you should use this
 * sha 256 encryption
 * 
 * and something like the following settings:
 * 
 * <code>
 * 	<!-- JDBC authentication of users -->
	<Realm
		className="org.apache.catalina.realm.JDBCRealm"
		driverName="oracle.jdbc.driver.OracleDriver"
		connectionURL="jdbc:oracle:thin:@//10.21.105.185:1552/CRODODEV.DE.MADM.NET"
		connectionName="PIM_DEV_USER"
		connectionPassword="**********"
		userTable="cpim_users"
		userNameCol="user_name"
		userCredCol="password"
		userRoleTable="cpim_user_roles"
		roleNameCol="role_name"
		digest="sha-256" />
 * </code>
 * 
 * @author Johannes.Hoehne
 * 
 */
public class Encrypter {

	private Encrypter() {
		// just util stuff
	}

	/**
	 * will encrypt your password asymetric 
	 * 
	 * @param clearCasePassword the to be encrypted password
	 * 
	 * @return the encrypted password
	 */
	public static String sha256(String clearCasePassword) {
		if (clearCasePassword == null) {
			return null;
		}
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			byte[] hash = digest.digest(clearCasePassword.getBytes("UTF-8"));
			StringBuffer hexString = new StringBuffer();

			for (int i = 0; i < hash.length; i++) {
				String hex = Integer.toHexString(0xff & hash[i]);
				if (hex.length() == 1)
					hexString.append('0');
				hexString.append(hex);
			}

			return hexString.toString();
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

}
