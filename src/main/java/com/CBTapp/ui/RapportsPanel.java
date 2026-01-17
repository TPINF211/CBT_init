package com.CBTapp.ui;

import com.CBTapp.services.StatsService;
import com.CBTapp.ui.components.CustomButton;
import javax.swing.*;
import java.awt.*;

public class RapportsPanel extends JPanel {
    private static final Color BLUE_COLOR = new Color(70, 130, 180);
    private static final Color BEIGE_COLOR = new Color(245, 245, 220);
    
    private StatsService statsService;
    
    public RapportsPanel() {
        statsService = new StatsService();
        setLayout(new BorderLayout());
        setBackground(BEIGE_COLOR);
        
        // Panel d'info
        JPanel infoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        infoPanel.setBackground(BEIGE_COLOR);
        JLabel infoLabel = new JLabel("En tant que Secrétaire, vous pouvez générer des rapports");
        infoLabel.setForeground(BLUE_COLOR);
        infoPanel.add(infoLabel);
        
        // Panel de boutons pour les rapports
        JPanel reportsPanel = new JPanel(new GridBagLayout());
        reportsPanel.setBackground(BEIGE_COLOR);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        
        CustomButton rapportMembresBtn = new CustomButton("Rapport des Membres");
        CustomButton rapportStatistiquesBtn = new CustomButton("Rapport des Statistiques");
        CustomButton rapportTontineBtn = new CustomButton("Rapport de la Tontine");
        CustomButton rapportRetardsBtn = new CustomButton("Rapport des Retards");
        
        rapportMembresBtn.addActionListener(e -> genererRapportMembres());
        rapportStatistiquesBtn.addActionListener(e -> genererRapportStatistiques());
        rapportTontineBtn.addActionListener(e -> genererRapportTontine());
        rapportRetardsBtn.addActionListener(e -> genererRapportRetards());
        
        gbc.gridx = 0; gbc.gridy = 0;
        reportsPanel.add(rapportMembresBtn, gbc);
        gbc.gridy = 1;
        reportsPanel.add(rapportStatistiquesBtn, gbc);
        gbc.gridy = 2;
        reportsPanel.add(rapportTontineBtn, gbc);
        gbc.gridy = 3;
        reportsPanel.add(rapportRetardsBtn, gbc);
        
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(BEIGE_COLOR);
        topPanel.add(infoPanel, BorderLayout.NORTH);
        
        add(topPanel, BorderLayout.NORTH);
        add(reportsPanel, BorderLayout.CENTER);
    }
    
    private void genererRapportMembres() {
        JOptionPane.showMessageDialog(
            this,
            "Rapport des Membres\n\n" +
            "Ce rapport liste tous les membres avec leurs informations.\n" +
            "Fonctionnalité à implémenter avec export PDF/Excel.",
            "Rapport Membres",
            JOptionPane.INFORMATION_MESSAGE
        );
    }
    
    private void genererRapportStatistiques() {
        JOptionPane.showMessageDialog(
            this,
            "Rapport des Statistiques\n\n" +
            "Ce rapport contient les statistiques globales de la tontine.\n" +
            "Fonctionnalité à implémenter avec export PDF/Excel.",
            "Rapport Statistiques",
            JOptionPane.INFORMATION_MESSAGE
        );
    }
    
    private void genererRapportTontine() {
        JOptionPane.showMessageDialog(
            this,
            "Rapport de la Tontine\n\n" +
            "Ce rapport contient les informations sur la tontine.\n" +
            "Fonctionnalité à implémenter avec export PDF/Excel.",
            "Rapport Tontine",
            JOptionPane.INFORMATION_MESSAGE
        );
    }
    
    private void genererRapportRetards() {
        JOptionPane.showMessageDialog(
            this,
            "Rapport des Retards\n\n" +
            "Ce rapport liste les membres en retard de paiement.\n" +
            "Fonctionnalité à implémenter avec export PDF/Excel.",
            "Rapport Retards",
            JOptionPane.INFORMATION_MESSAGE
        );
    }
}

