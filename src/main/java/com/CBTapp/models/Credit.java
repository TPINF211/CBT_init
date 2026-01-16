package com.CBTapp.models;

import java.time.LocalDate;

public class Credit {
    private int idCredit;
    private int idMembre;
    private double montant;
    private StatusCredit status;
    private double interet;
    private LocalDate dateDemande;
    private LocalDate dateRemboursement;

    public enum StatusCredit {
        EN_COURS,
        REMBOURSE
    }

    // Constructeurs
    public Credit() {}

    public Credit(int idMembre, double montant, double interet) {
        this.idMembre = idMembre;
        this.montant = montant;
        this.interet = interet;
        this.status = StatusCredit.EN_COURS;
        this.dateDemande = LocalDate.now();
    }

    // Getters et Setters
    public int getIdCredit() { return idCredit; }
    public void setIdCredit(int idCredit) { this.idCredit = idCredit; }

    public int getIdMembre() { return idMembre; }
    public void setIdMembre(int idMembre) { this.idMembre = idMembre; }

    public double getMontant() { return montant; }
    public void setMontant(double montant) { this.montant = montant; }

    public StatusCredit getStatus() { return status; }
    public void setStatus(StatusCredit status) { this.status = status; }

    public double getInteret() { return interet; }
    public void setInteret(double interet) { this.interet = interet; }

    public LocalDate getDateDemande() { return dateDemande; }
    public void setDateDemande(LocalDate dateDemande) { this.dateDemande = dateDemande; }

    public LocalDate getDateRemboursement() { return dateRemboursement; }
    public void setDateRemboursement(LocalDate dateRemboursement) { this.dateRemboursement = dateRemboursement; }
}
