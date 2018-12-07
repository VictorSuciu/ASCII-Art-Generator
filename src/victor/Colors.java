package victor;

import java.awt.*;

public class Colors {
	//NAMING KEY
	//P = Positive, N = Negavive
	//B = Background, F = Foreground

	static Color button_PF = new Color(0xd8d8d8);
	static Color artPanel_PB = new Color(0xEBEDF1);
	static Color artPanel_PF = new Color(0x000000);
	static Color artPanel_NB = new Color(0x111115);
	static Color artPanel_NF = new Color(0xEDEFF2);

	static Color fileButton_PB = new Color(0x2B2F37);
	static Color settingsButton_PB = new Color(0x3C3C3C);
	static Color negToggleButton_PB = new Color(0x464646);
	static Color zoomInButton_P = new Color(0x898989);
	static Color zoomOutButton_P = new Color(0x797979);
	static Color clipboardButton_P = new Color(0x656565);
	static Color useDefaultDimenButton_P = new Color(0x313131);
	static Color widthInput_P = new Color(0x414141);
	static Color heightInput_P = new Color(0x4F4F4F);
	static Color maxCharInput_P = new Color(0x474747);
	static Color printButton_P = new Color(0x0a9e5d);
    static Color inputError_P = new Color(0xF6402F);
	static Color controlsTag_P = new Color(0x9C9EA6);
	static Color controlsTag_N = new Color(0x4F4F57);
	
	static Color buttonHighlight_P = new Color(0x0bb774);
	static Color buttonClick_P = new Color(0x26ad6e);
	static Color printButtonHighlight_P = new Color(0x01b765);
    static Color loadingBar = new Color(0x0bb774);
    static Color loadingBarPanel = new Color(0x58595C);




	static Color tabPanel = new Color(0x435757);
	static Color settingsPanel = new Color(0xD0D4D7);
	static Color tabColor_B = new Color(0x5C6063);
	static Color tabColor_F = new Color(0xCDD2DB);
	static Color tabColorHighlight_B = new Color(0x8B8C94);
	static Color tabColorHighlight_F = new Color(0xE6EBF4);
	static Color settingLabel_F = new Color(0x6E7076);
	static Color settingInput_B = new Color(0xBBC1C8);
	static Color settingInput_F = new Color(0x52545A);
	static Color tabColorFocused_F = new Color(0x5B5D63);

	public static Color getIconColor(Color c) {
		int changeValueMax = 30;
		int changeValueMin = 0;

		int r = c.getRed();
		int g = c.getGreen();
		int b = c.getBlue();
		int avg = (r + g + b) / 3;
		int changeValue = (int)((changeValueMax - changeValueMin) * Math.cos(avg / 1024.0)) + changeValueMin;

		r += changeValue;
		g += changeValue;
		b += changeValue;

		int iconColorValue = ((b <= 255 ? b : 255) << 16) + ((g <= 255 ? g : 255) << 8) + (r <= 255 ? r : 255);

		Color iconColor = new Color(iconColorValue);

		return iconColor;
	}
}
