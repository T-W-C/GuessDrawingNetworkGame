package game.networking.packets;

import java.io.Serializable;

public class GameEventPacket implements Serializable {
    private static final long serialVersionUID = 24L;


    public enum EventType {
        PLAYER_GUESS,
        GUESS_CORRECT,
        GAME_END,
        GAME_START,
        ROUND_START,
        TURN_START
    }

    public String message;

    public EventType eventType;

    public GameEventPacket(EventType eventType, String message) {
        this.eventType = eventType;
        this.message = message;
    }

}
