package catchTheClown;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import source.Game;
import source.ImageLoader;
import source.Window;
import source.gameObject.GameObject;
import source.input.Input;
import source.room.Room;
import source.room.RoomManager;
import util.Util;

public class CatchTheClown {
	public static void main(String[] args) {
		Game catchTheClown = new Game(new Window("Catch The Clown", 1056, 704));
		RoomManager.setActiveRoom(new MainRoom());
		catchTheClown.start();
	}
}

class MainRoom extends Room {
	BufferedImage background = ImageLoader.loadImage("catchTheClown/full-background.bmp");
	private Clown clown;
	
	private int score, clicks;
	private double accuracy;
	private String roomLayout = "";
	
	public void init() {
		roomLayout += "111111111111111111111111111111111";
		roomLayout += "100000000000000000000000000000001";
		roomLayout += "100000000000000000000000000000001";
		roomLayout += "100000000000000000000000000000001";
		roomLayout += "100000000000000000000000000000001";
		roomLayout += "100000000000000000000000000000001";
		roomLayout += "100000000000000000000000000000001";
		roomLayout += "100000000000000000000000000000001";
		roomLayout += "100000000000000000000000000000001";
		roomLayout += "100000000000000000000000000000001";
		roomLayout += "100000000000000000000000000000001";
		roomLayout += "100000000000000000000000000000001";
		roomLayout += "100000000000000000000000000000001";
		roomLayout += "100000000000000000000000000000001";
		roomLayout += "100000000000000000000000000000001";
		roomLayout += "100000000000000000000000000000001";
		roomLayout += "100000000000000000000000000000001";
		roomLayout += "100000000000000000000000000000001";
		roomLayout += "100000000000000000000000000000001";
		roomLayout += "100000000000000000000000000000001";
		roomLayout += "100000000000000000000000000000001";
		roomLayout += "111111111111111111111111111111111";
		
		this.clown = new Clown();
		this.clown.setRandomLocationAndDirection();
		
		super.addObject(clown);
		
		for(int i = 0; i < roomLayout.length(); i++)
			if(roomLayout.charAt(i) == '1') {
				GameObject wall = new GameObject();
				wall.setSpriteSheet("catchTheClown/wall.bmp", 32, 32, 0);
				wall.setTransform(i % 33 * 32, i / 33 * 32, 0, 0, Math.PI / 2);
				wall.setName("wall");
				
				super.addObject(wall);
			}
	}
	
	public void update() {
		if(clicks != 0)
			accuracy = (double) score / clicks;
		
		if(Input.getMouseButton() == 1) {
			if(Util.checkCollisionWithMouse(this.clown, Input.getMouseX(), Input.getMouseY())) {
				this.clown.setRandomLocationAndDirection();
				score++;
			}
			clicks++;
		}
		super.updateObjects();
	}
	
	public void render(Graphics2D graphics) {
		graphics.drawImage(background, 0, 0, null);
		super.renderObjects(graphics);
		
		graphics.setColor(Color.WHITE);
		graphics.drawString("Score: " + score, 42, 50);
		graphics.drawString("Clicks: " + clicks, 42, 75);
		graphics.drawString(String.format("Accuracy: %.2f%%", accuracy * 100), 42, 100);
	}
}

class Clown extends GameObject {
	private double xStep, yStep, direction, velocityX = 5, velocityY = 5;
	
	public Clown() {
		super.setSpriteSheet("catchTheClown/clown.bmp", 32, 32, 0);
		super.setTransform(0, 0, 0, 0, Math.PI / 2);
		super.setName("clown");
	}
	
	@Override
	public void update() {
		super.setX(super.getX() + this.velocityX * this.xStep);
		for(GameObject wall : MainRoom.getInstancesOf("wall"))
			if(super.collidesWith(wall)) {
				velocityX = -velocityX;
				while(super.collidesWith(wall))
					super.setX(super.getX() + this.velocityX * this.xStep);
				break;
			}
		
		super.setY(super.getY() + this.velocityY * this.yStep);
		for(GameObject wall : MainRoom.getInstancesOf("wall"))
			if(super.collidesWith(wall)) {
				velocityY = -velocityY;
				while(super.collidesWith(wall))
					super.setY(super.getY() + this.velocityY * this.yStep);
				break;
			}
	}
	
	public void setRandomLocationAndDirection() {
		moveToRandomPosition();
		
		direction = ((int) (Math.random() * 8)) * (Math.PI / 4);
		
		this.xStep = Math.cos(this.direction);
		this.yStep = Math.sin(this.direction);
	}
	
	public void moveToRandomPosition() {
		super.setX(((int) (Math.random() * 976) / 32) * 32 + 32);
		super.setY(((int) (Math.random() * 624) / 32) * 32 + 32);
	}
}
