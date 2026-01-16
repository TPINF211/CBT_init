package com.CBTapp.models;

import java.time.LocalDateTime;

public class Groupe {
    private int idGroupe;
    private String nom;
    private String description;
    private TypeTontine typeTontine;
    private LocalDateTime dateCreation;

    public enum TypeTontine {
        PRESENCE,
        OPTIONNELLE
    }

    // Constructeurs
    public Groupe() {}

    public Groupe(String nom, String description, TypeTontine typeTontine) {
        this.nom = nom;
        this.description = description;
        this.typeTontine = typeTontine;
        this.dateCreation = LocalDateTime.now();
    }

    // Getters et Setters
    public int getIdGroupe() { return idGroupe; }
    public void setIdGroupe(int idGroupe) { this.idGroupe = idGroupe; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public TypeTontine getTypeTontine() { return typeTontine; }
    public void setTypeTontine(TypeTontine typeTontine) { this.typeTontine = typeTontine; }

    public LocalDateTime getDateCreation() { return dateCreation; }
    public void setDateCreation(LocalDateTime dateCreation) { this.dateCreation = dateCreation; }
}
