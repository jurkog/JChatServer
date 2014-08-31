import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;

/**
 * Created by Jurko on 28/08/14.
 */
public class Server {
    static ServerSocket ss;
    static ArrayList<Client> clients = new ArrayList<Client>();


    public static void main(String[] args) {
        System.out.println("Server initializing...");
        try {
            ss = new ServerSocket(5000);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Server initialized.");
        while (true) {
            try {
                Client client = new Client(ss.accept());
                System.out.println("Client attempting to connect from: " + client.socket.getInetAddress());
                Thread cThread = new Thread(client);
                cThread.start();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
