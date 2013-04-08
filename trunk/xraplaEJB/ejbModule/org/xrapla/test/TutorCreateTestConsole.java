package org.xrapla.test;

import java.util.LinkedList;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.xrapla.Constants;
import org.xrapla.entities.*;

public class TutorCreateTestConsole {
	public static void main(String[] args) {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory(Constants.PERSISTANCE_UNIT_NAME);
		
		EntityManager em = factory.createEntityManager();
		
		Tutor myTutor = new Tutor();
		myTutor.setTitle("Dr.");
		myTutor.setEmail("spam@example.com");
		myTutor.setFirstname("Andreas");
		myTutor.setLastname("Nonym");
		myTutor.setOwnedCourses(new LinkedList<Course>());
		myTutor.setPassword("password");
		myTutor.setSubject("Java-Programmierung");
		myTutor.setUsername("anonym");
		
		em.getTransaction().begin();
		em.persist(myTutor);
		em.getTransaction().commit();
		
		em.close();
	}
}
