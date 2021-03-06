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
    private static final int BG_LENGTH = 5;
    private Scanner scanner;
    private static LoginPortal portal;


    /**
     * Constructor for game captain
     *
     * @throws RemoteException in case of connection issues
     */
    protected Captain() throws RemoteException {
    }


    public static int getBG_LENGTH(){
        return BG_LENGTH;
    }

    /**
     * Run the main menu of the game, in a loop.
     */
    @Override
    public void run() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to Battleship!");

        while (true) {

            System.out.println("Play solo on CLI (1), solo on GUI(2), with someone(3), view the leaderboard(4) or quit(0)");
            int choice;
            try {
                String choiceStr = scanner.nextLine();
                // confirm a number was entered.
                if (choiceStr.matches("[0-9][0-9]*")) {
                    choice = Integer.parseInt(choiceStr);
                    if (choice == 1) {
                        soloGameCLI();
                    } else if (choice == 2) {
                        soloGameGUI();
                    } else if (choice == 3) {
                        multiplayer();
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
     * @throws RemoteException Base game for cli battleship
     *                         Prompts login - player must log in for the board to display
     *                         Manages hits/misses and displays update to player
     *                         Displays winner of game
     */
    public void soloGameCLI() throws RemoteException {
        System.out.print("Enter your name: ");
        scanner = new Scanner(System.in);
        String name = scanner.nextLine();
        LoginPortal portal = new LoginPortal();

        if(portal.loginCLI(name)) {
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
                    setPlayerFlagToFalse(name);
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
                    setPlayerFlagToFalse(name);
                    break;
                }
            }
        }
        else{
            System.out.println("Player already connected.");
        }
    }


    /**
     * Controlling method for the solo game against the AI.
     */
    public static void soloGameGUI() {
        portal = new LoginPortal();
        portal.createPage();
        String name = null;

        while (name == null) {
            name = portal.playerNamePopulated();
            BattleshipBoard playerOne = new BattleshipBoard(BG_LENGTH);
            BattleshipBoard ai = new BattleshipBoard(BG_LENGTH);
            BoardGUI playerOneGUI = new BoardGUI(playerOne, ai, BG_LENGTH, name);
        }
        //the game is finished so the player active flag will be set to false
        setPlayerFlagToFalse(name);
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
     * @param playerName - sets to boolean value if player is already playing or not
     */
    public static void setPlayerFlagToFalse(String playerName) {
        portal = new LoginPortal();
        portal.setActivePlayerFlagToFalse(playerName);

    }

    /**
     * Displays leaderboard - shows player, wins, losses and games played
     */
    private static void viewLeaderboard() {
        HashmapLeaderboard.print();
        System.out.println();
    }


    /**
     * initiate a multiplayer game
     */
    private void multiplayer() throws RemoteException, InterruptedException, MalformedURLException, NotBoundException {

        System.out.print("Enter your name: ");
        scanner = new Scanner(System.in);
        String name = scanner.nextLine();
        portal = new LoginPortal();
        portal.loginCLI(name);

        Thread thread = new Thread(new Thread(new GameClient(name,
                (GameServerInterface) Naming.lookup("//localhost:33333/Battleship"), BG_LENGTH)));

        thread.start();


        // pause the thread that called this method so it doesn't interfere with the multiplayer game!
        synchronized (this) {
            while (thread.isAlive()) {
                this.wait(1000);
            }
        }
        setPlayerFlagToFalse(name);
    }
}


