package networking;

import database.DatabaseHandler;

import javax.xml.crypto.Data;
import java.sql.Connection;
import java.sql.SQLException;

public class Main {
	public static void main(String[] args) {

		try (Connection activeConnection = DatabaseHandler.getInstance()
				.getConnection()) {
			if (!activeConnection.isClosed()){
				Server server = new Server(1337);
				server.start();
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
