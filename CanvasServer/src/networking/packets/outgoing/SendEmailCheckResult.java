package networking.packets.outgoing;

import java.io.Serializable;

public class SendEmailCheckResult implements Serializable {
    private static final long serialVersionUID = 1L;

    public int playerID;
    public boolean result;

    public SendEmailCheckResult(int playerID){
        this.playerID = playerID;
    }
}
