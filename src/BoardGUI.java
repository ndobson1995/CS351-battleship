import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


/**
 * This is the gui class responsible for building and playing the gui version of the game.
 * We would have liked to get multiplayer working here, but ran out of time.
 */
public class BoardGUI extends JFrame {

    private final JPanel yourShips;
    private static JFrame main;
    private JMenuItem quit, help;
    private final BattleshipBoard playerBattleshipBoard;
    private final BattleshipBoard opponentBattleshipBoard;
    private final String player;
    private int hitCount = 0;
    private int aiHitCount = 0;

    /**
     * Constructor for board.
     * @param playerBattleshipBoard your board
     * @param opponentBattleshipBoard your opponents board
     * @param bgLength size of the board
     * @param player player identity
     */
    public BoardGUI(BattleshipBoard playerBattleshipBoard, BattleshipBoard opponentBattleshipBoard, int bgLength, String player){
        this.playerBattleshipBoard = playerBattleshipBoard;
        this.opponentBattleshipBoard = opponentBattleshipBoard;
        this.player = player;
        yourShips = new JPanel();
        JPanel opponentShips = new JPanel();
        yourShips.setLayout(new GridLayout(bgLength,bgLength,5,5));
        opponentShips.setLayout(new GridLayout(bgLength,bgLength,5,5));
        mainGUI();
        main.add(initOpponentBoard(opponentShips));
        main.add(initPlayerBoard(yourShips));
        main.setVisible(true);
    }


    /**
     * Main GUI (JFrame) of the board.
     */
    public void mainGUI(){
        main = new JFrame("BATTLESHIP");
        main.setSize(1000,500);
        main.setLayout(new GridLayout(1,2));
        main.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent){
                main.dispose();
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
                    main.dispose();
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


    /**
     * generate the board that holds your ships. Called once in creation and again after the player has fired
     * @param board the JPanel holding your ships
     * @return the now built board
     */
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
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        return board;
    }


    /**
     * This holds the logic for building your opponents board and also the listener for player interaction.
     * @param board your opponents board
     * @return their board after it has been built
     */
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
                            hitCount = hitCount + playerBattleshipBoard.guiFire(hitThis, finalI, finalK);
                            if(hitCount == 5){
                                JOptionPane.showMessageDialog(main, "You win!");

                                main.dispatchEvent(new WindowEvent(main, WindowEvent.WINDOW_CLOSING));
                                // here we need to return or do something to the players score

                            }
                            controlAIFiring();

                        } else if (button.getText().equals("~")) {
                            button.setBackground(Color.GREEN);
                            String[][] hitThis = opponentBattleshipBoard.getBoard();
                            aiHitCount = aiHitCount + playerBattleshipBoard.guiFire(hitThis, finalI, finalK);
                            controlAIFiring();
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
        return board;
    }


    /**
     * control the AI firing and confirm the AI has/hasn't won
     */
    private void controlAIFiring() {
        if (opponentBattleshipBoard.aiFire(playerBattleshipBoard.getPlayer())) {
            aiHitCount++;
        }
        if (aiHitCount == 5) {
            JOptionPane.showMessageDialog(main, "You Lose!");
            main.dispatchEvent(new WindowEvent(main, WindowEvent.WINDOW_CLOSING));
        }
        updateYourBoard();
    }


    /**
     * Method to update the board that represents your pieces after the opponent has fired.
     */
    private void updateYourBoard(){
        yourShips.setVisible(false);
        yourShips.removeAll();
        initPlayerBoard(yourShips);
        yourShips.setVisible(true);
    }
}
