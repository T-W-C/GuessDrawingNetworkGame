package game.networking.objects;

import java.util.ArrayList;

public class MatchManager {
    /**
     * ArrayList which stores all the Active Matches on the Server
     */
    public static ArrayList<Match> ActiveMatches = new ArrayList<>();

    /**
     * Finds a MatchID which has less than four players, so a user can get assigned to this Match when attempting to find a game
     * @return
     */
    public static int ReturnActiveMatch(){
        for (Match p : ActiveMatches){
            if(p.getPlayerCount() < 4){
                return p.getMatchID();
            }
        }
        // -1 = No Matches Found :(
        return -1;
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

    /**
     * Adds a New Active Match into the ArrayList
     * @param matchID
     * @param playerCount
     */
    public static void AddActiveMatch(int matchID, int playerCount){
        Match activeMatch = new Match(matchID, playerCount, true);
        ActiveMatches.add(activeMatch);
    }

    /**
     * Updates the Player Count of the Match (I.e when a Player Leaves or Joins)
     * @param matchID of the match we want to update the playerCount of
     * @param newPlayerCount the new playerCount
     */
    public static void UpdatePlayerCount(int matchID, int newPlayerCount){
        for (Match p : ActiveMatches){
            if(p.getMatchID() == matchID){
                p.setPlayerCount(newPlayerCount);
            }
        }
    }
}
