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

public class AppointmentProviderTest {

	public static void main(String[] args) {
		
		AppointmentProvider prov = new AppointmentProvider();
		
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
		
		// Termine pro Woche
		List<Appointment> appointmentsPerUser = prov.getAppointments(1, 2013, user);		
		
		// nächsten Termine für alle User					    		
		List<Appointment> nextEvents = prov.getNextTwoAppointments(user);
	    
	    // Termine pro Woche pro Raum
	    Room room = new Room();
	    room.setNumber(474);
	    room.setWing('A');
	    List<Appointment> appointmentsPerRoom = prov.getAppointments(11, 2013, room);
		
		// Nächsten 3 Klausuren
		List<Appointment> exams = prov.getExams(user);				
		
		// Anzeige
		
		System.out.println("Termine in Woche 1 des Jahres 2013 für " + user);
		if(appointmentsPerUser != null){
			for(Appointment ap : appointmentsPerUser)
				System.out.println(ap);
		}
		
		System.out.println();
		
		System.out.println("Nächsten 2 Termine für " + user);
		if(appointmentsPerUser != null){
			for(Appointment ap : nextEvents)
				System.out.println(ap);
		}
		
	    System.out.println();
		
		System.out.println("Termine in Woche 11 des Jahres 2013 in Raum " + room);
		if(appointmentsPerUser != null){
			for(Appointment ap : appointmentsPerRoom)
				System.out.println(ap);
		}
		
		System.out.println();
		
		System.out.println("Nächste Klausuren: ");
		if(exams != null){
			for(Appointment exam : exams)
				System.out.println(exam);
		}
	}
}
