package game.networking.objects;

import database.dao.MatchDAO;

import java.util.*;

public class MatchManager {
    /**
     * ArrayList which stores all the Active Matches on the Server
     */
    private static ArrayList<Match> ActiveMatches = new ArrayList<>();

    private static HashMap<Integer, ArrayList<Player>> playersInMatch = new HashMap<>();
    /**
     * Finds a MatchID which has less than four players, so a user can get assigned to this Match when attempting to find a game
     * @return
     */
    public static Match ReturnActiveMatch(){
        // Sort Matches by Match ID
        //Collections.sort(ActiveMatches, SortedMatches);

        MatchDAO matchDAO = new MatchDAO();

        for (Map.Entry<Integer,Integer> matches : matchDAO.getPlayersInMatch().entrySet()) {
            // Match ID
            int key = matches.getKey();
            // Player Count
            int value = matches.getValue();
            // If PlayerCount is less than 0
            if (value < 4){
                return matchDAO.GetMatch(key);
            }
        }
        // No Matches Found :(
        return null;
    }

    /**
     * Removes the Active Match from the ArrayList, this happens when the game has concluded
     * @param matchID of the match to remove
     */
    public static void RemoveActiveMatch(int matchID){
        for (Match p : ActiveMatches){
            if (p.getMatchID() == matchID){
                ActiveMatches.remove(p);
            }
        }
    }

    public static Map<Integer, ArrayList<Player>> GetPlayersInMatch() {
        return playersInMatch;
    }

    /**
     * Adds a New Active Match into the ArrayList
     * @param match
     */
    public static void AddActiveMatch(Match match){
        ActiveMatches.add(match);
    }

    /**
     * Updates the PlayerDomain Count of the Match (I.e when a PlayerDomain Leaves or Joins)
     * @param matchID of the match we want to update the playerCount of
     * @param newPlayerCount the new playerCount
     */
    public static void UpdatePlayerCount(int matchID, int newPlayerCount){
        MatchDAO.UpdatePlayerCount(matchID, newPlayerCount);
    }

    /**
     * Comparator to sort Matches in a Link by using the MatchID
     */
    private static Comparator<Match> SortedMatches = Comparator.comparingInt(Match::getMatchID);
}
