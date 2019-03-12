package networking.packets.incoming;

import java.io.Serializable;

public class CheckPasswordHash implements Serializable {
    private static final long serialVersionUID = 1L;

    public int playerSession;

    public String password;

    public boolean passwordCheckResult;
}
