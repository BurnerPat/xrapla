package org.xrapla.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Entity implementation class for Entity: Room
 *
 */
@Entity
@Table(name="room")
public class Room implements Serializable {

	
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="number")
	int number;
	
	@Column(name="wing")
	char wing;
	
	@OneToMany(mappedBy="room")
	List<Appointment> appointments;
	
	public Room() {
		
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public char getWing() {
		return wing;
	}

	public void setWing(char wing) {
		this.wing = wing;
	}	
	
	public List<Appointment> getAppointments() {
		return appointments;
	}

	public void setAppointments(List<Appointment> appointments) {
		this.appointments = appointments;
	}

	public String toString(){
		return "" + wing + number;
	}
}
