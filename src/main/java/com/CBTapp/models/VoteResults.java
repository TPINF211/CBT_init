package com.CBTapp.models;

public class VoteResults {
    private int votesOui;
    private int votesNon;
    private int totalVotes;
    private double pourcentageOui;
    
    public VoteResults() {}
    
    public VoteResults(int votesOui, int votesNon, int totalVotes, double pourcentageOui) {
        this.votesOui = votesOui;
        this.votesNon = votesNon;
        this.totalVotes = totalVotes;
        this.pourcentageOui = pourcentageOui;
    }
    
    public int getVotesOui() { return votesOui; }
    public void setVotesOui(int votesOui) { this.votesOui = votesOui; }
    
    public int getVotesNon() { return votesNon; }
    public void setVotesNon(int votesNon) { this.votesNon = votesNon; }
    
    public int getTotalVotes() { return totalVotes; }
    public void setTotalVotes(int totalVotes) { this.totalVotes = totalVotes; }
    
    public double getPourcentageOui() { return pourcentageOui; }
    public void setPourcentageOui(double pourcentageOui) { this.pourcentageOui = pourcentageOui; }
}


