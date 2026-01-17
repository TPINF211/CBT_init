package com.CBTapp.dao;

import com.CBTapp.db.DatabaseManager;
import com.CBTapp.models.Cotisation;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CotisationDAO extends BaseDao {
    
    public List<Cotisation> getAll() throws SQLException {
        List<Cotisation> cotisations = new ArrayList<>();
        String query = "SELECT * FROM cotisations ORDER BY date DESC";
        ResultSet rs = DatabaseManager.executeQuery(query);
        
        while (rs.next()) {
            cotisations.add(mapResultSetToCotisation(rs));
        }
        return cotisations;
    }
    
    private Cotisation mapResultSetToCotisation(ResultSet rs) throws SQLException {
        Cotisation cotisation = new Cotisation();
        cotisation.setIdCotisation(rs.getInt("id_cotisation"));
        cotisation.setIdSeance(rs.getInt("id_seance"));
        cotisation.setIdMembre(rs.getInt("id_membre"));
        cotisation.setMontant(rs.getDouble("montant"));
        String typeStr = rs.getString("type");
        if (typeStr != null) {
            cotisation.setType(typeStr.equals("presence") ? 
                Cotisation.TypeCotisation.PRESENCE : Cotisation.TypeCotisation.OPTIONNELLE);
        }
        cotisation.setParts(rs.getDouble("parts"));
        if (rs.getDate("date") != null) {
            cotisation.setDate(rs.getDate("date").toLocalDate());
        }
        return cotisation;
    }
}

