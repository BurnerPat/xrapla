package org.xrapla.classes;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.xrapla.Constants;
import org.xrapla.beans.Room;

public class RoomProvider {
	@PersistenceContext(unitName=Constants.PERSISTANCE_UNIT_NAME)
	private EntityManager em;
	
	public List<Room> getRooms() {
//		EntityManagerFactory factory;
//		factory = Persistence
//				.createEntityManagerFactory(Constants.PERSISTANCE_UNIT_NAME);
//		EntityManager em = factory.createEntityManager();

		// Build and execute SQL-Statement
		TypedQuery<Room> q = em.createQuery("SELECT r " + "FROM Room r",
				Room.class);
		try {
			List<Room> rooms = q.getResultList();
			em.close();
			return rooms;
		} catch(NoResultException ex) {
			em.close();
			return null;
		}
	}
}
