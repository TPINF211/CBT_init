package com.CBTapp.models;

public class StatMembre {
    private int totalCotisations;
    private int totalBouffer;
    private int nombrePenalite;
    private double totalPenalite;
    private int nombreProp;
    
    public StatMembre() {}
    
    public StatMembre(int totalCotisations, int totalBouffer, int nombrePenalite, 
                     double totalPenalite, int nombreProp) {
        this.totalCotisations = totalCotisations;
        this.totalBouffer = totalBouffer;
        this.nombrePenalite = nombrePenalite;
        this.totalPenalite = totalPenalite;
        this.nombreProp = nombreProp;
    }
    
    public int getTotalCotisations() { return totalCotisations; }
    public void setTotalCotisations(int totalCotisations) { this.totalCotisations = totalCotisations; }
    
    public int getTotalBouffer() { return totalBouffer; }
    public void setTotalBouffer(int totalBouffer) { this.totalBouffer = totalBouffer; }
    
    public int getNombrePenalite() { return nombrePenalite; }
    public void setNombrePenalite(int nombrePenalite) { this.nombrePenalite = nombrePenalite; }
    
    public double getTotalPenalite() { return totalPenalite; }
    public void setTotalPenalite(double totalPenalite) { this.totalPenalite = totalPenalite; }
    
    public int getNombreProp() { return nombreProp; }
    public void setNombreProp(int nombreProp) { this.nombreProp = nombreProp; }
}


