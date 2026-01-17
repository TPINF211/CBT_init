package com.CBTapp.services;

import com.CBTapp.dao.PropositionDAO;
import com.CBTapp.models.Proposition;
import java.sql.SQLException;
import java.util.List;

public class PropositionService {
    private PropositionDAO propositionDAO;
    
    public PropositionService() {
        this.propositionDAO = new PropositionDAO();
    }
    
    public List<Proposition> getAllPropositions() throws SQLException {
        return propositionDAO.getAll();
    }
    
    public Proposition getPropositionById(int id) throws SQLException {
        return propositionDAO.getById(id);
    }
    
    public int createProposition(String nom, int idMembre, String description) throws SQLException {
        return propositionDAO.add(nom, idMembre, description);
    }
    
    public int deleteProposition(int id) throws SQLException {
        return propositionDAO.delete(id);
    }
    
    public int updatePropositionStatus(int id, String status) throws SQLException {
        return propositionDAO.updateStatus(id, status);
    }
}
