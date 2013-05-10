package org.xrapla.handlers;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.xrapla.entities.Appointment;
import org.xrapla.entities.User;
import org.xrapla.factory.BeanFactory;
import org.xrapla.sessionbean.AppointmentProviderLocal;

public class UserCalendarHandler {
	private List<Appointment> appointments;
	private ArrayList<ArrayList<Appointment>> day;
	private int minHour;
	private int maxHour;

	public UserCalendarHandler(User user, Calendar calendar) {
		AppointmentProviderLocal provider = BeanFactory.getAppointmentProvider();
		appointments = provider.getAppointments(calendar.get(Calendar.WEEK_OF_YEAR), calendar.get(Calendar.YEAR), user);

		if (appointments != null) {
			day = new ArrayList<ArrayList<Appointment>>();
			for (int i = 0; i < 7; i++) {
				day.add(new ArrayList<Appointment>());
			}

			categorizeDays();
			sortDays();
		}
	}

	private void categorizeDays() {
		Calendar calendar = Calendar.getInstance();
		for (Appointment item : appointments) {
			calendar.setTime(item.getDate());
			int weekday = calendar.get(Calendar.DAY_OF_WEEK);
			weekday = (weekday == 1) ? 7 : (weekday - 1);

			day.get(weekday).add(item);
		}
	}

	private void sortDays() {
		int min = 23;
		int max = 0;

		for (int i = 0; i < 7; i++) {
			ArrayList<Appointment> d = day.get(i);

			for (int j = 0; j < d.size(); j++) {
				for (int k = 1; k < d.size() - j; k++) {
					Appointment a1 = d.get(k - 1);
					Appointment a2 = d.get(k);

					if (a1.getTime().after(a2.getTime())) {
						d.set(k - 1, a2);
						d.set(k, a1);
					}
				}
			}

			if (d.size() > 0) {
				Date dmin = d.get(0).getTime();
				Date dmax = d.get(d.size() - 1).getTime();

				Calendar c = Calendar.getInstance();
				c.setTime(dmin);
				int imin = c.get(Calendar.HOUR_OF_DAY);
				c.setTime(dmax);
				int imax = c.get(Calendar.HOUR_OF_DAY) + ((d.get(d.size() - 1).getDuration() + 60) / 60);

				if (imin < min) {
					min = imin;
				}
				if (imax > max) {
					max = imax;
				}
			}
		}

		minHour = (min < 23) ? ((min >= 2) ? (min - 2) : 0) : 9;
		maxHour = (max > 0) ? ((max <= 21) ? (max + 2) : 23) : 16;

		maxHour = (maxHour - minHour > 8) ? maxHour : (minHour + 8);
	}

	public ArrayList<Appointment> getDay(int d) {
		return day.get(d);
	}

	public ArrayList<ArrayList<Appointment>> getDays() {
		return day;
	}

	public int getMinHour() {
		return minHour;
	}

	public int getMaxHour() {
		return maxHour;
	}

	public boolean isEmpty() {
		return (appointments == null);
	}
}
