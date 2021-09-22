package org.itechart.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class AbstractConnection {
    private static String url = "jdbc:postgresql://localhost:5432/car-service";
    private static String username = "username";
    private static String password = "password";
    private static Connection connection = null;

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("org.postgresql.Driver");
            if (connection == null) {
                connection = DriverManager.getConnection(url, username, password);
            }
        } catch (ClassNotFoundException | SQLException e) {
            throw new SQLException(e);
        }
        return connection;
    }

    public static void close() throws SQLException {
        if(connection != null){
            connection.close();
        }
        connection = null;
    }
}