package com.CBTapp.ui;

import javax.swing.*;
import java.awt.*;

public class ClientPanel extends JPanel {
    private static final Color BLUE_COLOR = new Color(70, 130, 180);
    private static final Color BEIGE_COLOR = new Color(245, 245, 220);
    
    private JTabbedPane tabbedPane;
    
    public ClientPanel() {
        setLayout(new BorderLayout());
        setBackground(BEIGE_COLOR);
        
        tabbedPane = new JTabbedPane();
        tabbedPane.setBackground(BEIGE_COLOR);
        tabbedPane.setForeground(BLUE_COLOR);
        
        // Ajouter les onglets pour chaque module
        tabbedPane.addTab("Propositions", new PropositionPanel());
        tabbedPane.addTab("Votes", new VotePanel());
        tabbedPane.addTab("Crédits", new CreditPanel());
        tabbedPane.addTab("Statistiques Membre", new StatMembrePanel());
        tabbedPane.addTab("Statistiques Tontine", new TontineStatPanel());
        tabbedPane.addTab("Membres en Retard", new MembreRetardPanel());
        tabbedPane.addTab("Solde Groupe", new SoldeGroupePanel());
        tabbedPane.addTab("Résultats Votes", new VoteResultsPanel());
        
        add(tabbedPane, BorderLayout.CENTER);
    }
}


