
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.TimerTask;
import javax.swing.*;
public class MatchCards {
    class Card{
        String cardName;
        ImageIcon cardImageIcon;

        Card(String CardName,ImageIcon cardImageIcon){
            this.cardName= cardName;
            this.cardImageIcon=cardImageIcon;
        }

        public String toString(){
            return cardName;    
        }
    }

    String[] cardList ={//track card name
        "darkness",
        "double",
        "fairy",
        "fighting",
        "fire",
        "grass",
        "lightning",
        "metal",
        "psychic",
        "water"
    };

    int rows=4;
    int columns=5;
    int cardWidth=90;
    int cardHeight=128;

    ArrayList<Card> cardSet; //create a deck of cards with cardNames and CardImageIcons
    ImageIcon cardBackImageIcon;

    int boardWidth= columns*cardWidth; //5*128=640px
    int boardHeight=410;//4*90  =360px

    int score1;
    int score2;
    String playerX;
    String playerO;
    String currentPlayer;

    JFrame frame= new JFrame("Pokemon Match Cards");
    JLabel textLabel= new JLabel();
    JPanel textPanel= new JPanel();
    JPanel boardPanel=new JPanel();


    JPanel gameScorePanel=new JPanel();
    JLabel gameScoreLabel=new JLabel();


    ArrayList<JButton>board;
    Timer hideCardTimer;
    boolean gameReady=false;
    JButton card1Selected;
    JButton card2Selected;

    MatchCards(int s1, int s2, String p1,String p2){
        this.score1=s1;
        this.score2=s2;
        this.playerX=p1;
        this.playerO=p2;
        this.currentPlayer=playerX;

        setupCards();
        shuffleCards();

        
        frame.setLayout(new BorderLayout());
        frame.setSize(boardWidth,boardHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //error text
        textLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        textLabel.setHorizontalAlignment(JLabel.CENTER);
        textLabel.setText("It's "+currentPlayer+"'s turn");

        textPanel.setPreferredSize(new Dimension(boardWidth,30));
        textPanel.add(textLabel);   
        frame.add(textPanel,BorderLayout.NORTH);

        gameScoreLabel.setBackground(Color.darkGray);
        gameScoreLabel.setForeground(Color.black);
        gameScoreLabel.setFont(new Font("Arial", Font.BOLD,30));
        gameScoreLabel.setText(p1+": "+ Integer.toString(score1)+"                   "+p2+": "+Integer.toString(score2)); //6tabs
        gameScorePanel.add(gameScoreLabel);
        frame.add(gameScorePanel,BorderLayout.SOUTH);

        //card game board
        board= new ArrayList<JButton>();
        boardPanel.setLayout(new GridLayout(rows,columns));  
        for(int i=0;i<cardSet.size();i++){
            JButton tile = new JButton();
            tile.setPreferredSize(new Dimension(cardWidth,cardHeight));
            tile.setOpaque(true);
            tile.setIcon(cardSet.get(i).cardImageIcon);
            tile.setFocusable(false);
            tile.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e){
                    if(!gameReady){
                        return;
                    }
                    JButton tile= (JButton) e.getSource();
                    if(tile.getIcon()==cardBackImageIcon){
                        if(card1Selected== null){
                            card1Selected=tile;
                            int index= board.indexOf(card1Selected);
                            card1Selected.setIcon(cardSet.get(index).cardImageIcon);
                        }
                        else if(card2Selected==null){
                            card2Selected=tile;
                            int index= board.indexOf(card2Selected);
                            card2Selected.setIcon(cardSet.get(index).cardImageIcon);

                            if(card1Selected.getIcon() != card2Selected.getIcon()){
                                currentPlayer = currentPlayer==playerX?playerO:playerX;
                                textLabel.setText("It's "+currentPlayer+"'s turn");
                                hideCardTimer.start();
                            }
                            else{
                                currentPlayer = currentPlayer==playerX?playerO:playerX;
                                textLabel.setText("It's "+currentPlayer+"'s turn");
                                if(currentPlayer==playerX){
                                    score2+=1;
                                }
                                else{
                                    score1+=1;
                                }
                                gameScoreLabel.setText(p1+": "+ Integer.toString(score1)+"                   "+p2+": "+Integer.toString(score2));
                                
                                //to display the end screen 
                                if(isGameOver()){
                                    frame.dispose();
                                    moveNext2 endScreen= new moveNext2(s2, s2, p1, p2);
                                }
                                
                                card1Selected=null;
                                card2Selected=null;

                            }
                        }
                    }
                }
            });
            board.add(tile);
            boardPanel.add(tile);
        }
        frame.add(boardPanel);



        frame.pack();
        frame.setVisible(true);

        //start game 
        hideCardTimer= new Timer(1500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                hideCards();
            }
        });
        hideCardTimer.setRepeats(false);
        hideCardTimer.start();  
        
    }

    void setupCards(){
            cardSet= new ArrayList<Card>();
            
            for(String cardName: cardList){
                //load each card image  
                Image cardImg= new ImageIcon(getClass().getResource("./img/"+cardName+".jpg")).getImage();
                ImageIcon cardImageIcon= new ImageIcon(cardImg.getScaledInstance(cardWidth,cardHeight,java.awt.Image.SCALE_SMOOTH));

                //create card object and add one to cardSet
                Card card= new Card(cardName, cardImageIcon);
                cardSet.add(card);
            }
            cardSet.addAll(cardSet);

            //load the back card image
            Image cardBackImg = new ImageIcon(getClass().getResource("./img/back.jpg")).getImage(); 
            cardBackImageIcon = new ImageIcon(cardBackImg.getScaledInstance(cardWidth, cardHeight, java.awt.Image.SCALE_SMOOTH));
    }
    void shuffleCards(){
        System.out.println(cardSet);    
        //shuffle
        for(int i =0;i<cardSet.size();i++){
            int j = (int) (Math.random()*cardSet.size()); //get random index
            //swap
            Card temp= cardSet.get(i);
            cardSet.set(i, cardSet.get(j));
            cardSet.set(j, temp);
        }
        System.out.println(cardSet);
    }

    void hideCards(){
        if(gameReady && card1Selected!=null && card2Selected!=null){//only flip 2 cards 
            card1Selected.setIcon(cardBackImageIcon);
            card1Selected=null;
            card2Selected.setIcon(cardBackImageIcon);
            card2Selected=null;
        }
        else{// flip all cards face down 
            for(int i =0;i<board.size();i++){
                board.get(i).setIcon(cardBackImageIcon);
            }
            gameReady=true; 
            //restartButton.setEnabled(true);
        }
            
    }

    boolean isGameOver(){//to check if game is over.
        boolean ans;
        if(score1+score2==11){
            ans=true;
        }
        else{
            ans=false;
        }
        return ans;    
    }
    
}
