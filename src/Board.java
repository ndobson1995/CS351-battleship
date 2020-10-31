import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Arrays;
import java.util.Random;

public class Board extends JFrame {

    public String[][] yourBoard;               // int array for the board
    public String[][] opponentBoard;               // int array for the board
    public final int bgLength = 7;    // 7x7 board is big enough. Useful to have this parameter.
    private JFrame main;
    private JMenuItem quit, help;
    private JPanel yourShips;
    private JPanel opponentShips;


    // Board constructor
    public Board(){
        yourBoard = new String[bgLength][bgLength];
        opponentBoard = new String[bgLength][bgLength];

        for (String[] strings : yourBoard) {
            // Whatever value you want to set them to
            Arrays.fill(strings, "-");
        }
        for (String[] strings : opponentBoard) {
            // Whatever value you want to set them to
            Arrays.fill(strings, "-");
        }

        mainGUI();

        // number of ships the user wants to play with. Capped at 5.
        int ships = 10;
        for(int i = 0; i < ships; i++){
            addShip(0);
            addShip(1);
        }
        initBoard();
    }


    public void mainGUI(){
        main = new JFrame("BATTLESHIP");
        main.setSize(1000,500);
        main.setLayout(new GridLayout(1,2));
        main.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent){
                System.exit(0);
            }
        });
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Menu");
        quit = new JMenuItem("Quit");
        help = new JMenuItem("Help");
        quit.addActionListener(e -> {
            if(e.getSource() == quit){
                int result = JOptionPane.showConfirmDialog((Component) e.getSource(),
                        "Close this application?");
                if (result == JOptionPane.YES_OPTION) {
                    System.exit(0);
                } else if (result == JOptionPane.NO_OPTION) {
                    System.out.println("Do nothing");
                }
            }
        });

        help.addActionListener(e -> {
            if(e.getSource() == help)
                JOptionPane.showMessageDialog(main,"Welcome");
        });

        menu.add(quit);
        menu.add(help);
        menuBar.add(menu);
        main.setJMenuBar(menuBar);
        main.setVisible(true);
    }

    public void initBoard() {

        yourShips = new JPanel();
        opponentShips = new JPanel();

        yourShips.setLayout(new GridLayout(bgLength,bgLength,5,5));
        opponentShips.setLayout(new GridLayout(bgLength,bgLength,5,5));

        //your ships
        for (String[] strings : yourBoard) {
            for (String string : strings) {
                JButton button = new JButton(string);
                button.setBorder(BorderFactory.createLineBorder(Color.black));
                button.setBackground(Color.WHITE);
                button.setFont(new Font("Verdana", Font.PLAIN, 20));
                button.setEnabled(false);
                button.setFocusPainted(false);
                button.addActionListener(actionEvent -> {});
                yourShips.add(button);
            }
        }

        //opponent
        for (String[] strings : opponentBoard) {
            for (String string : strings) {
                JButton button = new JButton(string);
                button.setFont(new Font("Verdana", Font.PLAIN, 20));
                button.setBackground(Color.WHITE);
                button.setForeground(Color.WHITE);
                
                button.addActionListener(actionEvent -> {
                    if(button.getText().equals("*")) {
                        button.setBackground(Color.RED);
                        JOptionPane.showMessageDialog(main, "Hit!");
                    }
                    else if(button.getText().equals("-")){
                        button.setBackground(Color.GREEN);
                    }
                });
                button.setFocusPainted(false);
                opponentShips.add(button);
            }
        }

        Border opponentBorder = BorderFactory.createTitledBorder("Opponent");
        opponentShips.setBorder(opponentBorder);

        Border yourBorder = BorderFactory.createTitledBorder("Your ships");
        yourShips.setBorder(yourBorder);


        setSize(400,400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        main.add(yourShips);
        main.add(opponentShips);
    }




    // add a ship
    public void addShip(int flag){
        Random rand = new Random();
        int xCoordinate = rand.nextInt(7);
        int yCoordinate = rand.nextInt(7);
        if(flag == 0) {
            if (shipAllowed(xCoordinate, yCoordinate)) {
                yourBoard[xCoordinate][yCoordinate] = "*";
            }
        }
        else{
            if (shipAllowed(xCoordinate, yCoordinate)) {
                opponentBoard[xCoordinate][yCoordinate] = "*";
            }
        }
    }

    // checks if a position is free.    - is free, * is occupied
    public boolean shipAllowed(int x, int y){
        return yourBoard[x][y].equals("-");
    }


    // this is just to print the game board so I can see it works.
    public void print() {
        System.out.println();
        System.out.println("   |0|1|2|3|4|5|6|");
        System.out.println("   ---------------");
        for(int i = 0; i < bgLength; i++){
            System.out.print(i + ": |");
            for(int j = 0; j < bgLength; j++){
                System.out.print(yourBoard[i][j] + "|");
            }
            System.out.println();
        }
    }

}
