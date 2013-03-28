package org.xrapla.factory;

import javax.ejb.Local;

import org.xrapla.sessionbean.UserProviderLocal;

@Local
public interface BeanFactoryLocal {
	public UserProviderLocal getUserProvider();
}
