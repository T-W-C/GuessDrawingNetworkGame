package networking.packets.outgoing;

import java.io.Serializable;

public class SendCreateAccountPacket implements Serializable {
    private static final long serialVersionUID = 9L;

    // Connection ID (doesn't go into database)
    public int playerID;

    public String email;
    public String username;
    public String hashedPassword;

    public SendCreateAccountPacket(int playerID) {
        this.playerID = playerID;
    }
}
