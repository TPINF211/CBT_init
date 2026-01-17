package com.CBTapp.services;

import com.CBTapp.dao.CreditDAO;
import com.CBTapp.models.Credit;
import java.sql.SQLException;
import java.util.List;

public class CreditService {
    private CreditDAO creditDAO;
    
    public CreditService() {
        this.creditDAO = new CreditDAO();
    }
    
    public List<Credit> getAllCredits() throws SQLException {
        return creditDAO.getAll();
    }
    
    public Credit getCreditById(int id) throws SQLException {
        return creditDAO.getById(id);
    }
    
    public int createCredit(int idMembre, double montant, double interet) throws SQLException {
        return creditDAO.add(idMembre, montant, interet);
    }
    
    public int deleteCredit(int id) throws SQLException {
        return creditDAO.delete(id);
    }
    
    public int updateCreditStatus(int id, String status) throws SQLException {
        return creditDAO.updateStatus(id, status);
    }
}


