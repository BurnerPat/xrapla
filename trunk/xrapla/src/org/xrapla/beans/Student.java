package org.xrapla.beans;

public class Student extends User {
	
	private int number;
	private Course course;
	
	public Student(){
		
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}
}
