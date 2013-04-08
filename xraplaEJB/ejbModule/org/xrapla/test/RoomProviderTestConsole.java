package org.xrapla.test;

import java.util.List;

import org.xrapla.entities.Room;
import org.xrapla.sessionbean.RoomProvider;

public class RoomProviderTestConsole {	
	public static void main(String[] args) {
		RoomProvider roomP = new RoomProvider();
		
		// alle R�ume
		System.out.println("Es folgen alle R�ume des Geb�udes:\n");
		List<Room> rooms = roomP.getRooms();
		for(Room room : rooms){
			System.out.println(room);
		}
	}

}
