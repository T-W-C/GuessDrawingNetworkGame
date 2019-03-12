package registrationWithGui;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Handler implements Runnable {
    private Socket socket;
    private InputStream is;
    private OutputStream os;
    private ObjectInputStream in;
    private ObjectOutputStream out;

    public Handler(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        try {
            os = socket.getOutputStream();
            out = new ObjectOutputStream(os); // create a writer to write to the socket
            is = socket.getInputStream();
            in = new ObjectInputStream(is); // create a scanner to read in from the socket

            Message m = (Message)in.readObject();
            if (m.getType().equals(Server.QueryType.REGISTRATION)) { // checks that the action is a registration action
                RegistrationHandler.run(in, out, socket); // run the registration code since the action is to do with registration
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                socket.close();
                if (socket.isClosed()) {
                    System.out.println("The socket " + socket + " is now closed!");
                }
            } catch (Exception e) {
                System.out.println("Error in socket: " + socket);
                e.printStackTrace();
            }
        }
    }
}