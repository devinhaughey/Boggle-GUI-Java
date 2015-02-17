//Devin Haughey
//Boggle GUI
//Assignment 3
// ID: 010298575

//package boggle1;

import java.awt.Color;
import java.awt.Font;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JPanel;
import java.awt.event.*;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Random;
import java.util.Collections;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;



class Boggle1 {
	String dice;
        String tmp;
	boolean[][] used;
        char[][] board;
        int m = 0;
        ArrayList compWords = new ArrayList();

	Boggle1(String d) {
		if(d.length() != 16)
			throw new IllegalArgumentException("Wrong size");
		//this.dice = d;
                //d = "xcybdjmztvlnrfhi";
                //d = "abcdefghijklmnop";
                //System.out.println(d);
		this.used = new boolean[4][4];
                this.board = new char[4][4];
                int k = 0;
                
                for(int i = 0; i < 4; i++) {
			for(int j = 0; j < 4; j++) {
				board[j][i] = d.charAt(k);
                                //System.out.println(board[j][i]);
                                k++;
			}
		}

	}
	public boolean check(int x, int y, String remainder) {
	// todo: Your job is to implement this method.
            used[x][y] = true;
            //System.out.println(remainder);

            if (remainder.charAt(m) == board[x][y] ){
                tmp = tmp+board[x][y];
                //System.out.println(tmp);
                if(tmp.contentEquals(remainder))
                {
                //System.out.println(remainder);
                compWords.add(remainder);
                m=0;
                tmp="";
                return true;
                }
                m++;
            
            if ( m <= remainder.length() )
            {
                
    //upper left
    if (0 <= x - 1 && 0 <= y - 1 && remainder.charAt(m) == board[x -1][y - 1] && !used[x -1][y - 1]){
        //System.out.println("UL");
      check(x-1, y-1, remainder);
    }
 
    //up
    if (0 <= y - 1 &&  remainder.charAt(m) == board[x][y-1] && !used[x][y - 1]){
      //System.out.println("U");
        check( x, y-1, remainder);
    }
 
    //upper right
    if (x + 1 < 4 && 0 <= y - 1 && remainder.charAt(m) == board[x+1][y-1] && !used[x+1][y - 1]){
      //System.out.println("UR");
        check(x+1, y-1, remainder);
    }

    //right
    if (x + 1 < 4 && remainder.charAt(m) == board[x+1][y] && !used[x+1][y]){
      //System.out.println("R");
        check(x+1, y, remainder);
    }
 
    //lower right
    if (x+1 < 4 && y+1 < board.length && remainder.charAt(m) == board[x+1][y+1] && !used[x+1][y+1]){
      //System.out.println("LR");
        check(x+1, y+1, remainder);
    }
 
    //down
    if (y + 1 < 4 && remainder.charAt(m) == board[x][y+1] && !used[x][y+1]){
      //System.out.println("D");
        check(x, y+1, remainder);
    }
 
    //lower left
    if (0 <= x - 1 && y + 1 < 4 && remainder.charAt(m) == board[x-1][y+1] && !used[x -1][y+1]){
      //System.out.println("LL");
        check(x-1, y+1, remainder);
    }
 
    //left
    if (0 <= x - 1 &&  remainder.charAt(m) == board[x-1][y] && !used[x -1][y]){
      //System.out.println("L");
        check(x-1, y, remainder);
    }
            
        }
            }
                tmp ="";
                m = 0;
		return false;
            
            
   
	}

	public boolean isFound(String word) {
		for(int y = 0; y < 4; y++) {
			for(int x = 0; x < 4; x++) {
                            for(int n = 0; n < 4; n++) {
                                for(int p = 0; p < 4; p++) {
                                    used[p][n] = false;
                                }
                            }
				if(check(x, y, word)){
                                    System.out.println(compWords.size());
					return true;
                                }
			}
		}
		return false;
	}

	public static void main(String[] args) throws FileNotFoundException {
		// Commented old code out
                //Boggle1 b = new Boggle1([] args);
		//Scanner s = new Scanner(new File("lexicon.txt"));
                
                // Calling the GUI Boggle class
                BoggleGUI theBoard = new BoggleGUI();
                theBoard.setVisible(true);
                
                // Commented old code out
		//while(s.hasNextLine()) {
		//	String word =  s.nextLine();
		//	if(b.isFound(word))
		//		System.out.println(word);
		//}
	}

}

class BoggleGUI extends JFrame implements ActionListener {
	JTextArea ta;
        
        // Declaring Variables
        private JPanel diceTray;
        private JButton startButton;
        
        private JLabel wordLabel;
        private final JLabel[][] letters;
        private JLabel finishTime;
        private JLabel fTime;
        
        private JTextField userWords;
        private JLabel numComp;
        private JLabel numUser;
        private JLabel numDiff;
        
