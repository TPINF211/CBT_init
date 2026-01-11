package com.CBTapp.services;

import com.CBTapp.dao.VoteDAO;
import com.CBTapp.models.Vote;
import java.sql.SQLException;
import java.util.List;

public class VoteService {
    private VoteDAO voteDAO;
    
    public VoteService() {
        this.voteDAO = new VoteDAO();
    }
    
    public List<Vote> getAllVotes() throws SQLException {
        return voteDAO.getAll();
    }
    
    public Vote getVoteById(int id) throws SQLException {
        return voteDAO.getById(id);
    }
    
    public void updateVoteChoix(int id, String choix) throws SQLException {
        voteDAO.updateChoix(id, choix);
    }
}

