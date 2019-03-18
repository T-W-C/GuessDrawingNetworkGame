package networking.packets.incoming;

import java.io.Serializable;

public class CheckCreateAccountPacket implements Serializable {
    private static final long serialVersionUID = 1L;

    // Connection ID (doesn't go into database)
    public int playerID;

    public String email;
    public String username;
    public String hashedPassword;
}
