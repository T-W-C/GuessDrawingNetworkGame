package database.domain;

import java.sql.Date;
import java.sql.Timestamp;

public class MatchDomain {
    private int matchID;
    private Timestamp timestamp;
    private int gamemode;
    private boolean isActive;

    public MatchDomain(Timestamp timestamp) {
        this.matchID = matchID;
        this.timestamp = timestamp;
        // We only have 1 game mode right
        this.gamemode = 1;
        this.isActive = true;
    }

    public int getMatchID() {
        return matchID;
    }

    public void setMatchID(int matchID) {
        this.matchID = matchID;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public int getGamemode() {
        return gamemode;
    }

    public void setGamemode(int gamemode) {
        this.gamemode = gamemode;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
