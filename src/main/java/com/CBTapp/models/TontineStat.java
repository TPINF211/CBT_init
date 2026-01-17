package com.CBTapp.models;

public class TontineStat {
    private int idGroupe;
    private String nomGroupe;
    private int totalMembres;
    private int toursRealises;
    private double totalCotisations;
    private double totalCreditsEnCours;
    private double totalPenalites;
    
    public TontineStat() {}
    
    public TontineStat(int idGroupe, String nomGroupe, int totalMembres, int toursRealises,
                      double totalCotisations, double totalCreditsEnCours, double totalPenalites) {
        this.idGroupe = idGroupe;
        this.nomGroupe = nomGroupe;
        this.totalMembres = totalMembres;
        this.toursRealises = toursRealises;
        this.totalCotisations = totalCotisations;
        this.totalCreditsEnCours = totalCreditsEnCours;
        this.totalPenalites = totalPenalites;
    }
    
    public int getIdGroupe() { return idGroupe; }
    public void setIdGroupe(int idGroupe) { this.idGroupe = idGroupe; }
    
    public String getNomGroupe() { return nomGroupe; }
    public void setNomGroupe(String nomGroupe) { this.nomGroupe = nomGroupe; }
    
    public int getTotalMembres() { return totalMembres; }
    public void setTotalMembres(int totalMembres) { this.totalMembres = totalMembres; }
    
    public int getToursRealises() { return toursRealises; }
    public void setToursRealises(int toursRealises) { this.toursRealises = toursRealises; }
    
    public double getTotalCotisations() { return totalCotisations; }
    public void setTotalCotisations(double totalCotisations) { this.totalCotisations = totalCotisations; }
    
    public double getTotalCreditsEnCours() { return totalCreditsEnCours; }
    public void setTotalCreditsEnCours(double totalCreditsEnCours) { this.totalCreditsEnCours = totalCreditsEnCours; }
    
    public double getTotalPenalites() { return totalPenalites; }
    public void setTotalPenalites(double totalPenalites) { this.totalPenalites = totalPenalites; }
}


