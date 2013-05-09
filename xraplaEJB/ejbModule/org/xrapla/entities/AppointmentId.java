package org.xrapla.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Embeddable
public class AppointmentId implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "roomid")
	private int roomId;

	@Temporal(TemporalType.DATE)
	@Column(name = "date")
	private Date date;

	@Temporal(TemporalType.TIME)
	@Column(name = "time")
	private Date time;

	public AppointmentId() {

	}

	public AppointmentId(int room, Date date, Date time) {
		this.roomId = room;
		this.date = date;
		this.time = time;
	}

	public int getRoomId() {
		return roomId;
	}

	public Date getDate() {
		return date;
	}

	public Date getTime() {
		return time;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public void setRoomId(int roomId) {
		this.roomId = roomId;
	}

	@Override
	public int hashCode() {
		return (roomId + date.hashCode() + time.hashCode());
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof AppointmentId) {
			AppointmentId o = (AppointmentId) obj;
			return (roomId == o.getRoomId() && date.equals(o.getDate()) && time
					.equals(o.getTime()));
		} else {
			return false;
		}
	}
}
