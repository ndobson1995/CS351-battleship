import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Scanner;

/**
 * Main creates the thread for the user.
 */
public class Main {

    /**
     * @param args args, just main things.
     * @throws RemoteException in case of connection issues
     * @throws MalformedURLException in case of url issues
     * @throws NotBoundException in case of port issues
     */
    public static void main(String[] args) throws RemoteException, MalformedURLException, NotBoundException {

        // take the username
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your name: ");
        String name = scanner.nextLine();
        System.out.println();

        // start thread (after checking login name)
        new Thread(new Captain(name)).start();
    }
}
