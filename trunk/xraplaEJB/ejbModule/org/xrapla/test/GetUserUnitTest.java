package org.xrapla.test;

import org.junit.Test;
import org.xrapla.factory.BeanFactory;
import org.xrapla.sessionbean.UserProviderLocal;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class GetUserUnitTest {
	
	@Test
	public void test() {
		String username = "gerkus";
		String password = "asdf";
		
		UserProviderLocal up = BeanFactory.getUserProvider();
		assertThat(up.getUser(username, password).getUsername(), is(username));
	
	}
	
	public static void main(String[] args) {
		
	}

}
