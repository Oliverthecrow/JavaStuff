import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.*;
import javax.swing.plaf.basic.BasicScrollBarUI;
import javax.swing.plaf.basic.BasicSplitPaneDivider;
import javax.swing.plaf.basic.BasicSplitPaneUI;

class picto implements Serializable {
    private static final long serialVersionUID = 1L; // Defines a unique serialVersionUID
    private String name;
    private int cost;
    private boolean enabled;
    private String type;

    picto(String n, int c, boolean b, String t) {
        name = n;
        cost = c;
        enabled = b;
        type = t;
    }

    public String getPictoName() {
        return name;
    }

    public int getPictoCost() {
        return cost;
    }

    public boolean getPictoBool() {
        return enabled;
    }

    public String getPictoType() {
        return type;
    }

    public void setPictoBool(boolean b) {
        enabled = b;
    }
}

public class BuildOrganizer {
    // some global variables
    static JFrame Organizer;
    static JPanel panelR;
    static JPanel panelL;
    static int cost = 0;
    static JLabel costLabel;
    static String AccentColor = "#cba6f7";
    static String BackgroundColor = "#1e1e2e";
    static boolean theme = false;
    static ArrayList<picto> pictos = loadArrayList(
            "c:/Users/Olive/OneDrive/Documents/JavaStuff/Big_Project/PictosList/PictoList.ser");

