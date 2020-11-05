import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class BoardGUI extends JFrame {

    private JPanel yourShips;
    private JPanel opponentShips;
    private static JFrame main;
    private JMenuItem quit, help;
    private BattleshipBoard playerBattleshipBoard;
    private BattleshipBoard opponentBattleshipBoard;
    private String player;


    // Board constructor
    public BoardGUI(BattleshipBoard playerBattleshipBoard, BattleshipBoard opponentBattleshipBoard, int bgLength, String player){
        this.playerBattleshipBoard = playerBattleshipBoard;
        this.opponentBattleshipBoard = opponentBattleshipBoard;
        this.player = player;
        yourShips = new JPanel();
        opponentShips = new JPanel();
        yourShips.setLayout(new GridLayout(bgLength,bgLength,5,5));
        opponentShips.setLayout(new GridLayout(bgLength,bgLength,5,5));
        mainGUI();
        main.add(initPlayerBoard(yourShips));
        main.add(initOpponentBoard(opponentShips));
        main.setVisible(true);
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
        main.setUndecorated(true);
    }

    public JPanel initPlayerBoard(JPanel board) {
        for (String[] strings : playerBattleshipBoard.getPlayer()) {
            for (String string : strings) {
                JButton button = new JButton(string);
                button.setFont(new Font("Verdana", Font.PLAIN, 20));


                if(button.getText().equals("*")){
                    button.setBackground(Color.GREEN);
                }
                else if(button.getText().equals("X")){
                    button.setBackground(Color.RED);
                }


                button.setFocusPainted(false);
                button.setEnabled(false);
                board.add(button);
            }
        }
        board.setBorder(BorderFactory.createTitledBorder("Your Board"));
        setSize(400,400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        return board;
    }


    public int hitCount = 0;
    public JPanel initOpponentBoard(JPanel board) {
        int i = 0;
        for (String[] strings : opponentBattleshipBoard.getPlayer()) {
            int k = 0;
            for (String string : strings) {
                JButton button = new JButton(string);
                button.setFont(new Font("Verdana", Font.PLAIN, 20));
                button.setBackground(Color.WHITE);
                button.setForeground(Color.WHITE);
                int finalI = i;
                int finalK = k;
                button.addActionListener(actionEvent -> {

                        if (button.getBackground() == Color.RED || button.getBackground() == Color.GREEN){
                            JOptionPane.showMessageDialog(main, "Already fired here, please try again!");
                        } else {
                            if (button.getText().equals("o")) {
                                button.setBackground(Color.RED);
                                JOptionPane.showMessageDialog(main, "Hit!");
                                String[][] hitThis = opponentBattleshipBoard.getBoard();
                                button.setBackground(Color.RED);
                                hitCount = hitCount + playerBattleshipBoard.guiFire(hitThis, finalI, finalK, player, opponentBattleshipBoard);
                                System.out.println(hitCount);
                                if(hitCount == 5){
                                    JOptionPane.showMessageDialog(main, "You win!");


                                    // here we need to return or do something to the players score


                                    main.dispatchEvent(new WindowEvent(main, WindowEvent.WINDOW_CLOSING));
                                }
                            } else if (button.getText().equals("~")) {
                                button.setBackground(Color.GREEN);
                                System.out.println(button.getX() + " " + button.getY());


                                String[][] hitThis = opponentBattleshipBoard.getBoard();
                                playerBattleshipBoard.guiFire(hitThis, finalI, finalK, player, opponentBattleshipBoard);


                            }
                        }
                });


                button.setFocusPainted(false);
                board.add(button);
                k++;
            }
            i++;
        }
        board.setBorder(BorderFactory.createTitledBorder("Firing Range"));
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        return board;
    }
}
