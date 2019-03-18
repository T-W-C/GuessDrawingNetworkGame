package chat;

import java.util.ArrayList;
import java.util.List;
import java.io.DataOutputStream;
import java.io.DataInputStream;
import java.net.ServerSocket;
import java.io.IOException;
import java.net.Socket;

public class ChatServer {

    public static List<ChatClient> ChatClients;
    public static DataOutputStream dataOutputStream;
    DataInputStream dataInputStream;

    ChatServer() {
        /**
         * ChatServer constructor.
         */
        String username;
        Socket client;
        ChatClients = new ArrayList<>();

        try {
            ServerSocket SSocket = new ServerSocket(9623);

            /**
             * This while loop loops continuously, waiting for new clients to join. If a new user joins, create a new
             * user and add it to the arraylist of clients, stored in this server object.
             */
            while (true) {
                client = SSocket.accept();
                dataInputStream = new DataInputStream(client.getInputStream());
                dataOutputStream = new DataOutputStream(client.getOutputStream());

                username = dataInputStream.readUTF();
                ChatClient user = new ChatClient(username, dataOutputStream, dataInputStream);
                ChatClients.add(user);
            }

        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    /**
     *  Main method that creates and runs a new server.
     *  To run entire program, first run this method, then ClientMain.
     */
    public static void main(String[] args) {
        new ChatServer();
    }
}