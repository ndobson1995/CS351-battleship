import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Board extends JFrame implements Runnable {

    private JPanel yourShips;
    private JPanel opponentShips;
    private static JFrame main;
    private JMenuItem quit, help;
    private BattleshipBoard playerBattleshipBoard;
    private BattleshipBoard opponentBattleshipBoard;


    // Board constructor
    public Board(BattleshipBoard playerBattleshipBoard, BattleshipBoard opponentBattleshipBoard, int bgLength){
        this.playerBattleshipBoard = playerBattleshipBoard;
        this.opponentBattleshipBoard = opponentBattleshipBoard;
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

    public JPanel initOpponentBoard(JPanel board) {
        for (String[] strings : opponentBattleshipBoard.getPlayer()) {
            for (String string : strings) {
                JButton button = new JButton(string);
                button.setFont(new Font("Verdana", Font.PLAIN, 20));
                button.setBackground(Color.WHITE);
                button.setForeground(Color.WHITE);
                button.addActionListener(actionEvent -> {
                    if (button.getText().equals("o")) {
                        button.setBackground(Color.RED);
                        JOptionPane.showMessageDialog(main, "Hit!");
                        button.setBackground(Color.RED);
                    } else if (button.getText().equals("~")) {
                        button.setBackground(Color.GREEN);
                        System.out.println(button.getX() + " " + button.getY());
                    }
                });

                button.setFocusPainted(false);
                board.add(button);
            }
        }
        board.setBorder(BorderFactory.createTitledBorder("Firing Range"));
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        return board;
    }



    @Override
    public void run() {

    }
}