    public static void main(String[] args) {
        ArrayList<JCheckBox> boxes = new ArrayList<>();

        // frame
        Organizer = new JFrame("Organizer");

        // right panel
        panelR = new JPanel();
        panelR.setLayout(new GridLayout(0, 2, 20, 20));
        panelR.setAlignmentY(JComponent.LEFT_ALIGNMENT);
        panelR.setBackground(Color.decode(BackgroundColor));

        // left panel
        panelL = new JPanel();
        panelL.setLayout(new BoxLayout(panelL, BoxLayout.Y_AXIS));
        panelL.setBackground(Color.decode(BackgroundColor));
        panelL.setBorder(BorderFactory.createEmptyBorder(50, 0, 0, 0));
        costLabel = new JLabel("Total Cost: " + cost, JLabel.CENTER);
        costLabel.setAlignmentY(Component.TOP_ALIGNMENT);
        costLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        costLabel.setFont(new Font("Monospaced", Font.PLAIN, 26));
        costLabel.setForeground(Color.decode(AccentColor));
        panelL.add(costLabel);
        panelL.add(Box.createRigidArea(new Dimension(0, 20)));

        // border panel for scrollPanel
        JPanel border = new JPanel(new BorderLayout());
        border.setBorder(BorderFactory.createEmptyBorder(50, 150, 0, 0));
        border.setBackground(Color.decode(BackgroundColor));
        border.add(panelR, BorderLayout.CENTER);

        // scroll panel inside of border
        JScrollPane scrollPanel = new JScrollPane(border);
        scrollPanel.setPreferredSize(new Dimension(500, 600));
        scrollPanel.getVerticalScrollBar().setUnitIncrement(16);
        scrollPanel.setBackground(Color.decode(BackgroundColor));
        scrollPanel.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
            public void configureScrollBarColors() {
                this.thumbColor = Color.decode(AccentColor);
                this.trackColor = Color.decode(BackgroundColor);
            }
        });
        scrollPanel.getHorizontalScrollBar().setUI(new BasicScrollBarUI() {
            public void configureScrollBarColors() {
                this.thumbColor = Color.decode(AccentColor);
                this.trackColor = Color.decode(BackgroundColor);
            }
        });
        JSplitPane splitPanel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, panelL, scrollPanel);
        splitPanel.setDividerLocation(280);
        splitPanel.setResizeWeight(0.0);
        // credit for divider color change to
        // https://stackoverflow.com/questions/8934169/how-to-change-the-color-or-background-color-of-jsplitpane-divider
        splitPanel.setUI(new BasicSplitPaneUI() {
            @Override
            public BasicSplitPaneDivider createDefaultDivider() {
                return new BasicSplitPaneDivider(this) {
                    public void paint(Graphics g) {
                        g.setColor(Color.decode(AccentColor));
                        g.fillRect(0, 0, getSize().width, getSize().height);
                        super.paint(g);
                    }
                };
            }
        });
        splitPanel.setBorder(null);

        // Offensive Button
        JButton offButton = new JButton("Offensive");
        buttonMaker(offButton, panelL);
        offButton.addActionListener((ActionEvent event) -> {
            filterPictos("Offensive", boxes, pictos, scrollPanel);
        });

        // Defensive Button
        JButton defButton = new JButton("Defensive");
        buttonMaker(defButton, panelL);
        defButton.addActionListener((ActionEvent event) -> {
            filterPictos("Defensive", boxes, pictos, scrollPanel);
        });

        // Supportive Button
        JButton supButton = new JButton("Supportive");
        buttonMaker(supButton, panelL);
        supButton.addActionListener((ActionEvent event) -> {
            filterPictos("Supportive", boxes, pictos, scrollPanel);
        });

        // All Button
        JButton allButton = new JButton("All");
        buttonMaker(allButton, panelL);
        allButton.addActionListener((ActionEvent event) -> {
            filterPictos("", boxes, pictos, scrollPanel);
        });

        // Save Build Button
        JButton saveButton = new JButton("Save Build");
        buttonMaker(saveButton, panelL);
        saveButton.addActionListener((ActionEvent event) -> {
            String buildName = javax.swing.JOptionPane
                    .showInputDialog("What Build Name To Save Active Pictos Under?");
            saveArrayList(pictos,
                    "c:/Users/Olive/OneDrive/Documents/JavaStuff/Big_Project/savedBuilds/" + buildName + ".ser");
        });

        // Open Build Button
        JButton openButton = new JButton("Open Build");
        buttonMaker(openButton, panelL);
        openButton.addActionListener((ActionEvent event) -> {
            String buildName = javax.swing.JOptionPane
                    .showInputDialog("What Build Do You Want To Open?");
            if (buildName == null || buildName.equals("")) {
                pictos = loadArrayList(
                        "c:/Users/Olive/OneDrive/Documents/JavaStuff/Big_Project/PictosList/PictoList.ser");
            } else {
                pictos = loadArrayList(
                        "c:/Users/Olive/OneDrive/Documents/JavaStuff/Big_Project/savedBuilds/" + buildName
                                + ".ser");
            }
            checkboxCreator(pictos, boxes, "", true);
            filterPictos("", boxes, pictos, scrollPanel);
            updateCost();
        });

        // themeButton
        JButton themeButton = new JButton("Theme Button");
        buttonMaker(themeButton, panelL);
        themeButton.addActionListener((ActionEvent event) -> {
            theme = !theme;
            ChangeTheme(panelL, border, scrollPanel, splitPanel, themeButton, boxes, offButton, defButton,
                    supButton, allButton, saveButton, openButton);
        });
        // END OF THE BUTTON SPAM

        checkboxCreator(pictos, boxes, "", false);

        // adds scroll panel and costpanel as splitframe to frame
        Organizer.add(splitPanel);
        System.out.println("Loaded " + pictos.size() + " pictos.");

        Organizer.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Organizer.setSize(1000, 1000);
        Organizer.setLocationRelativeTo(null); // Center of screen for some reason
        Organizer.setVisible(true);
    }

    // creates all checkbox's from .ser files ands adds to panelR
    public static void checkboxCreator(ArrayList<picto> pictos, ArrayList<JCheckBox> boxes, String type,
            boolean remove) {
        // if true first removes everything before remaking
        if (remove) {
            panelR.removeAll();
            boxes.clear();
        }
        // Checkbox Creater
        for (picto picto : pictos) {
            if (type.equals(picto.getPictoType()) || type.equals("")) {
                JCheckBox box = new JCheckBox(
                        String.format("%-50s Cost: %d", picto.getPictoName(), picto.getPictoCost()),
                        picto.getPictoBool());
                box.setBackground(Color.decode(BackgroundColor));
                box.setForeground(Color.decode(AccentColor));
                box.setBorder(BorderFactory.createLineBorder(null, 0));// gets rid of border with color null
                box.setFont(new Font("Monospaced", Font.PLAIN, 19));
                box.addActionListener((ActionEvent event) -> {
                    {
                        cost = checkboxChecked(event, cost, box, pictos);
                    }
                });
                panelR.add(box);
                boxes.add(box);
            }
        }
        catWindowMaker();
    }

    public static void catWindowMaker() {
        // Secret Cat Button
        JButton catButton = new JButton("Uber Secret Cat");
        buttonMaker(catButton, panelR);
        catButton.addActionListener((ActionEvent event) -> {
            JFrame catFrame = new JFrame();
            JPanel catPanel = new JPanel(new BorderLayout());
            JLabel catLabel = new JLabel();
            Image[] cats = {
                    new ImageIcon("C:/Users/Olive/OneDrive/Documents/JavaStuff/Big_Project/catPhotos/catPhoto.jpg")
                            .getImage(),
                    new ImageIcon("C:/Users/Olive/OneDrive/Documents/JavaStuff/Big_Project/catPhotos/catPhoto3.jpg")
                            .getImage(),
                    new ImageIcon("C:/Users/Olive/OneDrive/Documents/JavaStuff/Big_Project/catPhotos/catPhoto4.jpg")
                            .getImage(),
                    new ImageIcon("C:/Users/Olive/OneDrive/Documents/JavaStuff/Big_Project/catPhotos/catPhoto5.jpg")
                            .getImage() };
            int randomIndex = (int) (Math.random() * cats.length);
            ImageIcon cat = new ImageIcon(cats[randomIndex]);
            catLabel.setIcon(cat);
            catPanel.add(catLabel);
            catFrame.add(catPanel);
            catFrame.setSize(600, 500);
            catFrame.setLocationRelativeTo(null); // Center of screen for some reason
            catFrame.setVisible(true);
        });
    }

    public static void buttonMaker(JButton button, JPanel panelL) {
        button.setBackground(Color.decode(BackgroundColor));
        button.setForeground(Color.decode(AccentColor));
        button.setPreferredSize(new Dimension(200, 120));
        button.setMaximumSize(new Dimension(200, 120));
        button.setAlignmentY(Component.TOP_ALIGNMENT);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelL.add(button);
    }

    // this is the ActionEvent method for each JCheckBox that updates the cost and
    // sets that particular picto bool on or off
    public static int checkboxChecked(ActionEvent event, int cost, JCheckBox box, ArrayList<picto> pictos) {
        String text = box.getText();
        int pictoCost = Integer.parseInt(text.replaceAll("[^0-9]", ""));
        if (box.isSelected()) {
            cost += pictoCost;
            for (int i = 0; i < pictos.size(); i++) {
                if (text.contains(pictos.get(i).getPictoName())) {
                    pictos.get(i).setPictoBool(true);
                }
            }
        } else {
            cost -= pictoCost;
            for (int i = 0; i < pictos.size(); i++) {
                if (text.contains(pictos.get(i).getPictoName())) {
                    pictos.get(i).setPictoBool(false);
                }
            }
        }
        // updating label
        costLabel.setText("Total Cost: " + cost);
        return cost;
    }

    // correctly redraw the costLabel with the correct cost
    public static void updateCost() {
        cost = 0;
        for (picto picto : pictos) {
            if (picto.getPictoBool()) {
                cost += picto.getPictoCost();
            }
        }
        costLabel.setText("Total Cost: " + cost);
    }

    // changes color of all Jcomponents in bewteen black and white, and catppucin
    // theme
    public static void ChangeTheme(JPanel panelL, JPanel border, JScrollPane scrollPanel, JSplitPane splitPanel,
            JButton themeButton, ArrayList<JCheckBox> boxes, JButton offButton, JButton defButton, JButton supButton,
            JButton allButton, JButton saveButton, JButton openButton) {
        if (theme) {
            AccentColor = "#000000";
            BackgroundColor = "#FFFFFF";
        } else {
            AccentColor = "#cba6f7";
            BackgroundColor = "#1e1e2e";
        }
        ArrayList<JComponent> Jcomponents = new ArrayList<>(Arrays.asList(panelL, border, scrollPanel, splitPanel,
                themeButton, offButton, defButton, supButton, allButton, saveButton, openButton));
        for (JComponent Jcomp : Jcomponents) {
            updateColors(Jcomp, Color.decode(AccentColor), Color.decode(BackgroundColor));
        }
        updateColors(costLabel, Color.decode(AccentColor), Color.decode(BackgroundColor));
        updateColors(panelR, Color.decode(AccentColor), Color.decode(BackgroundColor));
        for (JCheckBox box : boxes) {
            box.setBackground(Color.decode(BackgroundColor));
            box.setForeground(Color.decode(AccentColor));
            box.repaint();
        }
        scrollPanel.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
            public void configureScrollBarColors() {
                this.thumbColor = Color.decode(AccentColor);
                this.trackColor = Color.decode(BackgroundColor);
            }
        });
        scrollPanel.getHorizontalScrollBar().setUI(new BasicScrollBarUI() {
            public void configureScrollBarColors() {
                this.thumbColor = Color.decode(AccentColor);
                this.trackColor = Color.decode(BackgroundColor);
            }
        });
    }

    // a method called inside of ChangeTheme()
    public static void updateColors(JComponent thing, Color ac, Color bg) {
        thing.setBackground(bg);
        thing.setForeground(ac);
        thing.repaint();
    }

    public static void filterPictos(String type, ArrayList<JCheckBox> boxes, ArrayList<picto> pictos,
            JScrollPane scrollPanel) {
        checkboxCreator(pictos, boxes, type, true);
        scrollPanel.revalidate();
        scrollPanel.repaint();
    }

    // these are serelization methods that create and read a file that holds an
    // arraylist permanently. learned from
    // https://www.geeksforgeeks.org/serialization-in-java/
    public static void saveArrayList(ArrayList<picto> pictos, String filePath) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(pictos); // Serialize the ArrayList and write it to the file
            System.out.println("ArrayList saved successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<picto> loadArrayList(String filePath) {
        ArrayList<picto> pictos = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            pictos = (ArrayList<picto>) ois.readObject(); // Deserialize the ArrayList from the file
            System.out.println("ArrayList loaded successfully.");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return pictos;
    }
}

