package gui.registration;

import networking.Client;
import networking.packets.incoming.CheckCreateAccountPacket;
import networking.packets.incoming.CheckEmailPacket;
import networking.packets.incoming.CheckUsernamePacket;


public class RegistrationHandler {
    public static boolean userResult = false;
    public static boolean emailResult = false;


    public void SendUsernamePacket(String username) {
        CheckUsernamePacket packet = new CheckUsernamePacket();
        packet.username = username;
        Client.sendObject(packet);
    }

    public void SendEmailPacket(String email) {
        CheckEmailPacket packet = new CheckEmailPacket();
        packet.email = email;
        Client.sendObject(packet);
    }

    public void SendCreateAccountPacket(String email, String username, String hashedPassword){
        CheckCreateAccountPacket packet = new CheckCreateAccountPacket();
        packet.email = email;
        packet.username = username;
        packet.hashedPassword = hashedPassword;
        Client.sendObject(packet);
    }
}
