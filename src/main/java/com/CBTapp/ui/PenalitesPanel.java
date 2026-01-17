package com.CBTapp.ui;

import com.CBTapp.dao.PenaliteDAO;
import com.CBTapp.models.Penalite;
import com.CBTapp.ui.components.CustomButton;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class PenalitesPanel extends JPanel {
    private static final Color BLUE_COLOR = new Color(70, 130, 180);
    private static final Color BEIGE_COLOR = new Color(245, 245, 220);
    
    private PenaliteDAO penaliteDAO;
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField idMembreField, montantField, raisonField;
    
    public PenalitesPanel() {
        penaliteDAO = new PenaliteDAO();
        setLayout(new BorderLayout());
        setBackground(BEIGE_COLOR);
        
        // Panel d'info
        JPanel infoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        infoPanel.setBackground(BEIGE_COLOR);
        JLabel infoLabel = new JLabel("En tant que Censeur, vous pouvez créer et gérer les pénalités");
        infoLabel.setForeground(BLUE_COLOR);
        infoPanel.add(infoLabel);
        
        // Panel de formulaire
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(BEIGE_COLOR);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("ID Membre:"), gbc);
        gbc.gridx = 1;
        idMembreField = new JTextField(20);
        formPanel.add(idMembreField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(new JLabel("Montant:"), gbc);
        gbc.gridx = 1;
        montantField = new JTextField(20);
        formPanel.add(montantField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(new JLabel("Raison:"), gbc);
        gbc.gridx = 1;
        raisonField = new JTextField(20);
        formPanel.add(raisonField, gbc);
        
        // Boutons
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(BEIGE_COLOR);
        CustomButton addBtn = new CustomButton("Ajouter Pénalité");
        CustomButton deleteBtn = new CustomButton("Supprimer");
        CustomButton refreshBtn = new CustomButton("Actualiser");
        
        addBtn.addActionListener(e -> ajouterPenalite());
        deleteBtn.addActionListener(e -> supprimerPenalite());
        refreshBtn.addActionListener(e -> actualiserTable());
        
        buttonPanel.add(addBtn);
        buttonPanel.add(deleteBtn);
        buttonPanel.add(refreshBtn);
        
        // Table
        String[] columns = {"ID", "ID Membre", "Montant", "Raison", "Date"};
        tableModel = new DefaultTableModel(columns, 0);
        table = new JTable(tableModel);
        table.setBackground(Color.WHITE);
        table.setSelectionBackground(BLUE_COLOR);
        JScrollPane scrollPane = new JScrollPane(table);
        
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(BEIGE_COLOR);
        topPanel.add(infoPanel, BorderLayout.NORTH);
        topPanel.add(formPanel, BorderLayout.CENTER);
        topPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        
        actualiserTable();
    }
    
    private void ajouterPenalite() {
        try {
            int idMembre = Integer.parseInt(idMembreField.getText());
            double montant = Double.parseDouble(montantField.getText());
            String raison = raisonField.getText();
            
            int result = penaliteDAO.add(idMembre, montant, raison);
            if (result > 0) {
                JOptionPane.showMessageDialog(this, "Pénalité ajoutée avec succès!");
                actualiserTable();
                idMembreField.setText("");
                montantField.setText("");
                raisonField.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "Erreur lors de l'ajout!");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erreur: " + e.getMessage());
        }
    }
    
    private void supprimerPenalite() {
        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Sélectionnez une pénalité!");
            return;
        }
        try {
            int id = (Integer) tableModel.getValueAt(row, 0);
            int result = penaliteDAO.delete(id);
            if (result > 0) {
                JOptionPane.showMessageDialog(this, "Pénalité supprimée!");
                actualiserTable();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erreur: " + e.getMessage());
        }
    }
    
    private void actualiserTable() {
        try {
            tableModel.setRowCount(0);
            List<Penalite> penalites = penaliteDAO.getAll();
            for (Penalite p : penalites) {
                tableModel.addRow(new Object[]{
                    p.getIdPenalite(),
                    p.getIdMembre(),
                    p.getMontant(),
                    p.getRaison(),
                    p.getDate()
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erreur: " + e.getMessage());
        }
    }
}

