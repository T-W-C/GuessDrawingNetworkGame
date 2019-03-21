package game.networking.packets;

import game.networking.objects.Player;

import java.io.Serializable;
import java.util.ArrayList;

public class GameServerArrayListPacket implements Serializable {
	private static final long serialVersionUID = 16L;

	private ArrayList<Player> players;
	private boolean testBool = false; // purely for testing purposes
	private ArrayList<Integer> testInts; // purely for testing purposes
	
	public ArrayList<Player> getPlayers() {
		return this.players;
	}
	public void setPlayers(ArrayList<Player> players) {
		this.players = players;
	}

	public boolean getTestBool() { // purely for testing purposes
		return testBool;
	} // purely for testing purposes

	public void setTestBool(boolean testBool) { // purely for testing purposes
		this.testBool = testBool;
	} // purely for testing purposes

	public ArrayList<Integer> getTestInts() { // purely for testing purposes
		return testInts;
	}

	public void setTestInts(ArrayList<Integer> testInts) { // purely for testing purposes
		this.testInts = testInts;
	}
}
