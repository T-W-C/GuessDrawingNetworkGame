package database.dao;

import database.DatabaseHandler;
import database.domain.PlayerDomain;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PlayerDAO implements IDAO<PlayerDomain> {

    private PlayerDomain currentPlayerDomain;

    public PlayerDAO(int playerID) {
        try (Connection activeConnection = DatabaseHandler.getInstance()
                .getConnection()) {
            Statement statement = null;
            if (isPlayerExisting(playerID)) {
                statement = activeConnection.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT * FROM player WHERE pid='" + playerID + "'");
                while (resultSet.next()) {
                    currentPlayerDomain = new PlayerDomain(resultSet.getInt("pid"), resultSet.getString("username"), resultSet.getString("email"),resultSet.getInt("level"), resultSet.getInt("totalScore"));
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
                    currentPlayerDomain = new PlayerDomain(resultSet.getInt("pid"), resultSet.getString("username"), resultSet.getString("email"),resultSet.getInt("level"), resultSet.getInt("totalScore"));
                }
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
    }

    public static void createPlayer(String username, String password, String email){
        if(!isPlayerExisting(username)){
            // PlayerDomain doesn't exist, so we can insert them into database
            try (Connection activeConnection = DatabaseHandler.getInstance()
                    .getConnection()) {
                PreparedStatement preparedStatement = null;
                preparedStatement = activeConnection.prepareStatement("INSERT INTO player (username, password, email)" + " values (?, ?, ?)");
                preparedStatement.setString(1, username.toLowerCase());
                preparedStatement.setString(2, password);
                preparedStatement.setString(3, email.toLowerCase());
                preparedStatement.execute();
            } catch (SQLException e) {
                System.out.println(e.toString());
            }
        }
        else {
            System.out.println("Username already exists!");
        }
    }

    public List<PlayerDomain> getAll() {
        ArrayList<PlayerDomain> playerDomainList = new ArrayList<>();
        try (Connection activeConnection = DatabaseHandler.getInstance()
                .getConnection()) {
            Statement statement = null;
            statement = activeConnection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM player");
            while (resultSet.next()) {
                PlayerDomain tempPlayerDomain = new PlayerDomain(resultSet.getInt("pid"), resultSet.getString("username"), resultSet.getString("email"), resultSet.getInt("level"), resultSet.getInt("totalScore"));

                playerDomainList.add(tempPlayerDomain);
            }

        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return playerDomainList;
    }

    @Override
    public PlayerDomain get() {
        return currentPlayerDomain;
    }


    @Override
    public void saveOrUpdate() {
        try (Connection activeConnection = DatabaseHandler.getInstance()
                .getConnection()) {
            PreparedStatement preparedStatement = null;
            preparedStatement = activeConnection.prepareStatement("UPDATE player SET username = ?, level = ?, totalScore = ? WHERE pid = '" + currentPlayerDomain.getPlayerID() + "'");
            preparedStatement.setString(1, currentPlayerDomain.getUsername());
            preparedStatement.setInt(2, currentPlayerDomain.getLevel());
            preparedStatement.setInt(3, currentPlayerDomain.getTotalScore());
            preparedStatement.setInt(4, currentPlayerDomain.getPlayerID());
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
            preparedStatement = activeConnection.prepareStatement("DELETE FROM player WHERE pid = '" + currentPlayerDomain.getPlayerID() + "'");
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
            ResultSet rs = stmt.executeQuery("SELECT pid FROM player WHERE username = '" + username.toLowerCase() + "'");
            result = rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static boolean isEmailExisting(String email) {
        Boolean result = false;
        try (Connection con = DatabaseHandler.getInstance()
                .getConnection()) {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT pid FROM player WHERE email = '" + email.toLowerCase() + "'");
            result = rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}
