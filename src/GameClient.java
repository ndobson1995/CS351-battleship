import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class GameClient extends UnicastRemoteObject implements GameClientInterface, Runnable {

    private static final long serialVersionUID = 1L;
    private final GameServerInterface gameServer;
    private final String name;
    public BattleshipBoard board;
    private String[][] blankBoard;
    private int hits = 0;

    /**
     * Constructor for game client
     *
     * @param name       name of player
     * @param gameServer reference to the running server
     * @throws RemoteException in case of connection issues
     */
    protected GameClient(String name, GameServerInterface gameServer, int BG_LENGTH) throws RemoteException {
        this.name = name;
        this.gameServer = gameServer;
        gameServer.registerClient(this);
        board = new BattleshipBoard(BG_LENGTH);
        blankBoard = new BattleshipBoard(Captain.getBG_LENGTH()).getOpponentBoard();
    }


    /**
     * Run the main menu of the game, in a loop.
     */
    @Override
    public void run() {
        try {
            gameServer.addToCollection(board, name);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        try {
            System.out.println("Waiting for a game");
            boolean gameAvailable = false;
            while(true) {
                if(gameServer.getPlayerBoards() < 2){
                    Thread.sleep(5000);
                }
                else if(gameServer.getPlayerBoards() > 2){
                    gameAvailable = false;
                    break;
                }
                else{
                    gameAvailable = true;
                    break;
                }
            }
            if(gameAvailable) {
                gameLogic();
            }
            else{
                System.out.println("No game available");
                gameServer.removeFromCollection(board, name);
            }
            gameServer.deregisterClient(this);
        } catch (RemoteException | InterruptedException e) {
            e.printStackTrace();
        }
        Thread.currentThread().interrupt();
    }



    private void gameLogic() throws RemoteException {
        System.out.println("\n---YOUR BOARD---");
        board.printBoards(gameServer.printPlayerBoards(name));
        System.out.println("\n---FIRING RANGE---");
        board.printBoards(blankBoard);
        while (true) {
            boolean firedSuccessfully = false;
            while(!firedSuccessfully) {
                firedSuccessfully = fire();
            }
            if(hits == 5){
                System.out.println("You win!");
                gameServer.tellThemTheyAreALoser();
                break;
            }

        }
    }

    private synchronized boolean fire(){
        ArrayList<Integer> coords = board.multiplayerFire();
        try {
            if (blankBoard[coords.get(1)][coords.get(0)].equals("X") ||
                    blankBoard[coords.get(1)][coords.get(0)].equals("*")
            ) {
                System.out.println("Already fired here, try again");
            } else if (gameServer.sendMoves(coords.get(0), coords.get(1), name)) {
                blankBoard[coords.get(1)][coords.get(0)] = "X";
                gameServer.updateOpponentBoard(coords.get(0), coords.get(1), this);
                hits++;
                return true;
            } else {
                blankBoard[coords.get(1)][coords.get(0)] = "*";
                gameServer.updateOpponentBoard(coords.get(0), coords.get(1), this);
                return true;
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * @param X X coordinate
     * @param Y Y coordinate
     */
    @Override
    public void getMoves(int X, int Y, BattleshipBoard board) throws RemoteException {
        board.printBoards(gameServer.printPlayerBoards(name));
    }

    @Override
    public BattleshipBoard getBoard() throws RemoteException {
        return board;
    }


    @Override
    public void printAfterShot() throws RemoteException {
        System.out.println("\n==============================");
        System.out.println("\n---YOUR BOARD---");
        board.printBoards(gameServer.printPlayerBoards(name));
        System.out.println("\n---FIRING RANGE---");
        board.printBoards(blankBoard);
    }

}


