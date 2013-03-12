package org.xrapla.classes;

import java.util.Calendar;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import org.xrapla.Constants;
import org.xrapla.beans.*;

public class AppointmentProvider {
	
	public List<Appointment> getAppointments(int weekOfYear, int year, Room room){
		Calendar monday = new GregorianCalendar();	    
	    monday.setWeekDate(year, weekOfYear, Calendar.MONDAY);	 	    
	    	    
	    Calendar sunday = new GregorianCalendar();
	    sunday.setWeekDate(year, weekOfYear, Calendar.MONDAY);
	    sunday.add(Calendar.DATE, 7);
	    
	    EntityManagerFactory factory;		 
	    factory = Persistence.createEntityManagerFactory(Constants.PERSISTANCE_UNIT_NAME);
	    EntityManager em = factory.createEntityManager();	       	    
	    
	    // Build and execute SQL-Statement
	    TypedQuery<Appointment> q = em.createQuery(
	    		"SELECT a " +
	    		"FROM Appointment a " +
	    		"WHERE a.date >= ?1 " +
	    		"AND a.date <= ?2 " +
	    		"AND a.room = ?3", Appointment.class);
	    
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
			
	public List<Appointment> getAppointments(int weekOfYear, int year)
	{			    		
	    Calendar monday = new GregorianCalendar();	    
	    monday.setWeekDate(year, weekOfYear, Calendar.MONDAY);	 	    
	    	    
	    Calendar sunday = new GregorianCalendar();
	    sunday.setWeekDate(year, weekOfYear, Calendar.MONDAY);
	    sunday.add(Calendar.DATE, 7);		    
	    
		EntityManagerFactory factory;		 
	    factory = Persistence.createEntityManagerFactory(Constants.PERSISTANCE_UNIT_NAME);
	    EntityManager em = factory.createEntityManager();	       	    
	    
	    // Build and execute SQL-Statement
	    TypedQuery<Appointment> q = em.createQuery(
	    		"SELECT a " +
	    		"FROM Appointment a " +
	    		"WHERE a.date>=?1 AND a.date<=?2", Appointment.class);
	    
	    q.setParameter(1, monday.getTime());
	    q.setParameter(2, sunday.getTime());
	    
    	List<Appointment> appointments = q.getResultList();
    	
	    return appointments;
	}
	
	@Deprecated
	public List<Appointment> getNextAppointments(){
		
		EntityManagerFactory factory;		 
	    factory = Persistence.createEntityManagerFactory(Constants.PERSISTANCE_UNIT_NAME);
	    EntityManager em = factory.createEntityManager();
	    
	    TypedQuery<Appointment> q = em.createQuery(
	    		"SELECT a " +
	    		"FROM Appointment a " +
	    		"WHERE a.date >= CURRENT_DATE ", Appointment.class);
	    		
		List<Appointment> appointments = new ArrayList<Appointment>();
		for(Appointment app : q.getResultList())
		{
			if(appointments.size() < 2)
				appointments.add(app);
			else
				break;
		}
				
		return appointments;			
	}
	
	public List<Appointment> getNextAppointments(User user){						
	    
	    if(user instanceof Student){
	    	Student student = (Student) user;
	    	List<CourseGroup> groups = student.getGroups();
	    	List<Appointment> apps = new ArrayList<Appointment>();
	    	for(CourseGroup group : groups){
	    		for(Appointment a : (group.getAppointments())){
	    			if(apps.size() < 2)
	    				apps.add(a);
	    		}
	    	}	    	
	    	return apps;	    		    	
	    } else
    		if(user instanceof Docent){
    			Docent docent = (Docent) user;
    			List<Appointment> apps = new ArrayList<Appointment>();
    			
    			for(Lecture lecture : docent.getLectures())
    				for(Appointment app : lecture.getAppointments())
    					if(apps.size() < 2)
    						apps.add(app);
    			return apps;
    		} else
    			return new ArrayList<Appointment>();		
	}
	
	public void insert(Appointment appointment){
		EntityManagerFactory factory = Persistence.createEntityManagerFactory(Constants.PERSISTANCE_UNIT_NAME);		
		EntityManager em = factory.createEntityManager();
		
		em.getTransaction().begin();
		em.persist(appointment);
		em.getTransaction().commit();
		
		em.close();
	}
	
	public void remove(Appointment appointment){
		EntityManagerFactory factory = Persistence.createEntityManagerFactory(Constants.PERSISTANCE_UNIT_NAME);		
		EntityManager em = factory.createEntityManager();
		
		em.getTransaction().begin();
		em.remove(appointment);
		em.getTransaction().commit();
		
		em.close();
	}	
		
	public Appointment update(Appointment appointment){		
	    /*Appointment app = em.find(Appointment.class, new AppointmentId());
	    if (emp != null) {
	      emp.setSalary(emp.getSalary() + raise);
	    }
	    return emp;	*/
		return appointment;
	}
}
