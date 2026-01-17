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
            p.setNom(rs.getString("nom"));
            p.setNomMembre(rs.getString("nom_membre"));
            p.setDescription(rs.getString("description"));
            p.setDate(rs.getDate("date"));
            p.setStatus(rs.getString("status"));
            propositions.add(p);
        }
        return propositions;
    }
    
    public Proposition getById(int id) throws SQLException {
        ResultSet rs = DatabaseManager.executeProcedure("GetPropositionByID", id);
        if (rs.next()) {
            Proposition p = new Proposition();
            p.setIdProposition(rs.getInt("id_proposition"));
            p.setIdMembre(rs.getInt("id_membre"));
            p.setNom(rs.getString("nom"));
            p.setNomMembre(rs.getString("nom_membre"));
            p.setDescription(rs.getString("description"));
            p.setDate(rs.getDate("date"));
            p.setStatus(rs.getString("status"));
            return p;
        }
        return null;
    }
    
    public int add(String nom, int idMembre, String description) throws SQLException {
        ResultSet rs = DatabaseManager.executeProcedure("AjouterProposition", nom, idMembre, description);
        if (rs.next()) {
            String status = rs.getString("status");
            if ("SUCCESS".equals(status)) {
            return rs.getInt("new_pro_id");
            }
        }
        return -1;
    }
    
    public int delete(int id) throws SQLException {
        ResultSet rs = DatabaseManager.executeProcedure("DeleteProposition", id);
        if (rs.next()) {
            return rs.getInt("affected_rows");
        }
        return 0;
    }
    
    public int updateStatus(int id, String newStatus) throws SQLException {
        ResultSet rs = DatabaseManager.executeProcedure("UpdatePropositionStatus", id, newStatus);
        if (rs.next()) {
            return rs.getInt("rows_updated");
        }
        return 0;
    }
}

