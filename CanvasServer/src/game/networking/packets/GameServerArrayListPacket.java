package game.networking.packets;

import java.io.Serializable;
import java.util.ArrayList;

import game.networking.objects.Player;

public class GameServerArrayListPacket implements Serializable {
	private static final long serialVersionUID = 16L;
	private ArrayList<Player> players;
	
	public ArrayList<Player> getPlayers() {
		return this.players;
	}
	public void setPlayers(ArrayList<Player> players) {
		this.players = players;
	}
}
