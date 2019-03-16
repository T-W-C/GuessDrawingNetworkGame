package Client;

import java.io.Serializable;

public class Player implements Serializable {
    private static final long serialVersionUID = 5L;

    private String playerName;
    private int playerScore;
    private boolean isDrawer;
    private boolean isHost;


    public void setIsHost(boolean isHost) {
        this.isHost = isHost;
    }

    public Player(String playerName, int playerScore, boolean isDrawer) {
        this.playerName = playerName;
        this.playerScore = playerScore;
        this.isDrawer = isDrawer;
    }

    public void setIsDrawer(boolean isDrawer) {
        this.isDrawer = isDrawer;
    }

    public boolean getIsDrawer() {
        return isDrawer;
    }

    public String getPlayerName() {
        return playerName;
    }

}
