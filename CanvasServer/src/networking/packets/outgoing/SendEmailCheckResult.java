package networking.packets.outgoing;

import java.io.Serializable;

public class SendEmailCheckResult implements Serializable {
    public int playerID;
    public boolean result;

    public SendEmailCheckResult(int playerID){
        this.playerID = playerID;
    }
}
