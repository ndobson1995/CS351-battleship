import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Interface for the clients.
 */
public interface GameClientInterface extends Remote {
    void getMoves(int X, int Y) throws RemoteException;
}


