package eventListeners;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

/**
 * The class {@code Mouse} holds information about mouse events, like presses of buttons.
 * @author Daniel
 */
public class Mouse extends MouseAdapter {
	/**
	 * The position of the mouse.
	 */
	public static int x, y;
	
	/**
	 * Is the mouse pressed.
	 */
	public static boolean pressed;
	
	/**
	 * Is the mouse held.
	 */
	public static boolean held;
	
	/**
	 * Is the mouse hovered over the window.
	 */
	public static boolean focused;
	
	/**
	 * Is the mouse being dragged.
	 */
	public static boolean dragging;
	
	// no mouse wheel support.
	@Override
	public void mouseWheelMoved(MouseWheelEvent event) {}
	
	@Override
	public void mouseDragged(MouseEvent event) {
		x = event.getX();
		y = event.getY();
		dragging = true;
	}
	
	@Override
	public void mouseMoved(MouseEvent event) {
		x = event.getX();
		y = event.getY();
	}
	
	@Override
	public void mouseEntered(MouseEvent event) {
		focused = true;
	}
	
	@Override
	public void mouseExited(MouseEvent event) {
		focused = false;
	}
	
	@Override
	public void mousePressed(MouseEvent event) {
		held = true;
	}
	
	@Override
	public void mouseReleased(MouseEvent event) {
		held = false;
		dragging = false;
		pressed = true;
	}
}
