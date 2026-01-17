package com.CBTapp.dao;

import com.CBTapp.db.DatabaseManager;
import com.CBTapp.models.Projet;
import com.CBTapp.models.Status;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProjetDAO {
    
    public List<Projet> getAll() throws SQLException {
        List<Projet> projets = new ArrayList<>();
        String query = "SELECT * FROM projets ORDER BY id_projet DESC";
        ResultSet rs = DatabaseManager.executeQuery(query);
        while (rs.next()) {
            Projet p = new Projet();
            p.setId_projet(rs.getInt("id_projet"));
            p.setId_groupe(rs.getInt("id_groupe"));
            p.setName(rs.getString("name"));
            p.setDescription(rs.getString("description"));
            p.setMontant(rs.getDouble("montant"));
            p.setStatus(Status.valueOf(rs.getString("status")));
            projets.add(p);
        }
        return projets;
    }
    
    public Projet getById(int id) throws SQLException {
        String query = "SELECT * FROM projets WHERE id_projet = ?";
        ResultSet rs = DatabaseManager.executeQuery(query, id);
        if (rs.next()) {
            Projet p = new Projet();
            p.setId_projet(rs.getInt("id_projet"));
            p.setId_groupe(rs.getInt("id_groupe"));
            p.setName(rs.getString("name"));
            p.setDescription(rs.getString("description"));
            p.setMontant(rs.getDouble("montant"));
            p.setStatus(Status.valueOf(rs.getString("status").toUpperCase()));
            return p;
        }
        return null;
    }
    
    public int add(Projet projet) throws SQLException {
        String query = "INSERT INTO projets (id_groupe, nom, description, montant, status) VALUES (?, ?, ?, ?, ?)";
        return DatabaseManager.executeUpdate(query,
            projet.getIdGroupe(),
            projet.getName(),
            projet.getDescription(),
            projet.getMontant(),
            projet.getStatus()
        );
    }
    
    public int updateStatus(int id, String status) throws SQLException {
        String query = "UPDATE projets SET status = ? WHERE id_projet = ?";
        return DatabaseManager.executeUpdate(query, status, id);
    }
}

