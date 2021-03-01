package game;

import java.awt.Graphics2D;

import eventListeners.Keyboard;
import eventListeners.Mouse;
import room.RoomManager;
import ui.Window;

/**
 * The class {@code Game} is meant for creating a simple 2d video game.
 * @author Daniel
 */
public final class Game implements Runnable {
	private Thread thread;
	private boolean running;
	
	private Window window;
	
	/**
	 * Constructor for class {@code Game}.
	 * @param window The window that the game resides in.
	 */
	public Game(Window window) {
		this.window = window;
	}
	
	/**
	 * Adds event handlers to the game (Key & Mouse).
	 */
	private void init() {
		Mouse mouse = new Mouse();
		Keyboard keyboard = new Keyboard();
		
		window.getJFrame().addKeyListener(keyboard);
		window.getCanvas().addMouseListener(mouse);
		window.getCanvas().addMouseMotionListener(mouse);
	}
	
	/**
	 * Updates the active room and resets input.
	 */
	private void update() {
		RoomManager.getRoom().update();
		
		Mouse.pressed = false;
		
		for(int i = 0; i < Keyboard.pressedKeys.length; i++)
			Keyboard.pressedKeys[i] = false;
	}
	
	/**
	 * Renders the active room.
	 */
	private void render() {
		// creates graphics object.
		Graphics2D graphics = (Graphics2D) window.getCanvas().getBufferStrategy().getDrawGraphics();
		
		graphics.clearRect(0, 0, window.getWidth(), window.getHeight());
		
		RoomManager.getRoom().render(graphics);
		
		window.getCanvas().getBufferStrategy().show();
		
		graphics.dispose();
	}
	
	/**
	 * Starts the game loop.
	 * Initializes the thread and calls its start method.
	 */
	public void start() {
		if(running)
			return;
		
		running = true;
		thread = new Thread(this);
		thread.start();
	}
	
	/**
	 * Stops the game loop.
	 * Joins the thread and terminates the currently running JVM.
	 */
	public void stop() {
		if(!running)
			return;
		
		running = false;
		
		try {
			thread.join();
		} catch(InterruptedException exception) {
			exception.printStackTrace();
		}
		
		System.exit(0);
	}
	
	/**
	 * Implements the run method from {@code Runnable}.
	 * This is the game loop.
	 */
	@Override
	public void run() {
		init();
		
		double start, previous = System.nanoTime() / 1_000_000_000.0;
		double deltaTime, realTime = 0;
		
		boolean render;
		
		while(running) {
			start = System.nanoTime() / 1_000_000_000.0;
			deltaTime = start - previous;
			previous = start;
			
			realTime += deltaTime;
			
			render = false;
			
			while(realTime >= 1.0 / 60 && realTime != 0) {
				realTime -= 1.0 / 60;
				update();
				render = true;
			}
			
			if(render)
				render();
			else
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
				}
		}
	}
	
	public Window getWindow() {
		return window;
	}
}
