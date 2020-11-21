import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Interface for the server
 */
public interface GameServerInterface extends Remote {
    void sendMoves(int X, int Y) throws RemoteException;
    void registerClient(GameClientInterface client) throws RemoteException;
    ArrayList<BattleshipBoard> getPlayerBoards() throws RemoteException;
    void addToHash(BattleshipBoard board) throws RemoteException;
//    void registerClient(GameClientInterface client, BattleshipBoard board) throws RemoteException;
}
