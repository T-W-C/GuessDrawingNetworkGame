package game.networking;

import game.networking.objects.MatchManager;
import game.networking.objects.Player;
import game.networking.packets.PaintPacket;
import game.networking.packets.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class GameServer extends Thread {

    private static GameServer gameServer;
    private GamePlay gameLogic;

    private boolean isGameInitiated = false;

    private ArrayList<Player> connectedPlayers = new ArrayList<>();

    public static GameServer getInstance() {
        if (gameServer == null) {
            gameServer = new GameServer();
        }
        return gameServer;
    }

    private ArrayList<Socket> clientsConnected = new ArrayList<>();
    private ArrayList<ObjectOutputStream> outputStreams = new ArrayList<>();
    private ArrayList<ObjectInputStream> inputStreams = new ArrayList<>();

    private ServerSocket server;

    private String serverAddress;
    private int port;

    ObjectInputStream inputStream;

    public GameServer setServerIP(String serverAddress) {
        this.serverAddress = serverAddress;
        return this;
    }

    public GameServer setPort(int port) {
        this.port = port;
        return this;
    }

    public void start() {
        // initialise stuff here
        super.start();
    }

    @Override
    public void run() {
        try {
            gameLogic = new GamePlay(this);
            server = new ServerSocket(port, 4);
            while (true) {
                try {
                    System.out.println("Listening for socket connection...");
                    Socket socket = server.accept();
                    // Object o = null;
                    // try {
                    //// inputStream = new ObjectInputStream(socket.getInputStream());
                    //// o = inputStream.readObject();
                    // } catch(Exception e) {
                    //
                    // }
                    // if(o instanceof String && o.equals("JOINEXISTINGGAME") &&
                    // connectedPlayers.size() == 0) {
                    //// sendPacket("invalid_connection");
                    // socket.close();
                    // }
                    System.out
                            .println("Socket connection: " + socket.getInetAddress().getHostName() + " has connected");
                    this.addClientSocket(socket);

                    new Thread(() -> {
                        listen(socket);
                    }).start();
                    System.out.println("Listening on another thread for inputs");

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addClientSocket(Socket socket) {
        clientsConnected.add(socket);
        try {
            addOutputStream(socket);
            addInputStream(socket);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Socket> getConnectedClients() {
        return clientsConnected;
    }

    private void addOutputStream(Socket socket) throws IOException {
        outputStreams.add(new ObjectOutputStream(socket.getOutputStream()));
    }

    private void addInputStream(Socket socket) throws IOException {
        inputStreams.add(new ObjectInputStream(socket.getInputStream()));
    }

    private void listen(Socket socket) {
        Thread listener = new Thread(() -> {
            Object packet = "";
            ObjectInputStream is = null;

            System.out.println("Listener started");
            // is = new ObjectInputStream(socket.getInputStream());
            //// System.out.println(is == null);
            is = inputStreams.get(inputStreams.size() - 1);
            inputStreams.add(is);

            try {

                packet = is.readObject();
                Player p = (Player) packet;
                synchronized (connectedPlayers) {
                    connectedPlayers.add(p); // add player to players array list
                    GameServerArrayListPacket gsalp = new GameServerArrayListPacket(); // create new packet to send the updated list
                    gsalp.setPlayers(connectedPlayers); // set the list of the packet to the updated player list in the game server
                    sendPacket(gsalp); // send packet to all clients
                    if(this.connectedPlayers.size() == 2) { // only tell players to update their sidebar once all 4 players are in since game doesnt start until then anyway

                    }
                }

                if (connectedPlayers.size() == 2) {
                    new Thread(() -> {
                        gameLogic.start();
                    }).start();
                }
            } catch (IOException e) {

                e.printStackTrace();

            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            do {
                try {
                    packet = is.readObject();

                    System.out.println("Object has been received on networking");
                    // handle the packet that is coming in - checks what type of packet it is
                    handlePacket(packet);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            } while (!packet.equals("Exit"));
        });
        listener.start();
    }

    public ArrayList<Player> getConnectedPlayers() {
        return connectedPlayers;
    }

    public synchronized void handlePacket(Object packet) {
        System.out.println("Packet is attempting to be handled");
        // sendPacket(packet);

        System.out.println("is this a guesspacket: " + (packet instanceof GuessPacket));
        if (packet instanceof String) {
            // show message in chat

            System.out.println(packet);
            sendPacket(packet);
            // handle all of the game events and the messages in the chat networking

        } else if (packet instanceof GuessPacket) {
            GuessPacket gp = (GuessPacket) packet;
            String guess = gp.getMessage();

            System.out.println(guess);
            // chatMsg consists of the player username plus msg as seen in the chat
            // component
            String chatMsg = gp.getPlayer().getPlayerName().toLowerCase() + ": "
                    + this.gameLogic.getWord().toLowerCase();
            if (guess.toLowerCase().equals(chatMsg)) {
                String message = "Player: " + gp.getPlayer().getPlayerName() + " has guessed the word!";
                sendPacket(new GameEventPacket(GameEventPacket.EventType.GUESS_CORRECT, message));
                System.out.println(message);

                gameLogic.guessedCorrect();
                // little delay here

                // int index = connectedPlayers.indexOf(gp.getPlayer());
                // connectedPlayers.get(index).updatePlayerScore(50);

                for (Player player : connectedPlayers) {
                    if (player.getIsDrawer()) {
                        player.updatePlayerScore(50);
                    } else if (player.equals(((GuessPacket) packet).getPlayer())) {
                        player.updatePlayerScore(50);
                    }
                }

            } else {
                sendPacket(guess);
            }
        } else if (packet.getClass().equals(PaintPacket.class)) {
            sendPacket(packet);

        }

        // Host Logic (Use this for CreateGame)
        else if (packet instanceof Player) {
            Player player = (Player) packet;
            if (connectedPlayers.size() == 0) {
                connectedPlayers.add(player);
                // send packet to update UI
                // update the side panel
                // draw the canvas

            } else if (connectedPlayers.size() < 4) {

                // send packet to update UI
                // update the side panel
                // draw the canvas
            } else {
                // send refused connection packet
                // when refused connection packet sent networking attempts to reestablish
                // connection with a different port binding...
                // sendPacket()
            }

        }
    }

    public void sendPacket(Object packet) {
        try {
            // if(networking.PaintPacket.class.isInstance(packet)) {
            // handle if the player is a drawer
            for (ObjectOutputStream os : outputStreams) {
                // System.out.println("writing: " + packet + ", to networking: " +
                // os.toString());
                os.reset(); // testing purposes only
                os.writeObject(packet);
                os.flush();
            }
            // } // else if( ... test for any more sending events {
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
