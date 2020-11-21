import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

/**
 * Starts the server! Using RMI because calling methods is simpler. (I've swerverd mentioning the headaches taken to get here)
 */
public class GameServerOp {

    /**
     * @param args arrrrrrgs
     * @throws RemoteException in case of connection issues
     * @throws MalformedURLException in case URL is incorrect
     */
    public static void main(String[] args) throws RemoteException, MalformedURLException {
        LocateRegistry.createRegistry(33333);
        Naming.rebind("//127.0.0.1:33333/Battleship", new GameServer());
        System.out.println("Sockets server running on port 13131");
    }
}
