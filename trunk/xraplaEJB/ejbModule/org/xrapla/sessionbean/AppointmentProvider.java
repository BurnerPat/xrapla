package org.xrapla.sessionbean;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.xrapla.entities.Appointment;
import org.xrapla.entities.AppointmentId;
import org.xrapla.entities.CourseGroup;
import org.xrapla.entities.Docent;
import org.xrapla.entities.Lecture;
import org.xrapla.entities.Room;
import org.xrapla.entities.Student;
import org.xrapla.entities.User;

/**
 * Session Bean implementation class AppointmentProvider
 */
@Stateless
public class AppointmentProvider implements AppointmentProviderLocal {

	@PersistenceContext
	private EntityManager em;

	public AppointmentProvider() {

	}

	@Override
	public List<Appointment> getAppointments(int weekOfYear, int year, Room room) {
		Calendar monday = new GregorianCalendar();
		monday.setWeekDate(year, weekOfYear, Calendar.MONDAY);

		Calendar sunday = new GregorianCalendar();
		sunday.setWeekDate(year, weekOfYear, Calendar.MONDAY);
		sunday.add(Calendar.DATE, 7);

		TypedQuery<Appointment> q = em.createQuery("SELECT a "
				+ "FROM Appointment a " + "WHERE a.id.date >= ?1 "
				+ "AND a.id.date <= ?2 " + "AND a.id.room = ?3",
				Appointment.class);

		q.setParameter(1, monday.getTime());
		q.setParameter(2, sunday.getTime());
		q.setParameter(3, room);

		try {
			List<Appointment> appointments = q.getResultList();
			return appointments;
		} catch (NoResultException ex) {
			return null;
		}
	}

	@Override
	public List<Appointment> getAppointments(int weekOfYear, int year, User user) {
		Calendar monday = new GregorianCalendar();
		monday.setWeekDate(year, weekOfYear, Calendar.MONDAY);

		Calendar sunday = new GregorianCalendar();
		sunday.setWeekDate(year, weekOfYear, Calendar.MONDAY);
		sunday.add(Calendar.DATE, 7);

		TypedQuery<Student> queryGroup = em
				.createQuery(
						"SELECT s FROM Student s JOIN FETCH s.groups WHERE s.username = :username",
						Student.class);

		queryGroup.setParameter("username", user.getUsername());

		String queryString = "SELECT a " + "FROM Appointment a "
				+ "WHERE a.id.date>=?1 AND a.id.date<=?2";

		if (user instanceof Student) {
			queryString += " AND a.group IN :groups";
		} else {
			if (user instanceof Docent) {
				queryString += " AND a.lecture IN (SELECT d.lectures FROM Docent d WHERE d = :docent)";
			} else {
				return null;
			}
		}

		TypedQuery<Appointment> q = em.createQuery(queryString,
				Appointment.class);

		q.setParameter(1, monday.getTime());
		q.setParameter(2, sunday.getTime());

		if (user instanceof Student) {
			q.setParameter("groups", queryGroup.getResultList().get(0)
					.getGroups());
		} else {
			if (user instanceof Docent) {
				q.setParameter("docent", ((Docent) user));
			}
		}

		List<Appointment> appointments = null;
		try {
			appointments = q.getResultList();
		} catch (NoResultException es) {
			appointments = null;
		}
		return appointments;
	}

	@Override
	public List<Appointment> getNextTwoAppointments(User user) {

		if (user instanceof Student) {
			Student student = (Student) user;
			List<CourseGroup> groups = student.getGroups();
			List<Appointment> apps = new ArrayList<Appointment>();
			for (CourseGroup group : groups) {
				for (Appointment a : (group.getAppointments())) {
					if (a.getDateTime().compareTo(new Date()) >= 0) {
						apps.add(a);
					}
				}
			}
			return apps.subList(0, apps.size() < 2 ? apps.size() : 2);
		} else if (user instanceof Docent) {
			Docent docent = (Docent) user;
			List<Appointment> apps = new ArrayList<Appointment>();

			for (Lecture lecture : docent.getLectures()) {
				for (Appointment app : lecture.getAppointments()) {
					if (app.getDateTime().compareTo(new Date()) >= 0) {
						apps.add(app);
					}
				}
			}
			return apps.subList(0, apps.size() < 2 ? apps.size() : 2);
		} else {
			return null;
		}
	}

	@Override
	public void insert(Appointment appointment) {
		em.getTransaction().begin();
		em.persist(appointment);
		em.getTransaction().commit();
	}

	@Override
	public void remove(Appointment appointment) {
		em.getTransaction().begin();
		em.remove(appointment);
		em.getTransaction().commit();
	}

	@Override
	public Appointment updateNonKey(Appointment appointment) {

		Appointment app = em.find(Appointment.class, new AppointmentId(
				appointment.getRoom().getNumber(), appointment.getDate(),
				appointment.getTime()));
		if (app != null) {
			em.getTransaction().begin();
			app.setCategory(appointment.getCategory());
			app.setDuration(appointment.getDuration());
			app.setGroup(appointment.getGroup());
			app.setLecture(appointment.getLecture());
			em.getTransaction().commit();
		}
		return app;
	}

	@Override
	public List<Appointment> getExams(User user) {

		if (user instanceof Student) {
			Student student = (Student) user;
			List<CourseGroup> groups = student.getGroups();
			List<Appointment> apps = new ArrayList<Appointment>();
			for (CourseGroup group : groups) {
				for (Appointment a : group.getAppointments()) {
					if (a.getCategory().equals(Appointment.CATEGORY_EXAM)
							&& a.getDateTime().compareTo(new Date()) >= 0) {
						apps.add(a);
					}
				}
			}
			apps = sort(apps);

			return apps.subList(0, apps.size() < 2 ? apps.size() : 2);
		} else {
			return null;
		}
	}

	private List<Appointment> sort(List<Appointment> appointments) {
		for (int i = 1; i < appointments.size(); i++) {
			Appointment appoinment = appointments.get(i);
			int j = i - 1;
			while (j >= 0 && compare(appoinment, appointments.get(j)) < 0) {
				appointments.remove(j + 1);
				appointments.add(j + 1, appointments.get(j));
				j--;
			}
			appointments.remove(j + 1);
			appointments.add(j + 1, appoinment);
		}
		return appointments;
	}

	private int compare(Appointment app1, Appointment app2) {
		return app1.getDate().compareTo(app2.getDate());
	}

	@Override
	public Appointment createAppointment(Appointment template, int groupId,
			int lectureId, int roomId) {
		return template;
	}
}
