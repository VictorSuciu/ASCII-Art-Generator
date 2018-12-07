package victor;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.datatransfer.*;
import java.awt.event.*;
import java.io.File;

public class GUI implements ComponentListener, MouseListener {
	
	int smallButtonPadding;
	private int width;
	private int height;
    private int maxChars;
	private int tagBorderThickness;
	private int inputBorderThickness;
	public int loadingSections;
	private double loadingBarPercent;
	private int loadingBarHeight;
	boolean mouseDown;
	boolean negative;
	boolean useWidthHeight;

	private static JFrame frame;
	private SettingsGUI settingsWindow;
	private JPanel mainPanel;
	private JLayeredPane layeredArtPane;
	private JPanel controlPanel;
	private JPanel artPanel;
	private JPanel allButFilePanel;
    private JPanel loadingPanel;
	private JLabel fileButton;
	private JLabel negToggleButton;
	private JLabel settingsButton;
	private JLabel zoomInButton;
	private JLabel zoomOutButton;
	private JLabel clipboardButton;
	private JLabel useDefaultDimenButton;
	private JLabel printButton;
	private JLabel controlsTag;
	private JLabel loadingBar;
	private JLabel loadingPercentDisplay;

	private JTextField widthInput;
	private JTextField heightInput;
	private JTextField maxCharInput;
	private static JTextArea artViewer;
	private JScrollPane artScrollPane;
	private JFileChooser imageChooser;
	private File imageFile;
	private Border inputHilight;
	private Border widthInputBorder;
	private Border heightInputBorder;
	private Border maxCharInputBorder;
	private Border inputErrorHilight;
	private Border tagBorder;

	private Dimension loadingPanelDimen;
	private Dimension loadingBarDimen;
	private Clipboard myClipboard;
	private StringSelection artCopyPasteItem;
	private int minGuiWidth;
	public Colors colors;
	
	ImageIcon settingsImg;
	
