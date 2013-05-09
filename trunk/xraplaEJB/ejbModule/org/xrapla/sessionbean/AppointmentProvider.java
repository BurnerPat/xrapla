package org.xrapla.sessionbean;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
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

		TypedQuery<Appointment> q;
		if (user instanceof Student)
			q = getQuery(monday, sunday, (Student) user);
		else if (user instanceof Docent)
			q = getQuery(monday, sunday, (Docent) user);
		else
			return null;

		if (q == null)
			return null;

		List<Appointment> appointments = null;
		try {
			appointments = q.getResultList();
		} catch (NoResultException es) {
			appointments = null;
		}
		return appointments;
	}

	private TypedQuery<Appointment> getQuery(Calendar monday, Calendar sunday,
			Student user) {

		TypedQuery<Student> queryGroup = em
				.createQuery(
						"SELECT s FROM Student s JOIN FETCH s.groups WHERE s.username = :username",
						Student.class);

		queryGroup.setParameter("username", user.getUsername());

		String queryString = "SELECT a FROM Appointment a "
				+ "WHERE a.id.date>=?1 AND a.id.date<=?2"
				+ " AND a.group IN :groups";

		TypedQuery<Appointment> q = em.createQuery(queryString,
				Appointment.class);

		q.setParameter(1, monday.getTime());
		q.setParameter(2, sunday.getTime());
		try {
			q.setParameter("groups", queryGroup.getResultList().get(0)
					.getGroups());
		} catch (IndexOutOfBoundsException ex) {
			return null;
		}

		return q;
	}

	private TypedQuery<Appointment> getQuery(Calendar monday, Calendar sunday,
			Docent user) {

		TypedQuery<Docent> queryLecture = em
				.createQuery(
						"SELECT s FROM Docent s JOIN FETCH s.lectures WHERE s.username = :username",
						Docent.class);

		queryLecture.setParameter("username", user.getUsername());

		String queryString = "SELECT a " + "FROM Appointment a "
				+ "WHERE a.id.date>=?1 AND a.id.date<=?2"
				+ " AND a.lecture IN :lectures";

		TypedQuery<Appointment> q = em.createQuery(queryString,
				Appointment.class);

		q.setParameter(1, monday.getTime());
		q.setParameter(2, sunday.getTime());

		try {
			q.setParameter("lectures", queryLecture.getResultList().get(0)
					.getLectures());
		} catch (IndexOutOfBoundsException ex) {
			return null;
		}

		return q;
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

	private void insert(Appointment appointment) {
		em.persist(appointment);
	}

	@Override
	public void remove(Appointment appointment) {
		em.remove(appointment);
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

	public List<Appointment> sort(List<Appointment> appointments) {
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

	public int compare(Appointment app1, Appointment app2) {
		return app1.getDateTime().compareTo(app2.getDateTime());
	}

	@Override
	public Appointment createAppointment(Appointment template, int groupId,
			int lectureId, int roomId) {

		try {
			CourseGroup group = getGroup(groupId);
			Lecture lecture = getLecture(lectureId);
			Room room = getRoom(roomId);

			template.setGroup(group);
			template.setLecture(lecture);
			template.setRoom(room);

			group.getAppointments().add(template);
			lecture.getAppointments().add(template);
			room.getAppointments().add(template);

			// template.getRoom().getAppointments().add(template);

			insert(template);
		} catch (NoResultException | NonUniqueResultException uex) {
			return null;
		}

		return template;
	}

	private CourseGroup getGroup(int id) {
		String queryString = "SELECT g FROM CourseGroup g "
				+ "WHERE g.id = :group";

		TypedQuery<CourseGroup> q = em.createQuery(queryString,
				CourseGroup.class);

		q.setParameter("group", id);

		return q.getSingleResult();
	}

	private Room getRoom(int id) {
		String queryString = "SELECT r FROM Room r " + "WHERE r.number = :room";

		TypedQuery<Room> q = em.createQuery(queryString, Room.class);

		q.setParameter("room", id);

		return q.getSingleResult();
	}

	private Lecture getLecture(int id) {
		String queryString = "SELECT l FROM Lecture l "
				+ "WHERE l.id = :lecture";

		TypedQuery<Lecture> q = em.createQuery(queryString, Lecture.class);

		q.setParameter("lecture", id);

		return q.getSingleResult();
	}
}
