package game.networking.objects;

import java.io.Serializable;

public class Player implements Serializable {
    private static final long serialVersionUID = 1L;

    private String playerName;
    private int playerScore;
    private boolean isDrawer;
    private boolean isHost;
    private boolean guessedWordCorrectly;

    public Player(String playerName, boolean isDrawer) {
        this.playerName = playerName;
        this.playerScore = 0;
        this.isDrawer = false;
        this.isHost = false;
        this.guessedWordCorrectly = false;
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

    public boolean getIsHost() {
        return isHost;
    }

    public void setIsHost(boolean host) {
        isHost = host;
    }

    public boolean getGuessedWordCorrectly() { // every time a player guesses a word correctly change this variable for that specific word
        return this.guessedWordCorrectly;
    }

    public void setGuessedWordCorrectly(boolean guessedWordCorrectly) {
        this.guessedWordCorrectly = guessedWordCorrectly;
    }


    public int getPlayerScore() {
        return this.playerScore;
    }

    public void updatePlayerScore(int score) {
        this.playerScore += score; // update the player score variable by the parameter
    }
}
