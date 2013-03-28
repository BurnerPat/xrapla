package org.xrapla.sessionbean;

import java.util.Date;

import javax.ejb.Local;

import org.xrapla.entities.Room;

@Local
public interface RoomValidatorLocal {
	public boolean isAvailable(Room room, Date date, Date time, int duration);	
	public Room findNearestAvailableRoom(Room room, Date date, Date time, int duration);	
}
