package com.CBTapp.ui;

import com.CBTapp.models.Credit;
import com.CBTapp.services.CreditService;
import com.CBTapp.ui.components.CustomButton;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class CreditPanel extends JPanel {
    private static final Color BLUE_COLOR = new Color(70, 130, 180);
    private static final Color BEIGE_COLOR = new Color(245, 245, 220);
    
    private CreditService creditService;
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField idMembreField, montantField, interetField;
    private JComboBox<String> statusCombo;
    
    public CreditPanel() {
        creditService = new CreditService();
        setLayout(new BorderLayout());
        setBackground(BEIGE_COLOR);
        
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
        formPanel.add(new JLabel("Intérêt:"), gbc);
        gbc.gridx = 1;
        interetField = new JTextField(20);
        formPanel.add(interetField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 3;
        formPanel.add(new JLabel("Status:"), gbc);
        gbc.gridx = 1;
        statusCombo = new JComboBox<>(new String[]{"en_cours", "rembourse"});
        formPanel.add(statusCombo, gbc);
        
        // Boutons
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(BEIGE_COLOR);
        CustomButton addBtn = new CustomButton("Ajouter");
        CustomButton deleteBtn = new CustomButton("Supprimer");
        CustomButton updateBtn = new CustomButton("Mettre à jour Status");
        CustomButton refreshBtn = new CustomButton("Actualiser");
        
        addBtn.addActionListener(e -> ajouterCredit());
        deleteBtn.addActionListener(e -> supprimerCredit());
        updateBtn.addActionListener(e -> mettreAJourStatus());
        refreshBtn.addActionListener(e -> actualiserTable());
        
        buttonPanel.add(addBtn);
        buttonPanel.add(deleteBtn);
        buttonPanel.add(updateBtn);
        buttonPanel.add(refreshBtn);
        
        // Table
        String[] columns = {"ID", "ID Membre", "Montant", "Intérêt", "Status", "Date Demande", "Date Remboursement"};
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
    
    private void ajouterCredit() {
        try {
            int idMembre = Integer.parseInt(idMembreField.getText());
            double montant = Double.parseDouble(montantField.getText());
            double interet = Double.parseDouble(interetField.getText());
            
            int result = creditService.createCredit(idMembre, montant, interet);
            if (result > 0) {
                JOptionPane.showMessageDialog(this, "Crédit ajouté avec succès!");
                actualiserTable();
                idMembreField.setText("");
                montantField.setText("");
                interetField.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "Erreur lors de l'ajout!");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erreur: " + e.getMessage());
        }
    }
    
    private void supprimerCredit() {
        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Sélectionnez un crédit!");
            return;
        }
        try {
            int id = (Integer) tableModel.getValueAt(row, 0);
            int result = creditService.deleteCredit(id);
            if (result > 0) {
                JOptionPane.showMessageDialog(this, "Crédit supprimé!");
                actualiserTable();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erreur: " + e.getMessage());
        }
    }
    
    private void mettreAJourStatus() {
        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Sélectionnez un crédit!");
            return;
        }
        try {
            int id = (Integer) tableModel.getValueAt(row, 0);
            String status = (String) statusCombo.getSelectedItem();
            int result = creditService.updateCreditStatus(id, status);
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
            List<Credit> credits = creditService.getAllCredits();
            for (Credit c : credits) {
                tableModel.addRow(new Object[]{
                    c.getIdCredit(),
                    c.getIdMembre(),
                    c.getMontant(),
                    c.getInteret(),
                    c.getStatus() == Credit.StatusCredit.EN_COURS ? "en_cours" : "rembourse",
                    c.getDateDemande(),
                    c.getDateRemboursement()
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erreur: " + e.getMessage());
        }
    }
}


