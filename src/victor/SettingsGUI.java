package victor;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.event.*;
import java.io.File;
import java.util.ArrayList;

public class SettingsGUI extends JFrame implements ComponentListener, MouseListener {

    File settingsFile;
    String filePath;
    //
    private JPanel mainPanel;
    private JPanel tabPanel;
    private JPanel settingsPanel;

    private JScrollPane tabScrollPane;
    private JScrollPane settingsScrollPane;

    private Dimension screenDimens;
    private int screenWidth;
    private int screenHeight;
    private int inputBorderThickness;

    private JLabel artTab;
    private JLabel fontTab;

    private JLabel artFontName;
    private JLabel charsPerPix;
    private JLabel uiFontSizeFile;
    private JLabel uiFontSizeText;
    private JLabel uiFontSizeTag;

    private Border inputBorder;
    private Border inputHighlight;

    private ArrayList<JLabel> tabList;
    JPanel[] settingsSections;
    private ArrayList<JLabel> settingsInputsButton;
    private ArrayList<JTextField> settingsInputsTextbox;

    private int tabFocusedIndex;

    public SettingsGUI() {
        super();
        filePath = "ASCII-Art-Resources/settings.txt";
        settingsFile = new File(filePath);

        tabFocusedIndex = 0;

        screenDimens = Toolkit.getDefaultToolkit().getScreenSize();
        screenWidth = (int)screenDimens.getWidth();
        screenHeight = (int)screenDimens.getHeight();

        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.LINE_AXIS));

        inputBorderThickness = 5;
        inputBorder = BorderFactory.createLineBorder(Colors.settingInput_B, inputBorderThickness);
        inputHighlight = BorderFactory.createLineBorder(Colors.buttonHighlight_P, inputBorderThickness);


        buildGUI();

        super.addComponentListener(this);
        super.addMouseListener(this);
        super.requestFocus();
        super.setContentPane(mainPanel);
        super.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        super.setSize(825, 400);
        super.setVisible(true);
    }
    public void buildGUI() {
        tabList = new ArrayList();

        tabPanel = new JPanel();
        tabPanel.setLayout(new BoxLayout(tabPanel, BoxLayout.PAGE_AXIS));

        settingsPanel = new JPanel();
        settingsPanel.setLayout(new BoxLayout(settingsPanel, BoxLayout.PAGE_AXIS));

        tabScrollPane = new JScrollPane(tabPanel);
        tabScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        tabScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        tabScrollPane.setBorder(BorderFactory.createEmptyBorder());
        tabScrollPane.setPreferredSize(new Dimension(200, 400));
        tabScrollPane.setMaximumSize(new Dimension((int)(400), 4000));
        tabScrollPane.setMinimumSize(new Dimension(1, 0));

        tabPanel.setBackground(Colors.tabPanel);

        settingsScrollPane = new JScrollPane(settingsPanel);
        settingsScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        settingsScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        settingsScrollPane.setBorder(BorderFactory.createEmptyBorder());
        settingsScrollPane.setPreferredSize(new Dimension(400, 400));
        settingsScrollPane.setMaximumSize(new Dimension(screenWidth, 4000));
        settingsScrollPane.setMinimumSize(new Dimension(2, 0));
        settingsPanel.setBackground(Colors.settingsPanel);

        buildTabs();

        settingsSections = new JPanel[tabList.size()];
        settingsInputsButton = new ArrayList();
        settingsInputsTextbox = new ArrayList();
        buildSections();


        mainPanel.add(tabScrollPane);
        mainPanel.add(settingsScrollPane);

    }
    public void updateSection(int addPanelIndex) {
        System.out.println("FocuseIndex =  " + tabFocusedIndex + " | addPanelIndex = " + addPanelIndex);

        settingsPanel.add(settingsSections[addPanelIndex]);
        settingsPanel.remove(settingsSections[tabFocusedIndex]);

        //settingsScrollPane.revalidate();
        settingsPanel.revalidate();

        //settingsScrollPane.repaint();
        settingsPanel.repaint();


        Component[] components = settingsSections[addPanelIndex].getComponents();
        System.out.println(components.length);
        for(Component c : components) {
            System.out.println(c);
        }



    }
    private void buildTabs() {

        createTab("ASCII Art");
        createTab("UI Fonts");
        addTabs();

    }
    private void buildSections() {

        for(int i = 0; i < settingsSections.length; i++) {
            settingsSections[i] = new JPanel();
            settingsSections[i].setLayout(new BoxLayout(settingsSections[i], BoxLayout.PAGE_AXIS));
            createSection(i);

        }
        settingsPanel.add(settingsSections[tabFocusedIndex]);

    }
    public void createSection(int sectionNum) {
        switch(sectionNum) {
            case 0: //ASCII Art
                createSetting("Characters per Pixel", new JTextField(), "2", sectionNum);
                createSetting("ASCII Art Font", new JLabel(), "Font", sectionNum);
                System.out.println("Built 1");
                break;
            case 1: //UI Fonts
                createSetting("File Button Font Size", new JTextField(), "22", sectionNum);
                createSetting("Dimension Input Font Size", new JTextField(), "18", sectionNum);
                createSetting("Tag Font Size", new JTextField(), "16", sectionNum);
                System.out.println("Built 2");
                break;
            default:
                System.out.println("SETTINGS - Could not find section: " + sectionNum);
        }
    }
    public void createTab(String label) {
        JLabel tab = new JLabel(label);

        tab.setHorizontalAlignment(JLabel.CENTER);
        tab.setVerticalAlignment(JLabel.CENTER);
        tab.setMinimumSize(new Dimension(0, 80));
        tab.setPreferredSize(new Dimension(0, 80));
        tab.setMaximumSize(new Dimension(screenWidth, screenHeight));
        tab.setBackground(Colors.tabColor_B);
        tab.setForeground(Colors.tabColor_F);
        tab.setFont(Fonts.tabText);
        tab.setOpaque(true);
        tab.addMouseListener(this);

        tabList.add(tab);
    }
    public void createSetting(String name, JLabel button, String buttonName, int sectionNum) {

        JPanel thisSettingPanel = new JPanel();
        thisSettingPanel.setLayout(new BoxLayout(thisSettingPanel, BoxLayout.LINE_AXIS));

        JLabel setting = new JLabel(name);

        setting.setHorizontalAlignment(JLabel.CENTER);
        setting.setVerticalAlignment(JLabel.CENTER);
        setting.setMinimumSize(new Dimension(100, 105));
        setting.setPreferredSize(new Dimension(300, 105));
        setting.setMaximumSize(new Dimension(screenWidth, screenHeight));
        setting.setBackground(Colors.settingsPanel);
        setting.setForeground(Colors.settingLabel_F);
        setting.setFont(Fonts.settingLabelText);
        setting.setOpaque(true);
        setting.addMouseListener(this);

        button = new JLabel(buttonName);
        button.setHorizontalAlignment(JLabel.CENTER);
        button.setVerticalAlignment(JLabel.CENTER);
        button.setBackground(Colors.settingInput_B);
        button.setForeground(Colors.settingInput_F);
        button.setFont(Fonts.settingLabelText);
        button.setMinimumSize(new Dimension(105, 50));
        button.setPreferredSize(new Dimension(150, 105));
        button.setMaximumSize(new Dimension(300, screenHeight));
        button.setOpaque(true);
        button.addMouseListener(this);

        thisSettingPanel.add(setting);
        thisSettingPanel.add(button);
        settingsSections[sectionNum].add(thisSettingPanel);
        settingsInputsButton.add(button);
    }
    public void createSetting(String name, JTextField textField, String text, int sectionNum) {

        JPanel thisSettingPanel = new JPanel();
        thisSettingPanel.setLayout(new BoxLayout(thisSettingPanel, BoxLayout.LINE_AXIS));

        JLabel setting = new JLabel(name);

        setting.setHorizontalAlignment(JLabel.CENTER);
        setting.setVerticalAlignment(JLabel.CENTER);
        setting.setMinimumSize(new Dimension(100, 105));
        setting.setPreferredSize(new Dimension(300, 105));
        setting.setMaximumSize(new Dimension(screenWidth, screenHeight));
        setting.setBackground(Colors.settingsPanel);
        setting.setForeground(Colors.settingLabel_F);
        setting.setFont(Fonts.settingLabelText);
        setting.setOpaque(true);
        setting.addMouseListener(this);

        textField = new JTextField(text);
        textField.setHorizontalAlignment(JLabel.CENTER);
        textField.setBackground(Colors.settingInput_B);
        textField.setForeground(Colors.settingInput_F);
        textField.setBorder(inputBorder);
        textField.setFont(Fonts.settingLabelText);
        textField.setMinimumSize(new Dimension(105, 50));
        textField.setPreferredSize(new Dimension(150, 105));
        textField.setMaximumSize(new Dimension(300, screenHeight));
        textField.setOpaque(true);
        textField.addMouseListener(this);

        thisSettingPanel.add(setting);
        thisSettingPanel.add(textField);
        settingsSections[sectionNum].add(thisSettingPanel);
        settingsInputsTextbox.add(textField);
    }
    public void addTabs() {
//        tabList.add(artTab);
//        tabList.add(fontTab);
        tabList.get(tabFocusedIndex).setBackground(Colors.settingsPanel);
        tabList.get(tabFocusedIndex).setForeground(Colors.tabColorFocused_F);

        for(JLabel tab : tabList) {
            tabPanel.add(tab);
        }
    }

    @Override
    public void componentResized(ComponentEvent e) {
//        System.out.println(tabScrollPane.getWidth() + " : " +
//                settingsScrollPane.getWidth() + " = " +
//                (Math.round(
//                        (double)tabScrollPane.getWidth() /
//                        (double)settingsScrollPane.getWidth() * 100.0) / 100.0));
    }
    @Override
    public void componentMoved(ComponentEvent e) {}
    @Override
    public void componentShown(ComponentEvent e) {}
    @Override
    public void componentHidden(ComponentEvent e) {}


    @Override
    public void mouseClicked(MouseEvent e) {
        int elementIndex;

        elementIndex = isTab(e);
        if(elementIndex != -1) {
            tabList.get(elementIndex).setBackground(Colors.settingsPanel);
            tabList.get(elementIndex).setForeground(Colors.tabColorFocused_F);
            tabList.get(tabFocusedIndex).setBackground(Colors.tabColor_B);
            tabList.get(tabFocusedIndex).setForeground(Colors.tabColor_F);
            System.out.println("Old tab: " + tabFocusedIndex + " -> New tab: " + elementIndex);

            updateSection(elementIndex);
            tabFocusedIndex = elementIndex;
            return;
        }

    }

    @Override
    public void mousePressed(MouseEvent e) {
        int elementIndex;

        elementIndex = isTextbox(e);
        if (elementIndex != -1) {
            return;
        }

        super.requestFocus();
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        int elementIndex;

        elementIndex = isTab(e);
        if(elementIndex != -1) {
            tabList.get(elementIndex).setBackground(Colors.tabColorHighlight_B);
            tabList.get(elementIndex).setForeground(Colors.tabColorHighlight_F);
            return;

        }

        elementIndex = isButton(e);
        if (elementIndex != -1) {
            settingsInputsButton.get(elementIndex).setBackground(Colors.buttonHighlight_P);
            settingsInputsButton.get(elementIndex).setForeground(Colors.tabColor_F);
            return;

        }
        elementIndex = isTextbox(e);
        if (elementIndex != -1) {
            settingsInputsTextbox.get(elementIndex).setBorder(inputHighlight);
            settingsInputsTextbox.get(elementIndex).repaint();
            return;
        }


    }

    @Override
    public void mouseExited(MouseEvent e) {
        int elementIndex;

        elementIndex = isTab(e);
        if(elementIndex != -1) {
            tabList.get(elementIndex ).setBackground(Colors.tabColor_B);
            tabList.get(elementIndex ).setForeground(Colors.tabColor_F);
            return;
        }

        elementIndex = isButton(e);
        if (elementIndex != -1) {
            settingsInputsButton.get(elementIndex ).setBackground(Colors.settingInput_B);
            settingsInputsButton.get(elementIndex ).setForeground(Colors.settingInput_F);
            return;
        }

        elementIndex = isTextbox(e);
        if (elementIndex != -1) {
            settingsInputsTextbox.get(elementIndex ).setBorder(inputBorder);
            settingsInputsTextbox.get(elementIndex ).repaint();
            return;
        }

    }
    public int isTab(MouseEvent e) {
        for(int i = 0; i < tabList.size(); i++) {
            if (i != tabFocusedIndex && e.getSource() == tabList.get(i)) {
                return i;
            }
        }
        return -1;
    }
    public int isButton(MouseEvent e) {
        for (int i = 0; i < settingsInputsButton.size(); i++) {
            if (e.getSource() == settingsInputsButton.get(i)) {
                return i;
            }
        }
        return -1;
    }
    public int isTextbox(MouseEvent e) {
        for (int i = 0; i < settingsInputsTextbox.size(); i++) {
            if (e.getSource() == settingsInputsTextbox.get(i)) {
                return i;
            }
        }
        return -1;
    }
}
