package room;

import java.awt.Graphics2D;

/**
 * This is where game objects reside in.
 * @author Daniel
 */
public abstract class Room {
	public abstract void init();
	public abstract void update(double elapsedTime);
	public abstract void render(Graphics2D graphics, double elapsedTime);
}
