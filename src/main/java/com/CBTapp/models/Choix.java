package com.CBTapp.models;

public enum Choix{
    YES("Oui"),
    NO("Non"),
    NULL("Null");
    private final String displayName;
    Choix(String displayName) {
        this.displayName = displayName;
    }
    public String getDisplayName() {
        return displayName;
    }
    @Override
    public String toString() {
        return super.toString();
    }
}
