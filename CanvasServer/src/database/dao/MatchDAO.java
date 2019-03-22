package database.dao;

import database.DatabaseHandler;
import database.domain.MatchDomain;
import database.domain.PlayerDomain;
import game.networking.objects.Match;
import game.networking.objects.MatchManager;

import java.sql.*;
import java.util.HashMap;

public class MatchDAO implements IDAO<MatchDomain> {

    HashMap<Integer,Integer> playersInMatch = new HashMap<>();

    public MatchDAO(){
        try (Connection activeConnection = DatabaseHandler.getInstance()
                .getConnection()) {
            // Get all active matches
            try (PreparedStatement stmt = activeConnection.prepareStatement("SELECT * FROM match WHERE isActive = 'true' ORDER BY port DESC");
                 ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    playersInMatch.put(rs.getInt("mid"), rs.getInt("playerCount"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void UpdatePlayerCount(int matchID, int newPlayerCount){
        try (Connection activeConnection = DatabaseHandler.getInstance()
                .getConnection()) {
            PreparedStatement preparedStatement = null;
            preparedStatement = activeConnection.prepareStatement("UPDATE match SET playerCount = ? WHERE mid = '" +matchID+"'");
            preparedStatement.setInt(1, newPlayerCount);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
    }

    public static void UpdateMatchPort(int matchID, int newPort){
        try (Connection activeConnection = DatabaseHandler.getInstance()
                .getConnection()) {
            PreparedStatement preparedStatement = null;
            preparedStatement = activeConnection.prepareStatement("UPDATE match SET port = ? WHERE mid = '" +matchID+"'");
            preparedStatement.setInt(1, newPort);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
    }

    public Match GetMatch(int matchID) {
        try (Connection activeConnection = DatabaseHandler.getInstance()
                .getConnection()) {
            Statement statement = null;
            statement = activeConnection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM match WHERE mid='" + matchID + "'");
            while (resultSet.next()) {
                Match matchResult = new Match(resultSet.getInt("mid"), resultSet.getInt("playerCount"), resultSet.getInt("port"), resultSet.getBoolean("isActive"));
                return matchResult;
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        // No Match Found
        return null;
    }

    /**
     * Creates new Match and Returns the ID of new Match
     *
     * @return ID of new Match
     */
    public static int CreateNewMatch() {
        // PlayerDomain doesn't exist, so we can insert them into database
        try (Connection activeConnection = DatabaseHandler.getInstance()
                .getConnection()) {

            MatchDomain newMatch = new MatchDomain(getCurrentTimeStamp());

            PreparedStatement preparedStatement = null;
            preparedStatement = activeConnection.prepareStatement("INSERT INTO match (timestamp, gamemode, isActive)" + " values (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setTimestamp(1, newMatch.getTimestamp());
            preparedStatement.setInt(2, newMatch.getGamemode());
            preparedStatement.setBoolean(3, true);
            preparedStatement.execute();

            ResultSet rs = preparedStatement.getGeneratedKeys();

            if (rs.next()) {
                 return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        // Failed to Create Match?
        return -1;
    }
    private static java.sql.Timestamp getCurrentTimeStamp() {
        java.util.Date today = new java.util.Date();
        return new java.sql.Timestamp(today.getTime());
    }

    public HashMap<Integer, Integer> getPlayersInMatch() {
        return playersInMatch;
    }

    @Override
    public MatchDomain get() {
        return null;
    }

    @Override
    public void insert(MatchDomain domain) {

    }

    @Override
    public void update(MatchDomain domain) {

    }

    @Override
    public void delete(MatchDomain domain) {

    }
}
