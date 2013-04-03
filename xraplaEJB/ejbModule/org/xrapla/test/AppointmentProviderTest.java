package org.xrapla.test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import org.junit.Test;
import org.xrapla.entities.*;
import org.xrapla.sessionbean.*;

public class AppointmentProviderTest {

	private User getTestUser() {
		EntityManagerFactory factory;
		factory = Persistence.createEntityManagerFactory("xrapla");
		EntityManager em = factory.createEntityManager();

		TypedQuery<User> q = em.createQuery("SELECT a " + "FROM User a "
				+ "WHERE TYPE(a) = ?1", User.class);

		q.setParameter(1, Student.class);

		List<User> users;
		try {
			users = q.getResultList();
		} catch (NoResultException ex) {
			users = new ArrayList<User>();
		}

		for (User us : users) {
			if (us instanceof Student) {
				em.close();
				return us;
			}
		}

		em.close();
		return null;
	}

	private Room getRoom() {
		EntityManagerFactory factory;
		factory = Persistence.createEntityManagerFactory("xrapla");
		EntityManager em = factory.createEntityManager();

		TypedQuery<Room> q = em.createQuery(
				"SELECT a " + 
				"FROM Room a " +
				"WHERE a.number = ?1", Room.class);

		q.setParameter(1, 474);

		List<Room> rooms;
		try {
			rooms = q.getResultList();
		} catch (NoResultException ex) {
			rooms = new ArrayList<Room>();
		}

		em.close();
		return rooms.size() == 0 ? null : rooms.get(0);
	}

	@Test
	public void testAppointmentForWeek() {
		AppointmentProvider prov = new AppointmentProvider();
		Calendar cal = new GregorianCalendar();

		User user = getTestUser();

		List<Appointment> appointmentsPerUser = prov.getAppointments(
				cal.get(Calendar.WEEK_OF_YEAR), cal.get(Calendar.YEAR), user);

		for (Appointment appointment : appointmentsPerUser) {
			Calendar calApp = new GregorianCalendar();
			calApp.setTime(appointment.getDate());
			assertThat(calApp.get(Calendar.WEEK_OF_YEAR),
					is(0));//cal.get(Calendar.WEEK_OF_YEAR)));
		}
	}

	@Test
	public void testAppointmentForWeekAndRoom() {
		AppointmentProvider prov = new AppointmentProvider();
		Calendar cal = new GregorianCalendar();

		Room room = getRoom();

		List<Appointment> appointmentsPerRoom = prov.getAppointments(
				cal.get(Calendar.WEEK_OF_YEAR), cal.get(Calendar.YEAR), room);

		for (Appointment appointment : appointmentsPerRoom) {
			Calendar calApp = new GregorianCalendar();
			calApp.setTime(appointment.getDate());
			assertThat("Weekday:", calApp.get(Calendar.WEEK_OF_YEAR),
					is(cal.get(Calendar.WEEK_OF_YEAR)));
			assertTrue("Room:", appointment.getRoom().getNumber() == room.getNumber());
		}

	}

	@Test
	public void testNextTwoAppointments() {
		AppointmentProvider prov = new AppointmentProvider();
		User user = getTestUser();

		List<Appointment> nextEvents = prov.getNextTwoAppointments(user);

		for (Appointment appointment : nextEvents) {
			assertTrue("Date: ",
					appointment.getDate().getTime() >= new Date().getTime());
			for (Student student : appointment.getGroup().getStudents())
				assertThat("User: ", student, is(user));
		}

		assertTrue("Count: ", nextEvents.size() <= 2);
	}

	@Test
	public void testNextExams() {
		AppointmentProvider prov = new AppointmentProvider();
		User user = getTestUser();

		List<Appointment> exams = prov.getExams(user);
		for (Appointment exam : exams) {
			assertThat("Category: ", exam.getCategory(),
					is(Appointment.CATEGORY_EXAM));
			assertTrue("Date: ",
					exam.getDate().getTime() >= new Date().getTime());
		}
	}
}
