package com.CBTapp.dao;

import com.CBTapp.db.DatabaseManager;
import com.CBTapp.models.Choix;
import com.CBTapp.models.Vote;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class VoteDAO {
    
    public List<Vote> getAll() throws SQLException {
        List<Vote> votes = new ArrayList<>();
        ResultSet rs = DatabaseManager.executeProcedure("GetAllVotes");
        while (rs.next()) {
            Vote v = new Vote();
            v.setId_vote(rs.getInt("id_vote"));
            v.setId_prop(rs.getInt("id_proposition"));
            v.setId_membre(rs.getInt("id_membre"));
            v.setChoix(Choix.valueOf(rs.getString("choix").toUpperCase()    ));
            v.setDate(rs.getDate("date"));
            votes.add(v);
        }
        return votes;
    }
    
    public Vote getById(int id) throws SQLException {
        ResultSet rs = DatabaseManager.executeProcedure("GetVoteByID", id);
        if (rs.next()) {
            Vote v = new Vote();
            v.setId_vote(rs.getInt("id_vote"));
            v.setId_prop(rs.getInt("id_proposition"));
            v.setId_membre(rs.getInt("id_membre"));
            v.setChoix(Choix.valueOf(rs.getString("choix").toUpperCase()));
            v.setDate(rs.getDate("date"));
            return v;
        }
        return null;
    }
    
    public int creerVote(int idProposition, int idMembre, String choix) throws SQLException {
        ResultSet rs = DatabaseManager.executeProcedure("CreerVote", idProposition, idMembre, choix);
        if (rs.next()) {
            String status = rs.getString("status");
            if ("SUCCESS".equals(status)) {
                return rs.getInt("new_vote_id");
            }
        }
        return -1;
    }
    
    public int updateChoix(int id, String newChoix) throws SQLException {
        ResultSet rs = DatabaseManager.executeProcedure("UpdateVote", id, newChoix);
        if (rs.next()) {
            return rs.getInt("rows_updated");
        }
        return 0;
    }
}

