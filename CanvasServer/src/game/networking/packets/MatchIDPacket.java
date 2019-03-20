package game.networking.packets;

import java.io.Serializable;

public class MatchIDPacket implements Serializable {
    private static final long serialVersionUID = 37L;
    private int matchID;

    public int getMatchID() {
        return matchID;
    }

    public void setMatchID(int matchID) {
        this.matchID = matchID;
    }
}
