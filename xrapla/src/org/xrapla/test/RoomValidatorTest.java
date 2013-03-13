package org.xrapla.test;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import org.xrapla.beans.Appointment;
import org.xrapla.beans.Room;
import org.xrapla.beans.Student;
import org.xrapla.beans.User;
import org.xrapla.classes.AppointmentProvider;
import org.xrapla.classes.RoomValidator;

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
		
		AppointmentProvider appProv = new AppointmentProvider();
		List<Appointment> appointments = appProv.getAppointments(1, 2013, user);
		
		System.out.println("Zu untersuchender Raum: " + appointments.get(0).getRoom());
		System.out.println("===========================================\n");
		
		RoomValidator roomV = new RoomValidator(em);
		if(!roomV.isAvailable(appointments.get(0).getRoom(), appointments.get(0).getDate(), appointments.get(0).getTime())){
			Room room = roomV.findNearestAvailableRoom(appointments.get(0).getRoom(), 
					appointments.get(0).getDate(), 
					appointments.get(0).getTime());
			System.out.println("Verf�gbarer (n�chster) Raum: " + room);
		} else
			System.out.println("Kein Raum verf�gbar!");
		
		em.close();
	}

}
