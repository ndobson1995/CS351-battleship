import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * Interface for the server
 */
public interface GameServerInterface extends Remote {
    boolean sendMoves(int X, int Y, String name) throws RemoteException;
    void registerClient(GameClientInterface client) throws RemoteException;
    int getPlayerBoards() throws RemoteException;
    void addToCollection(BattleshipBoard board, String name) throws RemoteException;
    String[][] printPlayerBoards(String name) throws RemoteException;
    void deregisterClient(GameClientInterface client) throws RemoteException;
    void updateOpponentBoard(int X, int Y, GameClientInterface client) throws RemoteException;
}
