package canvas;

import java.io.*;
import java.net.Socket;

public class CanvasHandler extends Thread {
    private Socket clientSocket;
    private ObjectInputStream is;
    private ObjectOutputStream os;
    private DrawCommand command;

    private Server server;

    public CanvasHandler(Socket clientSocket, Server server) {
        this.clientSocket = clientSocket;
        this.server = server;
    }


    @Override
    public void run() {
        try {
            is = new ObjectInputStream(clientSocket.getInputStream());
            os = new ObjectOutputStream(clientSocket.getOutputStream());
            while (this.readCommand()) {
            }
        } catch (Exception e) {
                e.printStackTrace();
            }
        }


        public boolean readCommand() {
            try {
                command = (DrawCommand) is.readObject();

            } catch(Exception e) {
                return false;
            }
            if(command == null) {

            } else {
            }
            return true;
        }

    public void writeCommand(DrawCommand command) {
        try {
            os.writeObject(command);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
