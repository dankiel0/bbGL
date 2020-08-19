package source;

import java.awt.Graphics2D;
import java.awt.RenderingHints;

import source.input.Input;
import source.room.RoomManager;

public class Game implements Runnable {
	private Thread thread;
	private boolean isRunning;
	private Window window;
	
	public Game(Window window) {this.window = window;}
	
	private void init() {
		Input input = new Input();
		this.window.getJFrame().addKeyListener(input);
		this.window.getCanvas().addMouseListener(input);
		this.window.getCanvas().addMouseMotionListener(input);
	}
	
	private void update() {RoomManager.getActiveRoom().update();}
	
	private void render() {
		Graphics2D graphics = (Graphics2D) this.window.getCanvas().getBufferStrategy().getDrawGraphics();
		graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		graphics.clearRect(0, 0, this.window.getWidth(), this.window.getHeight());
		RoomManager.getActiveRoom().render(graphics);
		this.window.getCanvas().getBufferStrategy().show();
		graphics.dispose();
	}
	
	public void start() {
		if(this.isRunning) return;
		this.isRunning = true;
		this.thread = new Thread(this);
		this.thread.run();
	}
	
	public void stop() {
		if(!this.isRunning) return;
		this.isRunning = false;
		try {
			this.thread.join();
		} catch (InterruptedException exception) {
			exception.printStackTrace();
		}
		System.exit(0);
	}
	
	public void run() {
		this.init();
		int fps = 60;
		double timePerTick = 1000000000.0 / fps, delta = 0;
		long now, lastTime = System.nanoTime();
		while(this.isRunning) {
			now = System.nanoTime();
			delta += (now - lastTime) / timePerTick;
			lastTime = now;
			if(delta >= 1) {
				this.update();
				this.render();
				delta--;
			}
		}
		this.stop();
	}
	
	public Window getWindow() {return this.window;}
}
