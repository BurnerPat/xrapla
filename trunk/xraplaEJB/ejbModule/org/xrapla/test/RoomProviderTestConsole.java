package org.xrapla.test;

import java.util.List;

import org.xrapla.entities.Room;
import org.xrapla.sessionbean.RoomProvider;

public class RoomProviderTestConsole {	
	public static void main(String[] args) {
		RoomProvider roomP = new RoomProvider();
		
		// alle Räume
		System.out.println("Es folgen alle Räume des Gebäudes:\n");
		List<Room> rooms = roomP.getRooms();
		for(Room room : rooms){
			System.out.println(room);
		}
	}

}
