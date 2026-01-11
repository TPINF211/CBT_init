package com.CBTapp.dao;

import com.CBTapp.db.DatabaseManager;
import com.CBTapp.models.Proposition;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PropositionDAO {
    
    public List<Proposition> getAll() throws SQLException {
        List<Proposition> propositions = new ArrayList<>();
        ResultSet rs = DatabaseManager.executeProcedure("GetAllPropositions");
        while (rs.next()) {
            Proposition p = new Proposition();
            p.setIdProposition(rs.getInt("id_proposition"));
            p.setIdMembre(rs.getInt("id_membre"));
            p.setDescription(rs.getString("description"));
            p.setDate(rs.getDate("date"));
            p.setStatus(rs.getString("status"));
            propositions.add(p);
        }
        return propositions;
    }
    
    public Proposition getById(int id) throws SQLException {
        ResultSet rs = DatabaseManager.executeProcedure("GetpropositionsByID", id);
        if (rs.next()) {
            Proposition p = new Proposition();
            p.setIdProposition(rs.getInt("id_proposition"));
            p.setIdMembre(rs.getInt("id_membre"));
            p.setDescription(rs.getString("description"));
            p.setDate(rs.getDate("date"));
            p.setStatus(rs.getString("status"));
            return p;
        }
        return null;
    }
    
    public int add(Proposition proposition) throws SQLException {
        String query = "INSERT INTO propositions (id_membre, description, date, status) VALUES (?, ?, NOW(), ?)";
        DatabaseManager.executeUpdate(query,
            proposition.getIdMembre(),
            proposition.getDescription(),
            proposition.getStatus() != null ? proposition.getStatus() : "En_Examen"
        );
        ResultSet rs = DatabaseManager.executeQuery("SELECT LAST_INSERT_ID() AS new_pro_id");
        if (rs.next()) {
            return rs.getInt("new_pro_id");
        }
        return -1;
    }
    
    public int delete(int id) throws SQLException {
        ResultSet rs = DatabaseManager.executeProcedure("DeleteProp", id);
        if (rs.next()) {
            return rs.getInt("affected_rows");
        }
        return 0;
    }
    
    public void updateStatus(int id, String newStatus) throws SQLException {
        DatabaseManager.executeProcedure("UpdateProSta", id, newStatus);
    }
}

