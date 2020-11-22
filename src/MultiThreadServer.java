//import java.io.IOException;
//import java.net.ServerSocket;
//import java.net.Socket;
//import java.util.ArrayList;
//import java.util.List;
//
//public class MultiThreadServer {
//
//    public static void main(String[] args) {
//        ServerSocket server = null;
//        try{
//            server = new ServerSocket(12346);
//            System.out.println("Server running on port: " + server.getLocalPort());
//            server.setReuseAddress(true);
//            while(true) {
//                Socket client = server.accept();
//                System.out.println("Client address: " + client.getInetAddress().getHostAddress() + ":" + client.getPort());
//                ClientManager clientSocket = new ClientManager(client);
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
//    }
//
//
//    private static class ClientManager implements Runnable{
//
//        private final Socket clientSocket;
//        private List<Integer> portsPlaying;
//
//        public ClientManager(Socket socket){
//            this.clientSocket = socket;
//            portsPlaying = new ArrayList<>();
//        }
//
//
//        @Override
//        public void run() {
//
//        }
//    }
//}
