package networking.packets.incoming;

import game.networking.objects.Match;
import game.networking.objects.Player;

import java.io.Serializable;
import java.util.ArrayList;

public class CheckFindMatchResult implements Serializable {
    private static final long serialVersionUID = 1L;

    public Match activeMatch;

    public ArrayList<Player> players;
    public ArrayList<Match> matches;

    public ArrayList<Match> getMatches() {
        return this.matches;
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

    public void setMatches(ArrayList<Player> players) {
        this.matches = matches;
    }
}
