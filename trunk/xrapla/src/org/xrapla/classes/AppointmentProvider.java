package org.xrapla.classes;

import java.util.Calendar;
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
	    		"FROM Appointment" +
	    		"WHERE date BETWEEN " + 
	    				calendarToSql(monday) +
    				" AND " +
    					calendarToSql(sunday));	   
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
