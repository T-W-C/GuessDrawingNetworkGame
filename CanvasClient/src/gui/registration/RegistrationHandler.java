package gui.registration;

import networking.Client;
import networking.CurrentSession;
import networking.packets.incoming.CheckUsernamePacket;
import networking.packets.outgoing.SendUsernameCheckResult;

public class RegistrationHandler {
    public void SendPacket(String username) {
        CheckUsernamePacket packet = new CheckUsernamePacket();
        packet.username = username;
        Client.sendObject(packet);
    }

    public boolean Result(){
        SendUsernameCheckResult result = new SendUsernameCheckResult(CurrentSession.id);
        return result.result;
    }
}
