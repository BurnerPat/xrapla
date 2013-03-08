package org.xrapla.beans;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.persistence.*;

@Entity
public class Appointment {

	@Id	
	private String room;
	@Id
	@Temporal(TemporalType.DATE)
	private Date date;
	@Id
	@Temporal(TemporalType.TIME)
	private Date time;
	
	private int duration;	
	private String category;
	
	@ManyToOne
	@JoinColumn(name="LECTURE_ID")
	private Lecture lecture;	
	
	@ManyToOne
	@JoinColumn(name="GROUP_ID")
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
	
	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}	

	public String toString(){
		SimpleDateFormat sdfDate = new SimpleDateFormat("dd.MM.yyyy");
		SimpleDateFormat sdfTime = new SimpleDateFormat("hh:mm");
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(time);
		cal.add(Calendar.MILLISECOND, duration);
		
		return sdfDate.format(date) 
				+ " " + sdfTime.format(time) 
				+ " - " 
				+ cal.get(Calendar.HOUR) + ":" + cal.get(Calendar.MINUTE) 
				+ "[" + room + "]" ;
	}
}
