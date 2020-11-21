import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class GameClient extends UnicastRemoteObject implements GameClientInterface, Runnable {

    private static final long serialVersionUID = 1L;
    private final GameServerInterface gameServer;
    private final String name;
    private static final int BG_LENGTH = 3;
    public BattleshipBoard board;


    /**
     * Constructor for game client
     *
     * @param name name of player
     * @param gameServer reference to the running server
     * @throws RemoteException in case of connection issues
     */
    protected GameClient(String name, BattleshipBoard board, GameServerInterface gameServer) throws RemoteException {
        this.name = name;
        this.gameServer = gameServer;
        this.board = board;
        gameServer.registerClient(this);
    }


    /**
     * Run the main menu of the game, in a loop.
     */
    @Override
    public void run() {
        while(true){
            board.printBoard();
            ArrayList<Integer> coords = board.multiplayerFire();
            try {
                gameServer.sendMoves(coords.get(0), coords.get(1));
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * @param X X coordinate
     * @param Y Y coordinate
     */
    @Override
    public void getMoves(int X, int Y){
        System.out.println(X+" " + Y);
        board.multiplayerHitOrMiss(board.getBoard(), X, Y);
    }

}


