import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class MultiThreadServer {

    public static void main(String[] args) {
        ServerSocket server = null;
        int counter = 0;
        try {
            server = new ServerSocket(12346);
            System.out.println("Server running on port: " + server.getLocalPort());
            server.setReuseAddress(true);
            while (true) {
                counter++;
                Socket client = server.accept();
                System.out.println("Client address: " + client.getInetAddress().getHostAddress() + ":" + client.getPort() + "at index: " + counter);
                ClientManager clientSocket = new ClientManager(client);
                new Thread(clientSocket).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (server != null) {
                try {
                    server.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    private static class ClientManager implements Runnable {

        private final Socket clientSocket;
        private List<String> portsPlaying;

        public ClientManager(Socket socket) {
            this.clientSocket = socket;
            portsPlaying = new ArrayList<>();
        }


        @Override
        public void run() {
            try {
                OutputStream out1 = clientSocket.getOutputStream();
                PrintWriter writer = new PrintWriter(out1, true);
                writer.println("Player  One");
                portsPlaying.add("name");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}



