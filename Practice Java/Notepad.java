import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

class Window extends JPanel {
    JTextArea textArea;

    public Window() {
        setPreferredSize(new Dimension(1000, 800));
        setBackground(Color.decode("#1e1e2e"));
        setLayout(new BorderLayout());
        setFocusable(true);

        textArea = new JTextArea();
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setForeground(Color.decode("#cba6f7")); // font color
        textArea.setBackground(Color.decode("#1e1e2e")); // background color
        textArea.setCaretColor(Color.WHITE);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 24));
        textArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setBorder(null);

        add(scrollPane, BorderLayout.CENTER);

        String filePath = "C:/Users/Olive/OneDrive/Documents/Notepad Saves/saved_file.txt";
        loadFromFile(filePath);
    }

    public void saveToFile(String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(textArea.getText());
            System.out.println("File saved to: " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadFromFile(String filePath) {
        File file = new File(filePath);
        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                textArea.read(reader, null);
                System.out.println("File loaded from: " + filePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("No file found to load at: " + filePath);
        }
    }
}

public class Notepad {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Notepad");
        Window Notepad = new Window();
        frame.add(Notepad);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null); // Center on screen
        frame.setVisible(true);
        frame.setAlwaysOnTop(true);

        // Specify full file path INCLUDING filename + extension
        String filePath = "C:/Users/Olive/OneDrive/Documents/Notepad Saves/saved_file.txt";

        // Add key listener to text area
        Notepad.textArea.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_S) {
                    Notepad.saveToFile(filePath);
                }
            }
        });

        Notepad.textArea.requestFocusInWindow();
    }
}
