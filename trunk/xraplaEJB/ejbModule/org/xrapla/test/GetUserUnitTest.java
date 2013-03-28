package org.xrapla.test;

import org.junit.Test;
import org.xrapla.sessionbean.UserProvider;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class GetUserUnitTest {
	
	@Test
	public void test() {
		String username = "gerkus";
		String password = "asdf";
		
		UserProvider up = new UserProvider();
		assertThat(up.getUser(username, password).getUsername(), is(username));
	
	}
	
	public static void main(String[] args) {
		
	}

}
