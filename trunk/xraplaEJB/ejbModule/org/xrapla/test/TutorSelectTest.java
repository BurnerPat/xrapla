package org.xrapla.test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import org.xrapla.Constants;
import org.xrapla.entities.User;

public class TutorSelectTest {
	public static void main(String[] args) {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory(Constants.PERSISTANCE_UNIT_NAME);
		
		EntityManager em = factory.createEntityManager();
		
		TypedQuery<User> q = em.createQuery("SELECT u FROM User u WHERE u.username=?1 AND u.password=?2", User.class);
		q.setParameter(1, "anonym");
		q.setParameter(2, "password");
		
		try {
			User user = q.getSingleResult();
			System.out.println(user.toString());
		}
		catch (Exception ex) {
			System.out.println(ex.getClass().getSimpleName());
		}
	}
}
