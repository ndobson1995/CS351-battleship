import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Scanner;

/**
 * Main driver for the program, called Captain because ships
 */

public class Captain {

    private static final int bgLength = 3;
    private static Scanner scanner;
    private static RMIinterface game;


    /**
     * run the game
     *
     * @param args normal arguments
     */
    public static void main(String[] args) throws IOException, NotBoundException {

        //DataInputStream in = new DataInputStream(socket.getInputStream());
        //MUST MATCH SERVER
        game = (RMIinterface) Naming.lookup("//localhost:11100/Battleship");
        scanner = new Scanner(System.in);


        while (true) {
            System.out.println("Play solo on CLI (1), solo on GUI(2), with someone(3) or quit(0)");
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
                        multiplayer();  //todo will ive taken out all the name parameter passed in, cos the user would choose from teh menu before they log in
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


    public static void soloGameCLI () throws RemoteException {
        System.out.println("Welcome to Battleship!");
        System.out.print("Enter your name: ");
        String name = scanner.nextLine();
        LoginPortal portal = new LoginPortal();
        portal.loginCLI(name);
        boolean cont = true;

        while (name!=null && cont) {
            BattleshipBoard playerOne = new BattleshipBoard(bgLength);
            BattleshipBoard ai = new BattleshipBoard(bgLength);

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
                Thread.sleep(3000);
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
    }


    /**
     * Controlling method for the solo game against the AI.
     */
    public static void soloGameGUI() {
        LoginPortal portal = new LoginPortal();
        String name = portal.playerNamePopulated();

    //todo WORK ON THIS get gui and potal login working- NUMBER ONE
        while(name !=null) {
            BattleshipBoard playerOne = new BattleshipBoard(bgLength);
            BattleshipBoard ai = new BattleshipBoard(bgLength);
            BoardGUI playerOneGUI = new BoardGUI(playerOne, ai, bgLength, name);
        }
        //the game is finished so the player active flag will be set to false
        setPlayerFlagToFalse(name);
    }


    public static boolean multiplayer;

    /**
     * Controlling method for the multiplayer game (2 real players)
     */
    public static void multiplayer() throws IOException {

        System.out.print("Enter your name: ");
        String name = scanner.nextLine();
//        Socket socket = new Socket("localhost", 12346);
//        Thread game = new Thread();
//        game.start();
//
//
//        InputStream in = socket.getInputStream();
//        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
//        System.out.println(reader.readLine());
//
        multiplayer = true;
        BattleshipBoard playerOne = new BattleshipBoard(bgLength);
//        BattleshipBoard ai = new BattleshipBoard(bgLength);


        game.multiplayerPlay();
        setPlayerFlagToFalse(name);

        // here is where we'd put the file checking for player one

        // and here is for player two.
//        boolean cont = true;
//        while(cont) {
//            BattleshipBoard playerOne = new BattleshipBoard(bgLength);
//            BattleshipBoard playerTwo = new BattleshipBoard(bgLength);
//
//            while (true) {
//                playerOne.printBoard();
//                playerTwo.printBoard();
//                // fire and pass in your opponents board to confirm if hit worked
//                if (fire(playerOneName, playerTwoName, playerOne, playerTwo)) break;
//                // as above, so below
//                if (fire(playerTwoName, playerOneName, playerTwo, playerOne)) break;
//            }
//            cont = rematch();
//        }
    }


//        /**
//         * FIRE
//         * @param player player firing
//         * @param opponent player being fired at
//         * @param playerBoard player firings board
//         * @param opponentBoard player being fired ats board
//         * @return boolean if it hit or not
//         */
//        private static boolean fire (String player, String opponent, BattleshipBoard playerBoard, BattleshipBoard
//        opponentBoard){
//            System.out.println(opponent + " fire");
//
//            if (playerBoard.playerFire(opponentBoard.getBoard())) {
//                opponentBoard.shipSunk();
//            }
//            if (opponentBoard.getShipsRemaining() == 0) {
//                System.out.println(player + " wins!");
//                updatePlayer(player, opponent);
//                return true;
//            }
//            return false;
//        }


    /**
     * @param winner player who won - file updated with win + games attempted
     * @param loser  player who lost - file updated with games attempted
     */
    public static void updatePlayer(String winner, String loser) {

        HashmapLeaderboard.write(winner, 1, 0);
        HashmapLeaderboard.write(loser, 0, 1);
    }

    public static void setPlayerFlagToFalse(String playerName){
        LoginPortal portal = new LoginPortal();
        portal.setActivePlayerFlagToFalse(playerName);

    }

    private static void viewLeaderboard() {
        System.out.println(HashmapLeaderboard.read());
        System.out.println();
    }

}
