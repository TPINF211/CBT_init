package com.CBTapp.models;

public class UserSession {
    private static UserSession instance;
    private Membre currentUser;
    
    private UserSession() {}
    
    public static UserSession getInstance() {
        if (instance == null) {
            instance = new UserSession();
        }
        return instance;
    }
    
    public void setCurrentUser(Membre membre) {
        this.currentUser = membre;
    }
    
    public Membre getCurrentUser() {
        return currentUser;
    }
    
    public boolean isLoggedIn() {
        return currentUser != null;
    }
    
    public boolean hasRole(Membre.Role role) {
        return currentUser != null && currentUser.getRole() == role;
    }
    
    public boolean isPresident() {
        return hasRole(Membre.Role.PRESIDENT);
    }
    
    public boolean isSecretaire() {
        return hasRole(Membre.Role.SECRETAIRE);
    }
    
    public boolean isCenseur() {
        return hasRole(Membre.Role.CENSEUR);
    }
    
    public boolean isCaissier() {
        return hasRole(Membre.Role.CASSIERE);
    }
    
    public boolean isMembre() {
        return hasRole(Membre.Role.MEMBRE);
    }
    
    public boolean isBureau() {
        return isPresident() || isSecretaire() || isCenseur();
    }
    
    public void logout() {
        this.currentUser = null;
    }
}

