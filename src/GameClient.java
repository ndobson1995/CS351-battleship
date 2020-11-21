import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class GameClient extends UnicastRemoteObject implements GameClientInterface, Runnable {

    private static final long serialVersionUID = 1L;
    private final GameServerInterface gameServer;
    private final String name;
    private static final int BG_LENGTH = 3;
    public BattleshipBoard board;
    public BattleshipBoard opponentBoard;


    /**
     * Constructor for game client
     *
     * @param name       name of player
     * @param gameServer reference to the running server
     * @throws RemoteException in case of connection issues
     */
    protected GameClient(String name, GameServerInterface gameServer) throws RemoteException {
        this.name = name;
        this.gameServer = gameServer;
        board = new BattleshipBoard(BG_LENGTH);
        opponentBoard = new BattleshipBoard(BG_LENGTH);
        gameServer.registerClient(this);
    }


    /**
     * Run the main menu of the game, in a loop.
     */
    @Override
    public void run() {
        try {
            gameServer.addToCollection(board);
            System.out.println(gameServer.getPlayerBoards().size());
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        try {
            outerloop:
            while(true) {
                if(gameServer.getPlayerBoards().size() == 1){
                    Thread.sleep(2000);
                }else {
                    //if (gameServer.getPlayerBoards().size() > 1) {
                        for (int i = 0; i < gameServer.getPlayerBoards().size(); i++) {
                            if(board.getBoard() == gameServer.getPlayerBoards().get(i).getBoard()){

                            }
                            else{
                                opponentBoard.setBoard(gameServer.getPlayerBoards().get(i).getBoard());
                                //board.setBoard(gameServer.getPlayerBoards().get(i + 1).getBoard());
                                break outerloop;
                            }
                        }
                   // }
                }
            }
        } catch (RemoteException | InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("true");

        while (true) {
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
    public void getMoves(int X, int Y) {
        System.out.println(X + " " + Y);
        System.out.println(board.multiplayerHitOrMiss(opponentBoard.getBoard(), X, Y));
    }

    @Override
    public BattleshipBoard getBoard() throws RemoteException {
        return board;
    }

}


