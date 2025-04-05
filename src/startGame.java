import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class startGame {
    static JFrame frame;
    static JTextField pt1;
    static JTextField pt2;
    static JButton pb1;
    static JButton pb2;
    static JButton start;
    static JLabel head;

    public startGame() {
        pt1=new JTextField("PlayerX");
        pt2=new JTextField("PlayerY");

        pt1.setFont(new Font("Comic Sans",Font.PLAIN,15));
        pt2.setFont(new Font("Comic Sans",Font.PLAIN,15));

        pt1.setBounds(20, 60, 250, 40);
        pt2.setBounds(20, 120, 250, 40);

        pb1=new JButton("Confirm");
        pb2 =new JButton("Confirm");
        start=new JButton("Start Game");

        start.setBounds(140,200,120,30);
        start.setFocusable(false);

        pb1.setBounds(290, 60, 90, 40);
        pb2.setBounds(290, 120, 90, 40);

        pb1.setFocusable(false);
        pb2.setFocusable(false);

        pb1.addActionListener(e ->{
            pt1.setEditable(false);
            pb1.setEnabled(false);
        });

        pb2.addActionListener(e ->{
            pt2.setEditable(false);
            pb2.setEnabled(false);
        });

        start.addActionListener(e ->{
            if((pb1.isEnabled()) || (pb2.isEnabled())){
                JOptionPane.showMessageDialog(null, "Confirm Your Name First!!", "Error", JOptionPane.ERROR_MESSAGE);
                
            }else{
                //code to open the next game
                TicTacToe game= new TicTacToe(pt1.getText(), pt2.getText());
                
                frame.dispose();
            }
        });

        frame=new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400,300);
        frame.setTitle("2-Player Game");
        frame.setResizable(false);
        frame.setLayout(null);
        frame.setLocationRelativeTo(null);

        head=new JLabel("Enter Your Names:");
        head.setBounds(110, 10, 200, 30);
        head.setFont(new Font("Comic Sans",Font.PLAIN,20));
        
        frame.add(head);
        frame.add(pt1);
        frame.add(pt2);
        frame.add(pb1);
        frame.add(pb2);
        frame.add(start);
        frame.setVisible(true);

    }
    

}