import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ConnectionHandler {
    public static int port;
    private static String serverAddress;

    private ObjectOutputStream os;

    private Boolean isHost;

    private PacketHandler packetHandler;

    private Player p;
    private String message;

    private Socket clientConnection;
    private ServerSocket server;
    private ArrayList<ObjectInputStream> inputStreams;
    private ArrayList<ObjectOutputStream> outputStreams;

    public ConnectionHandler(Player p, String serverAddress, int port) {
        // for each connection handler created there is a new array list that is initiated.
        //
        outputStreams = new ArrayList<>();
        inputStreams = new ArrayList<>();
        this.serverAddress = serverAddress;
        this.p = p;
        this.port = port;

    }

    private Socket connectToServer() throws IOException {
        clientConnection = new Socket(InetAddress.getByName(this.serverAddress), this.port);
        return clientConnection;
    }



    public void initiateServer(Runnable onClientConnection) {
        this.isHost = true;
        System.out.println("Started initiation of server...");
        //launch new thread for initiation of server
        new Thread(() -> {
           try {
               server = new ServerSocket(port, 4);
               System.out.println("Server " + server.toString() + " has initiated.");
               while(true) {
                   try {
                       System.out.println("Waiting to accept sockets...");
                       Socket socket = server.accept();
                       addOutputStream(socket);
                       // create a new thread to handle the inputs from the socket
                       // that has been accepted above.
                       new Thread(() -> {
                           onClientConnection.run();
                           this.listen(socket);
                       }).start();
                       //start the above thread.
                   } catch(IOException e) {
                        // close the application
                   }
               }
           } catch(IOException e) {
               System.out.println("Something went wrong");
           }
        }).start();
    }

    /**
     * for each of the client (socket connections) that have connected
     * to the server then they need to be added to the arraylist of socket
     * connections.
     * @param socket
     * @throws IOException
     */
    private void addOutputStream(Socket socket) throws IOException {
        os = new ObjectOutputStream(socket.getOutputStream());
        os.flush();
        outputStreams.add(os);
        System.out.println("Output streams have been added");
    }

    public void listen(Socket socket) {
        Thread listener = new Thread(() -> {
            Object packet = "";
            ObjectInputStream is = null;

            try {
                is = new ObjectInputStream(clientConnection.getInputStream());
                inputStreams.add(is);
            } catch(IOException e) {
                e.printStackTrace();
            }
            do {
                try {
                    packet = is.readObject();
                    packetHandler.handlePacket(packet);
                    /*
                    TODO: still to send to all the other connecting clients
                     */

                } catch(IOException e) {
                    e.printStackTrace();
                } catch(ClassNotFoundException e) {
                    e.printStackTrace();
                }
            } while(!packet.equals("/E"));
            // close all
        });
        listener.start();
    }

    public void sendPacket(Object packet) {
        try {
            if(isHost) {
                for(ObjectOutputStream os: outputStreams) {
                    os.writeObject(packet);
                    os.flush();
                }
            } else {
                os.writeObject(packet);
                os.flush();
            }
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void setPacketHandler(PacketHandler packetHandler) {
        this.packetHandler = packetHandler;
    }






}
