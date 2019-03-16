package gui.login;

import networking.Client;
import networking.packets.incoming.LoginUsernamePacket;

public class LoginHandler {
    public static boolean userExisting;
    public static boolean passwordMatches;

    public void SendLoginUsernameCheck(String username){
        LoginUsernamePacket packet = new LoginUsernamePacket();
        packet.username = username;
        Client.sendObject(packet);
    }
}
