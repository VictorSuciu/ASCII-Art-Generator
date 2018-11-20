package victor;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ArtGenerator {
	
	static File imageFile;
	static BufferedImage image;
	//static char[] charPixels = {' ', '\'', '.', '"', '+', ':', '-', '=', '+', '{', '?', '*', '$', '&', '#', '%', '@'};

	static char[] charPixels = {' ', '.', ':', '-', '=', '+', '*', '#', '%', '@'};
	//static char[] charPixels = {(char)0x2591, (char)0x2592, (char)0x2593};
	
	static boolean negative = true;
	
	
	public static void main(String[] args) throws IOException {
		
		String filePath = "/Users/Victor/Watermlone.jpg";
		
		
		try {
			Fonts.initIcons();
		} catch (FontFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//new GUI();
		new SettingsGUI();
		//ImageToASCII.generate(filePath);
	}
}