// this class/main can be run to setup the default .ser file for all pictos to
// be disabled
// Only nessecary to run one time, but might as well put it in this file anyway
class setupSER {
    public static void main(String[] args) {
        ArrayList<picto> pictos = new ArrayList<>(Arrays.asList(
                new picto("Accelerating Heal", 5, false, "Supportive"),
                new picto("Accelerating Last Stand", 3, false, "Supportive"),
                new picto("Accelerating Shots", 3, false, "Supportive"),
                new picto("Accelerating Tint", 5, false, "Supportive"),
                new picto("Aegis Revival", 5, false, "Defensive"),
                new picto("Anti-Blight", 10, false, "Defensive"),
                new picto("Anti-Burn", 15, false, "Defensive"),
                new picto("Anti-Charm", 10, false, "Defensive"),
                new picto("Anti-Freeze", 15, false, "Defensive"),
                new picto("Anti-Stun", 5, false, "Defensive"),
                new picto("At Death's Door", 5, false, "Offensive"),
                new picto("Attack Lifesteal", 15, false, "Supportive"),
                new picto("Augmented Aim", 3, false, "Offensive"),
                new picto("Augmented Attack", 7, false, "Offensive"),
                new picto("Augmented Counter I", 3, false, "Offensive"),
                new picto("Augmented Counter II", 5, false, "Offensive"),
                new picto("Augmented Counter III", 7, false, "Offensive"),
                new picto("Augmented First Strike", 5, false, "Offensive"),
                new picto("Auto Death", 1, false, "Supportive"),
                new picto("Auto Powerful", 10, false, "Supportive"),
                new picto("Auto Regen", 10, false, "Supportive"),
                new picto("Auto Rush", 10, false, "Supportive"),
                new picto("Auto Shell", 10, false, "Supportive"),
                new picto("Base Shield", 20, false, "Defensive"),
                new picto("Beneficial Contamination", 15, false, "Supportive"),
                new picto("Break Specialist", 1, false, "Offensive"),
                new picto("Breaker", 10, false, "Offensive"),
                new picto("Breaking Attack", 10, false, "Offensive"),
                new picto("Breaking Burn", 5, false, "Offensive"),
                new picto("Breaking Counter", 3, false, "Offensive"),
                new picto("Breaking Death", 5, false, "Supportive"),
                new picto("Breaking Shots", 1, false, "Offensive"),
                new picto("Breaking Slow", 5, false, "Offensive"),
                new picto("Burn Affinity", 10, false, "Offensive"),
                new picto("Burning Break", 3, false, "Offensive"),
                new picto("Burning Death", 5, false, "Offensive"),
                new picto("Burning Mark", 15, false, "Offensive"),
                new picto("Burning Shots", 3, false, "Offensive"),
                new picto("Charging Alteration", 10, false, "Supportive"),
                new picto("Charging Attack", 7, false, "Supportive"),
                new picto("Charging Burn", 5, false, "Supportive"),
                new picto("Charging Counter", 10, false, "Supportive"),
                new picto("Charging Critical", 10, false, "Supportive"),
                new picto("Charging Mark", 10, false, "Supportive"),
                new picto("Charging Stun", 5, false, "Supportive"),
                new picto("Charging Tint", 2, false, "Supportive"),
                new picto("Charging Weakness", 5, false, "Supportive"),
                new picto("Cheater", 40, false, "Offensive"),
                new picto("Cleansing Tint", 5, false, "Defensive"),
                new picto("Clea's Life", 30, false, "Supportive"),
                new picto("Combo Attack I", 10, false, "Offensive"),
                new picto("Combo Attack II", 20, false, "Offensive"),
                new picto("Combo Attack III", 30, false, "Offensive"),
                new picto("Confident", 20, false, "Defensive"),
                new picto("Confident Fighter", 15, false, "Offensive"),
                new picto("Critical Break", 5, false, "Offensive"),
                new picto("Critical Burn", 5, false, "Offensive"),
                new picto("Critical Moment", 5, false, "Offensive"),
                new picto("Critical Stun", 5, false, "Offensive"),
                new picto("Critical Vulnerability", 5, false, "Offensive"),
                new picto("Critical Weakness", 5, false, "Offensive"),
                new picto("Dead Energy I", 2, false, "Supportive"),
                new picto("Dead Energy II", 2, false, "Supportive"),
                new picto("Death Bomb", 5, false, "Offensive"),
                new picto("Defensive Mode", 1, false, "Defensive"),
                new picto("Dodger", 1, false, "Supportive"),
                new picto("Double Burn", 30, false, "Offensive"),
                new picto("Double Mark", 20, false, "Supportive"),
                new picto("Draining Cleanse", 15, false, "Supportive"),
                new picto("Effective Heal", 30, false, "Supporitve"),
                new picto("Effective Support", 5, false, "Supportive"),
                new picto("Empowering Attack", 10, false, "Supportive"),
                new picto("Empowering Break", 3, false, "Supportive"),
                new picto("Empowering Dodge", 5, false, "Offensive"),
                new picto("Empowering Last Stand", 3, false, "Supportive"),
                new picto("Empowering Parry", 5, false, "Offensive"),
                new picto("Empowering Tint", 5, false, "Supportive"),
                new picto("Energising Attack I", 10, false, "Supportive"),
                new picto("Energising Attack II", 15, false, "Supportive"),
                new picto("Energising Break", 3, false, "Supportive"),
                new picto("Energising Burn", 10, false, "Supportive"),
                new picto("Energising Cleanse", 10, false, "Supportive"),
                new picto("Energising Death", 5, false, "Supportive"),
                new picto("Energising Gradient", 10, false, "Supportive"),
                new picto("Energising Heal", 10, false, "Supportive"),
                new picto("Energising Jump", 5, false, "Supportive"),
                new picto("Energising Pain", 10, false, "Supportive"),
                new picto("Energising Parry", 15, false, "Supportive"),
                new picto("Energising Powerful", 10, false, "Supportive"),
                new picto("Energising Revive", 5, false, "Supportive"),
                new picto("Energising Rush", 10, false, "Supportive"),
                new picto("Energising Shell", 10, false, "Supportive"),
                new picto("Energising Shots", 10, false, "Offensive"),
                new picto("Energising Start I", 5, false, "Supportive"),
                new picto("Energising Start II", 10, false, "Supportive"),
                new picto("Energising Start III", 15, false, "Supportive"),
                new picto("Energising Start IV", 20, false, "Supportive"),
                new picto("Energising Stun", 10, false, "Supportive"),
                new picto("Energising Turn", 20, false, "Supportive"),
                new picto("Energy Master", 40, false, "Supportive"),
                new picto("Enfeebling Attack", 10, false, "Defensive"),
                new picto("Enfeebling Mark", 10, false, "Defensive"),
                new picto("Exhausting Power", 2, false, "Offensive"),
                new picto("Exposing Attack", 10, false, "Supportive"),
                new picto("Exposing Break", 5, false, "Supportive"),
                new picto("Faster Than Strong", 10, false, "Offensive"),
                new picto("First Offensive", 5, false, "Offensive"),
                new picto("First Strike", 10, false, "Offensive"),
                new picto("Fueling Break", 5, false, "Offensive"),
                new picto("Full Strength", 15, false, "Offensive"),
                new picto("Glass Canon", 10, false, "Offensive"),
                new picto("Gradient Break", 5, false, "Supportive"),
                new picto("Gradient Breaker", 5, false, "Offensive"),
                new picto("Gradient Fighter", 5, false, "Offensive"),
                new picto("Greater Defenceless", 15, false, "Offensive"),
                new picto("Greater Powerful", 10, false, "Offensive"),
                new picto("Greater Powerless", 15, false, "Defensive"),
                new picto("Greater Rush", 10, false, "Supportive"),
                new picto("Greater Shell", 10, false, "Supportive"),
                new picto("Greater Slow", 15, false, "Supportive"),
                new picto("Healing Boon", 10, false, "Supportive"),
                new picto("Healing Counter", 10, false, "Supportive"),
                new picto("Healing Death", 5, false, "Supportive"),
                new picto("Healing Fire", 10, false, "Supportive"),
                new picto("Healing Mark", 20, false, "Supportive"),
                new picto("Healing Parry", 5, false, "Supportive"),
                new picto("Healing Share", 5, false, "Supportive"),
                new picto("Healing Stun", 10, false, "Supportive"),
                new picto("Healing Tint Energy", 1, false, "Supportive"),
                new picto("Immaculate", 10, false, "Offensive"),
                new picto("In Medias Res", 10, false, "Defensive"),
                new picto("Inverted Affinity", 5, false, "Offensive"),
                new picto("Last Stand Critical", 3, false, "Offensive"),
                new picto("Longer Burn", 15, false, "Offensive"),
                new picto("Longer Powerful", 10, false, "Offensive"),
                new picto("Longer Rush", 10, false, "Supportive"),
                new picto("Longer Shell", 10, false, "Supportive"),
                new picto("Marking Break", 5, false, "Supportive"),
                new picto("Marking Shots", 3, false, "Supportive"),
                new picto("Painted Power", 5, false, "Offensive"),
                new picto("Painter", 10, false, "Offensive"),
                new picto("Perilous Parry", 5, false, "Supportive"),
                new picto("Piercing Shot", 2, false, "Offensive"),
                new picto("Powered Attack", 10, false, "Offensive"),
                new picto("Powerful Heal", 5, false, "Supportive"),
                new picto("Powerful Mark", 5, false, "Supportive"),
                new picto("Powerful on Shell", 10, false, "Supportive"),
                new picto("Powerful Revive", 3, false, "Supportive"),
                new picto("Powerful Shield", 5, false, "Offensive"),
                new picto("Powerful Shots", 3, false, "Supportive"),
                new picto("Pro Retreat", 40, false, "Defensive"),
                new picto("Protecting Attack", 10, false, "Supportive"),
                new picto("Protecting Death", 5, false, "Supportive"),
                new picto("Protecting Heal", 5, false, "Supportive"),
                new picto("Protecting Last Stand", 3, false, "Defensive"),
                new picto("Protecting Tint", 5, false, "Defensive"),
                new picto("Quick Break", 3, false, "Offensive"),
                new picto("Random Defense", 5, false, "Defensive"),
                new picto("Recovery", 10, false, "Supportive"),
                new picto("Rejuvenating Revive", 3, false, "Supportive"),
                new picto("Revive Paradox", 5, false, "Offensive"),
                new picto("Revive Tint Energy", 10, false, "Supportive"),
                new picto("Rewarding Mark", 5, false, "Supportive"),
                new picto("Roulette", 5, false, "Offensive"),
                new picto("Rush on Powerful", 10, false, "Supportive"),
                new picto("Shell On Rush", 10, false, "Supportive"),
                new picto("SOS Power", 5, false, "Supportive"),
                new picto("SOS Rush", 5, false, "Supportive"),
                new picto("SOS Shell", 5, false, "Defensive"),
                new picto("Second Chance", 40, false, "Supportive"),
                new picto("Shared Care", 10, false, "Supportive"),
                new picto("Shield Affinity", 15, false, "Offensive"),
                new picto("Shielding Death", 10, false, "Supportive"),
                new picto("Shielding Tint", 10, false, "Defensive"),
                new picto("Shortcut", 5, false, "Offensive"),
                new picto("Slowing Break", 5, false, "Supportive"),
                new picto("Sniper", 15, false, "Offensive"),
                new picto("Solidifying", 10, false, "Defensive"),
                new picto("Solo Fighter", 1, false, "Offensive"),
                new picto("Staggering Attack", 1, false, "Offensive"),
                new picto("Stay Marked", 10, false, "Offensive"),
                new picto("Stun Boost", 10, false, "Offensive"),
                new picto("Survivor", 20, false, "Supportive"),
                new picto("Sweet Kill", 5, false, "Supportive"),
                new picto("Tainted", 3, false, "Offensive"),
                new picto("Teamwork", 5, false, "Offensive"),
                new picto("The One", 1, false, "Supportive"),
                new picto("Time Tint", 5, false, "Supportive"),
                new picto("Versatile", 5, false, "Offensive"),
                new picto("Warming Up", 15, false, "Offensive"),
                new picto("Weakness Gain", 3, false, "Offensive")));

        // creates folder if not found
        File dir = new File("c:/Users/Olive/OneDrive/Documents/JavaStuff/Big_Project/PictosList/");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        saveArrayList(pictos,
                "c:/Users/Olive/OneDrive/Documents/JavaStuff/Big_Project/PictosList/PictoList.ser");
    }

    public static void saveArrayList(ArrayList<picto> pictos, String filePath) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(pictos); // Serialize the ArrayList and write it to the file
            System.out.println("ArrayList saved successfully.");
            System.out.println(filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
