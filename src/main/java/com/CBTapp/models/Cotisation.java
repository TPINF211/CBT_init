package com.CBTapp.models;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Cotisation {
    private int idCotisation;
    private int idSeance;
    private int idMembre;
    private Double montant;
    private TypeCotisation type;
    private Double parts;
    private LocalDate date;

    public enum TypeCotisation {
        PRESENCE,
        OPTIONNELLE
    }

    // Constructeurs
    public Cotisation() {}

    public Cotisation(int idSeance, int idMembre, double montant, TypeCotisation type, double parts) {
        this.idSeance = idSeance;
        this.idMembre = idMembre;
        this.montant = montant;
        this.type = type;
        this.parts = parts;
        this.date = LocalDate.now();
    }

    // Getters et Setters
    public int getIdCotisation() { return idCotisation; }
    public void setIdCotisation(int idCotisation) { this.idCotisation = idCotisation; }

    public int getIdSeance() { return idSeance; }
    public void setIdSeance(int idSeance) { this.idSeance = idSeance; }

    public int getIdMembre() { return idMembre; }
    public void setIdMembre(int idMembre) { this.idMembre = idMembre; }

    public double getMontant() { return montant; }
    public void setMontant(double montant) { this.montant = montant; }

    public TypeCotisation getType() { return type; }
    public void setType(TypeCotisation type) { this.type = type; }

    public double getParts() { return parts; }
    public void setParts(double parts) { this.parts = parts; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }
}
