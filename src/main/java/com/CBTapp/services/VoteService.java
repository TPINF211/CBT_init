package com.CBTapp.services;

import com.CBTapp.dao.VoteDAO;
import com.CBTapp.dao.StatsDAO;
import com.CBTapp.models.Vote;
import com.CBTapp.models.VoteResults;
import java.sql.SQLException;
import java.util.List;

public class VoteService {
    private VoteDAO voteDAO;
    private StatsDAO statsDAO;
    
    public VoteService() {
        this.voteDAO = new VoteDAO();
        this.statsDAO = new StatsDAO();
    }
    
    public List<Vote> getAllVotes() throws SQLException {
        return voteDAO.getAll();
    }
    
    public Vote getVoteById(int id) throws SQLException {
        return voteDAO.getById(id);
    }
    
    public int creerVote(int idProposition, int idMembre, String choix) throws SQLException {
        return voteDAO.creerVote(idProposition, idMembre, choix);
    }
    
    public int updateVote(int id, String choix) throws SQLException {
        return voteDAO.updateChoix(id, choix);
    }
    
    public VoteResults getVoteResults(int idProposition) throws SQLException {
        return statsDAO.getVoteResults(idProposition);
    }
}

