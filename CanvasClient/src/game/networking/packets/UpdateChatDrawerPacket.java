package game.networking.packets;

import game.networking.objects.Player;

import java.io.Serializable;

public class UpdateChatDrawerPacket implements Serializable {
    private static final long serialVersionUID = 26L;
    private Player player;

    public UpdateChatDrawerPacket(Player player) {
        this.player = player;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
