package de.mgi.mms.util.collection;

import java.util.Arrays;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

public class SortedMapTest {

	@Test
	public void sortMapTest() {
		SortMap<String, String> test = new SortMap<String, String>();
		SortUniqueMap<String, String> uniqueTest = new SortUniqueMap<String, String>();

		test.put("TEST", "1");
		test.put("TEST", "1");
		uniqueTest.put("TEST", "1");
		uniqueTest.put("TEST", "1");
		
		Assert.assertEquals(2, test.getContainer().get("TEST").size());
		Assert.assertEquals(1, uniqueTest.getContainer().get("TEST").size());
		
		String[] array = new String[]{"1", "2", "2", "3"};
		final List<String> asList = Arrays.asList(array);
		
		test.putAll("TEST", asList);
		uniqueTest.putAll("TEST", asList);

		Assert.assertEquals(6, test.getContainer().get("TEST").size());
		Assert.assertEquals(3, uniqueTest.getContainer().get("TEST").size());

	}

}
