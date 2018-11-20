package victor;

import java.awt.*;
import java.io.IOException;

public class Fonts {
	static String artFontString = "Andale Mono";
	static Font ButtonText = new Font("Hiragino Sans", Font.CENTER_BASELINE, 18);
    static Font filePathText = new Font("Hiragino Sans", Font.CENTER_BASELINE, 22);
    static Font tagText = new Font("Hiragino Sans", Font.CENTER_BASELINE, 16);
	static Font artFont = new Font(artFontString, Font.BOLD, 4);



	static Font tabText = new Font("Hiragino Sans", Font.CENTER_BASELINE, 24);
	static Font settingLabelText = new Font("Hiragino Sans", Font.CENTER_BASELINE, 20);

	static Font iconSet;
	
	static void initIcons() throws FontFormatException, IOException{
//		//InputStream is = Fonts.class.getResourceAsStream("/Resources/AsciiAppIconSet.ttf");
//		iconSet = Font.createFont(Font.TRUETYPE_FONT, new File("./Resources/AsciiAppIconSet.ttf"));
//		iconSet.deriveFont(Font.PLAIN, 5);
//		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
//		ge.registerFont(iconSet);
//
//		//iconSet = new Font("AsciiAppIconSet-Mac", Font.CENTER_BASELINE, 10);
	}
	//TESTING INTELLIJ COMMIT #2
}
