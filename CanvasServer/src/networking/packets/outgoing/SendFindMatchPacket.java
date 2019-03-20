package networking.packets.outgoing;

import game.networking.objects.Player;

import java.io.Serializable;

public class SendFindMatchPacket implements Serializable {
    private static final long serialVersionUID = 1L;
    public Player player;
}
