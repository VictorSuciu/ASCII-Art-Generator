package victor;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageToASCII {

	static BufferedImage image;
	static BufferedImage scaledImage;
	//static char[] charPixels = {' ', '\'', '.', '"', '+', ':', '-', '=', '+', '{', '?', '*', '$', '&', '#', '%', '@'};

	static char[] charPixels = {' ', '\'', '.', '"', ':', '+', ':', '-', '=', '+', '{', '?', '/', '*', '$', '&', '#', '%', '@'};
	//static char[] charPixels = {' ', '.', '\'', '`', '^', '"', ',', ':', 'I', 'l', '!', '<', '~', '+', '_', '-', '?', '[', '{', '1', '(', '|', '\\', '/', 'X', 'Y', 'U', 'J', 'C', 'L', 'Q', '0', 'O', 'Z', '*', '#', 'M', 'W', '&', '8', '%', '@', '$'};

	static String art;
	static boolean negative;
	static int imageWidth;
	static int imageHeight;
	static int loadingDivisor;
	static int totalPixels;
	public static void generate(File imageFile, int maxChars, boolean neg) {
		negative = neg;
		art = "";
		try {
			image = ImageIO.read(imageFile);
		}
		catch (IOException e) {
			e.printStackTrace();
		}

		imageWidth = (int)(Math.sqrt((double)(maxChars * image.getWidth()) / (image.getHeight() * 2.0)));
		imageHeight = (int)(Math.sqrt((double)(maxChars * image.getHeight()) / (image.getWidth() * 2.0)));
		outputArt();
	}
	public static void generate(File imageFile, int width, int height, boolean neg) {
        imageWidth = width;
        imageHeight = height;
		negative = neg;
		art = "";

		try {
			image = ImageIO.read(imageFile);

			
		} catch (IOException e) {
			e.printStackTrace();
		}
		if(width == 0 && height == 0) {
			return;
		}
		else if(height == 0) {
			imageHeight = (int)((double)image.getHeight() / (image.getWidth() / (double)width));
		}
		else if(width == 0) {
			imageWidth = (int)((double)image.getWidth() / (image.getHeight() / (double)height));
		}
		outputArt();
	}
	public static void outputArt() {

		System.out.println("DIMENSIONS: " + imageWidth + " " + imageHeight);
		scaledImage = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_RGB);

		Graphics2D resizePainter = scaledImage.createGraphics();
		resizePainter.drawImage(image, 0, 0, imageWidth, imageHeight, null);
		resizePainter.dispose();

		System.out.println(scaledImage.getWidth());
		System.out.println(scaledImage.getHeight());
		generateArtString();
		System.out.println("TOTAL CHARACTER COUNT = " + art.length());
		GUI.setArt(art);

	}
	public static void generateArtString() {
	    int loadingStatus = 0;
		char pixel;
		totalPixels = 0;

		int tempPixel;
		int avgLightValue;

		for(int y = 0; y < scaledImage.getHeight(); y++) {
			for(int x = 0; x < scaledImage.getWidth(); x++) {
				tempPixel = scaledImage.getRGB(x, y);
				if(tempPixel >> 24 == 0) {
					avgLightValue = negative ? 0 : 255;
				}
				else {
					avgLightValue = (((tempPixel >> 16) & 0xFF) +
							((tempPixel >> 8) & 0xFF) +
							((tempPixel) & 0xFF)) / 3;
				}
				if(negative == false) {
					pixel = charPixels[(charPixels.length - 1) - (avgLightValue / ((255 / charPixels.length) + 1))];
				}
				else {
					pixel = charPixels[avgLightValue / ((255 / charPixels.length) + 1)];
				}
                if(((x + 1) * (y * 1)) % ((imageWidth * imageHeight) / ArtGenerator.myGUI.loadingSections) == 0) {
                    //System.out.println("UPDATE LOADING BAR " + loadingStatus);
                    loadingStatus++;
                }
                totalPixels++;
				art += pixel + "" + pixel;

			}
			art += "\n";
		}
	}
}
