package com.CBTapp.ui;

import com.CBTapp.models.UserSession;
import javax.swing.*;
import java.awt.*;

public class RoleBasedPanel extends JPanel {
    private static final Color BLUE_COLOR = new Color(70, 130, 180);
    private static final Color BEIGE_COLOR = new Color(245, 245, 220);
    
    private JTabbedPane tabbedPane;
    private UserSession session;
    
    public RoleBasedPanel() {
        session = UserSession.getInstance();
        setLayout(new BorderLayout());
        setBackground(BEIGE_COLOR);
        
        tabbedPane = new JTabbedPane();
        tabbedPane.setBackground(BEIGE_COLOR);
        tabbedPane.setForeground(BLUE_COLOR);
        
        // Ajouter les onglets selon le rôle
        if (session.isPresident()) {
            addPresidentTabs();
        } else if (session.isSecretaire()) {
            addSecretaireTabs();
        } else if (session.isCenseur()) {
            addCenseurTabs();
        } else if (session.isCaissier()) {
            addCaissierTabs();
        } else {
            addMembreTabs();
        }
        
        add(tabbedPane, BorderLayout.CENTER);
    }
    
    private void addPresidentTabs() {
        // Président: votes, approbations, membres, propositions
        tabbedPane.addTab("Créer Votes", new VoteCreationPanel());
        tabbedPane.addTab("Approuver Membres", new ApprobationMembresPanel());
        tabbedPane.addTab("Approuver Propositions", new ApprobationPropositionsPanel());
        tabbedPane.addTab("Mes Propositions", new PropositionPanel());
        tabbedPane.addTab("Voter", new VotePanel());
        tabbedPane.addTab("Statistiques", new StatMembrePanel());
    }
    
    private void addSecretaireTabs() {
        // Secrétaire: rapports
        tabbedPane.addTab("Rapports", new RapportsPanel());
        tabbedPane.addTab("Mes Propositions", new PropositionPanel());
        tabbedPane.addTab("Voter", new VotePanel());
    }
    
    private void addCenseurTabs() {
        // Censeur: pénalités
        tabbedPane.addTab("Gérer Pénalités", new PenalitesPanel());
        tabbedPane.addTab("Membres en Retard", new MembreRetardPanel());
        tabbedPane.addTab("Mes Propositions", new PropositionPanel());
        tabbedPane.addTab("Voter", new VotePanel());
    }
    
    private void addCaissierTabs() {
        // Caissier: comptabilité (cotisations, crédits, solde)
        tabbedPane.addTab("Cotisations", new CotisationsPanel());
        tabbedPane.addTab("Crédits", new CreditPanel());
        tabbedPane.addTab("Solde Groupe", new SoldeGroupePanel());
        tabbedPane.addTab("Tours", new ToursPanel());
        tabbedPane.addTab("Mes Propositions", new PropositionPanel());
        tabbedPane.addTab("Voter", new VotePanel());
    }
    
    private void addMembreTabs() {
        // Membre: propositions et votes
        tabbedPane.addTab("Mes Propositions", new PropositionPanel());
        tabbedPane.addTab("Voter", new VotePanel());
        tabbedPane.addTab("Résultats Votes", new VoteResultsPanel());
    }
}

