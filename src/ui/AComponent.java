package ui;

import java.awt.FontMetrics;
import java.awt.Graphics2D;

public class AComponent {
	// coordinates of the component.
	protected int x, y;
	
	// dimensions of the component.
	protected int width, height;
	
	// the component text.
	private String componentText;
	
	public AComponent(String text) {
		componentText = text;
	}
	
	public void update() {}
	
	public void render(Graphics2D graphics) {
	    // Get the FontMetrics
	    FontMetrics metrics = graphics.getFontMetrics();
	    // Determine the X coordinate for the text
	    int centeredX = x + (width - metrics.stringWidth(componentText)) / 2;
	    // Determine the Y coordinate for the text (note we add the ascent, as in java 2d 0 is top of the screen)
	    int centeredY = y + ((height - metrics.getHeight()) / 2) + metrics.getAscent();
	    // Draw the String
	    graphics.drawString(componentText, centeredX, centeredY);
	}
}
