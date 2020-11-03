public class Captain {


    /**
     * run the game
     * @param args normal arguments
     */
    public static void main(String[] args) {

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
                System.out.println("Player 1 wins!");
                break;
            }
            // as above, so below
            if(playerTwo.fire(playerOne.getBoard())){
                playerOne.shipSunk();
            }
            if(playerOne.getShipsRemaining() == 0){
                System.out.println("Player 2 wins!");
                break;
            }
        }
    }
}
