package room;

/**
 * This class stores the current room.
 * @author Daniel
 */
public class RoomManager {
	private static Room activeRoom;
	
	/**
	 * Sets the {@code activeRoom} to a new room.
	 * @param room The new active room.
	 */
	public static void setRoom(Room room) {
		activeRoom = room;
		activeRoom.init();
	}
	
	// the current room
	public static Room getRoom() {
		if(activeRoom == null)
			throw new RuntimeException("Please set an active room before starting a game.");
		
		return activeRoom;
	}
}
