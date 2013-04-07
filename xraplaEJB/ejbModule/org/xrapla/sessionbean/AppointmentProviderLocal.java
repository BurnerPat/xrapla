package org.xrapla.sessionbean;

import java.util.List;

import javax.ejb.Local;

import org.xrapla.entities.Appointment;
import org.xrapla.entities.Room;
import org.xrapla.entities.User;

@Local
public interface AppointmentProviderLocal {

	public List<Appointment> getAppointments(int weekOfYear, int year, Room room);
	public List<Appointment> getAppointments(int weekOfYear, int year, User user);
	public List<Appointment> getNextTwoAppointments(User user);
	public void insert(Appointment appointment);
	public void remove(Appointment appointment);
	public Appointment updateNonKey(Appointment appointment);
	public List<Appointment> getExams(User user);	
}
