package game.networking.packets;

import game.networking.objects.Player;

import java.io.Serializable;

public class CreateMatchPacket implements Serializable {
    private static final long serialVersionUID = 28L;
    public Player playerInstance;
}
