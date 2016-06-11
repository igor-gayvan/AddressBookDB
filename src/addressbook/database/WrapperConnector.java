/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package addressbook.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.MissingResourceException;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Haivan
 */
public class WrapperConnector {

    private Connection connection;

    private static String driver;
    private static String url;
    private static String user;
    private static String pass;
    private static String useSSL;

    static {
        ResourceBundle resource = ResourceBundle.getBundle("config.database");
        
        driver = resource.getString("db.driver");
        url = resource.getString("db.url");
        user = resource.getString("db.user");
        pass = resource.getString("db.password");
        useSSL = resource.getString("db.useSSL");

        try {
            Class.forName(driver);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(WrapperConnector.class.getName()).log(Level.SEVERE, null, ex);

        } catch (MissingResourceException e) {
            System.err.println("properties file is missing " + e);
        }
    }

    public WrapperConnector() throws ClassNotFoundException {
        try {
            System.out.println("Connection to database...");

            ResourceBundle resource = ResourceBundle.getBundle("config.database");

            String connectionString = String.format("%s?useSSL=%s", url, useSSL);

            connection = DriverManager.getConnection(connectionString, user, pass);

            System.out.println("Connection established!");
        } catch (MissingResourceException e) {
            System.err.println("properties file is missing " + e);
        } catch (SQLException e) {
            System.err.println("not obtained connection " + e);
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public Statement getStatement() throws SQLException {
        if (connection != null) {
            Statement statement = connection.createStatement();
            if (statement != null) {
                return statement;
            }
        }
        throw new SQLException("connection or statement is null");
    }

    public void closeStatement(Statement statement) {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                System.err.println("statement is null " + e);
            }
        }
    }

    public void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                System.err.println(" wrong connection" + e);
            }
        }
    }
}
