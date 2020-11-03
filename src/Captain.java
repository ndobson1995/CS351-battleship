import java.util.Scanner;

public class Captain {


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


        BattleshipBoard playerOne = new BattleshipBoard();
        BattleshipBoard playerTwo = new BattleshipBoard();

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

        
    }
}
