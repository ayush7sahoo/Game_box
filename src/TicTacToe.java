import java.awt.*;
import javax.swing.*;

public class TicTacToe {
    int boardWidth = 600;
    int boardHeight = 700;
    int score1 = 0;
    int score2 = 0;

    JFrame frame = new JFrame("Tic-Tac-Toe");
    JLabel textLabel = new JLabel();
    JPanel textPanel = new JPanel();
    JPanel boardPanel = new JPanel();
    JPanel gameScorePanel = new JPanel();
    JLabel gameScoreLabel = new JLabel();
    JButton resetButton = new JButton("Reset");

    JButton[][] board = new JButton[3][3];
    String p1, p2;
    String playerX = "X";
    String playerO = "O";
    String currentPlayer = playerX;
    String currentPlayerLabel;

    boolean gameOver = false;
    int turns = 0;

    TicTacToe(String p1, String p2) {
        this.p1 = p1;
        this.p2 = p2;
        this.currentPlayerLabel = p1;

        frame.setSize(boardWidth, boardHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        textLabel.setBackground(Color.darkGray);
        textLabel.setForeground(Color.white);
        textLabel.setFont(new Font("Arial", Font.BOLD, 50));
        textLabel.setHorizontalAlignment(JLabel.CENTER);
        textLabel.setText(p1 + "'s turn");
        textLabel.setOpaque(true);

        textPanel.setLayout(new BorderLayout());
        textPanel.add(textLabel);
        frame.add(textPanel, BorderLayout.NORTH);

        boardPanel.setLayout(new GridLayout(3, 3));
        boardPanel.setBackground(Color.darkGray);
        frame.add(boardPanel);

        gameScoreLabel.setFont(new Font("Arial", Font.BOLD, 30));
        updateScoreLabel();
        gameScorePanel.add(gameScoreLabel);

        resetButton.addActionListener(e -> resetGame());
        gameScorePanel.add(resetButton);
        frame.add(gameScorePanel, BorderLayout.SOUTH);

        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                JButton tile = new JButton();
                board[r][c] = tile;
                boardPanel.add(tile);
                
                tile.setBackground(Color.darkGray);
                tile.setForeground(Color.white);
                tile.setFont(new Font("Arial", Font.BOLD, 120));
                tile.setFocusable(false);
                tile.addActionListener(e -> handleMove(tile));
            }
        }

        frame.setVisible(true);
    }

    void handleMove(JButton tile) {
        if (gameOver || !tile.getText().isEmpty()) return;

        tile.setText(currentPlayer);
        turns++;
        checkWinner();

        if (!gameOver) {
            currentPlayer = currentPlayer.equals(playerX) ? playerO : playerX;
            currentPlayerLabel = currentPlayerLabel.equals(p1) ? p2 : p1;
            textLabel.setText(currentPlayerLabel + "'s turn");
        }
    }

    void checkWinner() {
        for (int r = 0; r < 3; r++) {
            if (!board[r][0].getText().isEmpty() &&
                board[r][0].getText().equals(board[r][1].getText()) &&
                board[r][1].getText().equals(board[r][2].getText())) {
                setWinner(board[r][0].getText(), board[r]);
                return;
            }
        }

        for (int c = 0; c < 3; c++) {
            if (!board[0][c].getText().isEmpty() &&
                board[0][c].getText().equals(board[1][c].getText()) &&
                board[1][c].getText().equals(board[2][c].getText())) {
                setWinner(board[0][c].getText(), new JButton[]{board[0][c], board[1][c], board[2][c]});
                return;
            }
        }

        if (!board[0][0].getText().isEmpty() &&
            board[0][0].getText().equals(board[1][1].getText()) &&
            board[1][1].getText().equals(board[2][2].getText())) {
            setWinner(board[0][0].getText(), new JButton[]{board[0][0], board[1][1], board[2][2]});
            return;
        }

        if (!board[0][2].getText().isEmpty() &&
            board[0][2].getText().equals(board[1][1].getText()) &&
            board[1][1].getText().equals(board[2][0].getText())) {
            setWinner(board[0][2].getText(), new JButton[]{board[0][2], board[1][1], board[2][0]});
            return;
        }

        if (turns == 9) {
            textLabel.setText("It's a Tie!");
            gameOver = true;
        }
    }

    void setWinner(String winner, JButton[] tiles) {
        for (JButton tile : tiles) {
            tile.setForeground(Color.green);
            tile.setBackground(Color.gray);
        }

        textLabel.setText((winner.equals("X") ? p1 : p2) + " wins!");
        if (winner.equals("X")) score1++;
        else score2++;
        updateScoreLabel();
        gameOver = true;
        
        
        frame.dispose();
        moveNext nextGame= new moveNext(score1,score2,p1,p2);
        
    }

    void updateScoreLabel() {
        gameScoreLabel.setText(p1 + ": " + score1 + "  |  " + p2 + ": " + score2);
    }

    void resetGame() {
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                board[r][c].setText("");
                board[r][c].setForeground(Color.white);
                board[r][c].setBackground(Color.darkGray);
            }
        }
        gameOver = false;
        turns = 0;
        currentPlayer = playerX;
        currentPlayerLabel = p1;
        textLabel.setText(p1 + "'s turn");
    }
}
