package org.xrapla.classes;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.sql.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.xrapla.beans.*;

public class AppointmentProvider {
			
	public List<Appointment> getAppointments(int weekOfYear, int year)
	{			    		
	    Calendar monday = new GregorianCalendar();	    
	    monday.setWeekDate(year, weekOfYear, Calendar.MONDAY);	 	    
	    	    
	    Calendar sunday = new GregorianCalendar();
	    sunday.setWeekDate(year, weekOfYear, Calendar.MONDAY);
	    sunday.add(Calendar.DATE, 7);		    
	    
		EntityManagerFactory factory;		 
	    factory = Persistence.createEntityManagerFactory("xrapla");
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
	
	@SuppressWarnings("unchecked")
	public List<Appointment> getNextAppointments(){
		
		Date date = (Date) new java.util.Date();
		
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
		String retStr = "";
		retStr += day.get(Calendar.YEAR) + "-";
		retStr += (day.get(Calendar.MONTH) < 10) ? "0" : ""; 
		retStr += day.get(Calendar.MONTH) + "-";
		retStr += (day.get(Calendar.DATE) < 10) ? "0" : "";
		retStr += day.get(Calendar.DATE); 
		
		System.out.println(retStr);
		return retStr;
	}
}
