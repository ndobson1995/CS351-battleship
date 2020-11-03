import java.awt.*;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

/**
 * Main board building logic
 */
public class BattleshipBoard {


    private final String[][] player;            // int array for player board
    private final String[][] opponent;          // int array for opponent board
    private final int bgLength = 5;             // length and depth of board
    private int ships = 5;                      // number of ships to play with

    /**
     * Board constructor, create a board for the player and fill it, and build a "blank" board to fire on.
     */
    public BattleshipBoard(){
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
    public boolean fire(String[][] opponentBoard){
        Scanner scanner = new Scanner(System.in);
        int X = -1, Y = -1;

        // have the user enter coordinates for X (left-right)
        while(X < 0 || X >= bgLength) {
            System.out.print("Enter X coordinate: ");
            try{
                // must confirm they're entering a number and not a letter
                String str = scanner.nextLine();
                if(str.matches("[1-9][0-9]*")){
                    X = Integer.parseInt(str);
                }
            } catch(Exception e){
                System.out.println("You must enter a valid number.");
            }
        }

        // have the user enter coordinates for Y (up-down)
        while(Y < 0 || Y >= bgLength) {
            System.out.print("Enter Y coordinate: ");
            try {
                String str = scanner.nextLine();
                if(str.matches("[1-9][0-9]*")){
                    Y = Integer.parseInt(str);
                }
            } catch(Exception e){
                System.out.println("You must enter a valid number.");
            }
        }

        // after coordinates retrieved, fire at the board.
        // first, does it hit?
        if(opponentBoard[Y][X].equals("o")){
            System.out.println("SHIP SUNK");
            opponentBoard[Y][X] = "X";
            opponent[Y][X] = "X";
            return true;    // return confirmation
        }
        // or miss?
        else{
            System.out.println("MISS");
            opponentBoard[Y][X] = "*";
            opponent[Y][X] = "*";
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

}
