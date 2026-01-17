package com.CBTapp.db;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Manages database connections using JDBC
 */
public class DatabaseConnection {
    private static final Logger LOGGER = Logger.getLogger(DatabaseConnection.class.getName());
    private static Connection connection = null;
    private static String url;
    private static String username;
    private static String password;
    private static String driver;
    
    static {
        loadProperties();
    }
    
    /**
     * Load database configuration from properties file
     */
    private static void loadProperties() {
        try (InputStream input = DatabaseConnection.class.getClassLoader()
                .getResourceAsStream("config.properties")) {
            if (input == null) {
                LOGGER.log(Level.WARNING, "config.properties not found, using default values");
                url = "jdbc:mysql://localhost:3306/cbt_db";
                username = "root";
                password = "";
                driver = "com.mysql.cj.jdbc.Driver";
                return;
            }
            
            Properties prop = new Properties();
            prop.load(input);
            
            url = prop.getProperty("db.url", "jdbc:mysql://localhost:3306/cbt_db");
            username = prop.getProperty("db.username", "root");
            password = prop.getProperty("db.password", "");
            driver = prop.getProperty("db.driver", "com.mysql.cj.jdbc.Driver");
            
            // Load JDBC driver
            Class.forName(driver);
            
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error loading database properties", e);
        }
    }
    
    /**
     * Get a database connection
     * @return Connection object
     * @throws SQLException if connection fails
     */
    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(url, username, password);
            LOGGER.log(Level.INFO, "Database connection established");
        }
        return connection;
    }
    
    /**
     * Close the database connection
     */
    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                LOGGER.log(Level.INFO, "Database connection closed");
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error closing database connection", e);
        }
    }
    
    /**
     * Test database connection
     * @return true if connection is successful
     */
    public static boolean testConnection() {
        try {
            Connection conn = getConnection();
            return conn != null && !conn.isClosed();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Connection test failed", e);
            return false;
        }
    }
}

