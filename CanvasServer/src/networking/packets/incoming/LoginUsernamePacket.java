package networking.packets.incoming;

import java.io.Serializable;

public class LoginUsernamePacket implements Serializable {
    private static final long serialVersionUID = 1L;

    public String username;
}
