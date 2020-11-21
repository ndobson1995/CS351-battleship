////import java.io.IOException;
////import java.net.Socket;
////
////public class Client {
////    public static void main(String[] args) throws IOException {
////        Socket socket = new Socket("localhost",12346);
////    }
////}
//
//
//import java.net.Socket;
//import java.util.ArrayList;
//import java.util.List;
//
//public class Client implements Runnable{
//
//    private final Socket clientSocket;
//    private List<Integer> portsPlaying;
//
//    public Client (Socket socket){
//        this.clientSocket = socket;
//        portsPlaying = new ArrayList<>();
//    }
//
//
//    @Override
//    public void run(){
//
//    }
//
//
//    public void multiplayer(){
//        portsPlaying.add(clientSocket.getPort());
//        for(int port : portsPlaying){
//            System.out.println(port);
//        };
//    }
//}