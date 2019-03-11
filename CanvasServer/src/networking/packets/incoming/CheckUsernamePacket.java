package networking.packets.incoming;

import java.io.Serializable;

public class CheckUsernamePacket implements Serializable {
    private static final long serialVersionUID = 1L;

    public int playerID;
    public String username;
    public boolean usernameCheckResult;
}
