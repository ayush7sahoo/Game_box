
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;



public class endScreen {
    JFrame frame=new JFrame();
    JLabel setWinner=new JLabel();
    JPanel panel =new JPanel();
    JButton buttonYes =new JButton("Restart!");
    JButton buttonNo=new JButton("Exit!");

    endScreen(int score1, int score2, String p1, String p2){

        String winner="";
        if(score1>score2){
            winner=p1;
        }
        else if(score2>score1){
            winner=p2;
        }
        

        frame.setLayout(new BorderLayout());
        frame.setSize(500,200);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        setWinner.setFont(new Font("Arial", Font.PLAIN, 25));
        setWinner.setText("The Winner is: "+winner+"!!!!");
        setWinner.setHorizontalAlignment(JLabel.CENTER);

        buttonYes.setBounds(100, 70, 100, 30);
        buttonYes.setFocusable(false);

        buttonNo.setBounds(300, 70, 100, 30);
        buttonNo.setFocusable(false);

        buttonNo.addActionListener(e -> {
            GameStartScreen g=new GameStartScreen();
            frame.dispose();
        });

        buttonYes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                startGame g=new startGame();
                
            }
        });

        frame.add(buttonNo);
        frame.add(buttonYes);
        panel.add(setWinner);
        frame.add(panel);
        
         frame.setVisible(true);
                 
    }
}
