package game.networking.packets;

import game.networking.objects.Player;

import java.io.Serializable;


public class GuessPacket implements Serializable {


    private static final long serialVersionUID = 23L;
    
    private Player player;
    private String message;

    public GuessPacket(Player player, String message) {
        this.player = player;
        this.message = message;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
