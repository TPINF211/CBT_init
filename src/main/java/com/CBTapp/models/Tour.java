package com.CBTapp.models;


import java.time.LocalDate;

public class Tour {
    private int idTour;
    private int idGroupe;
    private int idBeneficiaire;
    private int numeroTour;
    private double montant;
    private LocalDate date;

    // Constructeurs
    public Tour() {}

    public Tour(int idGroupe, int idBeneficiaire, int numeroTour, double montant) {
        this.idGroupe = idGroupe;
        this.idBeneficiaire = idBeneficiaire;
        this.numeroTour = numeroTour;
        this.montant = montant;
        this.date = LocalDate.now();
    }

    // Getters et Setters
    public int getIdTour() { return idTour; }
    public void setIdTour(int idTour) { this.idTour = idTour; }

    public int getIdGroupe() { return idGroupe; }
    public void setIdGroupe(int idGroupe) { this.idGroupe = idGroupe; }

    public int getIdBeneficiaire() { return idBeneficiaire; }
    public void setIdBeneficiaire(int idBeneficiaire) { this.idBeneficiaire = idBeneficiaire; }

    public int getNumeroTour() { return numeroTour; }
    public void setNumeroTour(int numeroTour) { this.numeroTour = numeroTour; }

    public double getMontant() { return montant; }
    public void setMontant(double montant) { this.montant = montant; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }
}
