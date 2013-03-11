package org.xrapla.beans;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.persistence.*;

@Entity
@IdClass(AppointmentId.class)
public class Appointment {
	
	@Transient
	public static final String CATEGORY_EXAM = "exam";
	public static final String CATEGORY_DEFAULT = "default";
	
	@Id	
	@ManyToOne
	@JoinColumn(name="Room_ID")
	private Room room;
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
	private CourseGroup group;
	
	public Appointment(){
	}
	
	public Room getRoom() {
		return room;
	}
	public void setRoom(Room room) {
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
	public CourseGroup getGroup() {
		return group;
	}
	
	public void setGroup(CourseGroup group) {
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
		SimpleDateFormat sdfTime = new SimpleDateFormat("HH:mm");
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(time);
		cal.add(Calendar.MINUTE, duration);
				
		return sdfDate.format(date) 
				+ " " + sdfTime.format(time) 
				+ " Uhr - " 
				+ sdfTime.format(cal.getTime()) 
				+ " Uhr [Raum: " + room + "]" ;
	}
}
