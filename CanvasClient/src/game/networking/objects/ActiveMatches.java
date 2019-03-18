package game.networking.objects;

import java.util.ArrayList;
import java.util.HashMap;

public class ActiveMatches {
    private static HashMap<Integer, Match> ActiveMatches = new HashMap<>();

    public static HashMap<Integer, Match> GetActiveMatches(){
        return ActiveMatches;
    }
}
