package org.xrapla.sessionbean;

import java.util.List;

import javax.ejb.Local;

import org.xrapla.entities.Room;


@Local
public interface RoomProviderLocal {
	public List<Room> getRooms();
}
