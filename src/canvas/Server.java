package canvas;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) {
        ServerSocket serverSocket = null;

        try {
            serverSocket = new ServerSocket(8888);

        } catch(IOException e) {
            System.exit(1);
        }
        while(true) {
            Socket clientSocket = null;
            try {
                clientSocket = serverSocket.accept();

            } catch(IOException e) {
                break;
            }
            CanvasHandler con = new CanvasHandler(clientSocket);
            con.start();

        }
    }
}
