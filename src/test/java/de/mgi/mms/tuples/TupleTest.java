package de.mgi.mms.tuples;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import de.mgi.mms.util.tuples.Tuple;

public class TupleTest {

	@Test(expected = IllegalArgumentException.class)
	public void test2Tuple_1() throws Exception {
		new Tuple<String, Integer>("Hallo", null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void test2Tuple_2() throws Exception {
		new Tuple<String, Integer>(null, 1232);
	}

	@Test(expected = IllegalArgumentException.class)
	public void test2Tuple_3() throws Exception {
		new Tuple<String, Integer>(null, null);
	}

	@Test
	public void test2Tuple_4() throws Exception {
		Tuple<String, Integer> tup = new Tuple<String, Integer>("Hallo", 1232);
		assertEquals(tup.getFirst(), "Hallo");
		assertEquals(tup.getSecond(), new Integer(1232));
	}
	
	@Test
	public void test2Tuple_5() throws Exception {
		Tuple<String, Integer> tup = new Tuple<String, Integer>("Hallo", 1232);
		assertEquals("Tuple (Hallo, 1232)", tup.toString());
	}
	
	@Test
	public void testInSet() throws Exception {
		Tuple<String, String> tup1 = new Tuple<String, String>("Hallo", "Du");
		Tuple<String, String> tup2 = new Tuple<String, String>("Hallo", "Du");
		Tuple<String, String> tup3 = new Tuple<String, String>("Hallo", "Du");
		Tuple<String, String> tup4 = new Tuple<String, String>("Hallo", "Du");
		
		Set<Tuple<String, String>> container = new HashSet<Tuple<String, String>>();
		container.add(tup1);
		container.add(tup2);
		container.add(tup3);
		container.add(tup4);
		
		assertEquals(1, container.size());
		
		
	}

}
