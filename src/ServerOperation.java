import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;

public class ServerOperation extends UnicastRemoteObject implements RMIinterface {


    protected ServerOperation() throws RemoteException {
        super();
    }


    public static void main(String[] args) {
        try{
            LocateRegistry.createRegistry(11100);
            Naming.rebind("//localhost:11100/HelloServer", new ServerOperation());
            System.out.println("Server running");
        }
        catch(Exception e){
            System.err.println("Server Exception: " + e.toString());
            e.printStackTrace();
        }
    }


    // OVERRIDDEN METHODS FROM RMI Interface


    @Override
    public void soloGameGUI(int bgLength, String playerName) throws RemoteException{
        System.out.println();
        BattleshipBoard playerOne = new BattleshipBoard(bgLength);
        BattleshipBoard ai = new BattleshipBoard(bgLength);
        BoardGUI playerOneGUI = new BoardGUI(playerOne, ai, bgLength, playerName);
    }


    @Override
    public void multiplayerSetup(int bgLength, String playerName) throws RemoteException{
        BattleshipBoard playerOne = new BattleshipBoard(bgLength);
        //BattleshipBoard playerTwo = new BattleshipBoard(bgLength);

        while (true) {
            playerOne.printBoard();
            playerTwo.printBoard();
            // fire and pass in your opponents board to confirm if hit worked
            if (fire(playerOneName, playerTwoName, playerOne, playerTwo)) break;
            // as above, so below
            if (fire(playerTwoName, playerOneName, playerTwo, playerOne)) break;
        }
    }


    public void multiplayerPlay(){

    }
}
