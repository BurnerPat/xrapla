package org.xrapla.test;

import static org.junit.Assert.*;

import org.junit.Test;

public class JenkinsTest {

	@Test
	public void test() {
		System.out.println("Test reached");
		try{		
			assertTrue("Successful test",true);
		} catch(Exception e){
			e.printStackTrace();
		}
		//fail("Not yet implemented");
	}

}
