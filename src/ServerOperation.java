import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Vector;

public class ServerOperation extends UnicastRemoteObject implements RMIinterface {

    private static final long serialVersionUID = 1L;
    private static Vector<BattleshipBoard> boards = new Vector<>();

    protected ServerOperation() throws RemoteException {
        super();
    }


    public static void main(String[] args) {
        // RMI bit
        try{
            LocateRegistry.createRegistry(11100);
            Naming.rebind("//127.0.0.1:11100/Battleship", new ServerOperation());
            System.out.println("Sockets server running on port 11100");
        }
        catch(Exception e){
            System.err.println("Server Exception: " + e.toString());
            e.printStackTrace();
        }

//        // Sockets bit
//        ServerSocket server = null;
//        try{
//            server = new ServerSocket(32346);
//            System.out.println("RMI Server running on port: " + server.getLocalPort());
//            server.setReuseAddress(true);
//            while(true) {
//                Socket client = server.accept();
//                System.out.println("Client address: " + client.getInetAddress().getHostAddress() + ":" + client.getPort());
//                Client clientSocket = new Client(client);
//                new Thread(clientSocket).start();
//            }
//        }catch(IOException e){
//            e.printStackTrace();
//        }finally{
//            if (server != null){
//                try{
//                    server.close();
//                }catch(IOException e){
//                    e.printStackTrace();
//                }
//            }
//        }
    }


    @Override
    public void multiplayerPlay() throws RemoteException{
        System.out.println("Test");
    }
}
