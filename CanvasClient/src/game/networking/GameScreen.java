package game.networking;

import game.networking.objects.ActiveMatches;
import game.networking.objects.Match;
import game.networking.objects.Player;
import game.networking.packets.*;
import networking.Client;
import networking.packets.outgoing.SendFindMatchPacket;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GameScreen extends JPanel {
    private ArrayList<Player> players = new ArrayList<>();
    private Player player;
    private Match match;

    private ConnectionHandler connectionHandler;
    private boolean isGameStarted = false;

    private CanvasComponent canvasComponent;
    private ChatComponent chatComponent;

    public GameScreen(Player player) throws Exception {
        super();
        this.player = player;
        if (isValidPlayer(player)) {
            players.add(player);
        }

        this.setLayout(new BorderLayout(10, 10));
        this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        canvasComponent = new CanvasComponent(this.connectionHandler, player);
        chatComponent = new ChatComponent(player);
        this.add(canvasComponent, BorderLayout.CENTER);
        this.add(chatComponent, BorderLayout.EAST);

    }

    /**
     * checks valid player - only null check rest of checks to be done in player
     * class
     *
     * @param p
     * @return
     */
    public boolean isValidPlayer(Player p) {
        if (p != null) {
            return true;
        }
        return false;
    }

    public void joinGame(String serverAddress) throws InterruptedException {
        /* The purpose of Task is to give time for us to Send the Packets and get the results back from the networking before checking data*/

        /* Send all Packets to networking for Login */
        // Find a Active MatchID
        SendFindMatchPacket packet = new SendFindMatchPacket();
        packet.player = this.player;
        Client.sendObject(packet);
        Thread.sleep(500);

        this.match = ActiveMatches.GetCurrentMatch();

        //this.canvasComponent.updateSideBar(ActiveMatches.GetPlayersInMatch());

        this.connectionHandler = new ConnectionHandler(this.player, serverAddress, match.getMatchPort());
        this.connectionHandler.setPacketHandler(this::handlePacket);
        canvasComponent.start(this.connectionHandler);
        chatComponent.start(this.connectionHandler);
        connectionHandler.startClient();
        System.out.println("Attempting to Join Game on Port " + match.getMatchPort());
        // start the chat networking when join the game also;
        // write code for when player joins send code and stuff to draw
    }

    public void startGameLogic() {
        // decide on random word
        System.out.println("game logic started");
        // ...
    }

    public void handlePacket(Object packet) throws Exception {
        if (packet instanceof String) {
            // show message in chat
            System.out.println(packet);
            chatComponent.showMessage((String) packet);
            // handle all of the game events and the messages in the chat networking
        } else if (packet instanceof GameEventPacket) {

            GameEventPacket p = (GameEventPacket) packet;
            System.out.println("outside should call now");
            switch (p.eventType) {
            case GUESS_CORRECT:
                System.out.println("should call now");
                chatComponent.showMessage(p.message);
            }
        } else if (packet instanceof UpdateChatDrawerPacket) {
            this.chatComponent.updateDrawer(((UpdateChatDrawerPacket) packet).getPlayer());
            this.canvasComponent.updateDrawer(((UpdateChatDrawerPacket) packet).getPlayer());
        }
        else if(packet instanceof GameServerArrayListPacket) { // received arrayList of players from gameServer
            this.canvasComponent.updateSideBar(((GameServerArrayListPacket) packet).getPlayers());
        }
        else if(packet instanceof SendWordPacket) { // received word so update wordbar
            this.canvasComponent.updateWordBar(((SendWordPacket) packet).getWord());
        }


        else if (packet instanceof PaintPacket) {
            PaintPacket p = (PaintPacket) packet;
            switch (p.eventType) {
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
            }
        }
        // else if packet is instance of the game event
        // game events such as:
        // - change player turn
        // - correct guess
        // - game end
        // - player won
        // - player leaves the game - handle the networking getting closed

    }
}
