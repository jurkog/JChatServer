import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by Jurko on 28/08/14.
 */
public class Client implements Runnable {
    Socket socket;
    PrintWriter writer;
    BufferedReader reader;

    String username;

    public Client(Socket socket) throws IOException {
        this.socket = socket;
        this.writer = new PrintWriter(socket.getOutputStream());
        this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    @Override
    public void run() {
        sendClientMessage("Success/Jurko's Chat Room!");
        String message;
        try {
            while ((message = reader.readLine()) != null) {

                String[] data = message.split("/");
                if (data[0].equals("Connect")) {
                    // Creating 'Client' instance
                    this.username = data[1];
                    Server.clients.add(this);

                    // Let everyone know they joined
                    sendMessage(message);

                    // Send everyone else's info\
                    sendConnect("Connect", this);

                    System.out.println("Client " + this.username + " successfully connected from: " + this.socket.getInetAddress());
                } else if (data[0].equals("Disconnect")) {
                    this.socket.close();
                    sendDisconnect("Disconnect", this);
                    Server.clients.remove(this);
                    System.out.println("User " + username + " has disconnected from the server.");
                    break;
                } else if (data[0].equals("Chat")) {
                    sendMessage(message);

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public void sendClientMessage(String message) {
        this.writer.println(message);
        this.writer.flush();
    }

    public void sendConnect(String message, Client c) {
        for (Client cc: Server.clients) {
            if (!cc.equals(c)) {
                c.sendClientMessage(message + "/" + cc.username);
            }
        }
    }

    public void sendDisconnect(String message, Client c) {
        for (Client cc: Server.clients) {
            if (!cc.equals(c)) {
                cc.sendClientMessage(message + "/" + c.username);
            }
        }
    }

    public void sendMessage(String message) {
        for (Client c : Server.clients) {
            c.writer.println(message);
            c.writer.flush();
        }
    }
}
