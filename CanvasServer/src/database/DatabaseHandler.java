package database;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;

import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 *  The DatabaseHandler Class creates a connection to the database so that CRUD operations can be performed.
 *
 */
public class DatabaseHandler {
    /* Current Instance of the DatabaseHandler */
    private static DatabaseHandler dataSource;
    /* This is the pooled data source for which we will use to create our connection */
    private ComboPooledDataSource comboPooledDataSource;

    /**
     * The purpose of the constructor is to create a connection to the database per instance.
     */
    private DatabaseHandler() {
        try {
            /* Connection String Configuration*/
            comboPooledDataSource = new ComboPooledDataSource();
            comboPooledDataSource
                    // What driver we're using, in this case our database system is PostgreSQL
                    .setDriverClass("org.postgresql.Driver");
            comboPooledDataSource
                    // URI, Which includes the Host (IP+PORT) of Database server + Database Name
                    .setJdbcUrl("jdbc:postgresql://mod-msc-sw1.cs.bham.ac.uk:5432/group1");
            // Username of Database
            comboPooledDataSource.setUser("group1");
            // Password of Database
            comboPooledDataSource.setPassword("accm1ziurm");
        } catch (PropertyVetoException ex1) {
            ex1.printStackTrace();
        }
    }

    /**
     * Gets the current instance of DatabaseHandler, however, if the instance is null, it will create one.
     * @return instance that has a active database connection
     */
    public static DatabaseHandler getInstance() {
        // If the instance is null
        if (dataSource == null)
            // Then invoke the constructor and start the connection to the database
            dataSource = new DatabaseHandler();
        // Return current instance
        return dataSource;
    }

    /**
     * Gets the current active connection to the database
     * @return Active connection to database
     */
    public Connection getConnection() {
        // Create local variable instance of connection
        Connection con = null;
        try {
            // Attempt to get connection from pooled Datasource
            con = comboPooledDataSource.getConnection();
        } catch (SQLException e) {
            // Something went wrong (No active connection most likely)
            e.printStackTrace();
        }
        // Current active connection
        return con;
    }
}
