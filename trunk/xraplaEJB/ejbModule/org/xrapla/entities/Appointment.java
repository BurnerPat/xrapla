package org.xrapla.entities;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * Entity implementation class for Entity: Appointment
 *
 */
@Entity 
@Table(name="appointment")
public class Appointment implements Serializable {	
	private static final long serialVersionUID = 1L;

	@Transient
	public static final String CATEGORY_EXAM = "exam";
	public static final String CATEGORY_DEFAULT = "default";
	 
	@EmbeddedId
	private AppointmentId id;
	
	@ManyToOne
	@MapsId("roomId")  	
	@JoinColumn(name="room_number", referencedColumnName="number")		
//	@PrimaryKeyJoinColumn(name="ROOM_ID", referencedColumnName="ROOM_ID")
	private Room room;	
	
	@Column(name="duration")
	private int duration;
	@Column(name="category")
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
		//return id.getRoom();
		return room;
	}
		
	public Date getDate() {	
		return id.getDate();
	}
	
	public Date getTime() {	
		return id.getTime();
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
		Calendar dayCal = new GregorianCalendar();
		dayCal.setTime(getDate());
		
		Calendar timeCal = new GregorianCalendar();
		timeCal.setTime(getTime());
		dayCal.set(Calendar.HOUR_OF_DAY, timeCal.get(Calendar.HOUR_OF_DAY));
		dayCal.set(Calendar.MINUTE, timeCal.get(Calendar.MINUTE));
		dayCal.set(Calendar.SECOND, timeCal.get(Calendar.SECOND));
		return dayCal.getTime();
	}

	public String toString(){
		SimpleDateFormat sdfDate = new SimpleDateFormat("dd.MM.yyyy");
		SimpleDateFormat sdfTime = new SimpleDateFormat("HH:mm");
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(getTime());
		cal.add(Calendar.MINUTE, duration);
				
		return sdfDate.format(getDate()) 
				+ " " + sdfTime.format(getTime()) 
				+ " Uhr - " 
				+ sdfTime.format(cal.getTime()) 
				+ " Uhr [Raum: " + room + "]" ;
	}
}
