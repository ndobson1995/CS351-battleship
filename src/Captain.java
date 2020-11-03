import java.util.Scanner;

public class Captain {

    private static final int bgLength = 7;

    /**
     * run the game
     * @param args normal arguments
     */
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        System.out.print("Player One, enter your name: ");
        String playerOneName = scanner.nextLine();


        System.out.print("Player Two, enter your name: ");
        String playerTwoName = scanner.nextLine();


        boolean playerOneWin = false;
        boolean playerTwoWin = false;

        BattleshipBoard playerOne = new BattleshipBoard(bgLength);
        BattleshipBoard playerTwo = new BattleshipBoard(bgLength);

        Board playerOneGUI = new Board(playerOne, playerTwo, bgLength);
        Board playerTwoGUI = new Board(playerTwo, playerOne, bgLength);

        while(true){
            playerOne.printBoard();
            playerTwo.printBoard();
            // fire and pass in your opponents board to confirm if hit worked
            if(playerOne.fire(playerTwo.getBoard())){
                playerTwo.shipSunk();
            }
            if(playerTwo.getShipsRemaining() == 0){
                System.out.println(playerOneName + " wins!");
                playerOneWin = true;
                break;
            }
            // as above, so below
            if(playerTwo.fire(playerOne.getBoard())){
                playerOne.shipSunk();
            }
            if(playerOne.getShipsRemaining() == 0){
                System.out.println(playerTwoName + " wins!");
                playerTwoWin = true;
                break;
            }
        }

        if(playerOneWin){
            updatePlayer(playerOneName);
        }
        else{
            updatePlayer(playerTwoName);
        }
    }


    public static void updatePlayer(String player){

    }
}
