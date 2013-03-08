package org.xrapla.classes;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.xrapla.beans.*;

public class AppointmentProvider {
		
	@SuppressWarnings("unchecked")
	public List<Appointment> getAppointments(int weekOfYear, int year)
	{			    
	    Calendar monday = new GregorianCalendar();	    
	    monday.setWeekDate(year, weekOfYear, Calendar.MONDAY);	   	
	    	    
	    Calendar sunday = monday;
	    sunday.add(Calendar.DATE, 7);	
	    
		EntityManagerFactory factory;		 
	    factory = Persistence.createEntityManagerFactory("xrapla");
	    EntityManager em = factory.createEntityManager();	       	    
	    
	    // Build and execute SQL-Statement
	    Query q = em.createQuery(
	    		"SELECT * " +
	    		"FROM Appointment " +
	    		"WHERE date BETWEEN ?1 AND ?2");
	    
	    q.setParameter(1, calendarToSql(monday));
	    q.setParameter(2, calendarToSql(sunday));
	    
    	List<Appointment> appointments = q.getResultList();
    	
	    return appointments;
	}
	
	@SuppressWarnings("unchecked")
	public List<Appointment> getNextAppointments(){
		
		Date date = new Date();
		
		EntityManagerFactory factory;		 
	    factory = Persistence.createEntityManagerFactory("xrapla");
	    EntityManager em = factory.createEntityManager();
	    
	    Query q = em.createQuery(
	    		"SELECT * " +
	    		"FROM Appointment" +
	    		"Where date >= ?1" +
	    		"LIMIT 2");
	    
	    
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	    q.setParameter(1, sdf.format(date));
	    		
		List<Appointment> appointments = q.getResultList();
				
		return appointments;
		
		
		
	}
	
	private String calendarToSql(Calendar day)	
	{	
		
		return day.get(Calendar.YEAR) + "-" +
					day.get(Calendar.MONTH) + "-" +
					day.get(Calendar.DATE); 
	}
	
	

}
