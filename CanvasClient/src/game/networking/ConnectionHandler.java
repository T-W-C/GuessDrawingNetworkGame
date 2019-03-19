package game.networking;

import game.networking.objects.Player;
import game.networking.packets.GuessPacket;
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


    public void startClient() {
//        this.isDrawer = false;
//        this.isServer = false;
        try {
            this.clientConnection = new Socket(serverAddress, port);
            System.out.println("Connected to: " + clientConnection.getInetAddress().getHostName());
            outputStream = new ObjectOutputStream(this.clientConnection.getOutputStream());
            outputStream.writeObject(p);
//            outputStream.writeObject(new GuessPacket(p, "testing123"));
//            addOutputStream(this.clientConnection);
//            System.out.println(outputStreams.size());
            new Thread(() -> this.listen(this.clientConnection)).start();
        } catch(IOException e) {
            e.printStackTrace();
            //close all
        }
    }

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

            System.out.println(outputStream.toString() + " is the output stream");

            outputStream.writeObject(packet);
            outputStream.flush();


        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void setPacketHandler(PacketHandler packetHandler) {
        this.packetHandler = packetHandler;
    }
}
