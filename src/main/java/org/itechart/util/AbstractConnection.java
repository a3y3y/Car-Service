package org.itechart.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public abstract class AbstractConnection {

    private static Connection connection = null;

    public static Connection getConnection() throws SQLException {
        Properties props = new Properties();
        String resourceName = "application.properties";
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        try(InputStream resourceStream = loader.getResourceAsStream(resourceName)){
            props.load(resourceStream);

            String url = props.getProperty("url");
            String username = props.getProperty("username");
            String password = props.getProperty("password");

            Class.forName(props.getProperty("driverClassName"));
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