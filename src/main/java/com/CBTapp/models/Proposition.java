package com.CBTapp.models;

import java.sql.Date;

public class Proposition {
    private int idProposition;
    private int idMembre;
    private String description;
    private Date date;
    private String status;
    
    public Proposition() {}
    
    public Proposition(int idMembre, String description, Date date, String status) {
        this.idMembre = idMembre;
        this.description = description;
        this.date = date;
        this.status = status;
    }
    
    public int getIdProposition() {
        return idProposition;
    }
    
    public void setIdProposition(int idProposition) {
        this.idProposition = idProposition;
    }
    
    public int getIdMembre() {
        return idMembre;
    }
    
    public void setIdMembre(int idMembre) {
        this.idMembre = idMembre;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public Date getDate() {
        return date;
    }
    
    public void setDate(Date date) {
        this.date = date;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
}
