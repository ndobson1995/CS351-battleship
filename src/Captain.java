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
    private static String playerOneName;

  /**
     * run the game
     * @param args normal arguments
     */
    public static void main(String[] args) {
//        lookUp = (RMIinterface) Naming.lookup("//localhost:12959/HelloServer");       //MUST MATCH SERVER
//        scanner = new Scanner(System.in);
        String host = "127.0.0.1";
        int port = 12326;

        try(Socket socket = new Socket(host, port)) {
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
    } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


        public static void soloGameCLI() {
        boolean cont = true;
            //new LoginPortal();

            LoginPortal portal = new LoginPortal();
            playerOneName = portal.playerNamePopulated();
            int playerWin =0;
            int playerLose =0;
            int opponentWin =0;
            int opponentLose =0;



        while(playerOneName !=null) {
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
                    displayWinnerLoser(playerOneName, "AI");
                    int win = playerWin++;
                    int lose = opponentLose++;
                    updateWinnerLoserFile(playerOneName,win,"AI", lose);
                    break;
                }
                // as above, so below
                if (ai.aiFire(playerOne.getBoard())) {
                    playerOne.shipSunk();
                }
                if (playerOne.getShipsRemaining() == 0) {
                    System.out.println("Computer wins!");
                    displayWinnerLoser("AI", playerOneName);
                    int win = opponentWin++;
                    int lose = playerLose++;
                    updateWinnerLoserFile("AI",win,playerOneName,lose);
                    break;
                }
            }
            cont = rematch();
        }
        }



    /**
     * Controlling method for the solo game against the AI.
     */
    public static void soloGameGUI() {
        System.out.print("Player One, enter your name: ");
        String playerOneName = scanner.nextLine();

        BattleshipBoard playerOne = new BattleshipBoard(bgLength);
        BattleshipBoard ai = new BattleshipBoard(bgLength);
        BoardGUI playerOneGUI = new BoardGUI(playerOne, ai, bgLength, playerOneName);

        //lookUp.soloGameGUI(bgLength, playerOneName);
    }


    /**
     * Controlling method for the multiplayer game (2 real players)
     */
    public static void multiplayer() {

        System.out.println("Enter your name");
        String playerName = scanner.nextLine();
        //lookUp.multiplayerSetup(bgLength, playerName);


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
                updatePlayerStatus(playerOneName);
                break;
            }
            else if (choice.equalsIgnoreCase("n")) {
                exit();
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
            //todo sonja FIND WHERE THE SCORES ARE SAVED
            displayWinnerLoser(player, opponent);
            //updateWinnerLoserFile(player,playerScore, opponent, opponentScore);
            return true;
        }
        return false;
    }


    public static void displayWinnerLoser(String winner, String loser){
        System.out.println("The winner is " + winner);
        System.out.println("The loser is " + loser);
    }


    public static void updateWinnerLoserFile(String player,int playerWin, String opponent, int opponentLose){
        Leaderboard leaderboard = new Leaderboard();
        leaderboard.savePlayerStatstoFile(player, playerWin, opponent, opponentLose);
        System.out.println("PLAYER :" + player + "SCORE: "  + playerWin);
        System.out.println("OPONNENT :" + opponent + "SCORE: "  + opponentLose);

        //todo this still doesnt take into account the games attempted, no of wins or loses YET

    }



    public static void updatePlayerStatus(String playerName){
        LoginPortal portal = new LoginPortal();
        portal.setActivePlayerFlagToFalse(playerName);



        // todo SONJA WORK ON THIS!!!! This will use the file stuff Sonja is doing.

    }

    private static void exit() {
        System.out.println("Goodbye. Come back soon!");
        System.exit(0);
    }
}
