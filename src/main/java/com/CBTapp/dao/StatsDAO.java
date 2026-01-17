package com.CBTapp.dao;

import com.CBTapp.db.DatabaseManager;
import com.CBTapp.models.MembreRetard;
import com.CBTapp.models.SoldeGroupe;
import com.CBTapp.models.StatMembre;
import com.CBTapp.models.TontineStat;
import com.CBTapp.models.VoteResults;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StatsDAO extends BaseDao {
    
    public StatMembre getStatMembre(int idMembre) throws SQLException {
        ResultSet rs = DatabaseManager.executeProcedure("GetStatMembre", idMembre);
        if (rs.next()) {
            StatMembre stat = new StatMembre();
            stat.setTotalCotisations(rs.getInt("total_contisations"));
            stat.setTotalBouffer(rs.getInt("total_bouffer"));
            stat.setNombrePenalite(rs.getInt("nombre_penalite"));
            stat.setTotalPenalite(rs.getDouble("total_penalite"));
            stat.setNombreProp(rs.getInt("nombre_prop"));
            return stat;
        }
        return null;
    }
    
    public TontineStat getTontineStat(int idMembre) throws SQLException {
        ResultSet rs = DatabaseManager.executeProcedure("TontineStat", idMembre);
        if (rs.next()) {
            TontineStat stat = new TontineStat();
            stat.setIdGroupe(rs.getInt("id_groupe"));
            stat.setNomGroupe(rs.getString("nom_groupe"));
            stat.setTotalMembres(rs.getInt("total_membres"));
            stat.setToursRealises(rs.getInt("tours_realises"));
            stat.setTotalCotisations(rs.getDouble("total_cotisations"));
            stat.setTotalCreditsEnCours(rs.getDouble("total_credits_en_cours"));
            stat.setTotalPenalites(rs.getDouble("total_penalites"));
            return stat;
        }
        return null;
    }
    
    public List<MembreRetard> obtenirMembresEnRetard(int groupeId) throws SQLException {
        List<MembreRetard> membres = new ArrayList<>();
        ResultSet rs = DatabaseManager.executeProcedure("obtenir_membres_en_retard", groupeId);
        while (rs.next()) {
            MembreRetard membre = new MembreRetard();
            membre.setIdMembre(rs.getInt("id_membre"));
            membre.setNom(rs.getString("nom"));
            membre.setEmail(rs.getString("email"));
            membre.setCotisationsManquantes(rs.getInt("cotisations_manquantes"));
            membre.setJoursDepuisDerniereSeance(rs.getInt("jours_depuis_derniere_seance"));
            membres.add(membre);
        }
        return membres;
    }
    
    public SoldeGroupe calculerSoldeGroupe(int groupeId) throws SQLException {
        ResultSet rs = DatabaseManager.executeProcedure("calculer_solde_groupe", groupeId);
        if (rs.next()) {
            SoldeGroupe solde = new SoldeGroupe();
            solde.setNom(rs.getString("nom"));
            solde.setTotalCotisations(rs.getDouble("total_cotisations"));
            solde.setTotalCredits(rs.getDouble("total_credits"));
            solde.setTotalTours(rs.getDouble("total_tours"));
            solde.setTotalPenalites(rs.getDouble("total_penalites"));
            solde.setSolde(rs.getDouble("solde"));
            return solde;
        }
        return null;
    }
    
    public VoteResults getVoteResults(int idProposition) throws SQLException {
        ResultSet rs = DatabaseManager.executeProcedure("GetVoteResults", idProposition);
        if (rs.next()) {
            VoteResults results = new VoteResults();
            results.setVotesOui(rs.getInt("votes_oui"));
            results.setVotesNon(rs.getInt("votes_non"));
            results.setTotalVotes(rs.getInt("total_votes"));
            results.setPourcentageOui(rs.getDouble("pourcentage_oui"));
            return results;
        }
        return null;
    }
}


