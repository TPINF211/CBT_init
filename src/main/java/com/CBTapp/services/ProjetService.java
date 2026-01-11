package com.CBTapp.services;

import com.CBTapp.dao.ProjetDAO;
import com.CBTapp.models.Projet;
import java.sql.SQLException;
import java.util.List;

public class ProjetService {
    private ProjetDAO projetDAO;
    
    public ProjetService() {
        this.projetDAO = new ProjetDAO();
    }
    
    public List<Projet> getAllProjets() throws SQLException {
        return projetDAO.getAll();
    }
    
    public Projet getProjetById(int id) throws SQLException {
        return projetDAO.getById(id);
    }
    
    public int createProjet(Projet projet) throws SQLException {
        return projetDAO.add(projet);
    }
    
    public int updateProjetStatus(int id, String status) throws SQLException {
        return projetDAO.updateStatus(id, status);
    }
}

