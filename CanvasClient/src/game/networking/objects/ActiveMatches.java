package game.networking.objects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ActiveMatches {
    private static ArrayList<Match> ActiveMatches = new ArrayList<>();
    private static ArrayList<Player> playersInMatch = new ArrayList<>();
    private static Match currentMatch = null;

    public static ArrayList<Match> GetActiveMatches() {
        return ActiveMatches;
    }

    public static ArrayList<Player> GetPlayersInMatch() {
        return playersInMatch;
    }

    public static Match GetCurrentMatch() {
        return currentMatch;
    }

    public static void SetCurrentMatch(Match match) {
        currentMatch = match;
    }

    public static void SetCurrentPlayers(ArrayList<Player> players) {
        playersInMatch = players;
    }
}
