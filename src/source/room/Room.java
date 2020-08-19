package source.room;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import source.gameObject.GameObject;

public abstract class Room {
	private static List<GameObject> objects;
	
	public Room() {Room.objects = new ArrayList<GameObject>();}
	
	public abstract void init();
	public abstract void update();
	public abstract void render(Graphics2D graphics);
	
	public static void addObject(GameObject object) {
		Room.objects.add(object);
	}
	
	public static void removeObject(GameObject object) {
		Room.objects.remove(object);
	}
	
	public static void	renderObjects(Graphics2D graphics) {
		for(int i = 0; i < Room.objects.size(); i++)
			Room.objects.get(i).render(graphics);
	}
	
	public static void	updateObjects() {
		for(int i = 0; i < Room.objects.size(); i++)
			Room.objects.get(i).update();
	}
	
	public static ArrayList<GameObject> getInstancesOf(String name) {
		ArrayList<GameObject> instances = new ArrayList<GameObject>();
		
		for(int i = 0; i < Room.objects.size(); i++)
			if(Room.objects.get(i).getName().equals(name))
				instances.add(Room.objects.get(i));
		
		return instances;
	}
}
