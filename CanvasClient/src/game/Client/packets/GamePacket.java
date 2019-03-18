package game.client.packets;

import java.io.Serializable;

public class GamePacket implements Serializable {

    public enum GameEvents {
        CHANGE_TURN,
        END_GAME,
        PLAYER_WIN
    }

    private GameEvents eventTypes;
}
