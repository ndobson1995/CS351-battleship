import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MultiThreadServer {

    public static void main(String[] args) {
        ServerSocket server = null;
        try{
            server = new ServerSocket(12326);
            System.out.println("Server running on port: " + server.getLocalPort());
            server.setReuseAddress(true);
            while(true) {
                Socket client = server.accept();
                System.out.println("Client address: " + client.getInetAddress().getHostAddress() + ":" + client.getPort());
                ClientManager clientSocket = new ClientManager(client);
                new Thread(clientSocket).start();
            }
        }catch(IOException e){
            e.printStackTrace();
        }finally{
            if (server != null){
                try{
                    server.close();
                }catch(IOException e){
                    e.printStackTrace();
                }
            }
        }
    }


    private static class ClientManager implements Runnable{

        private final Socket clientSocket;
        private List<Integer> portsPlaying;

        public ClientManager(Socket socket){
            this.clientSocket = socket;
            portsPlaying = new ArrayList<>();
        }


        @Override
        public void run(){
//            Scanner scanner = new Scanner(System.in);
//            try{
//                String choiceStr = scanner.nextLine();
//                int choice;
//                // confirm a number was entered.
//                if (choiceStr.matches("[0-9][0-9]*")) {
//                    choice = Integer.parseInt(choiceStr);
//                    if (choice == 1) {
//                        Captain.soloGameCLI();
//                    } else if (choice == 2){
//                        Captain.soloGameGUI();
//                    } else if (choice == 3) {
//                        multiplayer();
//                    } else if (choice == 0) {
//                        System.out.println("Thanks for playing!");
//                        System.exit(0);
//                    } else {
//                        System.out.println("Please enter 1, 2 or 0");
//                    }
//                } else {
//                    System.out.println("Please enter a number");
//                }
//            }catch(Exception e) {
//                e.printStackTrace();
//            }
        }


        public void multiplayer(){
            portsPlaying.add(clientSocket.getPort());
            for(int port : portsPlaying){
                System.out.println(port);
            };
        }
    }
}
