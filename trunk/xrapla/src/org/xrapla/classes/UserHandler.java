package org.xrapla.classes;

import java.util.List;

import javax.persistence.Query;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.xrapla.beans.User;

public class UserHandler {
	
	private static final String PERSISTANCE_UNIT_NAME = "xrapla";
	private static EntityManagerFactory factory;
	
	public boolean isValidUser(String username, String password) {
		factory = Persistence.createEntityManagerFactory(PERSISTANCE_UNIT_NAME);
		
		EntityManager em = factory.createEntityManager();
		Query q = em.createQuery("SELECT username, password from User user WHERE username=?1 AND password=?2");
		
		q.setParameter(1, username);
		q.setParameter(2, password);
		
		@SuppressWarnings("unchecked")
		List<User> userResult = q.getResultList();
		
		em.close();
		
		return (userResult.size()>0);
	}
	
}
