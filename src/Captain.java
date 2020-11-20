import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.rmi.Naming;
import java.rmi.NotBoundException;
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
        System.out.println("Welcome to Battleship!");
        System.out.print("Enter your name: ");
        String name = scanner.nextLine().toLowerCase();

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
                        soloGameGUI(name);
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


    private static void viewLeaderboard() {
        HashmapLeaderboard.read();
    }


    public static void soloGameCLI(String name) {
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
                break;
            }
        }
    }


    /**
     * Controlling method for the solo game against the AI.
     */
    public static void soloGameGUI(String name) {
        BattleshipBoard playerOne = new BattleshipBoard(bgLength);
        BattleshipBoard ai = new BattleshipBoard(bgLength);
        BoardGUI playerOneGUI = new BoardGUI(playerOne, ai, bgLength, name);
    }


    public static boolean multiplayer;

    /**
     * Controlling method for the multiplayer game (2 real players)
     */
    public static void multiplayer(String name) throws IOException {
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
}
