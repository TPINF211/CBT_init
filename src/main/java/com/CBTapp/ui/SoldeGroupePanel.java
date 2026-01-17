package com.CBTapp.ui;

import com.CBTapp.models.SoldeGroupe;
import com.CBTapp.services.StatsService;
import com.CBTapp.ui.components.CustomButton;
import javax.swing.*;
import java.awt.*;

public class SoldeGroupePanel extends JPanel {
    private static final Color BLUE_COLOR = new Color(70, 130, 180);
    private static final Color BEIGE_COLOR = new Color(245, 245, 220);
    
    private StatsService statsService;
    private JTextField groupeIdField;
    private JLabel nomLabel, totalCotisationsLabel, totalCreditsLabel;
    private JLabel totalToursLabel, totalPenalitesLabel, soldeLabel;
    
    public SoldeGroupePanel() {
        statsService = new StatsService();
        setLayout(new BorderLayout());
        setBackground(BEIGE_COLOR);
        
        // Panel de formulaire
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(BEIGE_COLOR);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("ID Groupe:"), gbc);
        gbc.gridx = 1;
        groupeIdField = new JTextField(20);
        formPanel.add(groupeIdField, gbc);
        
        CustomButton searchBtn = new CustomButton("Calculer");
        searchBtn.addActionListener(e -> calculerSolde());
        gbc.gridx = 2;
        formPanel.add(searchBtn, gbc);
        
        // Panel de résultats
        JPanel resultPanel = new JPanel(new GridBagLayout());
        resultPanel.setBackground(BEIGE_COLOR);
        resultPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(BLUE_COLOR, 2), "Solde du Groupe"));
        GridBagConstraints gbc2 = new GridBagConstraints();
        gbc2.insets = new Insets(5, 5, 5, 5);
        gbc2.anchor = GridBagConstraints.WEST;
        
        gbc2.gridx = 0; gbc2.gridy = 0;
        resultPanel.add(new JLabel("Nom Groupe:"), gbc2);
        gbc2.gridx = 1;
        nomLabel = new JLabel("");
        resultPanel.add(nomLabel, gbc2);
        
        gbc2.gridx = 0; gbc2.gridy = 1;
        resultPanel.add(new JLabel("Total Cotisations:"), gbc2);
        gbc2.gridx = 1;
        totalCotisationsLabel = new JLabel("0.0");
        resultPanel.add(totalCotisationsLabel, gbc2);
        
        gbc2.gridx = 0; gbc2.gridy = 2;
        resultPanel.add(new JLabel("Total Crédits:"), gbc2);
        gbc2.gridx = 1;
        totalCreditsLabel = new JLabel("0.0");
        resultPanel.add(totalCreditsLabel, gbc2);
        
        gbc2.gridx = 0; gbc2.gridy = 3;
        resultPanel.add(new JLabel("Total Tours:"), gbc2);
        gbc2.gridx = 1;
        totalToursLabel = new JLabel("0.0");
        resultPanel.add(totalToursLabel, gbc2);
        
        gbc2.gridx = 0; gbc2.gridy = 4;
        resultPanel.add(new JLabel("Total Pénalités:"), gbc2);
        gbc2.gridx = 1;
        totalPenalitesLabel = new JLabel("0.0");
        resultPanel.add(totalPenalitesLabel, gbc2);
        
        gbc2.gridx = 0; gbc2.gridy = 5;
        JLabel soldeTitle = new JLabel("SOLDE:");
        soldeTitle.setFont(new Font(soldeTitle.getFont().getName(), Font.BOLD, 14));
        soldeTitle.setForeground(BLUE_COLOR);
        resultPanel.add(soldeTitle, gbc2);
        gbc2.gridx = 1;
        soldeLabel = new JLabel("0.0");
        soldeLabel.setFont(new Font(soldeLabel.getFont().getName(), Font.BOLD, 14));
        soldeLabel.setForeground(BLUE_COLOR);
        resultPanel.add(soldeLabel, gbc2);
        
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(BEIGE_COLOR);
        topPanel.add(formPanel, BorderLayout.NORTH);
        
        add(topPanel, BorderLayout.NORTH);
        add(resultPanel, BorderLayout.CENTER);
    }
    
    private void calculerSolde() {
        try {
            int groupeId = Integer.parseInt(groupeIdField.getText());
            SoldeGroupe solde = statsService.calculerSoldeGroupe(groupeId);
            if (solde != null) {
                nomLabel.setText(solde.getNom());
                totalCotisationsLabel.setText(String.valueOf(solde.getTotalCotisations()));
                totalCreditsLabel.setText(String.valueOf(solde.getTotalCredits()));
                totalToursLabel.setText(String.valueOf(solde.getTotalTours()));
                totalPenalitesLabel.setText(String.valueOf(solde.getTotalPenalites()));
                soldeLabel.setText(String.valueOf(solde.getSolde()));
            } else {
                JOptionPane.showMessageDialog(this, "Aucun solde trouvé!");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erreur: " + e.getMessage());
        }
    }
}

