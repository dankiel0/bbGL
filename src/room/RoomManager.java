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
	public static void setActiveRoom(Room room) {
		activeRoom = room;
		activeRoom.init();
	}
	
	/**
	 * @throws RuntimeException If the active room is null.
	 * @return {@code activeRoom} The active room.
	 */
	public static Room getActiveRoom() {
		if(activeRoom == null)
			throw new RuntimeException("Please set an active room before starting a game.");
		
		return activeRoom;
	}
}
