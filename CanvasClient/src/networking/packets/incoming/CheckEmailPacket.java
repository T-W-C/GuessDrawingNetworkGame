package networking.packets.incoming;

import java.io.Serializable;

public class CheckEmailPacket implements Serializable {
    private static final long serialVersionUID = 4L;

    public int playerSession;

    public String email;

}
