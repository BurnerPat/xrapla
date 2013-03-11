package org.xrapla.beans;

import javax.persistence.Id;

public class Room {
	
	@Id
	int number;
		
	char wing;

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
	
	public String toString(){
		return "" + wing + number;
	}
}
