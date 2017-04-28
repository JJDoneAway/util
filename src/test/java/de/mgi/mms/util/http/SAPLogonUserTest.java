package de.mgi.mms.util.http;

import org.junit.Assert;
import org.junit.Test;

public class SAPLogonUserTest {

	@Test
	public void backDoorTest() {
		System.getProperties().setProperty("machesohneportal", "Johannes.Hoehne@metroSystems.net");
		final String userName = SAPLogonUser.getUserName(null);
		Assert.assertEquals("johannes.hoehne@metrosystems.net", userName);
	}

}
