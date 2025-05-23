
import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;



public class moveNext {
    JFrame frame=new JFrame();
    JLabel label=new JLabel("Move To Next Round?");
    JPanel panel =new JPanel();
    JButton buttonYes =new JButton("Yes!");
    JButton buttonNo=new JButton("No!");

    moveNext(int score1, int score2, String p1, String p2){
        frame.setLayout(new BorderLayout());
        frame.setSize(400,200);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        label.setFont(new Font("Arial", Font.PLAIN, 25));
        label.setHorizontalAlignment(JLabel.CENTER);

        buttonYes.setBounds(110, 70, 60, 30);
        buttonYes.setFocusable(false);

        buttonNo.setBounds(210, 70, 60, 30);
        buttonNo.setFocusable(false);

        buttonNo.addActionListener(e -> {
            frame.dispose();
        });

        buttonYes.addActionListener(e->{
            frame.dispose();
            MatchCards game= new MatchCards(score1,score2,p1,p2);
        });

        frame.add(buttonNo);
        frame.add(buttonYes);
        panel.add(label);
        frame.add(panel);
        
         frame.setVisible(true);



        
    }
}
