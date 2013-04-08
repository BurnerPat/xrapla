package org.xrapla.test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.xrapla.factory.BeanFactory;
import org.xrapla.sessionbean.UserProviderLocal;

public class GetUserUnitTest {

	// @Test
	public void test() {
		String username = "gerkus";
		String password = "asdf";

		UserProviderLocal up = BeanFactory.getUserProvider();
		assertThat(up.getUser(username, password).getUsername(), is(username));

	}

	public static void main(String[] args) {

	}

}
