package gui.login;

import networking.Client;
import networking.packets.incoming.CheckPasswordHashConfirmation;
import networking.packets.incoming.LoginUsernamePacket;

public class LoginHandler {
    public static boolean userExisting;
    public static boolean passwordMatches;

    public void SendLoginUsernameCheck(String username){
        LoginUsernamePacket packet = new LoginUsernamePacket();
        packet.username = username;
        Client.sendObject(packet);
    }

    public void SendCheckPasswordHashCheck(String username, String password){
        CheckPasswordHashConfirmation passwordHashConfirmation = new CheckPasswordHashConfirmation();
        passwordHashConfirmation.username = username;
        passwordHashConfirmation.password = password;
        Client.sendObject(passwordHashConfirmation);
    }
}
