/*
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

public class ServerOperation extends UnicastRemoteObject implements RMIinterface {


//    protected ServerOperation() throws RemoteException {
//        super();
//    }

    public static void main(String[] args) {
        String host = "127.0.0.1";
        int port = 12345;

        try(Socket socket = new Socket(host, port)) {
            scanner = new Scanner(System.in);

    } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
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

//        while (true) {
//            playerOne.printBoard();
//            playerTwo.printBoard();
//            // fire and pass in your opponents board to confirm if hit worked
//            if (fire(playerOneName, playerTwoName, playerOne, playerTwo)) break;
//            // as above, so below
//            if (fire(playerTwoName, playerOneName, playerTwo, playerOne)) break;
//        }
    }


    public void multiplayerPlay(){

    }
}}
*/
