package com.CBTapp.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Provides utility methods for database operations
 */
public class DatabaseManager {
    private static final Logger LOGGER = Logger.getLogger(DatabaseManager.class.getName());
    
    /**
     * Execute a SELECT query and return ResultSet
     * @param query SQL query string
     * @return ResultSet from query
     * @throws SQLException if query execution fails
     */
    public static ResultSet executeQuery(String query) throws SQLException {
        Connection conn = DatabaseConnection.getConnection();
        Statement stmt = conn.createStatement();
        return stmt.executeQuery(query);
    }
    
    /**
     * Execute a SELECT query with parameters
     * @param query SQL query string with placeholders
     * @param params Parameters to bind to query
     * @return ResultSet from query
     * @throws SQLException if query execution fails
     */
    public static ResultSet executeQuery(String query, Object... params) throws SQLException {
        Connection conn = DatabaseConnection.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(query);
        
        for (int i = 0; i < params.length; i++) {
            pstmt.setObject(i + 1, params[i]);
        }
        
        return pstmt.executeQuery();
    }
    
    /**
     * Execute an UPDATE, INSERT, or DELETE statement
     * @param query SQL statement string
     * @return Number of affected rows
     * @throws SQLException if execution fails
     */
    public static int executeUpdate(String query) throws SQLException {
        Connection conn = DatabaseConnection.getConnection();
        Statement stmt = conn.createStatement();
        return stmt.executeUpdate(query);
    }
    
    /**
     * Execute an UPDATE, INSERT, or DELETE statement with parameters
     * @param query SQL statement string with placeholders
     * @param params Parameters to bind to statement
     * @return Number of affected rows
     * @throws SQLException if execution fails
     */
    public static int executeUpdate(String query, Object... params) throws SQLException {
        Connection conn = DatabaseConnection.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(query);
        
        for (int i = 0; i < params.length; i++) {
            pstmt.setObject(i + 1, params[i]);
        }
        
        return pstmt.executeUpdate();
    }
    
    /**
     * Execute a stored procedure
     * @param procedureName Name of the stored procedure
     * @param params Parameters for the procedure
     * @return ResultSet if procedure returns results
     * @throws SQLException if execution fails
     */
    public static ResultSet executeProcedure(String procedureName, Object... params) throws SQLException {
        Connection conn = DatabaseConnection.getConnection();
        StringBuilder call = new StringBuilder("{call " + procedureName + "(");
        
        if (params.length > 0) {
            call.append("?");
            for (int i = 1; i < params.length; i++) {
                call.append(", ?");
            }
        }
        call.append(")}");
        
        java.sql.CallableStatement cstmt = conn.prepareCall(call.toString());
        
        for (int i = 0; i < params.length; i++) {
            cstmt.setObject(i + 1, params[i]);
        }
        
        return cstmt.executeQuery();
    }
    
    /**
     * Execute a batch of statements
     * @param queries List of SQL statements
     * @return Array of update counts
     * @throws SQLException if batch execution fails
     */
    public static int[] executeBatch(List<String> queries) throws SQLException {
        Connection conn = DatabaseConnection.getConnection();
        Statement stmt = conn.createStatement();
        
        for (String query : queries) {
            stmt.addBatch(query);
        }
        
        return stmt.executeBatch();
    }
    
    /**
     * Check if a table exists
     * @param tableName Name of the table to check
     * @return true if table exists
     */
    public static boolean tableExists(String tableName) {
        try {
            Connection conn = DatabaseConnection.getConnection();
            ResultSet rs = conn.getMetaData().getTables(null, null, tableName, null);
            return rs.next();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error checking table existence", e);
            return false;
        }
    }
}

