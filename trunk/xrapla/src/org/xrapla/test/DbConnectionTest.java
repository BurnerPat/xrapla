package org.xrapla.test;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.servlet.http.HttpSession;

import org.xrapla.beans.Appointment;
import org.xrapla.beans.User;
import org.xrapla.classes.AppointmentProvider;
import org.xrapla.classes.UserProvider;
import org.xrapla.handlers.UserHandler;

public class DbConnectionTest {

	public static void main(String[] args) {
		
		AppointmentProvider prov = new AppointmentProvider();
		
		// Termine pro Woche
		List<Appointment> appointments = prov.getAppointments(1, 2013);
		System.out.println("Termine in Woche 1 des Jahres 2013:");
		for(Appointment ap : appointments)
			System.out.println(ap);
		
		System.out.println("");
		
		// nächsten Termine für User
		EntityManagerFactory factory;		 
	    factory = Persistence.createEntityManagerFactory("xrapla");
	    EntityManager em = factory.createEntityManager();
	    TypedQuery<User> q = em.createQuery(
	    		"SELECT a " +
	    		"FROM User a", User.class);
	    		
	    List<User> users = q.getResultList();
		
	    for(User user : users){
		//User user = new UserProvider().getUser("annkitkat", "123456");
		System.out.println("User: " + user + "\n");
			List<Appointment> nextEvents = prov.getNextAppointments(user);
			System.out.println("Nächsten 2 Termine (annkitkat):");
			for(Appointment ap : nextEvents)
				System.out.println(ap);
		}
	}
}