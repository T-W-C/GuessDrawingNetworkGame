package game.Server;

import java.io.Serializable;

public class JoinGamePacket implements Serializable {
    private String message;

    public JoinGamePacket() {
        this.message = "CAN_I_JOIN?"; // redundant field
    }

    public String getMessage() {
        return this.message;
    }
}
