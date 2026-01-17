package com.CBTapp.services;

import com.CBTapp.dao.StatsDAO;
import com.CBTapp.models.MembreRetard;
import com.CBTapp.models.SoldeGroupe;
import com.CBTapp.models.StatMembre;
import com.CBTapp.models.TontineStat;
import java.sql.SQLException;
import java.util.List;

public class StatsService {
    private StatsDAO statsDAO;
    
    public StatsService() {
        this.statsDAO = new StatsDAO();
    }
    
    public StatMembre getStatMembre(int idMembre) throws SQLException {
        return statsDAO.getStatMembre(idMembre);
    }
    
    public TontineStat getTontineStat(int idMembre) throws SQLException {
        return statsDAO.getTontineStat(idMembre);
    }
    
    public List<MembreRetard> obtenirMembresEnRetard(int groupeId) throws SQLException {
        return statsDAO.obtenirMembresEnRetard(groupeId);
    }
    
    public SoldeGroupe calculerSoldeGroupe(int groupeId) throws SQLException {
        return statsDAO.calculerSoldeGroupe(groupeId);
    }
}


