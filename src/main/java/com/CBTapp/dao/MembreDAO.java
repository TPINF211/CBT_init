package com.CBTapp.dao;

import com.CBTapp.db.DatabaseManager;
import com.CBTapp.models.Membre;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MembreDAO extends BaseDao {
    
    public Membre authenticate(String email, String password) throws SQLException {
        // Note: En production, il faudrait hasher le mot de passe
        // Pour l'instant, on compare directement (à améliorer)
        String query = "SELECT * FROM membres WHERE email = ? AND mdp_hash = ?";
        ResultSet rs = DatabaseManager.executeQuery(query, email, password);
        
        if (rs.next()) {
            return mapResultSetToMembre(rs);
        }
        return null;
    }
    
    public Membre getById(int id) throws SQLException {
        String query = "SELECT * FROM membres WHERE id_membre = ?";
        ResultSet rs = DatabaseManager.executeQuery(query, id);
        
        if (rs.next()) {
            return mapResultSetToMembre(rs);
        }
        return null;
    }
    
    public List<Membre> getAll() throws SQLException {
        List<Membre> membres = new ArrayList<>();
        String query = "SELECT * FROM membres ORDER BY nom";
        ResultSet rs = DatabaseManager.executeQuery(query);
        
        while (rs.next()) {
            membres.add(mapResultSetToMembre(rs));
        }
        return membres;
    }
    
    public List<Membre> getByRole(String role) throws SQLException {
        List<Membre> membres = new ArrayList<>();
        String query = "SELECT * FROM membres WHERE role = ? ORDER BY nom";
        ResultSet rs = DatabaseManager.executeQuery(query, role);
        
        while (rs.next()) {
            membres.add(mapResultSetToMembre(rs));
        }
        return membres;
    }
    
    public int updateRole(int idMembre, String newRole) throws SQLException {
        String query = "UPDATE membres SET role = ? WHERE id_membre = ?";
        return DatabaseManager.executeUpdate(query, newRole, idMembre);
    }
    
    private Membre mapResultSetToMembre(ResultSet rs) throws SQLException {
        Membre membre = new Membre();
        membre.setIdMembre(rs.getInt("id_membre"));
        membre.setNom(rs.getString("nom"));
        membre.setEmail(rs.getString("email"));
        membre.setMdpHash(rs.getString("mdp_hash"));
        
        // Convertir le rôle de la base de données vers l'enum
        String roleStr = rs.getString("role");
        if (roleStr != null) {
            switch (roleStr) {
                case "President":
                    membre.setRole(Membre.Role.PRESIDENT);
                    break;
                case "Secretaire":
                    membre.setRole(Membre.Role.SECRETAIRE);
                    break;
                case "Censeur":
                    membre.setRole(Membre.Role.CENSEUR);
                    break;
                case "Gestionnaire":
                    membre.setRole(Membre.Role.CASSIERE);
                    break;
                default:
                    membre.setRole(Membre.Role.MEMBRE);
            }
        }
        
        if (rs.getTimestamp("date_inscription") != null) {
            membre.setDateInscription(rs.getTimestamp("date_inscription").toLocalDateTime());
        }
        
        return membre;
    }
}

