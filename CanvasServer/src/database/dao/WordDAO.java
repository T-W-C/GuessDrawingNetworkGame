package database.dao;

import database.DatabaseHandler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

public class WordDAO {
    public static String GetRandomWord(){
        Random r = new Random();
        // Get a random number between 1-100
        int randomNumber = r.nextInt(100-1) + 1;
        String word = "";
        try (Connection activeConnection = DatabaseHandler.getInstance()
                .getConnection()) {
            // Get all active matches
            try (PreparedStatement stmt = activeConnection.prepareStatement("SELECT * FROM word WHERE wid = '" +randomNumber+"'");
                 ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    word = rs.getNString("word");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return word;
    }
}
