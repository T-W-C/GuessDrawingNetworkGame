package networking;

import database.DatabaseHandler;

import javax.xml.crypto.Data;
import java.sql.Connection;
import java.sql.SQLException;

public class StartServer {
	public static void main(String[] args) {
		Server server = new Server(1337);
		server.start();
		/*
		try (Connection activeConnection = DatabaseHandler.getInstance()
				.getConnection()) {
			if (!activeConnection.isClosed()){
				Server server = new Server(1337);
				server.start();
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		*/
	}
}
