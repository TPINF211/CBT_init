package com.CBTapp.ui;

import com.CBTapp.models.Proposition;
import com.CBTapp.models.UserSession;
import com.CBTapp.services.PropositionService;
import com.CBTapp.services.VoteService;
import com.CBTapp.ui.components.CustomButton;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class VoteCreationPanel extends JPanel {
    private static final Color BLUE_COLOR = new Color(70, 130, 180);
    private static final Color BEIGE_COLOR = new Color(245, 245, 220);
    
    private PropositionService propositionService;
    private VoteService voteService;
    private JTable table;
    private DefaultTableModel tableModel;
    private JComboBox<String> statusCombo;
    
    public VoteCreationPanel() {
        propositionService = new PropositionService();
        voteService = new VoteService();
        setLayout(new BorderLayout());
        setBackground(BEIGE_COLOR);
        
        // Panel d'info
        JPanel infoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        infoPanel.setBackground(BEIGE_COLOR);
        JLabel infoLabel = new JLabel("En tant que Président, vous pouvez créer des votes pour les propositions.");
        infoLabel.setForeground(BLUE_COLOR);
        infoPanel.add(infoLabel);
        
        // Panel de contrôle
        JPanel controlPanel = new JPanel(new FlowLayout());
        controlPanel.setBackground(BEIGE_COLOR);
        statusCombo = new JComboBox<>(new String[]{"En_Examen", "Approuvee", "Rejetee"});
        CustomButton createVoteBtn = new CustomButton("Créer Vote pour Proposition");
        CustomButton updateStatusBtn = new CustomButton("Mettre à jour Status");
        CustomButton refreshBtn = new CustomButton("Actualiser");
        
        createVoteBtn.addActionListener(e -> creerVote());
        updateStatusBtn.addActionListener(e -> mettreAJourStatus());
        refreshBtn.addActionListener(e -> actualiserTable());
        
        controlPanel.add(new JLabel("Status:"));
        controlPanel.add(statusCombo);
        controlPanel.add(createVoteBtn);
        controlPanel.add(updateStatusBtn);
        controlPanel.add(refreshBtn);
        
        // Table des propositions
        String[] columns = {"ID", "Nom", "Membre", "Description", "Date", "Status"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(tableModel);
        table.setBackground(Color.WHITE);
        table.setSelectionBackground(BLUE_COLOR);
        JScrollPane scrollPane = new JScrollPane(table);
        
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(BEIGE_COLOR);
        topPanel.add(infoPanel, BorderLayout.NORTH);
        topPanel.add(controlPanel, BorderLayout.SOUTH);
        
        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        
        actualiserTable();
    }
    
    private void creerVote() {
        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Sélectionnez une proposition!");
            return;
        }
        
        int idProposition = (Integer) tableModel.getValueAt(row, 0);
        String nomProposition = (String) tableModel.getValueAt(row, 1);
        
        int option = JOptionPane.showConfirmDialog(
            this,
            "Créer un vote pour la proposition: " + nomProposition + "?",
            "Créer Vote",
            JOptionPane.YES_NO_OPTION
        );
        
        if (option == JOptionPane.YES_OPTION) {
            // Le vote sera créé par les membres individuellement
            // Ici on peut juste changer le status de la proposition
            JOptionPane.showMessageDialog(
                this,
                "Les membres peuvent maintenant voter pour cette proposition.\n" +
                "Utilisez 'Mettre à jour Status' pour approuver après le vote du bureau."
            );
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

