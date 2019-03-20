package game.networking.packets;

import java.io.Serializable;

public class SendWordPacket implements Serializable{
	private static final long serialVersionUID = 18L;
	private String word;
	
	public String getWord() {
		return word;
	}
	
	public void setWord(String word) {
		this.word = word;
	}
}
