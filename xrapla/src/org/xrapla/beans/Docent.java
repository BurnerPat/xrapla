package org.xrapla.beans;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;

@Entity
public class Docent {
	
	private String title;
	private String subject;
	
	@ManyToMany
	@JoinTable(name="DOCENT_LECTURE",
	        joinColumns={ @JoinColumn(name="LECTURE_ID", referencedColumnName="ID")},
	        inverseJoinColumns={@JoinColumn(name="DOCENT_ID", referencedColumnName="username")})
	private List<Lecture> lectures;
		
	public List<Lecture> getLectures() {
		return lectures;
	}

	public void setLectures(List<Lecture> lectures) {
		this.lectures = lectures;
	}

	public Docent() {
		
	}

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
	
	
	
	
}
