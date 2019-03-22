package database.dao;

import database.DatabaseHandler;
import database.domain.PortDomain;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class PortDomainDAO implements IDAO<PortDomain>{

    HashMap<Integer,Boolean> ports = new HashMap<>();

    public PortDomainDAO() {
        try (Connection activeConnection = DatabaseHandler.getInstance()
                .getConnection()) {
            try (PreparedStatement stmt = activeConnection.prepareStatement("SELECT * FROM port ORDER BY port DESC");
                 ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    ports.put(rs.getInt("port"), rs.getBoolean("isActive"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void PortInUse(int port){
        try (Connection activeConnection = DatabaseHandler.getInstance()
                .getConnection()) {
            PreparedStatement preparedStatement = null;
            preparedStatement = activeConnection.prepareStatement("UPDATE port SET isActive = ? WHERE port = '" +port+"'");
            preparedStatement.setBoolean(1, true);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
    }

    public void PortNotInUse(int port){
        try (Connection activeConnection = DatabaseHandler.getInstance()
                .getConnection()) {
            PreparedStatement preparedStatement = null;
            preparedStatement = activeConnection.prepareStatement("UPDATE port SET isActive = ? WHERE port = '" +port+"'");
            preparedStatement.setBoolean(1, false);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
    }

    public HashMap<Integer, Boolean> getPorts(){
        return this.ports;
    }

    @Override
    public PortDomain get() {
        return null;
    }

    @Override
    public void insert(PortDomain domain) {

    }

    @Override
    public void update(PortDomain domain) {

    }

    @Override
    public void delete(PortDomain domain) {

    }
}
