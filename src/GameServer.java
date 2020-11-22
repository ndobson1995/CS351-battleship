import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * This is the server, it really is only used for multiplayer instances.
 */
public class GameServer extends UnicastRemoteObject implements GameServerInterface {

    private static final long serialVersionUID = 1L;
    private final ArrayList<GameClientInterface> clients;
    public LinkedHashMap<String, BattleshipBoard> playerBoards;
    public ArrayList<Integer> moves;


    /**
     * Create instance of game server
     *
     * @throws RemoteException in case of connection issue
     */
    protected GameServer() throws RemoteException {
        clients = new ArrayList<>();
        playerBoards = new LinkedHashMap<>();
        moves = new ArrayList<>();
    }


    /**
     * send the moves for the multiplayer game
     *
     * @param X X coordinate
     * @param Y Y coordinate
     */
    @Override
    public synchronized boolean sendMoves(int X, int Y, String name) {
        AtomicBoolean hit = new AtomicBoolean(false);

        playerBoards.forEach((k, v) -> {
            if (!k.equals(name)) {
                BattleshipBoard target = playerBoards.get(k);
                hit.set(target.multiplayerHitOrMiss(target.getBoard(), X, Y));
            }
        });
        return hit.get();
    }


    /**
     * add the new clients to a list
     *
     * @param client client to be registered
     */
    @Override
    public synchronized void registerClient(GameClientInterface client) {
        this.clients.add(client);
    }


    /**
     * add the new clients to a list
     *
     * @param client client to be registered
     */
    @Override
    public synchronized void deregisterClient(GameClientInterface client) {
        this.clients.remove(client);
    }


    /**
     * @return size of playerboards collection
     */
    @Override
    public int getPlayerBoards() {
        return playerBoards.size();
    }


    /**
     * add to collection
     *
     * @param board board to add
     * @param name  name to be used as key
     */
    @Override
    public void addToCollection(BattleshipBoard board, String name) {
        playerBoards.put(name, board);
    }


    @Override
    public void removeFromCollection(BattleshipBoard board, String name) {
        playerBoards.remove(name, board);
    }

    /**
     * @param name player name
     * @return players board
     */
    @Override
    public String[][] printPlayerBoards(String name) {
        return playerBoards.get(name).getBoard();
    }


    @Override
    public void updateOpponentBoard(int X, int Y, GameClientInterface client) throws RemoteException {
        for (GameClientInterface gameClientInterface : clients) {
            gameClientInterface.printAfterShot();
        }
    }


    @Override
    public void tellThemTheyAreALoser(String name){
        clients.forEach((cli) -> {
            try {
                if(!cli.isWon())
                cli.youLost();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        });
    }
}
