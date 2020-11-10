import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RMIinterface extends Remote {


    void soloGameGUI(int bgLength, String playerName) throws RemoteException;
    void multiplayerSetup(int bgLength, String playerName) throws RemoteException;
}
