package networking.packets.incoming;

import java.io.Serializable;

public class RemoveConnectionPacket implements Serializable{
	private static final long serialVersionUID = 1L;
	public int playerSession;
}
