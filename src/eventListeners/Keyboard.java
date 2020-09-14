package eventListeners;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * The class {@code Keyboard} holds information about key events, like presses of buttons.
 * @author Daniel
 */
public class Keyboard extends KeyAdapter {
	/**
	 * This holds information about keys.
	 */
	public static boolean[] keys;
	
	/**
	 * There are 255 keys.
	 */
	public Keyboard() {
		keys = new boolean[256];
	}
	
	@Override
	public void keyPressed(KeyEvent event) {
		if(event.getKeyCode() > keys.length)
			return;
		
		keys[event.getKeyCode()] = true;
	}
	
	@Override
	public void keyReleased(KeyEvent event) {
		if(event.getKeyCode() > keys.length)
			return;
		
		keys[event.getKeyCode()] = false;
	}
}
