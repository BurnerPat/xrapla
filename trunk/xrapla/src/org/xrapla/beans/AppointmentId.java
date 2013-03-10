package org.xrapla.beans;

import java.io.Serializable;
import java.util.Date;

public class AppointmentId implements Serializable {

	private static final long serialVersionUID = 1L;

	private String room;
	private Date date;
	private Date time;
	
	public AppointmentId() {
		
	}
	
	public AppointmentId(String room, Date date, Date time) {
		this.room = room;
		this.date = date;
		this.time = time;
	}

	public String getRoom() {
		return room;
	}

	public Date getDate() {
		return date;
	}

	public Date getTime() {
		return time;
	}
	
	@Override
	public int hashCode() {
		return (room.hashCode() + date.hashCode() + time.hashCode());
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof AppointmentId) {
			AppointmentId o = (AppointmentId)obj;
			return (room.equals(o.getRoom()) && date.equals(o.getDate()) && time.equals(o.getTime()));
		}
		else {
			return false;
		}
	}
}
