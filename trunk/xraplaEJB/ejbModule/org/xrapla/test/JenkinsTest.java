package org.xrapla.test;

import static org.junit.Assert.*;

import org.junit.Test;

public class JenkinsTest {

	@Test
	public void testHelloWorld() {
		String s = "HelloWorld";
		assertEquals("Just a test to see if everything works ...", "HelloWorld", s);
	}

}
