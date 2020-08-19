package source;


import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

public class Window {
	private JFrame window;
	private Canvas canvas;
	private Dimension windowDimension;
	
	public Window(String title, int width, int height) {
		windowDimension = new Dimension(width, height);
		this.canvas = new Canvas();
		this.canvas.setPreferredSize(windowDimension);
		this.canvas.setMinimumSize(windowDimension);
		this.canvas.setMaximumSize(windowDimension);
		this.canvas.setFocusable(false);
		
		this.window = new JFrame();
		this.window.getContentPane().add(this.canvas);
		this.window.setTitle(title);
		this.window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.window.setResizable(false);
		this.window.pack();
		this.window.setLocationRelativeTo(null);
		
		this.window.setVisible(true);
		
		BufferStrategy bufferStrategy = this.canvas.getBufferStrategy();
		if(bufferStrategy == null) this.canvas.createBufferStrategy(3);
	}
	
	public JFrame	getJFrame()	{return this.window;}
	public String	getTitle()	{return this.window.getTitle();}
	public int		getWidth()	{return this.windowDimension.width;}
	public int		getHeight()	{return this.windowDimension.height;}
	public Canvas	getCanvas()	{return this.canvas;}
}
