package com.CBTapp.models;





public class Projet {
    private int id_projet;
    private int id_groupe;
    private String name;
    private String description;
    private double montant;
    private Status status;
    
    public Projet() {}
    
    public Projet(int idGroupe, String nom, String description, double montant, String status) {
        this.id_groupe = idGroupe;
        this.name = nom;
        this.description = description;
        this.montant = montant;
        this.status = Status.PENDING;
    }

    //Getters
    public int getId_projet() {
        return id_projet;
    }
    public String getName() {
        return name;
    }
    
    public int getIdGroupe() {
        return id_groupe;
    }
    public String getDescription() {
        return description;
    }
    public Status getStatus() {
        return status;
    }

    //Setters
    public void setId_projet(int id_projet) {
        this.id_projet = id_projet;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    
    public double getMontant() {
        return montant;
    }
    
    public void setMontant(double montant) {
        this.montant = montant;
    }
    public void setStatus(Status status) {
        this.status = status;
    }
    public void setId_groupe(int id_groupe) {
        this.id_groupe = id_groupe;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
