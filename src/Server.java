//import java.io.PrintWriter;
//import java.net.ServerSocket;
//import java.net.Socket;
//
//public class Server extends Thread{
//
//    public static void main(String[] args) {
//
//        try{
//            ServerSocket server = new ServerSocket(12346);
//            int counter = 0;
//            System.out.println("Server has started....");
//            while (true){
//                counter++;
//                Socket client = server.accept();
//                System.out.println(">>" + "Client No: " + counter + " Started!");
//
//                PrintWriter out = new PrintWriter(client.getOutputStream());
//                out.write("Hello, Client NO: " + counter);
//                Thread t = new Thread(String.valueOf(client));
//                t.start();
//            }
//        }catch (Exception e){
//            System.out.println(e);
//        }
//    }
//}
//
