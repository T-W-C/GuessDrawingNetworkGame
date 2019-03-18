package game.networking.packets;

import java.io.Serializable;

public class GamePacket implements Serializable {
    private static final long serialVersionUID = 12L;

    public enum GameEvents {
        CHANGE_TURN,
        END_GAME,
        PLAYER_WIN
    }

    private GameEvents eventTypes;
}
