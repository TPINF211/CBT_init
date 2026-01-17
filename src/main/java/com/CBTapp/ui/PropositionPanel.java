package com.CBTapp.ui;

import com.CBTapp.models.Proposition;
import com.CBTapp.services.PropositionService;
import com.CBTapp.ui.components.CustomButton;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class PropositionPanel extends JPanel {
    private static final Color BLUE_COLOR = new Color(70, 130, 180);
    private static final Color BEIGE_COLOR = new Color(245, 245, 220);
    
    private PropositionService propositionService;
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField nomField, descriptionField;
    private JComboBox<String> statusCombo;
    private com.CBTapp.models.UserSession session;
    
    public PropositionPanel() {
        propositionService = new PropositionService();
        session = com.CBTapp.models.UserSession.getInstance();
        setLayout(new BorderLayout());
        setBackground(BEIGE_COLOR);
        
        // Panel de formulaire
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(BEIGE_COLOR);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("Nom:"), gbc);
        gbc.gridx = 1;
        nomField = new JTextField(20);
        formPanel.add(nomField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(new JLabel("Description:"), gbc);
        gbc.gridx = 1;
        descriptionField = new JTextField(20);
        formPanel.add(descriptionField, gbc);
        
        // Status combo seulement pour les membres qui voient leurs propres propositions
        if (session.isLoggedIn() && !session.isPresident()) {
            gbc.gridx = 0; gbc.gridy = 2;
            formPanel.add(new JLabel("Status:"), gbc);
            gbc.gridx = 1;
            statusCombo = new JComboBox<>(new String[]{"En_Examen", "Approuvee", "Rejetee"});
            statusCombo.setEnabled(false); // Les membres ne peuvent pas changer le status
            formPanel.add(statusCombo, gbc);
        }
        
        // Boutons
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(BEIGE_COLOR);
        CustomButton addBtn = new CustomButton("Ajouter");
        CustomButton deleteBtn = new CustomButton("Supprimer");
        CustomButton updateBtn = new CustomButton("Mettre à jour Status");
        CustomButton refreshBtn = new CustomButton("Actualiser");
        
        addBtn.addActionListener(e -> ajouterProposition());
        deleteBtn.addActionListener(e -> supprimerProposition());
        updateBtn.addActionListener(e -> mettreAJourStatus());
        refreshBtn.addActionListener(e -> actualiserTable());
        
        buttonPanel.add(addBtn);
        buttonPanel.add(deleteBtn);
        buttonPanel.add(updateBtn);
        buttonPanel.add(refreshBtn);
        
        // Table
        String[] columns = {"ID", "Nom", "Membre", "Description", "Date", "Status"};
        tableModel = new DefaultTableModel(columns, 0);
        table = new JTable(tableModel);
        table.setBackground(Color.WHITE);
        table.setSelectionBackground(BLUE_COLOR);
        JScrollPane scrollPane = new JScrollPane(table);
        
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(BEIGE_COLOR);
        topPanel.add(formPanel, BorderLayout.CENTER);
        topPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        
        actualiserTable();
    }
    
    private void ajouterProposition() {
        if (!session.isLoggedIn()) {
            JOptionPane.showMessageDialog(this, "Vous devez être connecté pour créer une proposition!");
            return;
        }
        
        try {
            String nom = nomField.getText();
            if (nom.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Le nom est requis!");
                return;
            }
            String description = descriptionField.getText();
            int idMembre = session.getCurrentUser().getIdMembre();
            
            int result = propositionService.createProposition(nom, idMembre, description);
            if (result > 0) {
                JOptionPane.showMessageDialog(this, "Proposition ajoutée avec succès!");
                actualiserTable();
                nomField.setText("");
                descriptionField.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "Erreur lors de l'ajout!");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erreur: " + e.getMessage());
        }
    }
    
    private void supprimerProposition() {
        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Sélectionnez une proposition!");
            return;
        }
        try {
            int id = (Integer) tableModel.getValueAt(row, 0);
            int result = propositionService.deleteProposition(id);
            if (result > 0) {
                JOptionPane.showMessageDialog(this, "Proposition supprimée!");
                actualiserTable();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erreur: " + e.getMessage());
        }
    }
    
    private void mettreAJourStatus() {
        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Sélectionnez une proposition!");
            return;
        }
        try {
            int id = (Integer) tableModel.getValueAt(row, 0);
            String status = (String) statusCombo.getSelectedItem();
            int result = propositionService.updatePropositionStatus(id, status);
            if (result > 0) {
                JOptionPane.showMessageDialog(this, "Status mis à jour!");
                actualiserTable();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erreur: " + e.getMessage());
        }
    }
    
    private void actualiserTable() {
        try {
            tableModel.setRowCount(0);
            List<Proposition> propositions = propositionService.getAllPropositions();
            
            // Filtrer pour les membres: seulement leurs propositions
            if (session.isLoggedIn() && session.isMembre() && !session.isBureau()) {
                int currentUserId = session.getCurrentUser().getIdMembre();
                propositions.removeIf(p -> p.getIdMembre() != currentUserId);
            }
            
            for (Proposition p : propositions) {
                tableModel.addRow(new Object[]{
                    p.getIdProposition(),
                    p.getNom(),
                    p.getNomMembre(),
                    p.getDescription(),
                    p.getDate(),
                    p.getStatus()
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erreur: " + e.getMessage());
        }
    }
}


