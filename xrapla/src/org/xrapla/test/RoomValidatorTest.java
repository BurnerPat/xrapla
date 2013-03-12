package org.xrapla.test;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.xrapla.Constants;
import org.xrapla.beans.Appointment;
import org.xrapla.beans.Room;
import org.xrapla.classes.AppointmentProvider;
import org.xrapla.classes.RoomValidator;

public class RoomValidatorTest {
	public static void main(String[] args) {
		
		EntityManagerFactory factory;		 
	    factory = Persistence.createEntityManagerFactory(Constants.PERSISTANCE_UNIT_NAME);
	    EntityManager em = factory.createEntityManager();
		
		AppointmentProvider appProv = new AppointmentProvider();
		List<Appointment> appointments = appProv.getAppointments(1, 2013);
		
		System.out.println("Zu untersuchender Raum: " + appointments.get(0).getRoom());
		System.out.println("===========================================\n");
		
		RoomValidator roomV = new RoomValidator(em);
		if(!roomV.isAvailable(appointments.get(0).getRoom(), appointments.get(0).getDate(), appointments.get(0).getTime())){
			Room room = roomV.findNearestAvailableRoom(appointments.get(0).getRoom(), 
					appointments.get(0).getDate(), 
					appointments.get(0).getTime());
			System.out.println("Verfügbarer (nächster) Raum: " + room);
		} else
			System.out.println("Kein Raum verfügbar!");
	}

}
