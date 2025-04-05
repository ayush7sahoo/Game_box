import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

class Coin {
    private Random random = new Random();

    public boolean toss() {
        return random.nextBoolean(); // True for heads, false for tails
    }
}

// Assuming you have these classes defined elsewhere in your project
class startGame extends JFrame {
    public startGame() {
        // Add your logic for starting a new game here
        setTitle("New Game Started");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Or another appropriate close operation
        setLocationRelativeTo(null);
        JLabel newGameLabel = new JLabel("New Game Screen", SwingConstants.CENTER);
        add(newGameLabel);
        setVisible(true);
    }
}

class GameStartScreen extends JFrame {
    public GameStartScreen() {
        // Add your logic for navigating to the game start screen here
        setTitle("Game Start Screen");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Or another appropriate close operation
        setLocationRelativeTo(null);
        JLabel startScreenLabel = new JLabel("Welcome to the Start Screen", SwingConstants.CENTER);
        add(startScreenLabel);
        setVisible(true);
    }
}

public class CoinTossGame extends JFrame {
    private JLabel label;
    private JButton tossButton;
    private JButton restartButton;
    private JButton exitButton;
    private Coin coin;
    private JLabel coinLabel;
    private ImageIcon headsIcon, tailsIcon;
    private JPanel buttonPanel;

    public CoinTossGame() {
        setTitle("Coin Toss Game");
        setSize(900, 900); // Increased window size for better visibility
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        label = new JLabel("It's a Tie!!! Click the button to toss the coin!", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 24));
        tossButton = new JButton("Toss Coin");
        tossButton.setFont(new Font("Arial", Font.BOLD, 18));
        coin = new Coin();

        // Load and scale images for heads and tails
        try {
            headsIcon = scaleImageIcon(new ImageIcon(getClass().getResource("/heads.jpg")), 600, 600);
            tailsIcon = scaleImageIcon(new ImageIcon(getClass().getResource("/tails.jpg")), 600, 600);
            if (headsIcon.getImageLoadStatus() == MediaTracker.ERRORED || tailsIcon.getImageLoadStatus() == MediaTracker.ERRORED) {
                JOptionPane.showMessageDialog(this, "Error loading images. Please ensure 'heads.jpg' and 'tails.jpg' are in the correct location.", "Image Load Error", JOptionPane.ERROR_MESSAGE);
                tossButton.setEnabled(false);
            }
        } catch (NullPointerException e) {
            JOptionPane.showMessageDialog(this, "Error: Could not find 'heads.jpg' or 'tails.jpg'. Please place them in the project's resource folder.", "Image Not Found", JOptionPane.ERROR_MESSAGE);
            tossButton.setEnabled(false);
        }

        coinLabel = new JLabel();
        coinLabel.setHorizontalAlignment(SwingConstants.CENTER);
        coinLabel.setPreferredSize(new Dimension(600, 600)); // Increased image display size
        coinLabel.setIcon(headsIcon); // Set an initial icon

        tossButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                animateCoinToss();
            }
        });

        // Create button panel
        buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        // Create restart button
        restartButton = new JButton("Restart");
        restartButton.setFont(new Font("Arial", Font.BOLD, 18));
        restartButton.setEnabled(false); // Initially disabled
        restartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new startGame();
                dispose(); // Dispose of the current CoinTossGame window
            }
        });
        buttonPanel.add(restartButton);

        // Create exit button
        exitButton = new JButton("Exit");
        exitButton.setFont(new Font("Arial", Font.BOLD, 18));
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new GameStartScreen();
                dispose(); // Dispose of the current CoinTossGame window
            }
        });
        buttonPanel.add(exitButton);

        add(label, BorderLayout.NORTH);
        add(coinLabel, BorderLayout.CENTER);
        add(tossButton, BorderLayout.SOUTH);
        add(buttonPanel, BorderLayout.EAST); // Added button panel to the EAST

        this.setVisible(true);
    }

    private void animateCoinToss() {
        tossButton.setEnabled(false); // Disable the toss button during animation
        label.setText("Tossing...");
        restartButton.setEnabled(false); // Disable restart during animation
        Timer timer = new Timer(100, new ActionListener() { // Slightly slower for better visibility
            int count = 0;
            int maxFlips = new Random().nextInt(15) + 10; // Adjusted flip count
            boolean currentSide = false; // Start with tails

            @Override
            public void actionPerformed(ActionEvent e) {
                count++;
                currentSide = !currentSide; // Alternate between heads and tails
                coinLabel.setIcon(currentSide ? headsIcon : tailsIcon);

                if (count >= maxFlips) {
                    ((Timer) e.getSource()).stop();
                    boolean finalResult = coin.toss();
                    revealResult(finalResult);
                    tossButton.setEnabled(true); // Re-enable the toss button after animation
                    restartButton.setEnabled(true); // Enable restart after result
                }
            }
        });
        timer.start();
    }

    private void revealResult(boolean isHeads) {
        coinLabel.setIcon(isHeads ? headsIcon : tailsIcon);
        label.setText(isHeads ? "Heads!" : "Tails!");
        String winner = isHeads ? "Player 1 wins!" : "Player 2 wins!";
        JOptionPane.showMessageDialog(this, "The coin landed on " + (isHeads ? "Heads!" : "Tails!") + "\n" + winner, "Coin Toss Result", JOptionPane.INFORMATION_MESSAGE);
    }

    private void resetGame() {
        label.setText("It's a Tie!!! Click the button to toss the coin!");
        coinLabel.setIcon(headsIcon); // Reset to initial coin state
        tossButton.setEnabled(true);
        restartButton.setEnabled(false);
    }

    private ImageIcon scaleImageIcon(ImageIcon icon, int width, int height) {
        if (icon != null && icon.getImage() != null) {
            Image img = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
            return new ImageIcon(img);
        }
        return null; // Handle the case where the icon is null
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            CoinTossGame game = new CoinTossGame();
        });
    }
}