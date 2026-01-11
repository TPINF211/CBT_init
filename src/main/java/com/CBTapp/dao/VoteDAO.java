package com.CBTapp.dao;

import com.CBTapp.db.DatabaseManager;
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
    
    public void updateChoix(int id, String newChoix) throws SQLException {
        DatabaseManager.executeProcedure("UpdateProSta", id, newChoix);
    }
}

