package source.gameObject;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class GameObject {
	private double x, y, centerX, centerY, rotation, current, delay;
	private List<BufferedImage> individualSprites;
	private boolean isAnimation;
	private int width, height;
	private String name = "";
	
	public void	setName(String name) {
		this.name = name;
	}
	
	public String getName()	{
		return this.name;
	}
	
	public void	setX(double x) {
		this.x = x;
	}
	
	public double getX() {
		return this.x;
	}
	
	public void	setY(double y) {
		this.y = y;
	}
	
	public double getY() {
		return this.y;
	}
	
	public void	setCenterX(double centerX) {
		this.centerX = centerX;
	}
	
	public double getCenterX() {
		return this.centerX;
	}
	
	public void	setCenterY(double centerY) {
		this.centerY = centerY;
	}
	
	public double getCenterY() {
		return this.centerY;
	}
	
	public void setRotation(double rotation) {
		this.rotation = rotation % (2 * Math.PI);
	}
	
	public double getRotation()	{
		return this.rotation;
	}
	
	public BufferedImage getCurrentSprite()	{
		return this.individualSprites.get((int) this.current);
	}
	
	public void	setCurrentSprite(int current) {
		this.current = current;
	}
	
	public int getWidth() {
		return this.width;
	}
	
	public int getHeight() {
		return this.height;
	}
	
	public void update() {}
	
	public void render(Graphics2D graphics) {
		AffineTransform oldTransform = graphics.getTransform();
		graphics.rotate(-this.rotation + Math.PI / 2, this.x, this.y);
		graphics.drawImage(getCurrentSprite(), (int) (this.x - this.centerX), (int) (this.y- this.centerY), null);
		graphics.setTransform(oldTransform);
	}
	
	public void setTransform(double x, double y, double centerX, double centerY, double rotation) {
		this.x = x;
		this.y = y;
		this.centerX = centerX;
		this.centerY = centerY;
		this.rotation = rotation;
	}
	
	public void setSpriteSheet(BufferedImage spriteSheet, int width, int height, int current) {
		this.individualSprites = new ArrayList<BufferedImage>();
		for(int i = 0; i < spriteSheet.getWidth() / width; i++)
			for(int j = 0; j < spriteSheet.getHeight() / height; j++)
				individualSprites.add(spriteSheet.getSubimage(i * width, j * height, width, height));
		this.current = current;
		
		this.width = width;
		this.height = height;
	}
	
	public void initAnimation(double delay) {
		if(this.isAnimation) return;
		this.isAnimation = true;
		this.delay = delay;
	}
	
	public void doAnimation() {
		if(!this.isAnimation) return;
		this.current += (1.0 / this.delay);
		if((int) current >= this.individualSprites.size()) this.current = 0;
	}
	
	public BufferedImage getLastSprite() {
		return this.individualSprites.get(this.individualSprites.size() - 1);
	}
	
	public boolean collidesWith(GameObject gameObject) {
		return this.x < gameObject.x + gameObject.width && this.x + this.width > gameObject.x && this.y < gameObject.y + gameObject.height && this.y + this.height > gameObject.y;
	}
}
