package org.xrapla.sessionbean;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.xrapla.entities.User;

@Stateless
public class UserProvider implements UserProviderLocal {

	@PersistenceContext
	private EntityManager em;

	public UserProvider() {

	}

	@Override
	public User getUser(String username, String password) {
		Query q = em
				.createQuery("SELECT u FROM User u WHERE u.username=?1 AND u.password=?2");

		q.setParameter(1, username);
		q.setParameter(2, password);

		User user;
		try {
			user = (User) q.getSingleResult();
		} catch (NoResultException ex) {
			user = null;
		}

		return (user);
	}
}