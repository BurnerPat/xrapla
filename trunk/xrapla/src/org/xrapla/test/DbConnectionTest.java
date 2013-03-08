package org.xrapla.test;

import java.util.List;

import org.xrapla.beans.Appointment;
import org.xrapla.classes.AppointmentProvider;

public class DbConnectionTest {

	public static void main(String[] args) {
		List<Appointment> appointments = new AppointmentProvider().getAppointments(5, 2013);
		for(Appointment ap : appointments)
			System.out.println(ap);
	}

}
