import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GameScreen extends JPanel {
    private ArrayList<Player> players = new ArrayList<>();
    private Player p;

    private ConnectionHandler connectionHandler;
    private boolean isGameStarted = false;


    private CanvasComponent canvasComponent;

    public GameScreen(Player p) {
        super();
        this.p = p;
        if(isValidPlayer(p)) {
            players.add(p);
        }

        this.setLayout(new BorderLayout(10,10));
        this.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));


        canvasComponent = new CanvasComponent(this.connectionHandler);
        this.add(canvasComponent, BorderLayout.CENTER);



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

    public void initialiseGame(String serverAddress, int port) {
        //create socket handler with the server address and port

        System.out.println("Attempting to initiate Server...");
        this.connectionHandler = new ConnectionHandler(p, serverAddress, port);
        this.connectionHandler.setPacketHandler(this::handlePacket);
        this.connectionHandler.initiateServer(() -> {
            // could do another check to see if the amount of players is equal to 4
            //if it is equal to 4 then call the startGameLogic method.
            if(!isGameStarted) {
                //
                this.canvasComponent.start(this.connectionHandler);
                System.out.println("Started server...");
                startGameLogic();
            }
        });


        this.canvasComponent.start(this.connectionHandler);
    }

    public void joinExistingGame(String serverAddress, int port) {
        this.connectionHandler = new ConnectionHandler(this.p, serverAddress, port);
        this.connectionHandler.setPacketHandler(this::handlePacket);
        connectionHandler.startClient();
        // start thte chat client when join the game also;


    }

    public void startGameLogic() {
        // decide on random word

        // ...
    }



    public void handlePacket(Object packet) {
        if(packet instanceof String) {
            //show message in chat

            // handle all of the game events and the messages in the chat client
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
                    this.canvasComponent.getToolsPanel().setSelectedColor(p.index);
                    break;
                case PRESSED:
                    this.canvasComponent.onPress(p.point);
                    break;
            } // else if packet is instance of the game event
            // game events such as:
            // - change player turn
            // - correct guess
            // - game end
            // - player won
            // - player leaves the game - handle the client getting closed
        }
    }
}
