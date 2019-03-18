package game.networking;

import game.networking.objects.Player;
import game.networking.packets.PaintPacket;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GameScreen extends JPanel {
    private ArrayList<Player> players = new ArrayList<>();
    private Player p;

    private ConnectionHandler connectionHandler;
    private boolean isGameStarted = false;


    private CanvasComponent canvasComponent;
    private ChatComponent chatComponent;

    public GameScreen(Player p) {
        super();
        this.p = p;
        if(isValidPlayer(p)) {
            players.add(p);
        }

        this.setLayout(new BorderLayout(10,10));
        this.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));


        canvasComponent = new CanvasComponent(this.connectionHandler);
        chatComponent = new ChatComponent(p);
        this.add(canvasComponent, BorderLayout.CENTER);
        this.add(chatComponent, BorderLayout.EAST);


    }

    /**
     * checks valid player - only null check rest of checks to be done in player class
     * @param p
     * @return
     */
    public boolean isValidPlayer(Player p) {
        if(p != null) {
            return true;
        }
        return false;
    }

    public void joinGame(String serverAddress, int port) {
        System.out.println("Attempting to Join Game...");
        this.connectionHandler = new ConnectionHandler(this.p, serverAddress, port);
        this.connectionHandler.setPacketHandler(this::handlePacket);
        canvasComponent.start(this.connectionHandler);
        chatComponent.start(this.connectionHandler);
        connectionHandler.startClient();
        // start the chat networking when join the game also;

        //this.connectionHandler.sendPacket(new PaintPacket(PaintPacket.PaintEvents.DRAG, new Point(100,100))); <-- example of sending a packet to the game networking

        // write code for when player joins send code and stuff to draw
    }

    public void startGameLogic() {
        // decide on random word
        System.out.println("game logic started");
        // ...
    }



    public void handlePacket(Object packet) {
        if(packet instanceof String) {
            //show message in chat
            System.out.println(packet);
            chatComponent.showMessage((String)packet);
            // handle all of the game events and the messages in the chat networking
        } else if(packet instanceof PaintPacket) {
            PaintPacket p = (PaintPacket) packet;
            switch(p.eventType) {
                case DRAG:
                    this.canvasComponent.onDrag(p.point);
                    break;
                case RELEASED:
                    this.canvasComponent.onRelease(p.point);
                    break;
                case CLEAR:
                    canvasComponent.clear();
                    break;
                case CHANGE_COLOR:
                    System.out.println("Event Dispatched: Changed Color");
                    this.canvasComponent.getToolsPanel().setSelectedColor(p.index);
                    break;
                case PRESSED:
                    this.canvasComponent.onPress(p.point);
                    break;
                case CHANGE_BRUSH_SIZE:
                    this.canvasComponent.updateStroke(p.index);
            } // else if packet is instance of the game event
            // game events such as:
            // - change player turn
            // - correct guess
            // - game end
            // - player won
            // - player leaves the game - handle the networking getting closed
        }
    }
}