        private ArrayList bagDice;
        private ArrayList<String> lex;
        private ArrayList<String> subWords= new ArrayList();
        public ArrayList<String> cWords = new ArrayList();
        
        private String list;
        
        
	BoggleGUI()  throws FileNotFoundException{
		this.ta = new JTextArea();
   
                windowLayout();
                letters = new JLabel[4][4];
                chooseDice();            
                startButton.addActionListener(this);    
                userWords.addActionListener(this);
                
                lex = new ArrayList();
                Scanner s = new Scanner(new File("lexicon.txt"));
                while(s.hasNextLine()) {
                    String word = s.nextLine();
                    lex.add(word);
                }
                
                
                
        }    
        
        public void newGame(String[] listofLetters){
            
            this.setBoggle(listofLetters);
            chooseDice();
            compList();
            subWords = new ArrayList();
            subWords.clear();
        }
        
        public void compList ()
        {
            Boggle1 b = new Boggle1(list);
            cWords = new ArrayList();
            
            
            
            Iterator iter = lex.iterator();
                while(iter.hasNext())
                {
                    String compW;
                    compW = iter.next().toString();

                    if(b.isFound(compW)){
                        cWords.add(compW);
                        
                    }
                    
                }
                cWords.addAll(b.compWords);
                
        }
                
            private void windowLayout(){
                this.setSize(500,270);
                this.setResizable(false);
                setLocation(10,10);
                setTitle("Boogle");
                setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                setLayout(null);
                
                // Adds all the boards GUI components
                diceTray = new JPanel();
                //diceTray.setEditable(false);
                diceTray.setBackground(Color.GRAY);
                diceTray.setFont(new Font("Courier", Font.BOLD, 24));
                
                diceTray.setSize(210, 220);
                diceTray.setLocation(10,10);
                diceTray.setLayout(new GridLayout(4,4));
                add(diceTray);
                                
                startButton =  new JButton("Begin");
                startButton.setSize(100, 40);
                startButton.setLocation(310, 10);
                startButton.setVisible(true);
                add(startButton);
                
                wordLabel = new JLabel("Enter letters here: ");
                wordLabel.setSize(200, 40);
                wordLabel.setLocation(240, 60);
                wordLabel.setVisible(true);
                add(wordLabel);
                
                fTime = new JLabel("Finish Time: ");
                fTime.setSize(200, 40);
                fTime.setLocation(240, 200);
                fTime.setVisible(true);
                add(fTime);
                
                finishTime = new JLabel("");
                finishTime.setSize(200, 60);
                finishTime.setLocation(320, 190);
                finishTime.setVisible(true);
                add(finishTime);               
                
                numUser = new JLabel("Valid entered words: ");
                numUser.setSize(200, 60);
                numUser.setLocation(240, 80);
                numUser.setVisible(true);
                add(numUser);
                
                numComp = new JLabel("Number found by Computer: ");
                numComp.setSize(240, 60);
                numComp.setLocation(240, 110);
                numComp.setVisible(true);
                add(numComp);
                
                numDiff = new JLabel("Words found not by Computer: ");
                numDiff.setSize(240, 60);
                numDiff.setLocation(240, 140);
                numDiff.setVisible(true);
                add(numDiff);
                
                userWords = new JTextField("");
                userWords.setSize(100,20);
                userWords.setLocation(360, 70);
                userWords.setVisible(true);
                add(userWords);

            }
            
                public void actionPerformed(ActionEvent e){
                    
                    long startTime=0;
                    long endTime;     
                    long finish;

                    // Adds words entered by the user into a arraylist
                    if(!"".equals(userWords.getText()))
                    {
                        String words = userWords.getText();
                        subWords.add(words);
                        userWords.setText("");
                    }
                    
                    String text = (String)e.getActionCommand();
                    
                    // Changes the txt on the button, starts the time, begins
                    // the game clearing score and generating a new board.
                    if(text.equals("Begin")){
                        startButton.setText("Done");
                        startTime = System.currentTimeMillis();
                        diceTray.removeAll();
                        diceTray.updateUI();
                        newGame(this.getRandBoard());
                        finishTime.setText("");
                        userWords.setText("");
                        numUser.setText("Valid entered words: ");
                        numComp.setText("Number found by Computer: ");
                        numDiff.setText("Words found not by Computer: ");        
                        subWords.clear();
                        
                    }
                    
                    // Changes the text on Button and finds the time along with 
                    // calling the gameFinished() function.
                    if(text.equals("Done")){
                        startButton.setText("Begin");
                        endTime = System.currentTimeMillis();
                        finish = TimeUnit.MILLISECONDS.toSeconds(endTime - startTime);
                        gameFinished(finish);
                    }
                    
                }
                
