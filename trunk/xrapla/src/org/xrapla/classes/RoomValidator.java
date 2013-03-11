package org.xrapla.classes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import org.xrapla.Constants;
import org.xrapla.beans.Appointment;
import org.xrapla.beans.Room;

public class RoomValidator {
	
	EntityManager em; 
	
	public RoomValidator(EntityManager em){
		this.em = em;
	}
	
	public boolean isAvailable(Room room, Date date, Date time){
		/*EntityManagerFactory factory;		 
	    factory = Persistence.createEntityManagerFactory(Constants.PERSISTANCE_UNIT_NAME);
	    EntityManager em = factory.createEntityManager();*/
	    
	    TypedQuery<Appointment> q = em.createQuery(
	    		"SELECT a " +
	    		"FROM Appointment a " +
	    		"WHERE a.room = ?1 AND " +
	    		"a.date = ?2 AND " +
	    		"a.time = ?3", Appointment.class);
	    
	    q.setParameter(1, room);
	    q.setParameter(2, date);
	    q.setParameter(3, time);
	    		
		Appointment appointment = q.getSingleResult();
		return (appointment == null);
	}
	
	public Room findNearestAvailableRoom(Room room, Date date, Date time){
		List<Room> rooms = sortByProximity(getAvailableRooms(date, time), room);			
		
		if(rooms == null)
			return null;
		else
			return rooms.get(0);
				
		/*for(Room freeRoom : rooms){
			if(compare(freeRoom, room) <= 1 ||
					(compare(freeRoom, room) >= - 1))
				return freeRoom;
		}			
		
		for(Room freeRoom : rooms){
			if(compareWing(freeRoom, room) <= 1 ||
					(compareWing(freeRoom, room) >= - 1))
				return freeRoom;
		}
		
		return null;*/
	}
	
	private List<Room> getAvailableRooms(Date date, Date time)
	{
		/*EntityManagerFactory factory;		 
	    factory = Persistence.createEntityManagerFactory(Constants.PERSISTANCE_UNIT_NAME);
	    EntityManager em = factory.createEntityManager();*/
	    
	    TypedQuery<Appointment> q = em.createQuery(
	    		"SELECT a " +
	    		"FROM Appointment a " +
	    		"WHERE a.date = ?2 AND " +
	    		"a.time = ?3", Appointment.class);
	    	    
	    q.setParameter(2, date);
	    q.setParameter(3, time);
	    		
		List<Appointment> appointments = q.getResultList();
		
		TypedQuery<Room> qRoom = em.createQuery(
	    		"SELECT r " +
	    		"FROM Room r ", Room.class);
	    		
		List<Room> rooms = qRoom.getResultList();
		
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
						
		return rooms;
	}
	
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
				System.out.println("[");
				for(Room r : freeRooms)
					System.out.println(r + ",");
				System.out.println("]");
			}
			freeRooms.remove(j+1);
			freeRooms.add(j+1, room);
			System.out.print("[");
			for(Room r : freeRooms)
				System.out.print(r + ",");
			System.out.print("]\n");
		}
		return freeRooms;
	}
	
	private int compareWing(Room room1, Room room2){
		return room1.getWing() - room2.getWing();			
	}
	
	private int compareLevel(Room room1, Room room2){
		return ((int) room1.getNumber() / 100) - ((int) room2.getNumber() / 100);
	}
	
	private boolean compareDistance(Room room1, Room room2, Room comparer){
		int distance1 = Math.abs(comparer.getNumber() - room1.getNumber());
		int distance2 = Math.abs(comparer.getNumber() - room2.getNumber());
		return (distance1 <= distance2);
	}
}
