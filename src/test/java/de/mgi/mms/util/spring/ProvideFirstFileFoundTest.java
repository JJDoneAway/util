package de.mgi.mms.util.spring;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import static org.junit.Assert.*;

public class ProvideFirstFileFoundTest {

	@Test
	public void ok_1() {
		List<String> locations = Arrays.asList(new String[] { "/a.txt", "/b.txt  ", "/c.txt" });
		assertEquals(3, locations.size());

		final ProvideFirstFileFound provideFirstFileFound = new ProvideFirstFileFound();
		provideFirstFileFound.setLocations(locations);

		final File file = provideFirstFileFound.getFile();

		assertNotNull(file);

		assertEquals("b.txt", file.getName());

	}

	@Test
	public void ok_2() throws IOException {
		final File temFile = File.createTempFile("b_test", "txt");

		final String canonicalPath = temFile.getCanonicalPath();
		List<String> locations = Arrays.asList(new String[] { "a.txt", canonicalPath, "/c.txt" });
		assertEquals(3, locations.size());

		final ProvideFirstFileFound provideFirstFileFound = new ProvideFirstFileFound();
		provideFirstFileFound.setLocations(locations);

		final File file = provideFirstFileFound.getFile();

		assertNotNull(file);

		assertEquals(canonicalPath, file.getCanonicalPath());

		temFile.deleteOnExit();

	}

	@Test(expected = IllegalArgumentException.class)
	public void not_ok_1() throws IOException {

		List<String> locations = Arrays.asList(new String[] { "a.txt", "foo", "bar" });
		assertEquals(3, locations.size());

		final ProvideFirstFileFound provideFirstFileFound = new ProvideFirstFileFound();
		provideFirstFileFound.setLocations(locations);

		provideFirstFileFound.getFile();

	}

}
