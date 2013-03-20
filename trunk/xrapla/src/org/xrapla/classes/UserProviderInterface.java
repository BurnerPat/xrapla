package org.xrapla.classes;

import org.xrapla.beans.User;

public interface UserProviderInterface {
	public User getUser(String username, String password);	
}

