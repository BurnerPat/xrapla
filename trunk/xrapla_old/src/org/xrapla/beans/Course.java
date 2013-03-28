package org.xrapla.beans;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="course")
public class Course {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@Column(name="name")
	private String name;
	
	@ManyToOne
	@JoinColumn(name="TUTOR_ID")
	private Tutor tutor;
	
	@OneToMany(mappedBy="course")
	private List<CourseGroup> groups;
	
	@OneToMany(mappedBy="relatedCourse")
	private List<Student> students;
	
	public Course(){
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Tutor getTutor() {
		return tutor;
	}
	public void setTutor(Tutor tutor) {
		this.tutor = tutor;
	}		
	public List<CourseGroup> getGroups() {
		return groups;
	}

	public void setGroups(List<CourseGroup> groups) {
		this.groups = groups;
	}

	public List<Student> getStudents() {
		return students;
	}

	public void setStudents(List<Student> students) {
		this.students = students;
	}

	public String toString()
	{
		return name + " [" + tutor + "]";
	}
}
