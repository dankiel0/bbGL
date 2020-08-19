package util;

import source.gameObject.GameObject;

public class Util {
	public static boolean checkCollisionWithMouse(GameObject object, int x, int y) {
		return object.getX() - object.getCenterX() <= x && object.getCurrentSprite().getWidth() + object.getX() - object.getCenterX() >= x && object.getY() - object.getCenterY() <= y && object.getCurrentSprite().getHeight() + object.getY() - object.getCenterY() >= y;
	}
	
	public static boolean checkXCollisionBetweenObjects(GameObject object1, GameObject object2) {
		return object1.getX() < object2.getX() + object2.getWidth() && object1.getX() + object1.getWidth() > object2.getX();
	}
	
	public static boolean checkYCollisionBetweenObjects(GameObject object1, GameObject object2) {
		return object1.getY() < object2.getY() + object2.getHeight() && object1.getY() + object1.getHeight() > object2.getY();
	}
	
	public static boolean checkCollision(GameObject object1, GameObject object2) {
		return object1.getX() < object2.getX() + object2.getWidth() && object1.getX() + object1.getWidth() > object2.getX() && object1.getY() < object2.getY() + object2.getHeight() && object1.getY() + object1.getHeight() > object2.getY();
	}
}
