package com.CBTapp.models;

import java.time.LocalDate;

public class Penalite {
    private int idPenalite;
    private int idMembre;
    private double montant;
    private String raison;
    private LocalDate date;

    // Constructeurs
    public Penalite() {}

    public Penalite(int idMembre, double montant, String raison) {
        this.idMembre = idMembre;
        this.montant = montant;
        this.raison = raison;
        this.date = LocalDate.now();
    }

    // Getters et Setters
    public int getIdPenalite() { return idPenalite; }
    public void setIdPenalite(int idPenalite) { this.idPenalite = idPenalite; }

    public int getIdMembre() { return idMembre; }
    public void setIdMembre(int idMembre) { this.idMembre = idMembre; }

    public double getMontant() { return montant; }
    public void setMontant(double montant) { this.montant = montant; }

    public String getRaison() { return raison; }
    public void setRaison(String raison) { this.raison = raison; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }
}