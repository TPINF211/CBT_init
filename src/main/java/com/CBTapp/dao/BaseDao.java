package com.CBTapp.dao;


import com.CBTapp.db.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class BaseDao {
    protected Connection getConnexion() throws SQLException {
        return DatabaseConnection.getConnection();
    }
    protected void closeRessourrces(PreparedStatement statement, ResultSet resultSet){
        try {
            if (resultSet!=null) resultSet.close();
            if(statement!=null) statement.close();
        } catch (SQLException e) {
            //e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    protected void closeRessourrces(PreparedStatement statement){
        closeRessourrces(statement,null);
    }
}
