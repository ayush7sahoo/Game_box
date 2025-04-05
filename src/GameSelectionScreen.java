import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameSelectionScreen {
    
    // Constructor to set up the game selection screen
    public GameSelectionScreen() {
        // Create a frame for the game selection screen
        JFrame frame = new JFrame("Game Selection");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null); // Center the frame on the screen
        
        // Panel to hold all UI components
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 1, 10, 20)); // GridLayout with 4 rows, 1 column
        
        // Label for game selection
        JLabel label = new JLabel("Choose Your Game", JLabel.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 18));
        panel.add(label);
        
        // Panel for buttons (Flappy Bird, Pacman, Snake)
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        
        // Flappy Bird Button
        JButton flappyBirdButton = new JButton("Flappy Bird");
        flappyBirdButton.setFont(new Font("Arial", Font.PLAIN, 16));
        flappyBirdButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                startFlappyBird();
            }
        });
        buttonPanel.add(flappyBirdButton);
        
        // Pacman Button
        JButton pacmanButton = new JButton("Pacman");
        pacmanButton.setFont(new Font("Arial", Font.PLAIN, 16));
        pacmanButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                startPacman();
            }
        });
        buttonPanel.add(pacmanButton);
        
        // Snake Button
        JButton snakeButton = new JButton("Snake");
        snakeButton.setFont(new Font("Arial", Font.PLAIN, 16));
        snakeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                startSnake();
            }
        });
        buttonPanel.add(snakeButton);
        
        
        panel.add(buttonPanel);
        frame.add(panel);
        frame.setVisible(true);
    }
    
    // Method to handle starting the Flappy Bird game
    private void startFlappyBird() {
        JOptionPane.showMessageDialog(null, "Starting Flappy Bird...");
        new App();
    }
    
    // Method to handle starting the Pacman game
    private void startPacman() {
        JOptionPane.showMessageDialog(null, "Starting Pacman...");
       
        new App3();

    }
    
    // Method to handle starting the Snake game
    private void startSnake() {
        JOptionPane.showMessageDialog(null, "Starting Snake...");
     
        new App2();
    }

}
