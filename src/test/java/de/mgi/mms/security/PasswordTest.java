package de.mgi.mms.security;

import static org.junit.Assert.*;

import org.junit.Test;

import de.mgi.mms.util.security.Encrypter;
import de.mgi.mms.util.security.PasswordRuleVeryfier;

public class PasswordTest {

	@Test
	public void noNullPointer() throws Exception {
		assertFalse(PasswordRuleVeryfier.validate(""));
		assertFalse(PasswordRuleVeryfier.validate(null));
	}

	@Test
	public void minimumLength() throws Exception {
		assertTrue(PasswordRuleVeryfier.validate("12345678910i"));
		assertTrue(PasswordRuleVeryfier.validate("1234567i"));
		assertFalse(PasswordRuleVeryfier.validate("123456i"));
	}

	@Test
	public void minimumOneNumber() throws Exception {
		assertTrue(PasswordRuleVeryfier.validate("abcdefgh1"));
		assertFalse(PasswordRuleVeryfier.validate("abcdefghij"));
	}

	@Test
	public void minimumOneLetter() throws Exception {
		assertTrue(PasswordRuleVeryfier.validate("123a456789"));
		assertFalse(PasswordRuleVeryfier.validate("123456789"));
	}

	@Test
	public void encryptString() throws Exception {
		assertNull(Encrypter.sha256(null));

		assertFalse(Encrypter.sha256("").trim().equalsIgnoreCase(""));

		assertFalse(Encrypter.sha256("test").trim().equalsIgnoreCase("test"));

		assertTrue("test".length() < Encrypter.sha256("test").trim().length());

		String one = Encrypter.sha256("test");

		for (int i = 0; i < 100; i++) {
			assertEquals(one, Encrypter.sha256("test"));
		}
	}
}
