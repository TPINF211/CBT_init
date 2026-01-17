package com.CBTapp.services;

import com.CBTapp.dao.MembreDAO;
import com.CBTapp.models.Membre;
import com.CBTapp.models.UserSession;
import java.sql.SQLException;

public class AuthService {
    private MembreDAO membreDAO;
    
    public AuthService() {
        this.membreDAO = new MembreDAO();
    }
    
    public boolean login(String email, String password) {
        try {
            Membre membre = membreDAO.authenticate(email, password);
            if (membre != null) {
                UserSession.getInstance().setCurrentUser(membre);
                return true;
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public void logout() {
        UserSession.getInstance().logout();
    }
    
    public Membre getCurrentUser() {
        return UserSession.getInstance().getCurrentUser();
    }
    
    public boolean isLoggedIn() {
        return UserSession.getInstance().isLoggedIn();
    }
}

