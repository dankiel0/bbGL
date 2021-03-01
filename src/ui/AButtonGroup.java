package ui;

package userInterface;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import eventListeners.Keyboard;

public class AButtonGroup {
	private AButton[] buttons;
	
	private int selectedButtonIndex;
	
	public ButtonGroup(AButton... buttons) {
		this.buttons = buttons;
	}
	
	public void update() {
		if(Keyboard.keyClicked(KeyEvent.VK_UP))
			selectedButtonIndex--;
		if(Keyboard.keyClicked(KeyEvent.VK_DOWN))
			selectedButtonIndex++;
		
		if(selectedButtonIndex < 0)
			selectedButtonIndex = 0;
		if(selectedButtonIndex > buttons.length - 1)
			selectedButtonIndex = buttons.length - 1;
		
		if(Keyboard.keyClicked(KeyEvent.VK_X) && buttons[selectedButtonIndex].onClick != null)
			buttons[selectedButtonIndex].onClick.doSomething();
	}
	
	public void render(Graphics2D graphics) {
		for(int i = 0; i < buttons.length; i++) {
			if(i == selectedButtonIndex)
				graphics.setColor(Color.RED);
			else
				graphics.setColor(Color.BLACK);
			
			buttons[i].render(graphics);
		}
	}
}
