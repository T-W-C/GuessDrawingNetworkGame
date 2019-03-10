package registration3;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client3 {
    private static String host =  "127.0.0.1";
    private static int port = 1254;

    public Client3(String host, int ip) {
        this.host = host;
        this.port = ip;
    }

    public static void main (String[] args) throws IOException {
        try (Socket client = new Socket(host, port); // create  a socket at port 1254
             Scanner scanner = new Scanner(System.in); // scanner to read in the client input from keyboard
             InputStream is = client.getInputStream(); // input stream of the client
             ObjectInputStream in = new ObjectInputStream(is); // objectInputStream to read in objects sent by the server
             OutputStream os = client.getOutputStream(); // output stream of the client
             ObjectOutputStream out = new ObjectOutputStream(os) // objectOutputStream to write to the server
        ) {
            Message m;
            out.writeObject(new Message(Server3.QueryType.REGISTRATION, "New client is registering")); // initial message to tell the server that the client is of type registration
            while (true) { // while server is sending something
                m = (Message)in.readObject();
                System.out.println(m.getMessage()); // read in line from server detailing user what to do
                out.writeObject(new Message(Server3.QueryType.REGISTRATION, scanner.nextLine())); // send that input to the server
            }
        } catch (Exception e) {
            System.out.println("oopsie");
            e.printStackTrace();
        }
    }
}
