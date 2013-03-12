package org.xrapla.beans;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@Entity
@IdClass(AppointmentId.class)
public class Appointment {
	
	@Transient
	public static final String CATEGORY_EXAM = "exam";
	public static final String CATEGORY_DEFAULT = "default";
	
	@Id	
	@ManyToOne
	@JoinColumn(name="ROOM_ID")	
	private Room room;
	
	@Id
	@Temporal(TemporalType.DATE)
	private Date date;
	
	@Id
	@Temporal(TemporalType.TIME)
	private Date time;
	
	/*@EmbeddedId
	private AppointmentId id;*/
	
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
	
	//@Transient
	public Room getRoom() {
		//return id.getRoom();
		return room;
	}
	
	//@Transient
	public Date getDate() {
		//return id.getDate();
		return date;
	}
	
	//@Transient
	public Date getTime() {
		//return id.getTime();
		return time;
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
	
	@Transient
	public Date getDateTime(){
		Date dateTime = date;
		dateTime.setTime(time.getTime());
		return dateTime;
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
