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
    
    public int createProposition(Proposition proposition) throws SQLException {
        return propositionDAO.add(proposition);
    }
    
    public int deleteProposition(int id) throws SQLException {
        return propositionDAO.delete(id);
    }
    
    public void updatePropositionStatus(int id, String status) throws SQLException {
        propositionDAO.updateStatus(id, status);
    }
}
