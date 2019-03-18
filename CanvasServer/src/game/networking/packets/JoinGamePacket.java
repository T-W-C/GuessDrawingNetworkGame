package game.networking.packets;

import java.io.Serializable;

public class JoinGamePacket implements Serializable {
    private static final long serialVersionUID = 1L;

    private String message;

    public JoinGamePacket() {
        this.message = "CAN_I_JOIN?"; // redundant field
    }

    public String getMessage() {
        return this.message;
    }
}
