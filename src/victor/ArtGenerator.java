package victor;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ArtGenerator {

	static GUI myGUI;
	
	public static void main(String[] args) throws IOException {
		try {
			Fonts.initIcons();
		} catch (FontFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		myGUI = new GUI();
	}
}
