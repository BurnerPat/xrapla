package org.xrapla.sessionbean;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.xrapla.Constants;
import org.xrapla.entities.User;

/**
 * Session Bean implementation class UserProvider
 */
@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
@LocalBean
public class UserProvider implements UserProviderLocal {
	@PersistenceContext(unitName=Constants.PERSISTANCE_UNIT_NAME)
	private EntityManager em;
    /**
     * Default constructor. 
     */
    public UserProvider() {
        // TODO Auto-generated constructor stub
    }

	public User getUser(String username, String password) {
//		factory = Persistence.createEntityManagerFactory(Constants.PERSISTANCE_UNIT_NAME);
//		
//		EntityManager em = factory.createEntityManager();
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
