package org.xrapla.beans;

import javax.persistence.Entity;

@Entity
public class Docent {
	
	private String title;
	private String subject;
	
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