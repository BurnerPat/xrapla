package org.xrapla.sessionbean;

import javax.ejb.Local;

import org.xrapla.entities.User;

@Local
public interface UserProviderLocal {
	public User getUser(String username, String password);
}
