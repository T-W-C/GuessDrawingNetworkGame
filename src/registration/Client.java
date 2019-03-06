package registration;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private static String host =  "127.0.0.1";
    private static int port = 1254;

    public Client(String host, int ip) {
        this.host = host;
        this.port = ip;
    }

    public static void main (String[] args) throws IOException {
        try (Socket client = new Socket(host, port); // create  a socket at port 1254
             Scanner scanner = new Scanner(System.in); // scanner to read in the client input from keyboard
             Scanner in = new Scanner(client.getInputStream()); // scanner to read in lines from the server
             PrintWriter out = new PrintWriter(client.getOutputStream(), true) // writer to write to the server
        ) {
            System.out.println(in.nextLine()); // read in line from server detailing user what to do
            while (scanner.hasNextLine()) { // while user is inputting something
                out.println(scanner.nextLine()); // send that input to the server
                System.out.println(in.nextLine()); //  print out the server's response
            }
        } catch (Exception e) {
            System.out.println("oopsie");
            e.printStackTrace();
        }
    }
}
