package spriteSheet;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public final class Spritesheet {
	private int spriteSheetWidth, spriteSheetHeight, spriteWidth, spriteHeight;
	private BufferedImage[] sprites;
	private double spriteIndex;
	
	private boolean animationPlaying;
	
	public Spritesheet(BufferedImage spriteSheet, int spriteWidth, int spriteHeight) {
		this.spriteWidth = spriteWidth;
		this.spriteHeight = spriteHeight;
		
		final int amountOfSprites = (spriteSheet.getWidth() / spriteWidth) * (spriteSheet.getHeight() / spriteHeight);
		
		spriteSheetWidth = spriteSheet.getWidth();
		spriteSheetHeight = spriteSheet.getHeight();
		
		sprites = new BufferedImage[amountOfSprites];
		
		cutUpSpritesheet(spriteSheet);
	}
	
	public void cutUpSpritesheet(BufferedImage spriteSheet) {
		for(int y = 0; y < spriteSheetHeight; y += spriteHeight)
			for(int x = 0; x < spriteSheetWidth; x += spriteWidth) {
				sprites[(int) spriteIndex] = spriteSheet.getSubimage(x, y, spriteWidth, spriteHeight);
				spriteIndex++;
			}
		
		spriteIndex = 0;
	}
	
	public void doAnimation(int start, int frames, double delay) {
		spriteIndex += 1 / delay;
		
		if(spriteIndex >= frames)
			spriteIndex = start;
		
		if(animationPlaying)
			return;
		
		spriteIndex = start;
		
		animationPlaying = true;
	}
	
	public void setSprite(double current) {
		animationPlaying = false;
		
		if(current != spriteIndex)
			spriteIndex = current;
	}
	
	public int getSpriteSheetWidth() {
		return spriteSheetWidth;
	}
	
	public int getSpriteSheetHeight() {
		return spriteSheetHeight;
	}
	
	public int getSpriteWidth() {
		return spriteWidth;
	}
	
	public int getSpriteHeight() {
		return spriteHeight;
	}
	
	public void render(Graphics2D graphics, double x, double y, int width, int height) {
		graphics.drawImage(sprites[(int) spriteIndex], (int) x, (int) y, width, height, null);
	}
}
