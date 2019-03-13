package networking.packets.outgoing;

import java.io.Serializable;

public class SendCreateAccountPacket implements Serializable {
    // Connection ID (doesn't go into database)
    public int playerID;

    public String email;
    public String username;
    public String hashedPassword;

    public SendCreateAccountPacket(int playerID) {
        this.playerID = playerID;
    }
}
