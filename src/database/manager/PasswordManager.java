package database.manager;

import database.DatabaseHandler;
import database.dao.PlayerDAO;
import org.apache.commons.codec.digest.DigestUtils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PasswordManager {
    private static String HashPassword(String plainTextPassword){
        String hashedPassword = DigestUtils.sha512Hex(plainTextPassword);
        return hashedPassword;
    }

    public static Boolean isValidPassword(String username, String password){
        if(PlayerDAO.isPlayerExisting(username)){
            try (Connection con = DatabaseHandler.getInstance()
                    .getConnection()) {
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM player WHERE username = '" + username + "'");
                if (HashPassword(password) == rs.getString("password")){
                    return true;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}
