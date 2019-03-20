package game.networking.objects;

import java.io.Serializable;

public class Match implements Serializable {

    private int matchID;
    private int playerCount;
    private boolean isMatchActive;
    private int matchPort;

    public Match(int matchID, int playerCount, int matchPort, boolean isMatchActive) {
        this.matchID = matchID;
        this.playerCount = playerCount;
        this.matchPort = matchPort;
        this.isMatchActive = isMatchActive;
    }

    /**
     * This instance is used to created a active match
     * @param matchID
     */
    public Match(int matchID) {
        this.matchID = matchID;
        this.playerCount = 1;
        this.matchPort = matchPort;
        this.isMatchActive = true;
    }

    public int getMatchID() {
        return matchID;
    }

    public void setMatchID(int matchID) {
        this.matchID = matchID;
    }

    public boolean getIsMatchActive() {
        return isMatchActive;
    }

    public void setMatchActive(boolean matchActive) {
        isMatchActive = matchActive;
    }

    public int getPlayerCount() {
        return playerCount;
    }

    public void setPlayerCount(int playerCount) {
        this.playerCount = playerCount;
    }

    public int getMatchPort() {
        return matchPort;
    }

    public void setMatchPort(int matchPort) {
        this.matchPort = matchPort;
    }

    // Create Packet to Update PlayerDomain Count
    // Create Packet to Update Match Active Status (True = Active, False = Finished)
}
