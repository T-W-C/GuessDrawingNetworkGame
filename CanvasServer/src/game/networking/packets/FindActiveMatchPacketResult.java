package game.networking.packets;

import java.io.Serializable;
import java.util.ArrayList;

import game.networking.objects.Player;

public class FindActiveMatchPacketResult implements Serializable {
    private static final long serialVersionUID = 14L;
    public int activeMatchID;
    public ArrayList<Player> players;

    public ArrayList<Player> getPlayers() {
        return this.players;
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }
}
