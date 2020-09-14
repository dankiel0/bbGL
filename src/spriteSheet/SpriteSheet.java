package spriteSheet;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public final class SpriteSheet {
	private BufferedImage spriteSheet;
	private BufferedImage[] sprites;
	private double currentSpriteIndex;
	
	public SpriteSheet(BufferedImage spriteSheet, int spritesAmount) {
		this.spriteSheet = spriteSheet;
		sprites = new BufferedImage[spritesAmount];
		
		
	}
	
	public void cut(int x, int y, int width, int height) {
		sprites[(int) currentSpriteIndex] = spriteSheet.getSubimage(x, y, width, height);
		currentSpriteIndex++;
	}
	
	public void setCurrentSprite(double current) {
		currentSpriteIndex = current;
	}
	
	private BufferedImage getCurrentSprite() {
		return sprites[(int) currentSpriteIndex];
	}
	
	public void render(Graphics2D graphics, double x, double y, double width, double height) {
		graphics.drawImage(getCurrentSprite() == null ? spriteSheet : getCurrentSprite(), (int) x, (int) y, (int) width, (int) height, null);
	}
}
