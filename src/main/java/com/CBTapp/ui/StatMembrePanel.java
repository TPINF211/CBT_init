package com.CBTapp.ui;

import com.CBTapp.models.StatMembre;
import com.CBTapp.services.StatsService;
import com.CBTapp.ui.components.CustomButton;
import javax.swing.*;
import java.awt.*;

public class StatMembrePanel extends JPanel {
    private static final Color BLUE_COLOR = new Color(70, 130, 180);
    private static final Color BEIGE_COLOR = new Color(245, 245, 220);
    
    private StatsService statsService;
    private JTextField idMembreField;
    private JLabel totalCotisationsLabel, totalBoufferLabel, nombrePenaliteLabel;
    private JLabel totalPenaliteLabel, nombrePropLabel;
    
    public StatMembrePanel() {
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
            BorderFactory.createLineBorder(BLUE_COLOR, 2), "Statistiques du Membre"));
        GridBagConstraints gbc2 = new GridBagConstraints();
        gbc2.insets = new Insets(5, 5, 5, 5);
        gbc2.anchor = GridBagConstraints.WEST;
        
        gbc2.gridx = 0; gbc2.gridy = 0;
        resultPanel.add(new JLabel("Total Cotisations:"), gbc2);
        gbc2.gridx = 1;
        totalCotisationsLabel = new JLabel("0");
        resultPanel.add(totalCotisationsLabel, gbc2);
        
        gbc2.gridx = 0; gbc2.gridy = 1;
        resultPanel.add(new JLabel("Total Bouffer:"), gbc2);
        gbc2.gridx = 1;
        totalBoufferLabel = new JLabel("0");
        resultPanel.add(totalBoufferLabel, gbc2);
        
        gbc2.gridx = 0; gbc2.gridy = 2;
        resultPanel.add(new JLabel("Nombre Pénalités:"), gbc2);
        gbc2.gridx = 1;
        nombrePenaliteLabel = new JLabel("0");
        resultPanel.add(nombrePenaliteLabel, gbc2);
        
        gbc2.gridx = 0; gbc2.gridy = 3;
        resultPanel.add(new JLabel("Total Pénalités:"), gbc2);
        gbc2.gridx = 1;
        totalPenaliteLabel = new JLabel("0.0");
        resultPanel.add(totalPenaliteLabel, gbc2);
        
        gbc2.gridx = 0; gbc2.gridy = 4;
        resultPanel.add(new JLabel("Nombre Propositions:"), gbc2);
        gbc2.gridx = 1;
        nombrePropLabel = new JLabel("0");
        resultPanel.add(nombrePropLabel, gbc2);
        
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(BEIGE_COLOR);
        topPanel.add(formPanel, BorderLayout.NORTH);
        
        add(topPanel, BorderLayout.NORTH);
        add(resultPanel, BorderLayout.CENTER);
    }
    
    private void rechercherStats() {
        try {
            int idMembre = Integer.parseInt(idMembreField.getText());
            StatMembre stat = statsService.getStatMembre(idMembre);
            if (stat != null) {
                totalCotisationsLabel.setText(String.valueOf(stat.getTotalCotisations()));
                totalBoufferLabel.setText(String.valueOf(stat.getTotalBouffer()));
                nombrePenaliteLabel.setText(String.valueOf(stat.getNombrePenalite()));
                totalPenaliteLabel.setText(String.valueOf(stat.getTotalPenalite()));
                nombrePropLabel.setText(String.valueOf(stat.getNombreProp()));
            } else {
                JOptionPane.showMessageDialog(this, "Aucune statistique trouvée!");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erreur: " + e.getMessage());
        }
    }
}

