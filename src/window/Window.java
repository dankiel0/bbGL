package window;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

/**
 * The class {@code Window} is meant for creating a JFrame that the game will reside in.
 * @author Daniel
 */
public final class Window {
	private JFrame window;
	private Canvas canvas;
	private Dimension windowDimension;
	
	/**
	 * Constructor for {@code Window}.
	 * Creates the Canvas, JFrame, and BufferStrategy.
	 * 
	 * @param title The title of the window.
	 * @param width The width of the window.
	 * @param height The height of the window.
	 */
	public Window(String title, int width, int height) {
		if(width < 0 || height < 0 || width > 1920 || height > 1080)
			throw new RuntimeException("Please enter valid dimensions.");
		
		windowDimension = new Dimension(width, height);
		
		canvas = new Canvas();
		canvas.setPreferredSize(windowDimension);
		canvas.setMinimumSize(windowDimension);
		canvas.setMaximumSize(windowDimension);
		canvas.setFocusable(false);
		
		window = new JFrame();
		window.getContentPane().add(canvas);
		window.setTitle(title);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.pack();
		window.setLocationRelativeTo(null);
		window.setVisible(true);
		
		BufferStrategy bufferStrategy = canvas.getBufferStrategy();
		
		if(bufferStrategy == null)
			canvas.createBufferStrategy(3);
	}
	
	/**
	 * @return The JFrame.
	 */
	public JFrame getJFrame() {
		return window;
	}
	
	/**
	 * @return The window width.
	 */
	public int getWidth() {
		return windowDimension.width;
	}
	
	/**
	 * @return The window height.
	 */
	public int getHeight() {
		return windowDimension.height;
	}
	
	/**
	 * @return The canvas.
	 */
	public Canvas getCanvas() {
		return canvas;
	}
}
