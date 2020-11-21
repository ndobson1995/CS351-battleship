import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

/**
 * This is the captain controlling the main game. It's called as a thread by main.
 * This holds the call to the single player games, but not the multiplayer, as that runs over the RMI.
 */
public class Captain extends UnicastRemoteObject implements Runnable {

    private static final long serialVersionUID = 1L;
    private final String name;
    private static final int BG_LENGTH = 3;


    /**
     * Constructor for game captain
     * @param name name of player
     * @throws RemoteException in case of connection issues
     */
    protected Captain(String name) throws RemoteException {
        this.name = name;
    }


    /**
     * Run the main menu of the game, in a loop.
     */
    @Override
    public void run() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to Battleship, " +  name + "!");

        while (true) {

            System.out.println("Play solo on CLI (1), solo on GUI(2), with someone(3), view the leaderboard(4) or quit(0)");
            int choice;
            try {
                String choiceStr = scanner.nextLine();
                // confirm a number was entered.
                if (choiceStr.matches("[0-9][0-9]*")) {
                    choice = Integer.parseInt(choiceStr);
                    if (choice == 1) {
                        soloGameCLI(name);
                    } else if (choice == 2) {
                        soloGameGUI(name, BG_LENGTH);
                    } else if (choice == 3) {
                        multiplayer(name);
                    } else if (choice == 4) {
                        viewLeaderboard();
                    } else if (choice == 0) {
                        System.out.println("Thanks for playing!");
                        System.exit(0);
                    } else {
                        System.out.println("Please enter 1, 2, 3 or 0");
                    }
                } else {
                    System.out.println("Please enter a number");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * initiate a single player game with a gui
     * @param name name of player
     * @param bgLength size of board
     */
    public void soloGameGUI(String name, int bgLength){
        BattleshipBoard playerOne = new BattleshipBoard(bgLength);
        BattleshipBoard ai = new BattleshipBoard(bgLength);
        BoardGUI playerOneGUI = new BoardGUI(playerOne, ai, bgLength, name);
    }


    /**
     * initiate a single player game on the command line
     * @param name name of player
     */
    public void soloGameCLI(String name){
        BattleshipBoard playerOne = new BattleshipBoard(BG_LENGTH);
        BattleshipBoard ai = new BattleshipBoard(BG_LENGTH);

        while (true) {
            playerOne.printBoard();
            // fire and pass in your opponents board to confirm if hit worked
            if (playerOne.playerFire(ai.getBoard())) {
                ai.shipSunk();
            }
            // check to see if the enemies ships are all gone
            if (ai.getShipsRemaining() == 0) {
                System.out.println(name + " wins!");
                updatePlayer(name, "AI");
                break;
            }

            // slow down the game for a more natural play
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // as above, so below
            if (ai.aiFire(playerOne.getBoard())) {
                playerOne.shipSunk();
            }
            if (playerOne.getShipsRemaining() == 0) {
                System.out.println("Computer wins!");
                updatePlayer("AI", name);
                break;
            }
        }
    }


    /**
     * @param winner player who won - file updated with win + games attempted
     * @param loser  player who lost - file updated with games attempted
     */
    public static void updatePlayer(String winner, String loser) {

        HashmapLeaderboard.write(winner, 1, 0);
        HashmapLeaderboard.write(loser, 0, 1);
    }


    /**
     * View the leaderboard
     */
    public void viewLeaderboard(){
        System.out.println(HashmapLeaderboard.read());
        System.out.println();
    }


    /**
     * initiate a multiplayer game
     * @param name name of player
     *
     */
    private void multiplayer(String name) throws RemoteException, InterruptedException, MalformedURLException, NotBoundException {
        BattleshipBoard player = new BattleshipBoard(BG_LENGTH);
        Thread thread = new Thread(new Thread(new GameClient(name, player, (GameServerInterface) Naming.lookup("//localhost:13131/Battleship"))));
        thread.start();

        // pause the thread that called this method so it doesn't interfere with the multiplayer game!
        synchronized(this){
            while (thread.isAlive()){
                this.wait();
            }
        }
    }
}


