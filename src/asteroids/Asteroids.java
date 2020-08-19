// ENGINE TODO: PHYSICS, MATH, SOUND, MULTI-ROOMING, PARTICLES, MODULES.
// ASTEROIDS TODO: OUTER ASTEROID SPAWN, ASTEROID 15 MINIMUM, BULLETS, EXPLOSIONS, PROPER COLLISION DETECTION,

package asteroids;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import source.Game;
import source.ImageLoader;
import source.Window;
import source.gameObject.GameObject;
import source.input.Input;
import source.room.Room;
import source.room.RoomManager;

public class Asteroids {
	public static BufferedImage backgroundSprite, explosionSprite, spaceshipSprite, bulletSprite;
	public static final int GAME_WIDTH = 1080, GAME_HEIGHT = 720;
	public static int time = 0;
	
	public static void main(String[] args) {
		backgroundSprite = ImageLoader.loadImage("asteroids/background.jpg");
		explosionSprite = ImageLoader.loadImage("asteroids/explosion.png");
		spaceshipSprite = ImageLoader.loadImage("asteroids/spaceship.png");
		bulletSprite = ImageLoader.loadImage("asteroids/bullet.png");
		
		Game asteroids = new Game(new Window("Asteroids", Asteroids.GAME_WIDTH, Asteroids.GAME_HEIGHT));
		RoomManager.setActiveRoom(new Cosmos());
		asteroids.start();
	}
}

class Cosmos extends Room {
	private Spaceship spaceship;
	
	public void init() {
		this.spaceship = new Spaceship();
		super.addObject(spaceship);
	}
	
	public void update() {
		super.updateObjects();
		this.checkAndDoCollisions();
	}
	
	public void render(Graphics2D graphics) {
		graphics.drawImage(Asteroids.backgroundSprite, 0, 0, null);
		super.renderObjects(graphics);
	}
	
	private void checkAndDoCollisions() {
		for(GameObject bullet : Cosmos.getInstancesOf("bullet"))
			for(GameObject asteroid : Cosmos.getInstancesOf("asteroid"))
				if(bullet.collidesWith(asteroid)) {
					
					GameObject explosion2 = new GameObject();
					explosion2.setSpriteSheet(Asteroids.explosionSprite, 256, 256, 0);
					explosion2.setTransform(asteroid.getX(), asteroid.getY(), 128, 128, Math.PI / 2);
					explosion2.initAnimation(1.5);
					explosion2.setName("explosion");
					Cosmos.addObject(explosion2);
					Cosmos.removeObject(asteroid);
					
					break;
				}
		
		
		for(GameObject explosion1 : Cosmos.getInstancesOf("explosion")) {
			if(explosion1.getCurrentSprite().equals(explosion1.getLastSprite()))
				Cosmos.removeObject(explosion1);
			explosion1.doAnimation();
		}
		
		if(Asteroids.time % 40 == 0) {
			Asteroid asteroid = new Asteroid();
			asteroid.initAnimation(3.5);
			Cosmos.addObject(asteroid);
		}
		Asteroids.time++;
	}
}

class Spaceship extends CosmosGameObject {
	private final double DEGREE_OF_ROTATION = Math.PI / 30;
	
	public Spaceship() {
		super.setSpriteSheet(Asteroids.spaceshipSprite, 39, 43, 2);
		super.setTransform(540, 360, 19, 21, Math.PI / 2);
	}
	
	@Override
	public void update() {
		doMovement();
		super.wrapAroundScreen();
	}
	
