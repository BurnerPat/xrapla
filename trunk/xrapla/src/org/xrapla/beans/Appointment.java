package org.xrapla.beans;

import java.util.Date;

import javax.persistence.Id;

public class Appointment {

	@Id	
	private String room;
	@Id
	private Date date;
	@Id
	private Date time;
	
	private int duration;
	private Lecture lecture;
	private Group group;
	
	public Appointment(){
	}
	
	public String getRoom() {
		return room;
	}
	public void setRoom(String room) {
		this.room = room;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	public int getDuration() {
		return duration;
	}
	public void setDuration(int duration) {
		this.duration = duration;
	}
	public Lecture getLecture() {
		return lecture;
	}
	public void setLecture(Lecture lecture) {
		this.lecture = lecture;
	}
	public Group getGroup() {
		return group;
	}
	public void setGroup(Group group) {
		this.group = group;
	}	
	
}