                public void gameFinished(long finish){
                    
                    int subUser = 0;
                    int cSize = 0;
                    int diff = 0;
                    
                    finishTime.setText("" + finish);
                    
                    int k = 0;

                    // Finds which words were submitted that are valid
                    for(int z =0; z < subWords.size(); z++)
                    {
                        if(k < cWords.size())
                        {
                            for(int j =0; j < cWords.size(); j++ )
                            {
                                String sWord = "";
                                String cWord = "";
                        
                                sWord = subWords.get(k);
                                cWord = cWords.get(j);
                                if(sWord.contains(cWord))
                                {
                                 //System.out.println(sWord);
                                 //System.out.println(k);
                                   subUser++;
                             } 
                            }
                        }
                        k++;
                       
                    }
                    
                    numUser.setText("Valid entered words: " + subUser);                       
                    
                    // Finds the size of the words the computer found from the board
                    if(cWords.size()<=0)
                    {
                        cSize = 0;
                    }
                    else
                    {
                        cSize = cWords.size();
                    }
                    numComp.setText("Number found by Computer: " + cSize);
                    
                    // Figues out the amount that the user submitted against the computer
                    if(subWords.size() > 0)
                    {
                        diff = subWords.size() - subUser;
                    }
                    
                    if(diff <=0)
                    {
                        diff = 0;
                    }
                    
                    numDiff.setText("Words found not by Computer: " + diff);
                    
                }
                
    
       // chooseDice contains 16 "official" Boggle dice for the 4X4 game
        private void chooseDice() 
        {
            bagDice = new ArrayList();
            bagDice.add(new Die("A", "O", "B", "B", "O", "J"));
            bagDice.add(new Die("W", "H", "G", "E", "E", "N"));
            bagDice.add(new Die("N", "R", "N", "Z", "H", "L"));
            bagDice.add(new Die("N", "A", "E", "A", "G", "E"));
            bagDice.add(new Die("D", "I", "Y", "S", "T", "T"));
            bagDice.add(new Die("I", "E", "S", "T", "S", "O"));
            bagDice.add(new Die("A", "O", "T", "T", "W", "O"));
            bagDice.add(new Die("H", "A", "U", "M", "N", "I"));
            bagDice.add(new Die("R", "Y", "T", "L", "T", "E"));
            bagDice.add(new Die("P", "O", "H", "C", "S", "A"));
            bagDice.add(new Die("L", "R", "E", "V", "Y", "D"));
            bagDice.add(new Die("E", "X", "L", "D", "I", "R"));
            bagDice.add(new Die("I", "E", "N", "S", "U", "E"));
            bagDice.add(new Die("S", "F", "F", "K", "A", "P"));
            bagDice.add(new Die("I", "O", "T", "M", "U", "C"));
            bagDice.add(new Die("E", "H", "W", "V", "T", "R"));
        }
        
        public String[] getRandBoard()  
        {
            // Get random faces that are random to populate listofLetters
            int bagDiceSize = bagDice.size();
            int bSize = 16;
            String[] listofLetters = new String[16];
            // we try to handle any size board with a fixed bagDice
            for (int i = 0; i<bSize; i++) 
            {
                if(i % bagDiceSize == 0 )  Collections.shuffle(bagDice);
                Die d = (Die) bagDice.get(i % bagDiceSize);
                listofLetters[i] =   d.getRandomFace();
            }
            // build board for the JPanels
            return listofLetters;
        }
        
            public void setBoggle(String[] listofLetters)  
        {
            // Declare variables
            int i =0;
            list = "";
            
            if(listofLetters == null ||
                    listofLetters.length != 16) 
            {
                throw new IllegalArgumentException(
                        "setBoggle(): String array length is not " + 16);
            }

            //Set board with given letters from list
            for (int row = 0; row < 4; row++)  
            {
                for (int col = 0; col < 4; col++)  
                {
                    // Sets the values of the board to the JLabel so the player 
                    // can see the board
                    letters[row][col] = new JLabel(listofLetters[i], SwingConstants.CENTER);
                             diceTray.add(letters[row][col]);
                             // creates the String for the computers board
                             list = list + listofLetters[i].toString();
                             i++;
                }
            }
            
            // Had issues with computer not reading random boards, this fixed it
            // The board was taking in a upper case String to which it couldn't
            // read which is why the computer would never return anything.
            list = list.toLowerCase();
            System.out.println(list);
        }
        
        static class Die 
        {
            private Random randomizer = new Random();
            private String[] dSides;
            private int faceSide;
            
            // Sets the die faces variables
            public Die(String face1, String face2, String face3,
                    String face4, String face5, String face6)    
            {
                dSides = new String[] {face1,face2,face3,face4,face5,face6};
                randomize();
            }

            // Finds the letter of the side of that particular die
            public String getLetter()    
            {
                return dSides[faceSide];
            }

            public String getRandomFace()    
            {
                randomize();
                return dSides[faceSide];
            }

            private void randomize()    
            {
                final int Sides = 6;
                faceSide = randomizer.nextInt(Sides);
            }
        } 
        }