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
        // TODO Auto-generated constructor stub
    }

	public List<Appointment> getAppointments(int weekOfYear, int year, Room room) {
		Calendar monday = new GregorianCalendar();
		monday.setWeekDate(year, weekOfYear, Calendar.MONDAY);

		Calendar sunday = new GregorianCalendar();
		sunday.setWeekDate(year, weekOfYear, Calendar.MONDAY);
		sunday.add(Calendar.DATE, 7);

		TypedQuery<Appointment> q = em.createQuery("SELECT a "
				+ "FROM Appointment a " + "WHERE a.date >= ?1 "
				+ "AND a.date <= ?2 " + "AND a.room = ?3", Appointment.class);

		q.setParameter(1, monday.getTime());
		q.setParameter(2, sunday.getTime());
		q.setParameter(3, room);		
		
		try {
			List<Appointment> appointments = q.getResultList();
			return appointments;
	    } catch(NoResultException ex) {
	    	return null;
	    }
	}

	public List<Appointment> getAppointments(int weekOfYear, int year, User user) {
		Calendar monday = new GregorianCalendar();
		monday.setWeekDate(year, weekOfYear, Calendar.MONDAY);

		Calendar sunday = new GregorianCalendar();
		sunday.setWeekDate(year, weekOfYear, Calendar.MONDAY);
		sunday.add(Calendar.DATE, 7);

		TypedQuery<Appointment> q = em.createQuery(
				"SELECT a " + 
				"FROM Appointment a " + 
				"WHERE a.date>=?1 AND a.date<=?2",
				Appointment.class);

		q.setParameter(1, monday.getTime());
		q.setParameter(2, sunday.getTime());
		
		List<Appointment> appointments = null;
		try{
			appointments = q.getResultList();
		}
		catch(NoResultException es){
			appointments = null;
		}
		return appointments;
	}

	public List<Appointment> getNextTwoAppointments(User user) {

		if (user instanceof Student) {
			Student student = (Student) user;
			List<CourseGroup> groups = student.getGroups();
			List<Appointment> apps = new ArrayList<Appointment>();
			for (CourseGroup group : groups) {
				for (Appointment a : (group.getAppointments())) {	
					if(a.getDateTime().compareTo(new Date()) >= 0)
						apps.add(a);
				}
			}							
			return apps.subList(0, apps.size() < 2 ? apps.size() : 2);
		} else if (user instanceof Docent) {
			Docent docent = (Docent) user;
			List<Appointment> apps = new ArrayList<Appointment>();

			for (Lecture lecture : docent.getLectures())
				for (Appointment app : lecture.getAppointments())		
					if(app.getDateTime().compareTo(new Date()) >= 0)
						apps.add(app);
			return apps.subList(0, apps.size() < 2 ? apps.size() : 2);
		} else
			return null;
	}

	public void insert(Appointment appointment) {
		em.getTransaction().begin();
		em.persist(appointment);
		em.getTransaction().commit();
	}

	public void remove(Appointment appointment) {
		em.getTransaction().begin();
		em.remove(appointment);
		em.getTransaction().commit();
	}

	public Appointment update(Appointment appointment) {
		/*
		 * Appointment app = em.find(Appointment.class, new AppointmentId()); if
		 * (emp != null) { emp.setSalary(emp.getSalary() + raise); } return emp;
		 */
		return appointment;
	}
	
	public List<Appointment> getExams(User user){
		
		if(user instanceof Student){
			Student student = (Student) user;
	    	List<CourseGroup> groups = student.getGroups();
	    	List<Appointment> apps = new ArrayList<Appointment>();
	    	for(CourseGroup group : groups){
	    		for(Appointment a : group.getAppointments()){
	    			if(a.getCategory().equals(Appointment.CATEGORY_EXAM) && 
	    					a.getDateTime().compareTo(new Date()) >= 0)
	    				apps.add(a);
	    		}
	    	}
	    	apps = sort(apps);
	    		    	
    		return apps.subList(0, apps.size() < 2 ? apps.size() : 2);	    	
		}else
			return null;
		
	}
	
	private List<Appointment> sort(List<Appointment> appointments) {
		for (int i = 1; i < appointments.size(); i++) {
			Appointment appoinment = appointments.get(i);
			int j = i - 1;
			while (j >= 0 && compare(appoinment, appointments.get(j)) < 0) {
				appointments.remove(j+1);
				appointments.add(j+1, appointments.get(j));
				j--;
			}
			appointments.remove(j+1);
			appointments.add(j+1, appoinment);			
		}
		return appointments;
	}
	
	private int compare(Appointment app1, Appointment app2){
		return app1.getDate().compareTo(app2.getDate());
	}	
}
