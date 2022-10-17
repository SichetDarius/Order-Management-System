package com.example.pt2022_30421_sichet_darius_assignment_3.Connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * This class creates the connection to the database
 */

public class ConnectionFactory {
    /**
     * The address to the MySQL database
     */
    private static final String DBURL = "jdbc:mysql://localhost:3306/schooldb";

    /**
     * The user for the MySQL database
     */
    private static final String USER = "root";

    /**
     * The password for the MySQL database
     */
    private static final String PASS = "Sde2001mus25!";

    /**
     * The connection to the MySQL database
     */
    private Connection connection;

    /**
     * An static instance of this class, ensuring that only a single connection will be used for the database operations
     */
    private static final ConnectionFactory singleInstance = new ConnectionFactory();

    /**
     * Creates or returns a connection
     * @return the new or existing connection
     */
    private Connection createConnection() throws SQLException {
        if(connection == null || connection.isClosed()) {
            try {
                connection = DriverManager.getConnection(DBURL, USER, PASS);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return connection;
    }

    /**
     * Retrieves a new or existing connection
     * @return the new or existing connection
     */
    public static Connection getConnection() throws SQLException {
        return singleInstance.createConnection();
    }

    /**
     * Closes the existing connection, if there is one
     */
    public static void close() throws SQLException {
        if (getConnection() != null) {
            try {
                getConnection().close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
