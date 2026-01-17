package com.CBTapp.dao;

import com.CBTapp.db.DatabaseManager;
import com.CBTapp.models.Tour;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TourDAO extends BaseDao {
    
    public List<Tour> getAll() throws SQLException {
        List<Tour> tours = new ArrayList<>();
        String query = "SELECT * FROM tours ORDER BY date DESC";
        ResultSet rs = DatabaseManager.executeQuery(query);
        
        while (rs.next()) {
            tours.add(mapResultSetToTour(rs));
        }
        return tours;
    }
    
    private Tour mapResultSetToTour(ResultSet rs) throws SQLException {
        Tour tour = new Tour();
        tour.setIdTour(rs.getInt("id_tour"));
        tour.setIdGroupe(rs.getInt("id_groupe"));
        tour.setIdBeneficiaire(rs.getInt("id_beneficiaire"));
        tour.setNumeroTour(rs.getInt("numero_tour"));
        tour.setMontant(rs.getDouble("montant"));
        if (rs.getDate("date") != null) {
            tour.setDate(rs.getDate("date").toLocalDate());
        }
        return tour;
    }
}

