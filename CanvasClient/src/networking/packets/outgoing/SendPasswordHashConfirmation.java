package networking.packets.outgoing;

import java.io.Serializable;

public class SendPasswordHashConfirmation implements Serializable {
    private static final long serialVersionUID = 11L;

    public boolean passwordResult;
}