	@SuppressWarnings("deprecation")
	public GUI() {
		negative = false;
		useWidthHeight = true;
		smallButtonPadding = 17;
		width = 100;
		height = -1;
		mouseDown = false;
		minGuiWidth = 150;
		inputBorderThickness = 3;
		tagBorderThickness = 5;
		loadingSections = 50;
		loadingBarPercent = 0.0;
		loadingBarHeight = 10;

		loadingPanelDimen = new Dimension(0, 0);

		frame = new JFrame("ASCII Art Generator");
		frame.setBounds(0, 0, 800, 600);
		
		mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));


		allButFilePanel = new JPanel();
		allButFilePanel.setLayout(null);

		layeredArtPane = new JLayeredPane();
        layeredArtPane.setLayout(null);
        layeredArtPane.setBackground(Color.BLACK);

		fileButton = new JLabel("Choose a File", JLabel.CENTER);
		fileButton.setMinimumSize(new Dimension(0, GuiConstants.fileButtonHeight));
		fileButton.setMaximumSize(new Dimension(1000000, GuiConstants.fileButtonHeight));
		fileButton.setPreferredSize(new Dimension(800, GuiConstants.fileButtonHeight));
		fileButton.setBackground(Colors.fileButton_PB);
		fileButton.setForeground(Colors.button_PF);
		fileButton.setFont(Fonts.filePathText);
		fileButton.setOpaque(true);
		fileButton.addMouseListener(this);

		imageChooser = new JFileChooser();

		controlPanel = new JPanel();
		controlPanel.setLayout(null);

		//controlPanel.setBackground(Colors.printButton_P);
		controlPanel.setBounds(0, GuiConstants.fileButtonHeight,
				frame.getWidth() / GuiConstants.controlPanelWR, 
				frame.getHeight());
		widthInputBorder = BorderFactory.createLineBorder(Colors.widthInput_P, inputBorderThickness);
		heightInputBorder = BorderFactory.createLineBorder(Colors.heightInput_P, inputBorderThickness);
		maxCharInputBorder = BorderFactory.createLineBorder(Colors.maxCharInput_P, inputBorderThickness);
		inputHilight = BorderFactory.createLineBorder(Colors.buttonHighlight_P, inputBorderThickness);
		inputErrorHilight = BorderFactory.createLineBorder(Colors.inputError_P, inputBorderThickness);
		tagBorder = BorderFactory.createLineBorder(Colors.controlsTag_P, tagBorderThickness);

		controlPanel.addMouseListener(this);
		buildControlPanel();
		controlPanel.addComponentListener(this);

		artPanel = new JPanel();
		artPanel.setLayout(new BoxLayout(artPanel, BoxLayout.LINE_AXIS));
		generateArtPanel();
		artPanel.setBackground(Colors.artPanel_PB);
		artPanel.addMouseListener(this);

        layeredArtPane.setBounds(controlPanel.getWidth(), 0,
                frame.getWidth() - controlPanel.getWidth(),
                frame.getHeight() - frame.getInsets().top - GuiConstants.fileButtonHeight);
        layeredArtPane.add(artScrollPane, new Integer(0));

		allButFilePanel.add(controlPanel);
		allButFilePanel.add(layeredArtPane);
		mainPanel.add(fileButton);
		mainPanel.add(allButFilePanel);
		//mainPanel.add(loadingPanel);
        //mainPanel.add(controlsTag);

		frame.addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent e) {
				if(frame.getWidth() < (GuiConstants.minControlPanelWidth * 2)) {
					
					frame.setSize(GuiConstants.minControlPanelWidth * 2, frame.getHeight());
				}
				if(frame.getHeight() < GuiConstants.minControlPanelHeight + GuiConstants.fileButtonHeight) {
					
					frame.setSize(frame.getWidth(), GuiConstants.minControlPanelHeight + GuiConstants.fileButtonHeight);
				}
				if(allButFilePanel.getWidth() > (GuiConstants.maxControlPanelWidth * GuiConstants.controlPanelWR)) {
					controlPanel.setBounds(0, 0, 
							GuiConstants.maxControlPanelWidth,
							allButFilePanel.getHeight());
				}
				else if(allButFilePanel.getWidth() < (GuiConstants.minControlPanelWidth * GuiConstants.controlPanelWR)) {
					controlPanel.setBounds(0, 0, 
							GuiConstants.minControlPanelWidth,
							allButFilePanel.getHeight());
				}
				else {
					controlPanel.setBounds(0, 0, 
							frame.getWidth() / GuiConstants.controlPanelWR,
							allButFilePanel.getHeight());
				}
                layeredArtPane.setBounds(controlPanel.getWidth(), 0,
                        frame.getWidth() - controlPanel.getWidth(),
                        frame.getHeight() - frame.getInsets().top - GuiConstants.fileButtonHeight);
				artScrollPane.setBounds(0, 0,
						frame.getWidth() - controlPanel.getWidth(),
						frame.getHeight() - frame.getInsets().top - GuiConstants.fileButtonHeight);
				//artViewer.setSize(artScrollPane.getWidth(), artScrollPane.getHeight());
			}
		});
		frame.setContentPane(mainPanel);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		artPanel.requestFocus();
		//printButton.setSize(printButton.getWidth(), printButton.getHeight() + frame.getInsets().top);
		//artScrollPane.setSize(artScrollPane.getWidth(), artScrollPane.getHeight() - frame.getInsets().top);
		//showLoadingBar(true);
	}
	public static JFrame getFrame() {
		return frame;
	}
	
	public void buildControlPanel() {

		settingsButton = new JLabel("", JLabel.CENTER);
		settingsButton.setBounds(0, 0, 
				controlPanel.getWidth() / GuiConstants.settingsButtonWR, 
				controlPanel.getHeight() / GuiConstants.controlSet1HR);

		settingsButton.setFont(Fonts.iconSet);
		settingsButton.setBackground(Colors.settingsButton_PB);
		settingsButton.setForeground(Colors.getIconColor(Colors.settingsButton_PB));

		settingsButton.setOpaque(true);
		settingsButton.addMouseListener(this);

		negToggleButton = new JLabel((char)0x25D0 + "", JLabel.CENTER);
		negToggleButton.setBounds(settingsButton.getWidth(), 0, 
				controlPanel.getWidth() - settingsButton.getWidth(), 
				controlPanel.getHeight() / GuiConstants.controlSet1HR);
		negToggleButton.setBackground(Colors.negToggleButton_PB);
		negToggleButton.setForeground(Colors.getIconColor(Colors.negToggleButton_PB));
		negToggleButton.setFont(new Font("Arial", Font.CENTER_BASELINE, 42));
		negToggleButton.setVerticalAlignment(SwingConstants.CENTER);
		negToggleButton.setOpaque(true);
		negToggleButton.addMouseListener(this);
		
		zoomInButton = new JLabel("+", JLabel.CENTER);
		zoomInButton.setBackground(Colors.zoomInButton_P);
		zoomInButton.setForeground(Colors.getIconColor(Colors.zoomInButton_P));
		zoomInButton.setFont(new Font("Heiti SC", Font.CENTER_BASELINE, 62));
		zoomInButton.setOpaque(true);
		zoomInButton.setBounds(0, settingsButton.getHeight(), 
				(controlPanel.getWidth() / GuiConstants.clipboardButtonWR) * GuiConstants.zoomButtonWR, 
				(controlPanel.getHeight() / (GuiConstants.controlSet2HR * 2)) * 3);

		zoomInButton.addMouseListener(this);
		
		zoomOutButton = new JLabel("-", JLabel.CENTER);
		zoomOutButton.setBackground(Colors.zoomOutButton_P);
		zoomOutButton.setForeground(Colors.getIconColor(Colors.zoomOutButton_P));
		zoomOutButton.setFont(new Font("Heiti SC", Font.CENTER_BASELINE, 62));
		zoomOutButton.setOpaque(true);
		zoomOutButton.setBounds(0, zoomInButton.getY() + zoomInButton.getHeight(), 
				(controlPanel.getWidth() / GuiConstants.clipboardButtonWR) * GuiConstants.zoomButtonWR, 
				(controlPanel.getHeight() / (GuiConstants.controlSet2HR * 2)) * 3);
		zoomOutButton.addMouseListener(this);
		
		clipboardButton = new JLabel("\u21EA", JLabel.CENTER);
		clipboardButton.setBackground(Colors.clipboardButton_P);
		clipboardButton.setForeground(Colors.getIconColor(Colors.clipboardButton_P));
		clipboardButton.setFont(new Font("Arial", Font.CENTER_BASELINE, 42));
		clipboardButton.setOpaque(true);
		clipboardButton.setBounds(zoomInButton.getX() + zoomInButton.getWidth(), settingsButton.getHeight(), 
				controlPanel.getWidth() - zoomInButton.getWidth(), 
				zoomInButton.getHeight() + zoomOutButton.getHeight());
		clipboardButton.addMouseListener(this);
		
		useDefaultDimenButton = new JLabel("<html>▲<br/>▼<html>", JLabel.CENTER);
		useDefaultDimenButton.setBackground(Colors.useDefaultDimenButton_P);
		useDefaultDimenButton.setForeground(Colors.getIconColor(Colors.useDefaultDimenButton_P));
		useDefaultDimenButton.setFont(new Font("Arial", Font.CENTER_BASELINE, 42));
		useDefaultDimenButton.setOpaque(true);
		useDefaultDimenButton.setBounds(0, zoomOutButton.getY() + zoomOutButton.getHeight(), 
				controlPanel.getWidth() / GuiConstants.defaultDimenButtonWR, 
				controlPanel.getHeight() / GuiConstants.controlSet3HR);
		useDefaultDimenButton.addMouseListener(this);
		
		widthInput = new JTextField("100");
		widthInput.setHorizontalAlignment(JTextField.CENTER);

		widthInput.setBackground(colors.widthInput_P);
		widthInput.setForeground(colors.button_PF);
		widthInput.setBorder(widthInputBorder);
		widthInput.setFont(Fonts.ButtonText);
		widthInput.setCaretColor(Colors.button_PF);
		widthInput.setOpaque(true);
		widthInput.setBounds(useDefaultDimenButton.getWidth(), zoomOutButton.getY() + zoomOutButton.getHeight(),
				controlPanel.getWidth() - useDefaultDimenButton.getWidth(),
				useDefaultDimenButton.getHeight() / 2);
		widthInput.addMouseListener(this);
		
		heightInput = new JTextField("");
		heightInput.setHorizontalAlignment(JTextField.CENTER);
		heightInput.setBackground(colors.heightInput_P);
		heightInput.setForeground(colors.button_PF);
		heightInput.setBorder(heightInputBorder);
		heightInput.setFont(Fonts.ButtonText);
		heightInput.setCaretColor(Colors.button_PF);
		heightInput.setOpaque(true);
		heightInput.setBounds(0, zoomOutButton.getY() + zoomOutButton.getHeight(),
				controlPanel.getWidth() / GuiConstants.defaultDimenButtonWR,
				controlPanel.getHeight() / GuiConstants.controlSet3HR);
		heightInput.addMouseListener(this);

		maxCharInput = new JTextField("");
		maxCharInput.setHorizontalAlignment(JTextField.CENTER);
		maxCharInput.setBackground(Colors.maxCharInput_P);
		maxCharInput.setForeground(Colors.button_PF);
		maxCharInput.setBorder(maxCharInputBorder);
		maxCharInput.setFont(Fonts.ButtonText);
		maxCharInput.setCaretColor(Colors.button_PF);
		maxCharInput.setOpaque(true);
		maxCharInput.setBounds(useDefaultDimenButton.getWidth(), useDefaultDimenButton.getY(),
				controlPanel.getWidth() - useDefaultDimenButton.getWidth(),
				useDefaultDimenButton.getHeight());
		maxCharInput.addMouseListener(this);

		printButton = new JLabel("a", JLabel.CENTER);
		printButton.setBackground(Colors.printButton_P);
		printButton.setForeground(Colors.button_PF);
		printButton.setFont(new Font("Webdings", Font.CENTER_BASELINE, 100));
		printButton.setOpaque(true);
		printButton.setLocation(0, useDefaultDimenButton.getY() + useDefaultDimenButton.getHeight());
		printButton.setBounds(0, useDefaultDimenButton.getY() + useDefaultDimenButton.getHeight(), 
				controlPanel.getWidth(), 
				controlPanel.getHeight() - (useDefaultDimenButton.getY() + useDefaultDimenButton.getHeight()));
		printButton.addMouseListener(this);

        controlsTag = new JLabel("Test Label", JLabel.CENTER);
        controlsTag.setBounds(-200, -200, 0, 0);
        controlsTag.setBorder(tagBorder);
        controlsTag.setBackground(Colors.controlsTag_P);
        controlsTag.setForeground(Colors.button_PF);
        controlsTag.setFont(Fonts.tagText);
        controlsTag.setOpaque(true);

		controlPanel.add(settingsButton);
		controlPanel.add(negToggleButton);
		controlPanel.add(zoomInButton);
		controlPanel.add(zoomOutButton);
		controlPanel.add(clipboardButton);
		controlPanel.add(useDefaultDimenButton);
		controlPanel.add(widthInput);
		controlPanel.add(heightInput);
		controlPanel.add(printButton);
		layeredArtPane.add(controlsTag);
		
		//controlPanel.add(control1);
	}
	public void generateArtPanel() {
		
		
		//frame.add(artScrollPane);
		
		artViewer = new JTextArea();
		artViewer.selectAll();
	    
		
		//artViewer.setBounds(0, 0, artPanel.getWidth(), artPanel.getHeight());
		artViewer.setBackground(colors.artPanel_PB);
		artViewer.setForeground(colors.artPanel_PF);
		artViewer.setFont(Fonts.artFont);
		artViewer.setBorder(BorderFactory.createEmptyBorder());
		artViewer.setOpaque(true);
		artViewer.setEditable(false);
		//artViewer.setWrapStyleWord(false);
		artPanel.add(artViewer);
		
		artScrollPane = new JScrollPane(artPanel,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, 
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		artScrollPane.getVerticalScrollBar().setUnitIncrement(16);
		artScrollPane.getHorizontalScrollBar().setUnitIncrement(16);
		artScrollPane.setBounds(controlPanel.getWidth(), 0,
				frame.getWidth() - controlPanel.getWidth(),
				frame.getHeight() - frame.getInsets().top - GuiConstants.fileButtonHeight);
	}
	public void generateLoadingBar() {

		loadingPanel = new JPanel();
		loadingPanel.setLayout(new BoxLayout(loadingPanel, BoxLayout.PAGE_AXIS));

		loadingPanelDimen.setSize(frame.getWidth(), loadingBarHeight);
		loadingPanel.setMinimumSize(loadingPanelDimen);
		loadingPanel.setPreferredSize(loadingPanelDimen);
		loadingPanel.setMaximumSize(loadingPanelDimen);
		loadingPanel.setBackground(Colors.loadingBarPanel);

		loadingBarDimen = new Dimension(frame.getWidth(), 0);
		loadingBar = new JLabel();
		loadingBar.setMinimumSize(loadingBarDimen);
		loadingBar.setPreferredSize(loadingBarDimen);
		loadingBar.setMaximumSize(loadingBarDimen);
		loadingBar.setBackground(Colors.loadingBar);
		loadingBar.setOpaque(true);

		loadingPercentDisplay = new JLabel();
		loadingPercentDisplay.setForeground(Colors.button_PF);
		loadingPercentDisplay.setBounds(0, 0, 0, 0);
		loadingPercentDisplay.setFont(Fonts.loadingText);

		loadingPanel.add(loadingBar);
		//loadingPanel.add(loadingPercentDisplay);
	}
	public static void setArt(String art) {
		artViewer.setText(art);
		artViewer.repaint();
	}

	public void setLoadingStatus(int loadingStatus) {
		loadingBarDimen.setSize((int)((double)loadingPanel.getWidth() * ((double)loadingStatus / (double)loadingSections)), loadingPanel.getHeight());
		loadingBar.setMinimumSize(loadingBarDimen);
		loadingBar.setPreferredSize(loadingBarDimen);
		loadingBar.setMaximumSize(loadingBarDimen);
		//loadingPercentDisplay.setText(Math.round((((double)loadingStatus / loadingSections)) * 100.0) / 100.0 + "%");
		loadingBar.repaint();
		//mainPanel.repaint();
		//loadingPercentDisplay.repaint();
	}
	public void showLoadingBar(boolean show) {
		if(show) {
//
			mainPanel.add(loadingPanel);
			mainPanel.revalidate();
			mainPanel.repaint();
		}
		else {
//
			mainPanel.remove(loadingPanel);
			mainPanel.revalidate();
			mainPanel.repaint();
		}
	}
	public int getDimensionInput(String input) {
		if(input.length() == 0) {
			return 0;
		}
		for(int i = 0; i < input.length(); i++) {
			if(input.charAt(i) < '0' || input.charAt(i) > '9') {

				return -1;
			}
		}
		int ret = Integer.parseInt(input);
		if(ret < 0) {
			return -1;
		}
		return ret;
	}
	public void updateTag(JComponent component, String label) {
	    controlsTag.setVisible(true);
		controlsTag.setText(label);
		//controlsTagDimen = controlsTag.getPreferredSize();
		controlsTag.setSize(controlsTag.getPreferredSize());
		controlsTag.setLocation(8, component.getY() + (component.getHeight() / 2) - (controlsTag.getHeight() / 2));

	    controlsTag.repaint();
    }
    public void generateArt() {
        if(useWidthHeight) {
            width = getDimensionInput(widthInput.getText());
            height = getDimensionInput(heightInput.getText());
            if(width == -1 || height == -1) {
                if(width == -1) {
                    widthInput.setBorder(inputErrorHilight);
                    widthInput.repaint();
                }
                if(height == -1) {
                    heightInput.setBorder(inputErrorHilight);
                    heightInput.repaint();
                }
            }
            else if(imageFile != null) {

                ImageToASCII.generate(imageFile, width, height, negative);
            }
        }
        else {
            maxChars = getDimensionInput(maxCharInput.getText());
			System.out.println("maxChars = " + maxChars);
            if(maxChars == -1) {
				maxCharInput.setBorder(inputErrorHilight);
				maxCharInput.repaint();
			}
            else if(imageFile != null) {
                ImageToASCII.generate(imageFile, maxChars, negative);
            }
        }
    }
	@Override
	public void componentResized(ComponentEvent e) {

		if(frame.getHeight() >= GuiConstants.minControlPanelHeight + GuiConstants.fileButtonHeight) {
			settingsButton.setBounds(0, 0,
					controlPanel.getWidth() / GuiConstants.settingsButtonWR,
					controlPanel.getHeight() / GuiConstants.controlSet1HR);
			negToggleButton.setBounds(settingsButton.getWidth(), 0,
					controlPanel.getWidth() - settingsButton.getWidth(),
					controlPanel.getHeight() / GuiConstants.controlSet1HR);
			zoomInButton.setBounds(0, settingsButton.getHeight(),
					(controlPanel.getWidth() / GuiConstants.clipboardButtonWR) * GuiConstants.zoomButtonWR,
					(controlPanel.getHeight() / (GuiConstants.controlSet2HR * 2)) * 3);
			zoomOutButton.setBounds(0, zoomInButton.getY() + zoomInButton.getHeight(),
					(controlPanel.getWidth() / GuiConstants.clipboardButtonWR) * GuiConstants.zoomButtonWR,
					(controlPanel.getHeight() / (GuiConstants.controlSet2HR * 2)) * 3);
			clipboardButton.setBounds(zoomInButton.getX() + zoomInButton.getWidth(), settingsButton.getHeight(),
					controlPanel.getWidth() - zoomInButton.getWidth(),
					zoomInButton.getHeight() + zoomOutButton.getHeight());
			useDefaultDimenButton.setBounds(0, zoomOutButton.getY() + zoomOutButton.getHeight(),
					controlPanel.getWidth() / GuiConstants.defaultDimenButtonWR,
					controlPanel.getHeight() / GuiConstants.controlSet3HR);
			widthInput.setBounds(useDefaultDimenButton.getWidth(), zoomOutButton.getY() + zoomOutButton.getHeight(),
					controlPanel.getWidth() - useDefaultDimenButton.getWidth(),
					useDefaultDimenButton.getHeight() / 2);
			heightInput.setBounds(useDefaultDimenButton.getWidth(), widthInput.getY() + widthInput.getHeight(),
					controlPanel.getWidth() - useDefaultDimenButton.getWidth(),
					useDefaultDimenButton.getHeight() - widthInput.getHeight());

			maxCharInput.setBounds(useDefaultDimenButton.getWidth(), useDefaultDimenButton.getY(),
					controlPanel.getWidth() - useDefaultDimenButton.getWidth(),
					useDefaultDimenButton.getHeight());

			printButton.setBounds(0, useDefaultDimenButton.getY() + useDefaultDimenButton.getHeight(),
					controlPanel.getWidth(),
					controlPanel.getHeight() - (useDefaultDimenButton.getY() + useDefaultDimenButton.getHeight()));
		}
	}

	
	@Override
	public void componentMoved(ComponentEvent e) {}
	@Override
	public void componentShown(ComponentEvent e) {}
	@Override
	public void componentHidden(ComponentEvent e) {}
	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getSource() == settingsButton) {
			if(settingsWindow == null || !settingsWindow.isVisible()) {
				//settingsWindow = new SettingsGUI();
			}
		}
		else if(e.getSource() == zoomInButton) {
			Fonts.artFont = new Font(Fonts.artFontString, Font.BOLD, Fonts.artFont.getSize() + 1);
			artViewer.setFont(Fonts.artFont);
			artScrollPane.repaint();
			System.out.println(Fonts.artFont.getSize());
		}
		else if(e.getSource() == zoomOutButton) {
			Fonts.artFont = new Font(Fonts.artFontString, Font.BOLD, Fonts.artFont.getSize() - 1);
			artViewer.setFont(Fonts.artFont);
			artScrollPane.repaint();
			System.out.println(Fonts.artFont.getSize());
		}
		else if(e.getSource() == fileButton) {
			imageChooser.showOpenDialog(mainPanel);
			imageFile = imageChooser.getSelectedFile();

			if(imageFile != null) {
                fileButton.setText(imageFile.getAbsolutePath());
				//ImageToASCII.generate(imageFile, width, height, negative);
			}
		}
		else if(e.getSource() == negToggleButton) {
			negative = !negative;
			if(negative) {
				artViewer.setBackground(colors.artPanel_NB);
				artViewer.setForeground(colors.artPanel_NF);
				controlsTag.setBackground(Colors.controlsTag_N);

				tagBorder = BorderFactory.createLineBorder(Colors.controlsTag_N, tagBorderThickness);
				controlsTag.setBorder(tagBorder);
			}
			else {
				artViewer.setBackground(colors.artPanel_PB);
				artViewer.setForeground(colors.artPanel_PF);
				controlsTag.setBackground(Colors.controlsTag_P);

				tagBorder = BorderFactory.createLineBorder(Colors.controlsTag_P, tagBorderThickness);
				controlsTag.setBorder(tagBorder);
			}
			controlsTag.repaint();
			if(imageFile != null && artViewer.getText().length() != 0) {
			    generateArt();
			}
		}
		else if(e.getSource() == printButton) {
			generateArt();
		}
		else if(e.getSource() == useDefaultDimenButton) {
			System.out.println("TOGGLING INPUT");
			useWidthHeight = !useWidthHeight;

			if(useWidthHeight) {
				controlPanel.add(widthInput);
				controlPanel.add(heightInput);
				controlPanel.remove(maxCharInput);
			}
			else {
				controlPanel.remove(widthInput);
				controlPanel.remove(heightInput);
				controlPanel.add(maxCharInput);
			}
			controlPanel.repaint();

		}
		else if(e.getSource() == clipboardButton) {
			artCopyPasteItem = new StringSelection(artViewer.getText());
			myClipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
			myClipboard.setContents(artCopyPasteItem, null);
			updateTag(clipboardButton, "Artwork Copied to Clipboard");
		}
	}
	
	@Override
	public void mousePressed(MouseEvent e) {

		if(e.getSource() != widthInput && e.getSource() != heightInput) {
			//artPanel.requestFocus();
			widthInput.repaint();
			heightInput.repaint();
		}
		if(e.getSource() == settingsButton) {
			settingsButton.setBackground(Colors.buttonClick_P);
			settingsButton.repaint();
		}
		else if(e.getSource() == negToggleButton) {
			negToggleButton.setBackground(Colors.buttonClick_P);
			negToggleButton.repaint();
		}
		else if(e.getSource() == zoomInButton) {
			zoomInButton.setBackground(Colors.buttonClick_P);
			zoomInButton.repaint();
		}
		else if(e.getSource() == zoomOutButton) {
			zoomOutButton.setBackground(Colors.buttonClick_P);
			zoomOutButton.repaint();
		}
		else if(e.getSource() == clipboardButton) {
			clipboardButton.setBackground(Colors.buttonClick_P);
			clipboardButton.repaint();
		}
		else if(e.getSource() == useDefaultDimenButton) {
			useDefaultDimenButton.setBackground(Colors.buttonClick_P);
			useDefaultDimenButton.repaint();
		}
		else if(e.getSource() == printButton) {

		}
		else if(e.getSource() == fileButton) {
			fileButton.setBackground(Colors.buttonClick_P);
			fileButton.repaint();
		}
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		if(e.getSource() == settingsButton) {
			settingsButton.setBackground(Colors.buttonHighlight_P);
			settingsButton.repaint();
		}
		else if(e.getSource() == negToggleButton) {
			negToggleButton.setBackground(Colors.buttonHighlight_P);
			negToggleButton.repaint();
		}
		else if(e.getSource() == zoomInButton) {
			zoomInButton.setBackground(Colors.buttonHighlight_P);
			zoomInButton.repaint();
		}
		else if(e.getSource() == zoomOutButton) {
			zoomOutButton.setBackground(Colors.buttonHighlight_P);
			zoomOutButton.repaint();
		}
		else if(e.getSource() == clipboardButton) {
			clipboardButton.setBackground(Colors.buttonHighlight_P);
			clipboardButton.repaint();
		}
		else if(e.getSource() == useDefaultDimenButton) {
			useDefaultDimenButton.setBackground(Colors.buttonHighlight_P);
			useDefaultDimenButton.repaint();
		}
		else if(e.getSource() == printButton) {
			printButton.setBackground(Colors.printButtonHighlight_P);
			printButton.repaint();
		}
		else if(e.getSource() == fileButton) {
			fileButton.setBackground(Colors.buttonHighlight_P);
			fileButton.repaint();
		}
	}
	@Override
	public void mouseEntered(MouseEvent e) {

		if(e.getSource() == settingsButton) {
			settingsButton.setBackground(Colors.buttonHighlight_P);
			settingsButton.setForeground(Colors.button_PF);
			settingsButton.repaint();
			updateTag(settingsButton, "Settings");
		}
		else if(e.getSource() == negToggleButton) {
			negToggleButton.setBackground(Colors.buttonHighlight_P);
			negToggleButton.setForeground(Colors.button_PF);
			negToggleButton.repaint();
            updateTag(negToggleButton, "Background Toggle");
		}
		else if(e.getSource() == zoomInButton) {
			zoomInButton.setBackground(Colors.buttonHighlight_P);
			zoomInButton.setForeground(Colors.button_PF);
			zoomInButton.repaint();
            updateTag(zoomInButton, "Zoom In");
		}
		else if(e.getSource() == zoomOutButton) {
			zoomOutButton.setBackground(Colors.buttonHighlight_P);
			zoomOutButton.setForeground(Colors.button_PF);
			zoomOutButton.repaint();
            updateTag(zoomOutButton, "Zoom Out");
		}
		else if(e.getSource() == clipboardButton) {
			clipboardButton.setBackground(Colors.buttonHighlight_P);
			clipboardButton.setForeground(Colors.button_PF);
			clipboardButton.repaint();
            updateTag(clipboardButton, "Copy Artwork");
		}
		else if(e.getSource() == useDefaultDimenButton) {
			useDefaultDimenButton.setBackground(Colors.buttonHighlight_P);
			useDefaultDimenButton.setForeground(Colors.button_PF);
			useDefaultDimenButton.repaint();
            updateTag(useDefaultDimenButton, useWidthHeight ? "Use Target Character Number" : "Use Width and Height");
		}
		else if(e.getSource() == printButton) {
			printButton.setBackground(Colors.printButtonHighlight_P);
			printButton.repaint();
            updateTag(printButton, "Draw Image!");
		}
		else if(e.getSource() == widthInput) {
			widthInput.setBorder(inputHilight);
			widthInput.repaint();
            updateTag(widthInput, "Width");
		}
		else if(e.getSource() == heightInput) {
			heightInput.setBorder(inputHilight);
			heightInput.repaint();
			updateTag(heightInput, "Height");
		}
		else if(e.getSource() == maxCharInput) {
			maxCharInput.setBorder(inputHilight);
			maxCharInput.repaint();
			updateTag(maxCharInput, "Character Limit");
		}
		else if(e.getSource() == fileButton) {
			fileButton.setBackground(Colors.buttonHighlight_P);
			fileButton.repaint();
		}
        else {
            controlsTag.setBounds(0, 0, 0, 0);
            controlsTag.repaint();
        }
	}
	@Override
	public void mouseExited(MouseEvent e) {
        controlsTag.setVisible(false);
		if(e.getSource() == settingsButton) {
			settingsButton.setBackground(Colors.settingsButton_PB);
			settingsButton.setForeground(Colors.getIconColor(Colors.settingsButton_PB));
			settingsButton.repaint();
		}
		else if(e.getSource() == negToggleButton) {
			negToggleButton.setBackground(Colors.negToggleButton_PB);
			negToggleButton.setForeground(Colors.getIconColor(Colors.negToggleButton_PB));
			negToggleButton.repaint();
		}
		else if(e.getSource() == zoomInButton) {
			zoomInButton.setBackground(Colors.zoomInButton_P);
			zoomInButton.setForeground(Colors.getIconColor(Colors.zoomInButton_P));
			zoomInButton.repaint();
		}
		else if(e.getSource() == zoomOutButton) {
			zoomOutButton.setBackground(Colors.zoomOutButton_P);
			zoomOutButton.setForeground(Colors.getIconColor(Colors.zoomOutButton_P));
			zoomOutButton.repaint();
		}
		else if(e.getSource() == clipboardButton) {
			clipboardButton.setBackground(Colors.clipboardButton_P);
			clipboardButton.setForeground(Colors.getIconColor(Colors.clipboardButton_P));
			clipboardButton.repaint();
		}
		else if(e.getSource() == useDefaultDimenButton) {
			useDefaultDimenButton.setBackground(Colors.useDefaultDimenButton_P);
			useDefaultDimenButton.setForeground(Colors.getIconColor(Colors.useDefaultDimenButton_P));
			useDefaultDimenButton.repaint();
		}
		else if(e.getSource() == printButton) {
			printButton.setBackground(Colors.printButton_P);
			printButton.repaint();
		}
		else if(e.getSource() == widthInput) {
			widthInput.setBorder(widthInputBorder);
			widthInput.repaint();
		}
		else if(e.getSource() == heightInput) {
			heightInput.setBorder(heightInputBorder);
			heightInput.repaint();
		}
		else if(e.getSource() == maxCharInput) {
			maxCharInput.setBorder(maxCharInputBorder);
			maxCharInput.repaint();
		}
		else if(e.getSource() == fileButton) {
			fileButton.setBackground(Colors.fileButton_PB);
			fileButton.repaint();
		}
        else {
            controlsTag.setBounds(0, 0, 0, 0);
            controlsTag.repaint();
        }
	}
}
