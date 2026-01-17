package com.CBTapp.models;

import java.sql.Date;


public class Vote {
    private int id_vote;
    private int id_prop;
    private int id_membre;
    private Choix choix;
    private Date date;
    


    public Vote() {}
    
    public Vote(int id_vote,int id_prop,int id_membre, Choix choix){
         this.id_vote = id_vote;
         this.id_membre = id_membre;
         this.id_prop = id_prop;
         this.choix = Choix.NULL;
     }

    public Choix getChoix() {
        return choix;
    }

    public int getId_vote() {
        return id_vote;
    }

    public int getId_prop() {
        return id_prop;
    }
    
    public int getId_membre() {
        return id_membre;
    }
    
    public void setChoix(Choix choix) {
        this.choix = choix;
    }

    public void setId_vote(int id_vote) {
        this.id_vote = id_vote;
    }
    
    public void setId_prop(int id_prop) {
        this.id_prop = id_prop;
    }

    public void setId_membre(int id_membre) {
        this.id_membre = id_membre;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
