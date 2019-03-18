package game.networking;

import game.networking.objects.Player;
import game.networking.packets.PaintPacket;
import networking.EventListener;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ConnectionHandler {
    public int port;
    private static String serverAddress;

    private ObjectOutputStream os;

    private Boolean isDrawer = true;

    private PacketHandler packetHandler;

    private Player p;
    private String message;

    private Socket clientConnection;
    private ServerSocket server;
    private ArrayList<ObjectInputStream> inputStreams;
    private ArrayList<ObjectOutputStream> outputStreams;

    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;

    public ConnectionHandler(Player p, String serverAddress, int port) {
        ConnectionHandler.serverAddress = serverAddress;
        this.p = p;
        this.port = port;
    }

    public ObjectOutputStream getOutputStream() {
        return outputStream;
    }

    public ObjectInputStream getInputStream() {
        return inputStream;
    }

    public boolean getIsDrawer() {
        return isDrawer;
    }



//    public void initiateServer(Runnable onClientConnection) {
//        this.isDrawer = true;
//        this.isServer = true;
//        System.out.println("Started initiation of networking...");
//        //launch new thread for initiation of networking
//        new Thread(() -> {
//           try {
//               networking = new ServerSocket(port, 2);
//               System.out.println("networking " + networking.toString() + " has initiated.");
//               while(true) {
//                   try {
//                       System.out.println("Waiting to accept sockets...");
//                       Socket socket = networking.accept();
//                       addOutputStream(socket);
//                       // create a new thread to handle the inputs from the socket
//                       // that has been accepted above.
//                       new Thread(() -> {
//                           onClientConnection.run();
//                           this.listen(socket);
//                       }).start();
//
////                       new Thread(() -> {
////                           socket.getInputS
////                       })
//                       //start the above thread.
//                   } catch(IOException e) {
//                        // close the application
//                   }
//               }
//           } catch(IOException e) {
//               System.out.println("Something went wrong");
//           }
//        }).start();
//    }

    public void startClient() {
//        this.isDrawer = false;
//        this.isServer = false;
        try {
            this.clientConnection = new Socket(serverAddress, port);
            System.out.println("Connected to: " + clientConnection.getInetAddress().getHostName());
            outputStream = new ObjectOutputStream(this.clientConnection.getOutputStream());
            outputStream.writeObject(p);
//            addOutputStream(this.clientConnection);
//            System.out.println(outputStreams.size());
            new Thread(() -> this.listen(this.clientConnection)).start();
        } catch(IOException e) {
            e.printStackTrace();
            //close all
        }
    }

//    /**
//     * for each of the networking (socket connections) that have connected
//     * to the networking then they need to be added to the arraylist of socket
//     * connections.
//     * @param socket
//     * @throws IOException
//     */
//    private void addOutputStream(Socket socket) throws IOException {
//        os = new ObjectOutputStream(socket.getOutputStream());
//        os.flush();
//        outputStreams.add(os);
//        System.out.println("Output streams have been added");
//    }

    private boolean isServer;

    public void listen(Socket socket) {
        Thread listener = new Thread(() -> {
            Object packet = "";
            try {
                inputStream = new ObjectInputStream(socket.getInputStream());
                System.out.println("OUTPUT AND INPUT STREAMS INITIATED");
            } catch(IOException e) {
                e.printStackTrace();
            }
            do {
                try {
                    packet = inputStream.readObject();
                    packetHandler.handlePacket(packet);
//                    if(isServer) {
//                        sendPacket(packet);
//                    }

                } catch(IOException e) {
                    e.printStackTrace();
                } catch(ClassNotFoundException e) {
                    e.printStackTrace();
                }
            } while(!packet.equals("Exit"));
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
//                for(ObjectOutputStream os: outputStreams) {
//                    os.writeObject(packet);
//                    os.flush();
//                }
//            } else {
                System.out.println(outputStream.toString() + " is the output stream");
                outputStream.writeObject(packet);
                outputStream.flush();
            }
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void setPacketHandler(PacketHandler packetHandler) {
        this.packetHandler = packetHandler;
    }
}
