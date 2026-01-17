package com.CBTapp.models;

public class MembreRetard {
    private int idMembre;
    private String nom;
    private String email;
    private int cotisationsManquantes;
    private int joursDepuisDerniereSeance;
    
    public MembreRetard() {}
    
    public MembreRetard(int idMembre, String nom, String email, 
                       int cotisationsManquantes, int joursDepuisDerniereSeance) {
        this.idMembre = idMembre;
        this.nom = nom;
        this.email = email;
        this.cotisationsManquantes = cotisationsManquantes;
        this.joursDepuisDerniereSeance = joursDepuisDerniereSeance;
    }
    
    public int getIdMembre() { return idMembre; }
    public void setIdMembre(int idMembre) { this.idMembre = idMembre; }
    
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public int getCotisationsManquantes() { return cotisationsManquantes; }
    public void setCotisationsManquantes(int cotisationsManquantes) { 
        this.cotisationsManquantes = cotisationsManquantes; 
    }
    
    public int getJoursDepuisDerniereSeance() { return joursDepuisDerniereSeance; }
    public void setJoursDepuisDerniereSeance(int joursDepuisDerniereSeance) { 
        this.joursDepuisDerniereSeance = joursDepuisDerniereSeance; 
    }
}


