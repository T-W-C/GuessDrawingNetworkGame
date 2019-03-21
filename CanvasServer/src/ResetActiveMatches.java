import database.DatabaseHandler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ResetActiveMatches {
    public static void main(String[] args){
        try (Connection activeConnection = DatabaseHandler.getInstance()
                .getConnection()) {
            PreparedStatement preparedStatement = null;
            preparedStatement = activeConnection.prepareStatement("UPDATE match SET isActive = ? WHERE isActive = 'true'");
            preparedStatement.setBoolean(1, false);
            preparedStatement.executeUpdate();
            System.out.println("All matches have been reset to in-active :)");
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        try (Connection activeConnection = DatabaseHandler.getInstance()
                .getConnection()) {
            PreparedStatement preparedStatement = null;
            preparedStatement = activeConnection.prepareStatement("UPDATE port SET isActive = ? WHERE isActive = 'true'");
            preparedStatement.setBoolean(1, false);
            preparedStatement.executeUpdate();
            System.out.println("All ports have been reset to in-active :)");
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
    }
}
