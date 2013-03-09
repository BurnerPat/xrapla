package org.xrapla.classes;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import org.xrapla.Constants;
import org.xrapla.beans.User;

public class UserProvider {
	private static EntityManagerFactory factory;
	
	public User getUser(String username, String password) {
		factory = Persistence.createEntityManagerFactory(Constants.PERSISTANCE_UNIT_NAME);
		
		EntityManager em = factory.createEntityManager();
		TypedQuery<User> q = em.createQuery("SELECT u FROM User u WHERE u.username=?1 AND u.password=?2", User.class);
		
		q.setParameter(1, username);
		q.setParameter(2, password);
		
		User user;
		try {
			user = q.getSingleResult();
		}
		catch (NoResultException ex) {
			user = null;
		}
		
		em.close();
		
		return (user);
	}
	
}
