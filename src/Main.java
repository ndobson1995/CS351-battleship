import java.rmi.RemoteException;

/**
 * Main creates the thread for the user.
 */
public class Main {

    /**
     * @param args args, just main things.
     * @throws RemoteException in case of connection issues
     */
    public static void main(String[] args) throws RemoteException{

        // start thread (after checking login name)
        new Thread(new Captain()).start();
    }
}
