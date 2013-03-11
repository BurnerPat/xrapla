package org.xrapla.test;

import java.util.List;

import org.xrapla.beans.Room;
import org.xrapla.classes.RoomProvider;

public class RoomProviderTest {	
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
