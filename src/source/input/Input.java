package source.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class Input implements KeyListener, MouseListener, MouseMotionListener {
	private static boolean[] singlePressKeys, doublePressKeys;
	private static int mouseX, mouseY, mouseButton, previousMouseButton = -1;
	
	public Input() {
		Input.singlePressKeys = new boolean[255];
		Input.doublePressKeys = new boolean[255];
	}
	
	public void mouseMoved(MouseEvent event) {
		Input.mouseX = event.getX();
		Input.mouseY = event.getY();
	}
	
	public void mousePressed(MouseEvent event) {Input.mouseButton = event.getButton();}
	
	public void keyPressed(KeyEvent event) {
		if(event.getKeyCode() > Input.singlePressKeys.length)
			return;
		Input.singlePressKeys[event.getKeyCode()] = true;
		Input.doublePressKeys[event.getKeyCode()] = !Input.doublePressKeys[event.getKeyCode()];
	}
	
	public void keyReleased(KeyEvent event) {
		if(event.getKeyCode() > Input.singlePressKeys.length)
			return;
		Input.singlePressKeys[event.getKeyCode()] = false;
	}
	
	public static boolean[] getSinglePressKeys() 		{return Input.singlePressKeys;}
	public static boolean[] getDoublePressKeys() 		{return Input.doublePressKeys;}
	public static int 		getMouseX() 				{return Input.mouseX;}
	public static int 		getMouseY() 				{return Input.mouseY;}
	public static int 		getPreviousMouseButton() 	{return Input.previousMouseButton;}
	public static int getMouseButton() {
		int temp = Input.mouseButton;
		Input.previousMouseButton = Input.mouseButton;
		Input.mouseButton = -1;
		return temp;
	}
	
	public void mouseClicked(MouseEvent event) 	{}
	public void mouseDragged(MouseEvent event) 	{}
	public void mouseReleased(MouseEvent event) {}
	public void mouseEntered(MouseEvent event) 	{}
	public void mouseExited(MouseEvent event) 	{}
	public void keyTyped(KeyEvent event) 		{}
}
