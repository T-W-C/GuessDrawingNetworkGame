package game.networking.objects;

public class Match {
    private int matchID;
    private int playerCount;
    private boolean isMatchActive;

    public Match(int matchID, int playerCount, boolean isMatchActive) {
        this.matchID = matchID;
        this.playerCount = playerCount;
        this.isMatchActive = isMatchActive;
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

    // Create Packet to Update Player Count
    // Create Packet to Update Match Active Status (True = Active, False = Finished)
}
