package org.xrapla.beans;

import java.io.Serializable;
import java.util.Date;

//@Embeddable
public class AppointmentId implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/*@ManyToOne
	@JoinColumn(name="ROOM_ID")	*/
	private Room room;
	
	//@Temporal(TemporalType.DATE)
	private Date date;
	//@Temporal(TemporalType.TIME)
	private Date time;
	
	public AppointmentId() {
		
	}
	
	public AppointmentId(Room room, Date date, Date time) {
		this.room = room;
		this.date = date;
		this.time = time;
	}

	public Room getRoom() {
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
			return (room.getNumber() == o.getRoom().getNumber() && date.equals(o.getDate()) && time.equals(o.getTime()));
		}
		else {
			return false;
		}
	}
}
