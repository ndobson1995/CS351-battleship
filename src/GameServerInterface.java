import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Interface for the server
 */
public interface GameServerInterface extends Remote {
    void sendMoves(int X, int Y, BattleshipBoard board) throws RemoteException;
    void registerClient(GameClientInterface client) throws RemoteException;
    ArrayList<BattleshipBoard> getPlayerBoards() throws RemoteException;
    void addToCollection(BattleshipBoard board) throws RemoteException;
//    void registerClient(GameClientInterface client, BattleshipBoard board) throws RemoteException;
}
