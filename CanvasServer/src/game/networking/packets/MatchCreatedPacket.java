package game.networking.packets;

import game.networking.objects.Match;

import java.io.Serializable;

public class MatchCreatedPacket implements Serializable {
    private static final long serialVersionUID = 19L;
    public Match createdMatchInstance;
}
