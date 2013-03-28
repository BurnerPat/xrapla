package org.xrapla.classes;

import java.util.List;

import javax.ejb.Local;

import org.xrapla.beans.Appointment;
import org.xrapla.beans.Room;
import org.xrapla.beans.User;

@Local
public interface AppointmentProviderInterface {

	public List<Appointment> getAppointments(int weekOfYear, int year, Room room);
	public List<Appointment> getAppointments(int weekOfYear, int year, User user);
	public List<Appointment> getNextTwoAppointments(User user);
	public void insert(Appointment appointment);
	public void remove(Appointment appointment);
	public Appointment update(Appointment appointment);
	public List<Appointment> getExams(User user);
}
