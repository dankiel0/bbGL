package tools;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.filechooser.FileNameExtensionFilter;

public class BoundingBoxTool {
	private BufferedImage bufferedImage;
	private List<Line2D> lines = new ArrayList<Line2D>();

	private double zoomValue = 1;
	
	public BoundingBoxTool() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException exception) {
			exception.printStackTrace();
		}
		
		JFrame window = new JFrame();
		
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileFilter(new FileNameExtensionFilter("Image files", ImageIO.getReaderFileSuffixes()));
		JButton loadImage = new JButton("Load Image");
		
		loadImage.addActionListener((ActionEvent event) -> {
			fileChooser.showOpenDialog(null);
			
			if(fileChooser.getSelectedFile() == null)
				return;
			
			try {
				bufferedImage = ImageIO.read(fileChooser.getSelectedFile());
				window.getContentPane().repaint();
			} catch (IOException exception) {
				exception.printStackTrace();
			}
		});
		
		JButton getLineCoordinates = new JButton("Get Line Coordinates");
		
		getLineCoordinates.addActionListener((ActionEvent event) -> {
			JFrame frame = new JFrame();
			
			String full = "";
			
			for(Line2D line : lines)
				full += "new Line2D.Double(new Point2D.Double(" + line.getX1() + ", " + line.getY1() + "), new Point2D.Double(" + line.getX2() + ", " + line.getY2() + ")), ";
			
			frame.getContentPane().add(new JTextArea(full));
			
			frame.setTitle("Lines");
			frame.setSize(540, 360);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setResizable(false);
			frame.setLocationRelativeTo(null);
			frame.setVisible(true);
		});
		
		JButton zoomIn = new JButton("Zoom In");
		
		zoomIn.addActionListener((ActionEvent event) -> {
			zoomValue += 0.5;
			
			window.getContentPane().repaint();
		});
		
		JButton zoomOut = new JButton("Zoom Out");
		
		zoomOut.addActionListener((ActionEvent event) -> {
			zoomValue -= 0.5;
			
			if(zoomValue < 1)
				zoomValue = 1;
			
			window.getContentPane().repaint();
		});
		
		JButton resetZoomValue = new JButton("Reset Zoom");
		
		resetZoomValue.addActionListener((ActionEvent event) -> {
			zoomValue = 1;
			
			window.getContentPane().repaint();
		});
		
		window.getContentPane().add(loadImage);
		window.getContentPane().add(getLineCoordinates);
		window.getContentPane().add(zoomIn);
		window.getContentPane().add(zoomOut);
		window.getContentPane().add(resetZoomValue);
		window.getContentPane().add(new PaintSurface());
		
		window.setTitle("Bounding Box Tool");
		window.setSize(1080, 720);
		window.setLayout(new FlowLayout());
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.setLocationRelativeTo(null);
		window.setVisible(true);
	}
	
	class PaintSurface extends JPanel {
		private static final long serialVersionUID = 1L;
		
		private Point2D point1Temp, point2Temp;
		
		public PaintSurface() {
			point1Temp = new Point2D.Double();
			point2Temp = new Point2D.Double();
			
			super.setPreferredSize(new Dimension(1050, 610));
			super.setBackground(new Color(0x4DFFAC));
			
			super.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent event) {
					if(lines.size() != 0)
						repaint();
					
					if(event.getButton() == 1) {
						point2Temp = event.getPoint();
						point1Temp = point2Temp;
						lines.add(new Line2D.Double(point1Temp, point2Temp));
					}
					
					if(event.getButton() == 3) {
						if(lines.size() > 0)
							lines.remove(lines.size() - 1);
						if(lines.size() > 0)
							point1Temp = lines.get(lines.size() - 1).getP1();
					}
				}
				
				@Override
				public void mouseExited(MouseEvent event) {
					if(lines.size() > 0)
						lines.remove(lines.size() - 1);
					repaint();
				}
			});
			
			super.addMouseMotionListener(new MouseMotionAdapter() {
				@Override
				public void mouseMoved(MouseEvent event) {
					if(lines.size() > 0) {
						lines.remove(lines.size() - 1);
						lines.add(new Line2D.Double(point1Temp, event.getPoint()));
					}
					repaint();
				}
			});
		}
		
		@Override
		public void paintComponent(Graphics g) {
			Graphics2D graphics = (Graphics2D) g;
			graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			super.paintComponent(graphics);
			
			if(bufferedImage != null)
				graphics.drawImage(bufferedImage, (int) (525 - bufferedImage.getWidth() * zoomValue / 2), (int) (305 - bufferedImage.getHeight() * zoomValue / 2), (int) (bufferedImage.getWidth() * zoomValue), (int) (bufferedImage.getHeight() * zoomValue), null);
			
			for(Line2D l : lines) {
				l.getP1().setLocation(l.getP1().getX() * zoomValue, l.getP1().getY() * zoomValue);
				l.getP2().setLocation(l.getP2().getX() * zoomValue, l.getP2().getY() * zoomValue);
//				l.setLine(l.getX1() * zoomValue, l.getY1() * zoomValue, l.getX2() * zoomValue, l.getY2() * zoomValue);
			}
			
			lines.forEach(line -> graphics.draw(line));
		}
	}
	
	public static void main(String[] args) {
		new BoundingBoxTool();
	}
}
