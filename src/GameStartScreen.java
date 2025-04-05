import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class GameStartScreen {
    GameStartScreen(){
        // Create a frame for the game start screen
        JFrame frame = new JFrame("Game Start Screen");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null); // Center the frame on the screen
        
        // Panel to hold all UI components
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 1, 10, 20)); // GridLayout with 3 rows, 1 column
        
        // Label for the game mode
        JLabel label = new JLabel("Choose Your Game Mode", JLabel.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 18));
        panel.add(label);
        
        // Panel for buttons (Single Player and Multiplayer)
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        
        // Single Player Button
        JButton singlePlayerButton = new JButton("Single Player");
        singlePlayerButton.setFont(new Font("Arial", Font.PLAIN, 16));
        singlePlayerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startSinglePlayer();
            }
        });
        singlePlayerButton.setFocusable(false);
        buttonPanel.add(singlePlayerButton);
        
        // Multiplayer Button
        JButton multiPlayerButton = new JButton("Multiplayer");
        multiPlayerButton.setFont(new Font("Arial", Font.PLAIN, 16));
        multiPlayerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startMultiplayer();
            }
        });
        multiPlayerButton.setFocusable(false);
        buttonPanel.add(multiPlayerButton);
        panel.add(buttonPanel);
        frame.add(panel);
        frame.setVisible(true);
    }
    
    // Method to handle starting the game in Single Player mode
    private static void startSinglePlayer() {
        JOptionPane.showMessageDialog(null, "Starting Single Player Mode...");
        
        new GameSelectionScreen();
    }
    
    // Method to handle starting the game in Multiplayer mode
    private static void startMultiplayer() {
        JOptionPane.showMessageDialog(null, "Starting Multiplayer Mode...");
        new startGame();
    }
    public static void main(String[] args) {
        new GameStartScreen();
 }

    }
    
    
