package networking.packets.outgoing;

import java.io.Serializable;

public class SendPasswordHashConfirmation implements Serializable {
    public int playerID;
    public String password;
    public String hashedPassword;
    public boolean passwordResult;

    public SendPasswordHashConfirmation(int playerID) {
        this.playerID = playerID;
    }
}
