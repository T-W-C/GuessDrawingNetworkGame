package networking.packets.outgoing;

import java.io.Serializable;

public class SendPasswordHashRequest implements Serializable {
    public int playerID;
    public String password;
    public boolean passwordResult;

    public SendPasswordHashRequest(int playerID) {
        this.playerID = playerID;
    }
}
