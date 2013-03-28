package org.xrapla.classes;

import javax.ejb.Local;

import org.xrapla.beans.User;

@Local
public interface UserProviderInterface {
	public User getUser(String username, String password);	
}

