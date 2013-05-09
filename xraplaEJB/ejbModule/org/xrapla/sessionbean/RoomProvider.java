package org.xrapla.sessionbean;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.xrapla.entities.Room;

@Stateless
public class RoomProvider implements RoomProviderLocal {

	@PersistenceContext
	private EntityManager em;

	public RoomProvider() {
	}

	@Override
	public List<Room> getRooms() {
		TypedQuery<Room> q = em.createQuery("SELECT r " + "FROM Room r",
				Room.class);
		try {
			List<Room> rooms = q.getResultList();
			return rooms;
		} catch (NoResultException ex) {
			return null;
		}
	}

	@Override
	public Room getRoom(int id) {
		String queryString = "SELECT r FROM Room r " + "WHERE r.id = :room";

		TypedQuery<Room> q = em.createQuery(queryString, Room.class);

		q.setParameter("room", id);

		return q.getSingleResult();
	}
}
