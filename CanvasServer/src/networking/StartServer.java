package networking;

import database.DatabaseHandler;
import game.networking.GameServer;

import javax.xml.crypto.Data;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;

public class StartServer {
	public static void main(String[] args) {
		try (Connection activeConnection = DatabaseHandler.getInstance()
				.getConnection()) {
			if (!activeConnection.isClosed()) {
				Server mainServer = new Server(1337);
				mainServer.start();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
