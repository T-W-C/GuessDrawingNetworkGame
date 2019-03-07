package database.dao;

import database.DatabaseHandler;
import database.domain.Player;
import org.apache.commons.codec.digest.DigestUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PlayerDAO implements IDAO<Player> {

    private Player currentPlayer;

    public PlayerDAO(int playerID) {
        try (Connection activeConnection = DatabaseHandler.getInstance()
                .getConnection()) {
            Statement statement = null;
            if (isPlayerExisting(playerID)) {
                statement = activeConnection.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT * FROM player WHERE pid='" + playerID + "'");
                while (resultSet.next()) {
                    currentPlayer = new Player(resultSet.getInt("pid"), resultSet.getString("username"), resultSet.getInt("level"), resultSet.getInt("totalScore"));
                }
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
    }
    public PlayerDAO(String username) {
        try (Connection activeConnection = DatabaseHandler.getInstance()
                .getConnection()) {
            Statement statement = null;
            if (isPlayerExisting(username)) {
                statement = activeConnection.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT * FROM player WHERE username='" + username + "'");
                while (resultSet.next()) {
                    currentPlayer = new Player(resultSet.getInt("pid"), resultSet.getString("username"), resultSet.getInt("level"), resultSet.getInt("totalScore"));
                }
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
    }

    public PlayerDAO CreatePlayer(){
        return this;
    }

    public List<Player> getAll() {
        ArrayList<Player> playerList = new ArrayList<>();
        try (Connection activeConnection = DatabaseHandler.getInstance()
                .getConnection()) {
            Statement statement = null;
            statement = activeConnection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM player");
            while (resultSet.next()) {
                Player tempPlayer = new Player(resultSet.getInt("pid"), resultSet.getString("username"), resultSet.getInt("level"), resultSet.getInt("totalScore"));

                playerList.add(tempPlayer);
            }

        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return playerList;
    }

    @Override
    public Player get() {
        return currentPlayer;
    }

    @Override
    public void saveOrUpdate() {
        try (Connection activeConnection = DatabaseHandler.getInstance()
                .getConnection()) {
            PreparedStatement preparedStatement = null;
            preparedStatement = activeConnection.prepareStatement("UPDATE player SET username = ?, level = ?, totalScore = ? WHERE pid = '" + currentPlayer.getPlayerID() + "'");
            preparedStatement.setString(1, currentPlayer.getUsername());
            preparedStatement.setInt(2, currentPlayer.getLevel());
            preparedStatement.setInt(3, currentPlayer.getTotalScore());
            preparedStatement.setInt(4, currentPlayer.getPlayerID());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
    }

    @Override
    public void delete() {
        try (Connection activeConnection = DatabaseHandler.getInstance()
                .getConnection()) {
            PreparedStatement preparedStatement = null;
            preparedStatement = activeConnection.prepareStatement("DELETE FROM player WHERE pid = '" + currentPlayer.getPlayerID() + "'");
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean isPlayerExisting(int playerID) {
        Boolean result = false;
        try (Connection con = DatabaseHandler.getInstance()
                .getConnection()) {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT pid FROM player HWERE pid = '" + playerID + "'");
            result = rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static boolean isPlayerExisting(String username) {
        Boolean result = false;
        try (Connection con = DatabaseHandler.getInstance()
                .getConnection()) {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT pid FROM player WHERE username = '" + username + "'");
            result = rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}
