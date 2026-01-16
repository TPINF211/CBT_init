package com.CBTapp.models;

import java.time.LocalDate;

public class Seance {
    private int idSeance;
    private int idGroupe;
    private String lieu;
    private LocalDate date;

    // Constructeurs
    public Seance() {}

    public Seance(int idGroupe, String lieu, LocalDate date) {
        this.idGroupe = idGroupe;
        this.lieu = lieu;
        this.date = date;
    }

    // Getters et Setters
    public int getIdSeance() { return idSeance; }
    public void setIdSeance(int idSeance) { this.idSeance = idSeance; }

    public int getIdGroupe() { return idGroupe; }
    public void setIdGroupe(int idGroupe) { this.idGroupe = idGroupe; }

    public String getLieu() { return lieu; }
    public void setLieu(String lieu) { this.lieu = lieu; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }
}
