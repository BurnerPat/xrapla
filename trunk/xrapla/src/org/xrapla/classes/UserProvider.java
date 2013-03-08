package org.xrapla.classes;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.xrapla.beans.User;

public class UserProvider {
	
	private static final String PERSISTANCE_UNIT_NAME = "xrapla";
	private static EntityManagerFactory factory;
	
	public User getUser(String username, String password) {
		factory = Persistence.createEntityManagerFactory(PERSISTANCE_UNIT_NAME);
		
		EntityManager em = factory.createEntityManager();
		Query q = em.createQuery("SELECT * from User user WHERE username=?1 AND password=?2");
		
		q.setParameter(1, username);
		q.setParameter(2, password);

		User user;
		try {
			user = (User)q.getSingleResult();
		}
		catch (NoResultException ex) {
			user = null;
		}
		
		em.close();
		
		return (user);
	}
	
}
