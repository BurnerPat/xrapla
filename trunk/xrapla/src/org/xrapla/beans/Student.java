package org.xrapla.beans;

import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

@Entity
@DiscriminatorValue("STUDENT")
public class Student extends User {
	
	private int number;
	
	@ManyToOne
	@JoinColumn(name="COURSE_ID")
	private Course relatedCourse;
	
	@ManyToMany
	@JoinTable(name="STUDENT_GROUP",
    	joinColumns={ @JoinColumn(name="STUDENT_ID", referencedColumnName="username")},
    	inverseJoinColumns={@JoinColumn(name="GROUP_ID", referencedColumnName="ID")})
	private List<CourseGroup> groups;
	
	public Student(){
		
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public Course getRelatedCourse() {
		return relatedCourse;
	}

	public void setRelatedCoursee(Course relatedCourse) {
		this.relatedCourse = relatedCourse;
	}

	public List<CourseGroup> getGroups() {
		return groups;
	}

	public void setGroups(List<CourseGroup> groups) {
		this.groups = groups;
	}
}
