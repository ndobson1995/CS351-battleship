import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;
/**
 * Main board building and interaction logic
 */

public class BattleshipBoard {

    private final String[][] player;            // int array for player board
    private final String[][] opponent;          // int array for opponent board
    private final int bgLength;                 // length and depth of board
    private int ships = 5;                      // number of ships to play with


    /**
     * Board constructor, create a board for the player and fill it, and build a "blank" board to fire on.
     */
    public BattleshipBoard(int bgLength){
        this.bgLength = bgLength;
        player = new String[bgLength][bgLength];
        opponent = new String[bgLength][bgLength];

        // build your board that shows your ships
        for (String[] strings : player) {
            Arrays.fill(strings, "~");
        }

        // now build the opponent board that shows where you've fired
        for (String[] strings : opponent) {
            Arrays.fill(strings, "~");
        }

        // number of ships the user wants to play with. Capped at 5.
        for(int i = 0; i < ships; i++) {
            addShip();
        }
    }


    /**
     * @return player string array, used with GUI
     */
    public String[][] getPlayer() {
        return player;
    }


    /**
     * checks if a position is free.    - is free, * is occupied
     * @param x row
     * @param y column
     * @return boolean saying if position free (true) or occupied (false)
     */
    public boolean shipAllowed(int x, int y){
        return this.player[x][y].equals("~");
    }


    /**
     * This method handles the call to print your board and the firing range
     */
    public void printBoard() {
        System.out.println("\n---YOUR BOARD---");
        printBoards(player);
        System.out.println("\n---FIRING RANGE---");
        printBoards(opponent);
    }


    /**
     * prints the boards given
     * @param board either the player or the opponents board
     */
    private void printBoards(String[][] board) {
        int iter2 = 0;
        System.out.print("   |");
        while(iter2 != bgLength){
            System.out.print(iter2 + "|");
            iter2++;
        }
        System.out.println();
        for (int i = 0; i < bgLength; i++) {
            System.out.print(i + ": |");
            for (int j = 0; j < bgLength; j++) {
                System.out.print(board[i][j] + "|");
            }
            System.out.println();
        }
        System.out.println("--------------------------");
    }


    /**
     * This method randomly adds ships to the board, after confirming a position is free of other ships
     */
    public void addShip(){
        Random rand = new Random();
        // bound the random number to the length of the board
        int xCoordinate = rand.nextInt(bgLength);
        int yCoordinate = rand.nextInt(bgLength);

        // if the following is true, add the ship
        if (shipAllowed(xCoordinate, yCoordinate)) {
            this.player[xCoordinate][yCoordinate] = "o";
        }
        // if it's false though, there's already a ship there so try again.
        // Yes, I know, this is recursive. But not looped so it won't ever run away.
        else{
            addShip();
        }
    }


    /**
     * This passes back your board so the opponents instance can check for a hit.
     * @return your board to the other player
     */
    public String[][] getBoard(){
        return player;
    }


    /**
     * Actually fire at a position. If you hit it will return true, miss false.
     * @param opponentBoard opponents board passed in to confirm hit
     * @return boolean to say if you hit or not
     */
    public boolean playerFire(String[][] opponentBoard){
        while(true) {
            int X = -1, Y = -1;

            // have the user enter coordinates for X (left-right)
            while (X < 0 || X >= bgLength) {
                System.out.print("Enter X coordinate: ");
                X = getCoord(X);
            }

            // have the user enter coordinates for Y (up-down)
            while (Y < 0 || Y >= bgLength) {
                System.out.print("Enter Y coordinate: ");
                Y = getCoord(Y);
            }
            System.out.println();
            if (opponentBoard[Y][X].equals("*") ||
                    opponentBoard[Y][X].equals("X")) {
                System.out.println("Already fired here, try again.");
            } else {
                return hitOrMiss(opponentBoard, X, Y, "You");
            }
        }
    }


    /**
     * Method to validate number was entered for coordinate
     * @param coordinate number to be checked if it's a number and not a letter
     * @return the number
     */
    private int getCoord(int coordinate) {
        Scanner scanner = new Scanner(System.in);
        try {
            // must confirm they're entering a number and not a letter
            String str = scanner.nextLine();
            if (str.matches("[0-9][0-9]*")) {
                coordinate = Integer.parseInt(str);
            }
            else{
                System.out.println("Please enter a number.");
            }
        } catch (Exception e) {
            System.out.println("You must enter a valid number.");
        }
        return coordinate;
    }


    /**
     * Method to control the "AI" firing mechanism.
     * @param opponentBoard the board of the person the AI is playing against
     * @return boolean signifying hit (true) or miss (false)
     */
    public boolean aiFire(String[][] opponentBoard){
        Random rand = new Random();
        while(true) {
            int xCoordinate = rand.nextInt(bgLength);
            int yCoordinate = rand.nextInt(bgLength);
            // after coordinates retrieved, fire at the board.
            // first, does it hit?
            if (!opponentBoard[yCoordinate][xCoordinate].equals("*") &&
                    !opponentBoard[yCoordinate][xCoordinate].equals("X")) {
                        return hitOrMiss(opponentBoard, xCoordinate, yCoordinate, "AI");
                    }
        }
    }


    /**
     * method that determines if you/the AI hit or miss.
     * @param opponentBoard board of the opponent of whoever is firing
     * @param xCoordinate X coordinate passed in by player/AI
     * @param yCoordinate Y coordinate passed in by player/AI
     * @return true if hit, false if fail.
     */
    private boolean hitOrMiss(String[][] opponentBoard, int xCoordinate, int yCoordinate, String who) {
        if (opponentBoard[yCoordinate][xCoordinate].equals("o")) {
            System.out.println(who + ": SHIP SUNK");
            opponentBoard[yCoordinate][xCoordinate] = "X";
            opponent[yCoordinate][xCoordinate] = "X";
            return true;    // return confirmation
        }
        // or miss?
        else {
            System.out.println(who + ": MISSED");
            opponentBoard[yCoordinate][xCoordinate] = "*";
            opponent[yCoordinate][xCoordinate] = "*";
            return false;   // return failure
        }
    }


    /**
     * YOU SUNK MY BATTLESHIP - decrement the number of ships the player has
     */
    public void shipSunk(){
        --ships;
    }


    /**
     * Check how many ships are left, without decrementing it
     * @return number of ships remaining currently
     */
    public int getShipsRemaining(){
        return ships;
    }







    public int guiFire(String[][] opponentBoard, int yCoord, int xCoord, String playerIdentity, BattleshipBoard opponent){
        boolean hit = hitOrMiss(opponentBoard, xCoord, yCoord, playerIdentity);
        int hitCount = 0;
        if(hit){
            hitCount++;
        }
        return hitCount;
    }
}
