import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Scanner;
/**
 * Main driver for the program, called Captain because ships
 */

public class Captain {

    private static final int bgLength = 5;
    private static Scanner scanner;
    private static RMIinterface lookUp;


    /**
     * run the game
     * @param args normal arguments
     */
    public static void main(String[] args) throws MalformedURLException, RemoteException, NotBoundException {

        lookUp = (RMIinterface) Naming.lookup("//localhost:11100/HelloServer");       //MUST MATCH SERVER
        scanner = new Scanner(System.in);

        // basic menu
        while (true) {
            System.out.println("Welcome to Battleship!");
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
                        multiplayer();
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


    public static void soloGameCLI() {
        boolean cont = true;
        System.out.print("Player One, enter your name: ");
        String playerOneName = scanner.nextLine();

        while(cont) {
            BattleshipBoard playerOne = new BattleshipBoard(bgLength);
            BattleshipBoard ai = new BattleshipBoard(bgLength);

            while(true){
                playerOne.printBoard();
                // fire and pass in your opponents board to confirm if hit worked
                if (playerOne.playerFire(ai.getBoard())) {
                    ai.shipSunk();
                }
                // check to see if the enemies ships are all gone
                if (ai.getShipsRemaining() == 0) {
                    System.out.println(playerOneName + " wins!");
                    updatePlayer(playerOneName, "AI");
                    break;
                }
                // as above, so below
                if (ai.aiFire(playerOne.getBoard())) {
                    playerOne.shipSunk();
                }
                if (playerOne.getShipsRemaining() == 0) {
                    System.out.println("Computer wins!");
                    updatePlayer("AI", playerOneName);
                    break;
                }
            }
            cont = rematch();
        }
    }


    /**
     * Controlling method for the solo game against the AI.
     */
    public static void soloGameGUI() throws RemoteException {
        System.out.print("Player One, enter your name: ");
        String playerOneName = scanner.nextLine();
        lookUp.soloGameGUI(bgLength, playerOneName);
    }


    /**
     * Controlling method for the multiplayer game (2 real players)
     */
    public static void multiplayer() throws RemoteException {

        System.out.println("Enter your name");
        String playerName = scanner.nextLine();
        lookUp.multiplayerSetup(bgLength, playerName);
//        System.out.print("Player One, enter your name: ");
//        String playerOneName = scanner.nextLine();
//
//        // here is where we'd put the file checking for player one
//
//        System.out.print("Player Two, enter your name: ");
//        String playerTwoName = scanner.nextLine();
//
//        // and here is for player two.
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

    private static boolean rematch() {
        while(true) {
            System.out.println("Would you like to play again? (Y/N)");
            String choice = scanner.nextLine();
            if (choice.equalsIgnoreCase("y")) {
                break;
            }
            else if (choice.equalsIgnoreCase("n")) {
                return false;
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
