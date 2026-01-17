package com.CBTapp.ui;

import com.CBTapp.models.TontineStat;
import com.CBTapp.services.StatsService;
import com.CBTapp.ui.components.CustomButton;
import javax.swing.*;
import java.awt.*;

public class TontineStatPanel extends JPanel {
    private static final Color BLUE_COLOR = new Color(70, 130, 180);
    private static final Color BEIGE_COLOR = new Color(245, 245, 220);
    
    private StatsService statsService;
    private JTextField idMembreField;
    private JLabel idGroupeLabel, nomGroupeLabel, totalMembresLabel, toursRealisesLabel;
    private JLabel totalCotisationsLabel, totalCreditsLabel, totalPenalitesLabel;
    
    public TontineStatPanel() {
        statsService = new StatsService();
        setLayout(new BorderLayout());
        setBackground(BEIGE_COLOR);
        
        // Panel de formulaire
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(BEIGE_COLOR);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("ID Membre:"), gbc);
        gbc.gridx = 1;
        idMembreField = new JTextField(20);
        formPanel.add(idMembreField, gbc);
        
        CustomButton searchBtn = new CustomButton("Rechercher");
        searchBtn.addActionListener(e -> rechercherStats());
        gbc.gridx = 2;
        formPanel.add(searchBtn, gbc);
        
        // Panel de résultats
        JPanel resultPanel = new JPanel(new GridBagLayout());
        resultPanel.setBackground(BEIGE_COLOR);
        resultPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(BLUE_COLOR, 2), "Statistiques de la Tontine"));
        GridBagConstraints gbc2 = new GridBagConstraints();
        gbc2.insets = new Insets(5, 5, 5, 5);
        gbc2.anchor = GridBagConstraints.WEST;
        
        gbc2.gridx = 0; gbc2.gridy = 0;
        resultPanel.add(new JLabel("ID Groupe:"), gbc2);
        gbc2.gridx = 1;
        idGroupeLabel = new JLabel("0");
        resultPanel.add(idGroupeLabel, gbc2);
        
        gbc2.gridx = 0; gbc2.gridy = 1;
        resultPanel.add(new JLabel("Nom Groupe:"), gbc2);
        gbc2.gridx = 1;
        nomGroupeLabel = new JLabel("");
        resultPanel.add(nomGroupeLabel, gbc2);
        
        gbc2.gridx = 0; gbc2.gridy = 2;
        resultPanel.add(new JLabel("Total Membres:"), gbc2);
        gbc2.gridx = 1;
        totalMembresLabel = new JLabel("0");
        resultPanel.add(totalMembresLabel, gbc2);
        
        gbc2.gridx = 0; gbc2.gridy = 3;
        resultPanel.add(new JLabel("Tours Réalisés:"), gbc2);
        gbc2.gridx = 1;
        toursRealisesLabel = new JLabel("0");
        resultPanel.add(toursRealisesLabel, gbc2);
        
        gbc2.gridx = 0; gbc2.gridy = 4;
        resultPanel.add(new JLabel("Total Cotisations:"), gbc2);
        gbc2.gridx = 1;
        totalCotisationsLabel = new JLabel("0.0");
        resultPanel.add(totalCotisationsLabel, gbc2);
        
        gbc2.gridx = 0; gbc2.gridy = 5;
        resultPanel.add(new JLabel("Total Crédits en Cours:"), gbc2);
        gbc2.gridx = 1;
        totalCreditsLabel = new JLabel("0.0");
        resultPanel.add(totalCreditsLabel, gbc2);
        
        gbc2.gridx = 0; gbc2.gridy = 6;
        resultPanel.add(new JLabel("Total Pénalités:"), gbc2);
        gbc2.gridx = 1;
        totalPenalitesLabel = new JLabel("0.0");
        resultPanel.add(totalPenalitesLabel, gbc2);
        
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(BEIGE_COLOR);
        topPanel.add(formPanel, BorderLayout.NORTH);
        
        add(topPanel, BorderLayout.NORTH);
        add(resultPanel, BorderLayout.CENTER);
    }
    
    private void rechercherStats() {
        try {
            int idMembre = Integer.parseInt(idMembreField.getText());
            TontineStat stat = statsService.getTontineStat(idMembre);
            if (stat != null) {
                idGroupeLabel.setText(String.valueOf(stat.getIdGroupe()));
                nomGroupeLabel.setText(stat.getNomGroupe());
                totalMembresLabel.setText(String.valueOf(stat.getTotalMembres()));
                toursRealisesLabel.setText(String.valueOf(stat.getToursRealises()));
                totalCotisationsLabel.setText(String.valueOf(stat.getTotalCotisations()));
                totalCreditsLabel.setText(String.valueOf(stat.getTotalCreditsEnCours()));
                totalPenalitesLabel.setText(String.valueOf(stat.getTotalPenalites()));
            } else {
                JOptionPane.showMessageDialog(this, "Aucune statistique trouvée!");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erreur: " + e.getMessage());
        }
    }
}

