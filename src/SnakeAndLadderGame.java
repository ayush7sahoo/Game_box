import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

public class SnakeAndLadderGame extends JFrame {

    private int player1Position = 1;
    private int player2Position = 1;
    private String currentPlayer; 
    private boolean gameWon = false;

    //constructor parameters
    String p1;
    String p2;
    int s1;
    int s2;

    private Map<Integer, Integer> snakesAndLadders = new HashMap<>();
    private Map<Integer, JLabel> squareLabels = new HashMap<>();

    // all GUI components
    private JLabel statusLabel;
    private JButton rollButton;
    private Random random = new Random();

    ImageIcon player1Icon = new ImageIcon("player1SD.png");
    ImageIcon  player2Icon= new ImageIcon("player2SD.png");

    public SnakeAndLadderGame(int sc1, int sc2, String p1, String p2) {

        this.currentPlayer=p1;
        this.s1=sc1;
        this.s2=sc2;
        this.p1=p1;
        this.p2=p2;

        // for configure main window
        setTitle("Snake and Ladder");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 600);
        JPanel mainPanel = new JPanel(new BorderLayout());

        //generating the path for background image
        BufferedImage background = null;
        try {
            String currentDirectory = System.getProperty("user.dir");
            String imagePath = currentDirectory + File.separator + "board.jpg";
            background=ImageIO.read(new File(imagePath));

        } catch (Exception e) {
            System.err.println("Error loading background image: " + e.getMessage());
            System.exit(1);
        }

        BoardPanel boardPanel = new BoardPanel(background);

        //10x10 grid of labels
        for (int r = 0; r < 10; r++) {
            for (int c = 0; c < 10; c++) {
                //square number (boustrophedon: bottom row is game row 0)
                int gameRow = 9 - r; // r=0 is top, gameRow=9; r=9 is bottom, gameRow=0
                int number;
                if (gameRow % 2 == 0) {
                    // even game rows: left to right
                    number = gameRow * 10 + c + 1;
                } else {
                    // odd game rows: right to left
                    number = (gameRow + 1) * 10 - c;
                }
                JLabel label = new JLabel("", SwingConstants.CENTER);
                label.setOpaque(false);
                boardPanel.add(label);
                squareLabels.put(number, label);
            }
        }

        JPanel controlPanel = new JPanel();
        statusLabel = new JLabel("Player 1's turn. Click Roll Die.");
        rollButton = new JButton("Roll Die");
        controlPanel.add(statusLabel);
        controlPanel.add(rollButton);

        // assemble all GUI
        mainPanel.add(boardPanel, BorderLayout.CENTER);
        mainPanel.add(controlPanel, BorderLayout.SOUTH);
        add(mainPanel);
        setVisible(true);


        // Ladders coordinates
        snakesAndLadders.put(9, 27);
        snakesAndLadders.put(18, 37);
        snakesAndLadders.put(25, 54);
        snakesAndLadders.put(28, 51);
        snakesAndLadders.put(56, 64);
        snakesAndLadders.put(68, 88);
        snakesAndLadders.put(76, 97);
        snakesAndLadders.put(79, 100);
        // Snakes coordinates
        snakesAndLadders.put(16, 7);
        snakesAndLadders.put(59, 17);
        snakesAndLadders.put(63, 19);
        snakesAndLadders.put(67, 30);
        snakesAndLadders.put(87, 24);
        snakesAndLadders.put(93, 69);
        snakesAndLadders.put(95, 75);
        snakesAndLadders.put(99, 77);

        this.setLocationRelativeTo(null);

        //setting size of board
        this.setSize(750,850);
        this.setResizable(false);

        updateBoard();

        rollButton.addActionListener(e -> {
            if (gameWon) {
                if(player1Position==100){
                    s1+=2;
                }
                else if(player2Position==100){
                    s2+=2;
                }
                if(s1==s2){
                    SwingUtilities.invokeLater(() -> {
                    CoinTossGame game = new CoinTossGame();
                });
                }
                else{
                endScreen endScreen=new endScreen(s1,s2,p1,p2);
                this.dispose();
                }
                
            }
            int dieRoll = random.nextInt(6) + 1;
            int currentPosition = (currentPlayer == p1) ? player1Position : player2Position;
            int potential = currentPosition + dieRoll;
            String status = "Player " + currentPlayer + " rolled a " + dieRoll;

            if (potential > 100) {
                status += ", but cannot move.";
            } else {
                // Move player
                if (currentPlayer == p1) player1Position = potential;
                else player2Position = potential;
                status += ", moved to " + potential;

                // check for snake or ladder
                if (snakesAndLadders.containsKey(potential)) {
                    int newPos = snakesAndLadders.get(potential);
                    status += ", then " + (newPos > potential ? "climbed to " : "slid to ") + newPos;
                    if (currentPlayer == p1) player1Position = newPos;
                    else player2Position = newPos;
                }

                if (player1Position == 100 || player2Position == 100) {
                    gameWon = true;
                    status += ". Player " + currentPlayer + " wins!";
                    rollButton.setText("Finish");
                } else {
                    currentPlayer = currentPlayer==p1?p2:p1;; // switch players
                    status += ". Player " + currentPlayer + "'s turn.";
                }
            }
            statusLabel.setText(status);
            updateBoard();
        });
    }


    private void updateBoard() {
        for (int num = 1; num <= 100; num++) {
            ImageIcon players=new ImageIcon("./plain.png");
            
            if (player1Position == num) players = player1Icon;
            if (player2Position == num) players = player2Icon;
            squareLabels.get(num).setIcon(players);
        }
    }

    class BoardPanel extends JPanel {
        private BufferedImage background;

        public BoardPanel(BufferedImage background) {
            this.background = background;
            setLayout(new GridLayout(10, 10));
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (background != null) {
                g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
            }
        }
    }    
}