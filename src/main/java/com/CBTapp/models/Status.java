package com.CBTapp.models;

public enum Status{
    APPROVED("Approuvée"),
    REJECTED("Rejetée"),
    PENDING("En attente");

    private final String displayName;
    Status(String displayName) {
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

