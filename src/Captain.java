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
        int port = 12345;

        try(Socket socket = new Socket(host, port)) {


            scanner = new Scanner(System.in);

            System.out.println("Welcome to Battleship!");
            System.out.println("Play solo(1), with someone(2) or quit(0)");

            // basic menu
            while (true) {
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
        System.out.print("Player One, enter your name: ");
        String playerOneName = scanner.nextLine();

        // here is where we'd put the file checking for the player

        BattleshipBoard playerOne = new BattleshipBoard(bgLength);
        BattleshipBoard ai = new BattleshipBoard(bgLength);

        BoardGUI playerOneGUI = new BoardGUI(playerOne, ai, bgLength, playerOneName);

//        while(true){
//            playerOne.printBoard();
//            // fire and pass in your opponents board to confirm if hit worked
//            if(playerOne.playerFire(ai.getBoard())){
//                ai.shipSunk();
//            }
//            // check to see if the enemies ships are all gone
//            if(ai.getShipsRemaining() == 0){
//                System.out.println(playerOneName + " wins!");
//                updatePlayer(playerOneName, "AI");
//                break;
//            }
//            // as above, so below
//            if(ai.aiFire(playerOne.getBoard())){
//                playerOne.shipSunk();
//            }
//            if(playerOne.getShipsRemaining() == 0){
//                System.out.println("Computer wins!");
//                updatePlayer("AI", playerOneName);
//                break;
//            }
//        }
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

        BattleshipBoard playerOne = new BattleshipBoard(bgLength);
        BattleshipBoard playerTwo = new BattleshipBoard(bgLength);

        // GUI implementation
//        Board playerOneGUI = new Board(playerOne, playerTwo, bgLength);
//        Board playerTwoGUI = new Board(playerTwo, playerOne, bgLength);

        while(true){
            playerOne.printBoard();
            playerTwo.printBoard();
            // fire and pass in your opponents board to confirm if hit worked
            if(playerOne.playerFire(playerTwo.getBoard())){
                playerTwo.shipSunk();
            }
            // check to see if the enemies ships are all gone
            if(playerTwo.getShipsRemaining() == 0){
                System.out.println(playerOneName + " wins!");
                updatePlayer(playerOneName, playerTwoName);
                break;
            }
            // as above, so below
            if(playerTwo.playerFire(playerOne.getBoard())){
                playerOne.shipSunk();
            }
            if(playerOne.getShipsRemaining() == 0){
                System.out.println(playerTwoName + " wins!");
                updatePlayer(playerTwoName, playerOneName);
                break;
            }
        }
    }


    /**
     * @param winner player who won - file updated with win + games attempted
     * @param loser player who lost - file updated with games attempted
     */
    public static void updatePlayer(String winner, String loser){

        // This will use the file stuff Sonja is doing.

    }
}
