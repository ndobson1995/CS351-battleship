import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * This is the server, it really is only used for multiplayer instances.
 */
public class GameServer extends UnicastRemoteObject implements GameServerInterface{

    private static final long serialVersionUID = 1L;
    private final ArrayList<GameClientInterface> clients;
    private HashMap<Integer, String[][]> playerBoards;


    /**
     * Create instance of game server
     * @throws RemoteException in case of connection issue
     */
    protected GameServer() throws RemoteException {
        clients = new ArrayList<>();
        playerBoards = new HashMap<>();
    }


    /**
     * send the moves for the multiplayer game
     * @param X X coordinate
     * @param Y Y coordinate
     */
    @Override
    public void sendMoves(int X, int Y) throws RemoteException {
        int i = 0;
        while(i < clients.size()){
            clients.get(i++).getMoves(X, Y);
        }
    }


    /**
     * add the new clients to a list
     * @param client client to be registered
     */
    @Override
    //public synchronized void registerClient(GameClientInterface client, BattleshipBoard board){
    public synchronized void registerClient(GameClientInterface client) throws RemoteException {
        this.clients.add(client);
        //playerBoards.put(client.hashCode(), client.getBoard().getBoard());
    }

    public HashMap<Integer, String[][]> getPlayerBoards() {
        return playerBoards;
    }
}
