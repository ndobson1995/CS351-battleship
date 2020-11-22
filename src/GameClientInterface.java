import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Interface for the clients.
 */
public interface GameClientInterface extends Remote {
    void getMoves(int X, int Y, BattleshipBoard board) throws RemoteException;
    BattleshipBoard getBoard() throws RemoteException;
    void printAfterShot() throws RemoteException;
}


