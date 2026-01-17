package com.CBTapp.dao;

import com.CBTapp.db.DatabaseManager;
import com.CBTapp.models.Penalite;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PenaliteDAO extends BaseDao {
    
    public List<Penalite> getAll() throws SQLException {
        List<Penalite> penalites = new ArrayList<>();
        String query = "SELECT * FROM penalites ORDER BY date DESC";
        ResultSet rs = DatabaseManager.executeQuery(query);
        
        while (rs.next()) {
            penalites.add(mapResultSetToPenalite(rs));
        }
        return penalites;
    }
    
    public Penalite getById(int id) throws SQLException {
        String query = "SELECT * FROM penalites WHERE id_penalite = ?";
        ResultSet rs = DatabaseManager.executeQuery(query, id);
        
        if (rs.next()) {
            return mapResultSetToPenalite(rs);
        }
        return null;
    }
    
    public int add(int idMembre, double montant, String raison) throws SQLException {
        String query = "INSERT INTO penalites (id_membre, montant, raisont, date) VALUES (?, ?, ?, CURDATE())";
        return DatabaseManager.executeUpdate(query, idMembre, montant, raison);
    }
    
    public int delete(int id) throws SQLException {
        String query = "DELETE FROM penalites WHERE id_penalite = ?";
        return DatabaseManager.executeUpdate(query, id);
    }
    
    private Penalite mapResultSetToPenalite(ResultSet rs) throws SQLException {
        Penalite penalite = new Penalite();
        penalite.setIdPenalite(rs.getInt("id_penalite"));
        penalite.setIdMembre(rs.getInt("id_membre"));
        penalite.setMontant(rs.getDouble("montant"));
        penalite.setRaison(rs.getString("raisont"));
        if (rs.getDate("date") != null) {
            penalite.setDate(rs.getDate("date").toLocalDate());
        }
        return penalite;
    }
}

