package victor;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageToASCII {
	
	static File imageFile;
	static BufferedImage image;
	static BufferedImage scaledImage;
	//static char[] charPixels = {' ', '\'', '.', '"', '+', ':', '-', '=', '+', '{', '?', '*', '$', '&', '#', '%', '@'};

	static char[] charPixels = {' ', '\'', '.', '"', ':', '+', ':', '-', '=', '+', '{', '?', '/', '*', '$', '&', '#', '%', '@'};
	//static char[] charPixels = {' ', '.', '\'', '`', '^', '"', ',', ':', 'I', 'l', '!', '<', '~', '+', '_', '-', '?', '[', '{', '1', '(', '|', '\\', '/', 'X', 'Y', 'U', 'J', 'C', 'L', 'Q', '0', 'O', 'Z', '*', '#', 'M', 'W', '&', '8', '%', '@', '$'};

	static String art;
	static boolean negative;
	static int imageWidth;
	static int imageHeight;
	public static void generate(File imageFile, int maxChars, boolean neg) {
		System.out.println("MC");
		//maxChars /= 2;
		negative = neg;
		art = "";
		try {
			image = ImageIO.read(imageFile);
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		imageWidth = (int)(Math.sqrt((double)(maxChars * image.getWidth()) / (image.getHeight() * 3.0)));
		imageHeight = (int)(Math.sqrt((double)(maxChars * image.getHeight()) / (image.getWidth() * 3.0)));
		outputArt();
	}
	public static void generate(File imageFile, int width, int height, boolean neg) {
		System.out.println("WH");
        imageWidth = width;
        imageHeight = height;
		negative = neg;
		art = "";

		try {
			image = ImageIO.read(imageFile);

			
		} catch (IOException e) {
			// TODO Auto-generated catch block
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
		generateArtString(imageWidth);
		System.out.println("TOTAL CHARACTER COUNT = " + art.length());
		GUI.setArt(art);

	}
	public static void generateArtString(int width) {
		int pixelSize = scaledImage.getWidth() / width;
		double truePixelSize = (double)scaledImage.getWidth() / (double)width;
		char pixel = ' ';
		
		int pixelSizeCounterX = 0;
		double truePixelSizeCounterX = 0.0;
		int pixelSizeCounterY = 0;
		double truePixelSizeCounterY = 0.0;
		int tempPixel = 0;
		int avgLightValue = 0;
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
				//avgLightValue /= (3);
				if(negative == false) {
					pixel = charPixels[(charPixels.length - 1) - (avgLightValue / ((255 / charPixels.length) + 1))];
				}
				else {
					pixel = charPixels[avgLightValue / ((255 / charPixels.length) + 1)];
				}

				/*
				if(truePixelSizeCounterY - (double)pixelSizeCounterY >= 1.0) {
					if(truePixelSizeCounterX - (double)pixelSizeCounterX >= 1.0) {
						pixel = getPixel(pixelSizeCounterX, y * pixelSize, pixelSize + 1, pixelSize + 1);
						pixelSizeCounterX++;
						pixelSizeCounterY++;
					}
					else {
						pixel = getPixel(pixelSizeCounterX, y * pixelSize, pixelSize, pixelSize + 1);
						pixelSizeCounterY++;
					}
				}
				else {
					if(truePixelSizeCounterX - (double)pixelSizeCounterX >= 1.0) {
						pixel = getPixel(pixelSizeCounterX, y * pixelSize, pixelSize + 1, pixelSize);
						pixelSizeCounterX++;
					}
					else {
						pixel = getPixel(pixelSizeCounterX, y * pixelSize, pixelSize, pixelSize);
					}
				}
				*/
				//pixelSizeCounterX += pixelSize;
				//truePixelSizeCounterX += truePixelSize;
				//System.out.print(pixel);
				//System.out.print(pixel);

				art += pixel + "" + pixel + "" + pixel;

			}
			//System.out.println(art.length());
			art += "\n";
			//System.out.println();
			pixelSizeCounterX = 0;
			truePixelSizeCounterX = 0.0;
			
			//pixelSizeCounterY += pixelSize;
			//truePixelSizeCounterY += truePixelSize;
		}
		
		//System.out.println(art);
	}
	public static char getPixel(int imgX, int imgY, int pixelSizeX, int pixelSizeY) {
		//System.out.println("GETTING PIXEL...");
		int avgLightValue = 0;
		int tempPixel = 0;
		
		for(int y = imgY; y < imgY + pixelSizeY; y++) {
			for(int x = imgX; x < imgX + pixelSizeX; x++) {
				
				tempPixel = image.getRGB(x,  y);
				if(tempPixel >> 24 == 0) {
					avgLightValue = negative ? 0 : 255;
				}
				else {
					avgLightValue += (((tempPixel >> 16) & 0xFF) +
									 ((tempPixel >> 8) & 0xFF) +
									 ((tempPixel) & 0xFF)) / 3;
				}
			}
		}
		avgLightValue /= (pixelSizeX * pixelSizeY);
		if(negative == false) {
			return charPixels[(charPixels.length - 1) - (avgLightValue / ((255 / charPixels.length) + 1))];
		}
		return charPixels[avgLightValue / ((255 / charPixels.length) + 1)];
	}
}
