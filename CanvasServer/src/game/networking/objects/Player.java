package game.networking.objects;

import java.io.Serializable;

public class Player implements Serializable {
    private static final long serialVersionUID = 14L;

    private String playerName;
    private int playerScore;
    private boolean isDrawer;

    public Player(String playerName) {
        this.playerName = playerName;
        this.playerScore = 0;
        this.isDrawer = false;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public boolean getIsDrawer() {
        return isDrawer;
    }

    // every new word this should be used from the game screen to change this variable for one player
    public void setIsDrawer(boolean isDrawer) {
        this.isDrawer = isDrawer;
    }

    public int getPlayerScore() {
        return this.playerScore;
    }

    public void updatePlayerScore(int score) {
        this.playerScore += score; // update the player score variable by the parameter
    }
}
