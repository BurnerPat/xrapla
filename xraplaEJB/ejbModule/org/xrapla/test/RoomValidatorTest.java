package org.xrapla.test;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import org.xrapla.entities.*;
import org.xrapla.factory.BeanFactory;
import org.xrapla.sessionbean.*;

public class RoomValidatorTest {
		
	public static void main(String[] args) {
		
		EntityManagerFactory factory;		 
	    factory = Persistence.createEntityManagerFactory("xrapla");
	    EntityManager em = factory.createEntityManager();
	    TypedQuery<User> q = em.createQuery(
	    		"SELECT a " +
	    		"FROM User a " +
	    		"WHERE TYPE(a) = ?1", User.class);
	    
	    q.setParameter(1, Student.class);
	    		
	    List<User> users = q.getResultList();
	    Student user = null;
	    for(User us : users){
	    	if(us instanceof Student){
	    		user = (Student) us;
	    		break;
	    	}
	    }
		
	    AppointmentProviderLocal appProv = BeanFactory.getAppointmentProvider();
		List<Appointment> appointments = appProv.getAppointments(1, 2013, user);
	
		RoomValidator roomV = new RoomValidator();
								
		if(!roomV.isAvailable(appointments.get(1).getRoom(), appointments.get(1).getDate(), appointments.get(1).getTime(), appointments.get(1).getDuration())){
			Room room = roomV.findNearestAvailableRoom(appointments.get(1).getRoom(), 
					appointments.get(1).getDate(), 
					appointments.get(1).getTime(), appointments.get(1).getDuration());
			System.out.println("Zu untersuchender Raum: " + appointments.get(1).getRoom() + " für " + appointments.get(1).getDateTime());
			System.out.println("===========================================\n");	
			System.out.println("Verfügbarer (nächster) Raum: " + room);
		} else
			System.out.println("Kein Raum verfügbar!");
		
		em.close();
	}

}
