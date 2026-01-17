package com.CBTapp.services;

import com.CBTapp.dao.MembreDAO;
import com.CBTapp.models.Membre;
import java.sql.SQLException;
import java.util.List;

public class MembreService {
    private MembreDAO membreDAO;
    
    public MembreService() {
        this.membreDAO = new MembreDAO();
    }
    
    public List<Membre> getAllMembres() throws SQLException {
        return membreDAO.getAll();
    }
    
    public Membre getMembreById(int id) throws SQLException {
        return membreDAO.getById(id);
    }
    
    public List<Membre> getMembresByRole(String role) throws SQLException {
        return membreDAO.getByRole(role);
    }
    
    public int updateMembreRole(int idMembre, String newRole) throws SQLException {
        return membreDAO.updateRole(idMembre, newRole);
    }
}

