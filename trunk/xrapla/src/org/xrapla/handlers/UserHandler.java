package org.xrapla.handlers;

import javax.servlet.http.HttpSession;

import org.xrapla.beans.User;
import org.xrapla.classes.UserProvider;

public class UserHandler {
	private static final String USER = "USER";
	
	private final HttpSession session;
	
	public UserHandler(HttpSession session) {
		this.session = session;
	}
	
	public boolean isLoggedIn() {
		return (getUser() != null);
	}
	
	public boolean login(String username, String password) {
		UserProvider provider = new UserProvider();		
		User user = provider.getUser(username, password);
		
		if (user != null) {
			session.setAttribute(USER, user);
			return true;
		}
		else {
			return false;
		}
	}
	
	public User getUser() {
		return (User)session.getAttribute(USER);
	}
}
