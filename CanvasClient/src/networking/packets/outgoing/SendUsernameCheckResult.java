package networking.packets.outgoing;

import gui.registration.RegistrationHandler;

import java.io.Serializable;

public class SendUsernameCheckResult implements Serializable {
    public int playerID;
    public boolean result;

    public SendUsernameCheckResult(int playerID){
        this.playerID = playerID;
    }
}
