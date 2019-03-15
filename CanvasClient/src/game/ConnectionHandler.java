import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ConnectionHandler {
    public static int port;
    private static String serverAddress;

    private ObjectOutputStream os;

    private Boolean isDrawer;

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

    public boolean getIsDrawer() {
        return isDrawer;
    }



    public void initiateServer(Runnable onClientConnection) {
        this.isDrawer = true;
        this.isServer = true;
        System.out.println("Started initiation of server...");
        //launch new thread for initiation of server
        new Thread(() -> {
           try {
               server = new ServerSocket(port, 2);
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

//                       new Thread(() -> {
//                           socket.getInputS
//                       })
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

    public void startClient() {
        this.isDrawer = false;
        this.isServer = false;
        try {
            this.clientConnection = new Socket(serverAddress, port);
            System.out.println("Connected to: " + clientConnection.getInetAddress().getHostName());
            addOutputStream(this.clientConnection);
            System.out.println(outputStreams.size());
            new Thread(() -> this.listen(this.clientConnection)).start();
        } catch(IOException e) {
            e.printStackTrace();
            //close all
        }
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

    private boolean isServer;

    public void listen(Socket socket) {
        Thread listener = new Thread(() -> {
            Object packet = "";
            ObjectInputStream is = null;

            try {
                is = new ObjectInputStream(socket.getInputStream());
                inputStreams.add(is);
            } catch(IOException e) {
                e.printStackTrace();
            }
            do {
                try {
                    packet = is.readObject();
                    packetHandler.handlePacket(packet);
                    if(isServer) {
                        sendPacket(packet);
                    }
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
            if(PaintPacket.class.isInstance(packet)) {
                System.out.println("YES IT IS AN INST");
            }
            System.out.println("connection is: " + isDrawer);
            if(isDrawer) {
                for(ObjectOutputStream os: outputStreams) {
                    os.writeObject(packet);
                    os.flush();
                }
            } else {
                System.out.println(os.toString() + " is the output stream");
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
