package com.CBTapp.ui;

import com.CBTapp.models.MembreRetard;
import com.CBTapp.services.StatsService;
import com.CBTapp.ui.components.CustomButton;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class MembreRetardPanel extends JPanel {
    private static final Color BLUE_COLOR = new Color(70, 130, 180);
    private static final Color BEIGE_COLOR = new Color(245, 245, 220);
    
    private StatsService statsService;
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField groupeIdField;
    
    public MembreRetardPanel() {
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
        
        CustomButton searchBtn = new CustomButton("Rechercher");
        searchBtn.addActionListener(e -> rechercherMembres());
        gbc.gridx = 2;
        formPanel.add(searchBtn, gbc);
        
        // Table
        String[] columns = {"ID Membre", "Nom", "Email", "Cotisations Manquantes", "Jours depuis dernière séance"};
        tableModel = new DefaultTableModel(columns, 0);
        table = new JTable(tableModel);
        table.setBackground(Color.WHITE);
        table.setSelectionBackground(BLUE_COLOR);
        JScrollPane scrollPane = new JScrollPane(table);
        
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(BEIGE_COLOR);
        topPanel.add(formPanel, BorderLayout.NORTH);
        
        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }
    
    private void rechercherMembres() {
        try {
            int groupeId = Integer.parseInt(groupeIdField.getText());
            tableModel.setRowCount(0);
            List<MembreRetard> membres = statsService.obtenirMembresEnRetard(groupeId);
            for (MembreRetard m : membres) {
                tableModel.addRow(new Object[]{
                    m.getIdMembre(),
                    m.getNom(),
                    m.getEmail(),
                    m.getCotisationsManquantes(),
                    m.getJoursDepuisDerniereSeance()
                });
            }
            if (membres.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Aucun membre en retard trouvé!");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erreur: " + e.getMessage());
        }
    }
}

