package org.itechart.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public abstract class AbstractConnection {

    private static Connection connection = null;

    public static Connection getConnection() throws SQLException {
        Properties props = new Properties();
        try {
            props.load(new FileInputStream("application.properties"));

            String url = props.getProperty("url");
            String username = props.getProperty("username");
            String password = props.getProperty("password");

            Class.forName("org.postgresql.Driver");
            if (connection == null) {
                connection = DriverManager.getConnection(url, username, password);
            }
        } catch (ClassNotFoundException | SQLException e) {
            throw new SQLException(e);
        } catch (IOException e) {
            e.getMessage();
        }
        return connection;
    }

    public static void close() throws SQLException {
        if (connection != null) {
            connection.close();
        }
        connection = null;
    }
}