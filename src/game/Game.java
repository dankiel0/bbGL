package game;

import java.awt.Graphics2D;

import eventListeners.Keyboard;
import eventListeners.Mouse;
import room.RoomManager;
import window.Window;

/**
 * The class {@code Game} is meant for creating a simple 2d video game.
 * @author Daniel
 */
public final class Game implements Runnable {
	private Thread thread;
	private boolean running;
	private static Window window;
	
	/**
	 * Constructor for class {@code Game}.
	 * @param window The window that the game resides in.
	 */
	public Game(Window window) {
		Game.window = window;
	}
	
	/**
	 * Adds event handlers to the game (Key & Mouse).
	 */
	private void init() {
		Mouse mouse = new Mouse();
		
		window.getJFrame().addKeyListener(new Keyboard());
		window.getCanvas().addMouseListener(mouse);
		window.getCanvas().addMouseMotionListener(mouse);
	}
	
	/**
	 * Updates the active room.
	 */
	private void update(double elapsedTime) {
		RoomManager.getActiveRoom().update(elapsedTime);
	}
	
	/**
	 * Renders the active room.
	 */
	private void render(double elapsedTime) {
		// creates graphics object.
		Graphics2D graphics = (Graphics2D) window.getCanvas().getBufferStrategy().getDrawGraphics();
		
		graphics.clearRect(0, 0, window.getWidth(), window.getHeight());
		
		RoomManager.getActiveRoom().render(graphics, elapsedTime);
		
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
	 * This is the game loop. It runs as fast as possible.
	 * 
	 * TODO: !!!FIGURE OUT A WAY TO RUN ON 60 FPS!!!
	 */
	@Override
	public void run() {
		init();
		
		double timer = 1;
		int frames = 0;
		
		long current, previous = System.nanoTime();
		
		while(running) {
			current = System.nanoTime();
			double elapsedTime = (current - previous) / 1_000_000_000.0;
			previous = current;
			
			update(elapsedTime);
			render(elapsedTime);
			
			timer += elapsedTime;
			frames++;
			
			if(timer >= 1) {
				timer--;
				
				frames = 0;
			}
			
			Mouse.pressed = true;
		}
	}
	
	/**
	 * @return The window that the game resides in.
	 */
	public static Window getWindow() {
		return window;
	}
}

/*
 * fps
 * buffers
 * thread
 * events
 * dimensions
 * game name
 * current room
 * 
 */
