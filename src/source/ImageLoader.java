package source;


import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageLoader {
	/**
	 * Returns an image given a path.
	 * 
	 * @param path the path of the image file.
	 * @return the image as a buffered image.
	 * @throws RunTimeException if the index argument is not valid.
	 */
	public static BufferedImage loadImage(String path) {
		RuntimeException runtimeException = new RuntimeException("Please fix path argument. \"" + path + "\" yields no results.");
		BufferedImage image;
		
		if(ImageLoader.class.getClassLoader().getResourceAsStream(path) == null)
			throw runtimeException;
		
		try {
			image = ImageIO.read(ImageLoader.class.getClassLoader().getResourceAsStream(path));
		} catch (IOException exception) {
			throw runtimeException;
		}
		
		if(image == null)
			throw runtimeException;
		
		return image;
	}
}
