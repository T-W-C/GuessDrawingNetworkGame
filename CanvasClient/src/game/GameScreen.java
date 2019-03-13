import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GameScreen extends JPanel {
    private ArrayList<Player> players = new ArrayList<>();

    public GameScreen(Player p) {
        super();
        if(isValidPlayer(p)) {
            players.add(p);
        }

        setLayout(new BorderLayout(10,10));
        setBorder(BorderFactory.createEmptyBorder(10,10,10,10));




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

    }
}
