package canvas;

import java.io.*;
import java.net.Socket;

public class CanvasHandler extends Thread {
    private Socket clientSocket;
    private ObjectInputStream is;
    private ObjectOutputStream os;
    private Object o;

    public CanvasHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }


    @Override
    public void run() {
        try {
            is = new ObjectInputStream(clientSocket.getInputStream());
            os = new ObjectOutputStream(clientSocket.getOutputStream());
            while(this.readCommand()) {

            }


        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public boolean readCommand() {
        try {
            o = is.readObject();
        } catch(Exception e) {
            o = null;
        }
        if (o == null) {
            // close the socket here through a custom
            // close socket method that I'll need to write.
        }

        if(o.getClass() == DrawCommand.class) {
            DrawCommand dc = (DrawCommand) o;

        }
    }


}
