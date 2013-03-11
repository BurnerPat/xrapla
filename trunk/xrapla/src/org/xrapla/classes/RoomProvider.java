package org.xrapla.classes;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import org.xrapla.Constants;
import org.xrapla.beans.Appointment;
import org.xrapla.beans.Room;

public class RoomProvider {
	public List<Room> getRooms(){
		EntityManagerFactory factory;		 
	    factory = Persistence.createEntityManagerFactory(Constants.PERSISTANCE_UNIT_NAME);
	    EntityManager em = factory.createEntityManager();	       	    
	    
	    // Build and execute SQL-Statement
	    TypedQuery<Room> q = em.createQuery(
	    		"SELECT r " +
	    		"FROM room r", Room.class);
	    	    
    	List<Room> rooms = q.getResultList();
    	
	    return rooms;
	}
}
