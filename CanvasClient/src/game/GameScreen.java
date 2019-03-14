import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GameScreen extends JPanel {
    private ArrayList<Player> players = new ArrayList<>();

    private CanvasComponent canvasComponent;

    public GameScreen(Player p) {
        super();
        if(isValidPlayer(p)) {
            players.add(p);
        }

        this.setLayout(new BorderLayout(10,10));
        this.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));


        canvasComponent = new CanvasComponent();
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

        this.canvasComponent.start(serverAddress, port);
    }
}
