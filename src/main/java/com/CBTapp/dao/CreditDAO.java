package com.CBTapp.dao;

import com.CBTapp.db.DatabaseManager;
import com.CBTapp.models.Credit;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CreditDAO extends BaseDao {
    
    public List<Credit> getAll() throws SQLException {
        List<Credit> credits = new ArrayList<>();
        ResultSet rs = DatabaseManager.executeProcedure("GetAllCredits");
        while (rs.next()) {
            Credit c = new Credit();
            c.setIdCredit(rs.getInt("id_credit"));
            c.setIdMembre(rs.getInt("id_membre"));
            c.setMontant(rs.getDouble("montant"));
            String statusStr = rs.getString("status");
            c.setStatus(statusStr != null ? 
                (statusStr.equals("en_cours") ? Credit.StatusCredit.EN_COURS : Credit.StatusCredit.REMBOURSE) 
                : Credit.StatusCredit.EN_COURS);
            c.setInteret(rs.getDouble("interet"));
            if (rs.getDate("date_demande") != null) {
                c.setDateDemande(rs.getDate("date_demande").toLocalDate());
            }
            if (rs.getDate("date_remboursement") != null) {
                c.setDateRemboursement(rs.getDate("date_remboursement").toLocalDate());
            }
            credits.add(c);
        }
        return credits;
    }
    
    public Credit getById(int id) throws SQLException {
        ResultSet rs = DatabaseManager.executeProcedure("GetCreditByID", id);
        if (rs.next()) {
            Credit c = new Credit();
            c.setIdCredit(rs.getInt("id_credit"));
            c.setIdMembre(rs.getInt("id_membre"));
            c.setMontant(rs.getDouble("montant"));
            String statusStr = rs.getString("status");
            c.setStatus(statusStr != null ? 
                (statusStr.equals("en_cours") ? Credit.StatusCredit.EN_COURS : Credit.StatusCredit.REMBOURSE) 
                : Credit.StatusCredit.EN_COURS);
            c.setInteret(rs.getDouble("interet"));
            if (rs.getDate("date_demande") != null) {
                c.setDateDemande(rs.getDate("date_demande").toLocalDate());
            }
            if (rs.getDate("date_remboursement") != null) {
                c.setDateRemboursement(rs.getDate("date_remboursement").toLocalDate());
            }
            return c;
        }
        return null;
    }
    
    public int add(int idMembre, double montant, double interet) throws SQLException {
        ResultSet rs = DatabaseManager.executeProcedure("AjouterCredits", idMembre, montant, interet);
        if (rs.next()) {
            String status = rs.getString("status");
            if ("SUCCESS".equals(status)) {
                return rs.getInt("new_credit_id");
            }
        }
        return -1;
    }
    
    public int delete(int id) throws SQLException {
        ResultSet rs = DatabaseManager.executeProcedure("DeleteCredits", id);
        if (rs.next()) {
            return rs.getInt("affected_rows");
        }
        return 0;
    }
    
    public int updateStatus(int id, String newStatus) throws SQLException {
        ResultSet rs = DatabaseManager.executeProcedure("UpdateCreditStatus", id, newStatus);
        if (rs.next()) {
            return rs.getInt("rows_updated");
        }
        return 0;
    }
}


