package org.xrapla.beans;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@DiscriminatorValue("TUTOR")
@Table(name="tutor")
public class Tutor extends User {

	@Column(name="title")
	private String title;
	@Column(name="subject")
	private String subject;
	
	@OneToMany(mappedBy="tutor")
	private List<Course> ownedCourses;	

	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public String getSubject() {
		return subject;
	}


	public void setSubject(String subject) {
		this.subject = subject;
	}


	public List<Course> getOwnedCourses() {
		return ownedCourses;
	}


	public void setOwnedCourses(List<Course> ownedCourses) {
		this.ownedCourses = ownedCourses;
	}
}
	
	