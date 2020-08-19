package source.room;

public class RoomManager {
	private static Room activeRoom;
	
	public static void setActiveRoom(Room room) {
		RoomManager.activeRoom = room;
		RoomManager.activeRoom.init();
	}
	
	public static Room getActiveRoom() {return RoomManager.activeRoom;}
}
