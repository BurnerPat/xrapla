package org.xrapla.classes;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.xrapla.beans.Appointment;
import org.xrapla.beans.Room;

public class RoomValidator {	
	
	@PersistenceContext
	private EntityManager em;
	
	public boolean isAvailable(Room room, Date date, Date time, int duration){
//		EntityManagerFactory factory;		 
//	    factory = Persistence.createEntityManagerFactory(Constants.PERSISTANCE_UNIT_NAME);
//	    EntityManager em = factory.createEntityManager();
	    
	    TypedQuery<Appointment> q = em.createQuery(
	    		"SELECT a " +
	    		"FROM Appointment a " +
	    		"WHERE a.room = ?1 AND " +
	    		"a.date = ?2 AND " +
	    		"((a.time < :startTime AND " +
	    		"(a.time + a.duration) >= :startTime) OR" +
	    		"(a.time >= :startTime AND " +
	    		"a.time <= :endTime))", Appointment.class);
	    
	    q.setParameter(1, room);
	    q.setParameter(2, date);
	    q.setParameter("startTime", time);
	    
	    Calendar cal = new GregorianCalendar();
	    cal.setTime(time);
	    cal.add(Calendar.MINUTE, duration);
	    q.setParameter("endTime", cal.getTime());
	    
	    try {
			return q.getResultList().size() == 0;
	    }
	    catch (NoResultException ex) {
	    	return true;
	    }
	    finally{
	    	em.close();
	    }
	    	    
	}
	
	public Room findNearestAvailableRoom(Room room, Date date, Date time, int duration){
		List<Room> rooms = sortByProximity(getAvailableRooms(date, time, duration), room);			
		
		if(rooms == null)
			return null;
		else
			return rooms.get(0);				
	}
	
	private List<Room> getAvailableRooms(Date date, Date time, int duration)
	{
//		EntityManagerFactory factory;		 
//	    factory = Persistence.createEntityManagerFactory(Constants.PERSISTANCE_UNIT_NAME);
//	    EntityManager em = factory.createEntityManager();
	    
	    TypedQuery<Appointment> q = em.createQuery(
	    		"SELECT a " +
	    		"FROM Appointment a " +
	    		"WHERE " +
	    		"a.date = ?2 AND " +
	    		"((a.time < :startTime AND " +
	    		"(a.time + a.duration) >= :startTime) OR" +
	    		"(a.time >= :startTime AND " +
	    		"a.time <= :endTime))", Appointment.class);
	    	    
	    q.setParameter(2, date);
	    q.setParameter("startTime", time);
	    
	    Calendar cal = new GregorianCalendar();
	    cal.setTime(time);
	    cal.add(Calendar.MINUTE, duration);
	    q.setParameter("endTime", cal.getTime());
	    		
		List<Appointment> appointments = q.getResultList();
		
		TypedQuery<Room> qRoom = em.createQuery(
	    		"SELECT r " +
	    		"FROM Room r ", Room.class);
		
		List<Room> rooms = null;
		try{    		
			rooms = qRoom.getResultList();
		}
		catch(NoResultException ex){
			return null;
		}
		
		for(int i = 0; i < rooms.size(); i++)
		{
			for(Appointment app : appointments){
				if(rooms.get(i).equals(app.getRoom()))
				{
					rooms.remove(i);
					i--;
				}
			}			
		}		
		em.close();
		return rooms.size() == 0 ? null : rooms;
	}
	
	@SuppressWarnings("unused")
	private List<Room> sort(List<Room> rooms) {
		for (int i=1; i <= rooms.size(); i++) {
			Room room = rooms.get(i);
			int j = i - 1;
			while (j >= 0 && compareWing(room, rooms.get(j)) < 0) {
				rooms.remove(j+1);
				rooms.add(j+1, rooms.get(j));
				j--;
			}
			rooms.remove(j+1);
			rooms.add(j+1, room);			
		}
		return rooms;
	}
	
	private List<Room> sortByProximity(List<Room> freeRooms, Room roomInMidth){
		for (int i = 1; i < freeRooms.size(); i++) {
			Room room = freeRooms.get(i);
			int j = i - 1;
			while (j >= 0 && compareDistance(room, freeRooms.get(j), roomInMidth)) {
				freeRooms.remove(j+1);
				freeRooms.add(j+1, freeRooms.get(j));
				j--;
			}
			freeRooms.remove(j+1);
			freeRooms.add(j+1, room);
		}
		return freeRooms;
	}
	
	private int compareWing(Room room1, Room room2){
		return room1.getWing() - room2.getWing();			
	}
	
	@SuppressWarnings("unused")
	private int compareLevel(Room room1, Room room2){
		return ((int) room1.getNumber() / 100) - ((int) room2.getNumber() / 100);
	}
	
	private boolean compareDistance(Room room1, Room room2, Room comparer){
		int distance1 = Math.abs(comparer.getNumber() - room1.getNumber());
		int distance2 = Math.abs(comparer.getNumber() - room2.getNumber());
		return (distance1 <= distance2);
	}
}
