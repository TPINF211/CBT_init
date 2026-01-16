package com.CBTapp.models;

import java.time.LocalDateTime;

public class Membre {
    private int idMembre;
    private String nom;
    private String email;
    private String mdpHash;
    private Role role;
    private LocalDateTime dateInscription;

    public enum Role {
        MEMBRE,
        PRESIDENT,
        CASSIERE,
        CENSEUR,
        SECRETAIRE
    }

    // Constructeurs
    public Membre() {}

    public Membre(String nom, String email, String mdpHash, Role role) {
        this.nom = nom;
        this.email = email;
        this.mdpHash = mdpHash;
        this.role = role;
        this.dateInscription = LocalDateTime.now();
    }

    // Getters et Setters
    public int getIdMembre() { return idMembre; }
    public void setIdMembre(int idMembre) { this.idMembre = idMembre; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getMdpHash() { return mdpHash; }
    public void setMdpHash(String mdpHash) { this.mdpHash = mdpHash; }

    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }

    public LocalDateTime getDateInscription() { return dateInscription; }
    public void setDateInscription(LocalDateTime dateInscription) { this.dateInscription = dateInscription; }
}
