package com.CBTapp.ui;

import com.CBTapp.models.Vote;
import com.CBTapp.services.VoteService;
import com.CBTapp.ui.components.CustomButton;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class VotePanel extends JPanel {
    private static final Color BLUE_COLOR = new Color(70, 130, 180);
    private static final Color BEIGE_COLOR = new Color(245, 245, 220);
    
    private VoteService voteService;
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField idPropositionField, idMembreField;
    private JComboBox<String> choixCombo;
    
    public VotePanel() {
        voteService = new VoteService();
        setLayout(new BorderLayout());
        setBackground(BEIGE_COLOR);
        
        // Panel de formulaire
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(BEIGE_COLOR);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("ID Proposition:"), gbc);
        gbc.gridx = 1;
        idPropositionField = new JTextField(20);
        formPanel.add(idPropositionField, gbc);
        
        // ID Membre automatique si connecté
        if (com.CBTapp.models.UserSession.getInstance().isLoggedIn()) {
            int currentUserId = com.CBTapp.models.UserSession.getInstance().getCurrentUser().getIdMembre();
            gbc.gridx = 0; gbc.gridy = 1;
            formPanel.add(new JLabel("Votre ID (automatique):"), gbc);
            gbc.gridx = 1;
            JLabel idLabel = new JLabel(String.valueOf(currentUserId));
            idLabel.setForeground(BLUE_COLOR);
            formPanel.add(idLabel, gbc);
        } else {
            gbc.gridx = 0; gbc.gridy = 1;
            formPanel.add(new JLabel("ID Membre:"), gbc);
            gbc.gridx = 1;
            idMembreField = new JTextField(20);
            formPanel.add(idMembreField, gbc);
        }
        
        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(new JLabel("Choix:"), gbc);
        gbc.gridx = 1;
        choixCombo = new JComboBox<>(new String[]{"Oui", "Non"});
        formPanel.add(choixCombo, gbc);
        
        // Boutons
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(BEIGE_COLOR);
        CustomButton addBtn = new CustomButton("Créer Vote");
        CustomButton updateBtn = new CustomButton("Mettre à jour");
        CustomButton refreshBtn = new CustomButton("Actualiser");
        
        addBtn.addActionListener(e -> creerVote());
        updateBtn.addActionListener(e -> mettreAJourVote());
        refreshBtn.addActionListener(e -> actualiserTable());
        
        buttonPanel.add(addBtn);
        buttonPanel.add(updateBtn);
        buttonPanel.add(refreshBtn);
        
        // Table
        String[] columns = {"ID Vote", "ID Proposition", "ID Membre", "Choix", "Date"};
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
    
    private void creerVote() {
        if (!com.CBTapp.models.UserSession.getInstance().isLoggedIn()) {
            JOptionPane.showMessageDialog(this, "Vous devez être connecté pour voter!");
            return;
        }
        
        try {
            int idProposition = Integer.parseInt(idPropositionField.getText());
            int idMembre;
            if (com.CBTapp.models.UserSession.getInstance().isLoggedIn()) {
                idMembre = com.CBTapp.models.UserSession.getInstance().getCurrentUser().getIdMembre();
            } else {
                idMembre = Integer.parseInt(idMembreField.getText());
            }
            String choix = (String) choixCombo.getSelectedItem();
            
            int result = voteService.creerVote(idProposition, idMembre, choix);
            if (result > 0) {
                JOptionPane.showMessageDialog(this, "Vote créé avec succès!");
                actualiserTable();
                idPropositionField.setText("");
                if (idMembreField != null) {
                    idMembreField.setText("");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Erreur: Vous avez peut-être déjà voté pour cette proposition!");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erreur: " + e.getMessage());
        }
    }
    
    private void mettreAJourVote() {
        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Sélectionnez un vote!");
            return;
        }
        try {
            int id = (Integer) tableModel.getValueAt(row, 0);
            String choix = (String) choixCombo.getSelectedItem();
            int result = voteService.updateVote(id, choix);
            if (result > 0) {
                JOptionPane.showMessageDialog(this, "Vote mis à jour!");
                actualiserTable();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erreur: " + e.getMessage());
        }
    }
    
    private void actualiserTable() {
        try {
            tableModel.setRowCount(0);
            List<Vote> votes = voteService.getAllVotes();
            for (Vote v : votes) {
                tableModel.addRow(new Object[]{
                    v.getId_vote(),
                    v.getId_prop(),
                    v.getId_membre(),
                    v.getChoix().getDisplayName(),
                    v.getDate()
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erreur: " + e.getMessage());
        }
    }
}


