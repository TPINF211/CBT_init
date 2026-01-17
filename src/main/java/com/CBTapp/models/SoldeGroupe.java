package com.CBTapp.models;

public class SoldeGroupe {
    private String nom;
    private double totalCotisations;
    private double totalCredits;
    private double totalTours;
    private double totalPenalites;
    private double solde;
    
    public SoldeGroupe() {}
    
    public SoldeGroupe(String nom, double totalCotisations, double totalCredits,
                      double totalTours, double totalPenalites, double solde) {
        this.nom = nom;
        this.totalCotisations = totalCotisations;
        this.totalCredits = totalCredits;
        this.totalTours = totalTours;
        this.totalPenalites = totalPenalites;
        this.solde = solde;
    }
    
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    
    public double getTotalCotisations() { return totalCotisations; }
    public void setTotalCotisations(double totalCotisations) { this.totalCotisations = totalCotisations; }
    
    public double getTotalCredits() { return totalCredits; }
    public void setTotalCredits(double totalCredits) { this.totalCredits = totalCredits; }
    
    public double getTotalTours() { return totalTours; }
    public void setTotalTours(double totalTours) { this.totalTours = totalTours; }
    
    public double getTotalPenalites() { return totalPenalites; }
    public void setTotalPenalites(double totalPenalites) { this.totalPenalites = totalPenalites; }
    
    public double getSolde() { return solde; }
    public void setSolde(double solde) { this.solde = solde; }
}


