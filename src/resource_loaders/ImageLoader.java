package resource_loaders;


import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * The {@code ImageLoader} class loads image.
 * @author Daniel
 */
public class ImageLoader {
	/**
	 * Returns an image given a path.
	 * 
	 * @param path The path of the image file.
	 * @return The image as a BufferedImage.
	 * @throws RunTimeException If the path argument is not valid.
	 */
	public static BufferedImage loadImage(String path) {
		RuntimeException runtimeException = new RuntimeException("Please fix image's path. \"" + path + "\" yields no results.");
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
