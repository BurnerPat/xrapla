package org.xrapla.handlers;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.xrapla.beans.Appointment;
import org.xrapla.beans.User;
import org.xrapla.classes.AppointmentProvider;

public class CalendarHandler {
	private final List<Appointment> appointments;
	
	public CalendarHandler(User user, Calendar calendar) {
		AppointmentProvider provider = new AppointmentProvider();
		
	}
}
