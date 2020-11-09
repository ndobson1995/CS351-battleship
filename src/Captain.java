import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
/**
 * Main driver for the program, called Captain because ships
 */

public class Captain {

    private static final int bgLength = 5;
    private static Scanner scanner;

    /**
     * run the game
     * @param args normal arguments
     */
    public static void main(String[] args) {

        String host = "127.0.0.1";
        int port = 12346;

        try(Socket socket = new Socket(host, port)) {
            scanner = new Scanner(System.in);


            // basic menu
            while (true) {
                System.out.println("Welcome to Battleship!");

                System.out.println("Play solo(1), with someone(2) or quit(0)");
                int choice;
                try {
                    String choiceStr = scanner.nextLine();
                    // confirm a number was entered.
                    if (choiceStr.matches("[0-9][0-9]*")) {
                        choice = Integer.parseInt(choiceStr);
                        if (choice == 1) {
                            soloGame();
                        } else if (choice == 2) {
                            multiplayer();
                        } else if (choice == 0) {
                            System.out.println("Thanks for playing!");
                            System.exit(0);
                        } else {
                            System.out.println("Please enter 1, 2 or 0");
                        }
                    } else {
                        System.out.println("Please enter a number");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Controlling method for the solo game against the AI.
     */
    public static void soloGame() {
        boolean cont = true;
//        System.out.print("Player One, enter your name: ");
//        String playerOneName = scanner.nextLine();
        while (cont) {

            //todo 1. added login portal here, commented out scanner input above
            new LoginPortal();

            LoginPortal portal = new LoginPortal();
            String playerOneName = portal.playerNamePopulated();

            BattleshipBoard playerOne = new BattleshipBoard(bgLength);
            BattleshipBoard ai = new BattleshipBoard(bgLength);

            BoardGUI playerOneGUI = new BoardGUI(playerOne, ai, bgLength, playerOneName);

            // don't restart the loop until a key is pressed (CLI, not GUI).
            cont = rematch();
        }
    }
    /**
     * Controlling method for the multiplayer game (2 real players)
     */
    public static void multiplayer() {
        System.out.print("Player One, enter your name: ");
        String playerOneName = scanner.nextLine();

        // here is where we'd put the file checking for player one

        System.out.print("Player Two, enter your name: ");
        String playerTwoName = scanner.nextLine();

        // and here is for player two.
        boolean cont = true;
        while(cont) {
            BattleshipBoard playerOne = new BattleshipBoard(bgLength);
            BattleshipBoard playerTwo = new BattleshipBoard(bgLength);

            // GUI implementation
//        BoardGUI playerOneGUI = new BoardGUI(playerOne, playerTwo, bgLength);
//        BoardGUI playerTwoGUI = new BoardGUI(playerTwo, playerOne, bgLength);

            while (true) {
                playerOne.printBoard();
                playerTwo.printBoard();
                // fire and pass in your opponents board to confirm if hit worked
                if (fire(playerOneName, playerTwoName, playerOne, playerTwo)) break;
                // as above, so below
                if (fire(playerTwoName, playerOneName, playerTwo, playerOne)) break;
            }
            cont = rematch();
        }
    }

    private static boolean rematch() {
        while(true) {
            System.out.println("Would you like to play again? (Y/N)");
            String choice = scanner.nextLine();
            if (choice.equalsIgnoreCase("y")) {
                break;
            }
            else if (choice.equalsIgnoreCase("n")) {
                //todo 1st - call method to set active plyer to false here!!
                LoginPortal portal = new LoginPortal();
                portal.setActivePlayerFlagToFalse();
                //todo 2nd - print the leaderboard here
                new Leaderboard();
                //todo 3rd - exit system
                System.out.println("Goodbye. Come back soon!");
                System.exit(0);
            }
            else{
                System.out.println("Please enter a Y or N");
            }
        }
        return true;
    }


    /**
     * FIRE
     * @param player player firing
     * @param opponent player being fired at
     * @param playerBoard player firings board
     * @param opponentBoard player being fired ats board
     * @return boolean if it hit or not
     */
    private static boolean fire(String player, String opponent, BattleshipBoard playerBoard, BattleshipBoard opponentBoard) {
        System.out.println(opponent + " fire");

        if(playerBoard.playerFire(opponentBoard.getBoard())){
            opponentBoard.shipSunk();
        }
        if(opponentBoard.getShipsRemaining() == 0){
            System.out.println(player + " wins!");
            updatePlayer(player, opponent);
            return true;
        }
        return false;
    }


    /**
     * @param winner player who won - file updated with win + games attempted
     * @param loser player who lost - file updated with games attempted
     */
    public static void updatePlayer(String winner, String loser){

        // This will use the file stuff Sonja is doing.

    }
}
