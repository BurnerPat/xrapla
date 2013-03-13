package org.xrapla.beans;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Table;

//@Embeddable
@Table(name="appointmentid")
public class AppointmentId implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/*@ManyToOne
	@JoinColumn(name="ROOM_ID")	*/
	@Column(name="room")
	private Room room;
	
	//@Temporal(TemporalType.DATE)
	@Column(name="date")
	private Date date;
	//@Temporal(TemporalType.TIME)
	@Column(name="time")
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
