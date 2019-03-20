package networking.packets.incoming;

import java.io.Serializable;

public class CheckUsernamePacket implements Serializable {
    private static final long serialVersionUID = 6L;

    public int playerSession;

    public String username;

    public boolean usernameCheckResult;
}