	public void doMovement() {
		for(GameObject asteroid : Cosmos.getInstancesOf("asteroid"))
			if(asteroid.collidesWith(this)) {
				Cosmos.removeObject(this);
				
				GameObject explosion = new GameObject();
				explosion.setSpriteSheet(Asteroids.explosionSprite, 256, 256, 0);
				explosion.setTransform(super.getX(), super.getY(), 128, 128, Math.PI / 2);
				explosion.initAnimation(1.5);
				explosion.setName("explosion");
				Cosmos.addObject(explosion);
		}
		
		// reset rotation on each update.
		double rotation = 0;
		
		// if space is pressed, create bullet.
		if(Input.getSinglePressKeys()[32] && Asteroids.time % 5 == 0)
			Cosmos.addObject(new Bullet(Math.cos(super.getRotation()), Math.sin(-super.getRotation()), super.getX(), super.getY(), super.getRotation()));
		
		// if up key is not pressed, set sprite to default.
		if(!Input.getSinglePressKeys()[38])
			super.setCurrentSprite(2);
		
		// if up key is presed, calculate direction of motion and set velocity to 5.
		if(Input.getSinglePressKeys()[38]) {
			super.setCurrentSprite(3);
			super.setXStep(Math.cos(-super.getRotation()));
			super.setYStep(Math.sin(-super.getRotation()));
			super.setSpeed(15);
		}
		
		// if left key is pressed, rotate to the left.
		if(Input.getSinglePressKeys()[37])
			rotation = this.DEGREE_OF_ROTATION;
		
		// if right key is pressed, rotate to the right.
		if(Input.getSinglePressKeys()[39])
			rotation = -this.DEGREE_OF_ROTATION;
		
		super.doMovement();
		super.setRotation(super.getRotation() + rotation);
		
		// decreases velocity on each update. this imitates friction.
		super.setSpeed(super.getSpeed() * 0.975);
	}
}

class Asteroid extends CosmosGameObject {
	public Asteroid() {
		super.setName("asteroid");
		super.setTransform(0, 0, 32, 32, Math.PI / 2);
		
		// set random spritesheet.
		if((int) (Math.random() * 2) == 0)
			super.setSpriteSheet(ImageLoader.loadImage("asteroids/rock1.png"), 64, 64, 0);
		else
			super.setSpriteSheet(ImageLoader.loadImage("asteroids/rock2.png"), 64, 64, 0);
		
		// set random direction.
		super.setXStep(Math.random());
		super.setYStep(Math.random());
		
		// set random speed.
		if((int) (Math.random() * 2) == 0)
			super.setSpeed(Math.random() * 6 - 10);
		else
			super.setSpeed(Math.random() * 6 + 5);
	}
	
	@Override
	public void update() {
		super.wrapAroundScreen();
		super.doMovement();
		super.doAnimation();
	}
}

class Bullet extends CosmosGameObject {
	private double distance;
	
	public Bullet(double xStep, double yStep, double x, double y, double rotation) {
		super.setName("bullet");
		
		super.setXStep(xStep);
		super.setYStep(yStep);
		super.setSpeed(10);
		
		super.setTransform(x, y, 11, 23, rotation);
		super.setSpriteSheet(Asteroids.bulletSprite, 22, 47, 0);
	}
	
	@Override
	public void update() {
		super.wrapAroundScreen();
		super.doMovement();
		
		// destroy bullet if distance is greater than 50.
		if(distance > 50)
			Cosmos.removeObject(this);
		distance++;
	}
}

class CosmosGameObject extends GameObject {
	private double xStep, yStep, speed;
	
	public void doMovement() {
		super.setX(super.getX() + this.speed * this.xStep);
		super.setY(super.getY() + this.speed * this.yStep);
	}
	
	public void wrapAroundScreen() {
		if(this.getX() + this.getCenterX() < 0)
			this.setX(Asteroids.GAME_WIDTH + this.getCenterX());
		else if(this.getX() - this.getCenterX() > Asteroids.GAME_WIDTH)
			this.setX(-this.getCenterX());
		else if(this.getY() + this.getCenterY() < 0)
			this.setY(Asteroids.GAME_HEIGHT + this.getCenterY());
		else if(this.getY() - this.getCenterY() > Asteroids.GAME_HEIGHT)
			this.setY(-this.getCenterY());
	}
	
	public void setXStep(double xStep) {
		this.xStep = xStep;
	}
	
	public void setYStep(double yStep) {
		this.yStep = yStep;
	}
	
	public void setSpeed(double speed) {
		this.speed = speed;
	}
	
	public double getSpeed() {
		return this.speed;
	}
}
