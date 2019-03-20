package game.networking.packets;

import java.io.Serializable;

public class UpdateSideBarPacket implements Serializable {
	private static final long serialVersionUID = 15L;
    // This class acts as a request, we don't need to pass any data, all this does is let the server know
	// that a player has joined or left the game
}