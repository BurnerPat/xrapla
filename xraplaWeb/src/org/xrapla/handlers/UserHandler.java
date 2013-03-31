package org.xrapla.handlers;

import javax.servlet.http.HttpSession;

import org.xrapla.entities.User;
import org.xrapla.sessionbean.UserProviderLocal;

public class UserHandler {
	private static final String USER = "USER";
	
	private final HttpSession session;
	private final UserProviderLocal provider; 
	
	public UserHandler(HttpSession session, UserProviderLocal provider) {
		this.session = session;
		this.provider = provider;
	}
	
	public boolean isLoggedIn() {
		return (getUser() != null);
	}
	
	public boolean login(String username, String password) {
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
