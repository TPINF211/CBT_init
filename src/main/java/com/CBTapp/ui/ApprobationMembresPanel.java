package com.CBTapp.ui;

import com.CBTapp.models.Membre;
import com.CBTapp.models.UserSession;
import com.CBTapp.services.MembreService;
import com.CBTapp.ui.components.CustomButton;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class ApprobationMembresPanel extends JPanel {
    private static final Color BLUE_COLOR = new Color(70, 130, 180);
    private static final Color BEIGE_COLOR = new Color(245, 245, 220);
    
    private MembreService membreService;
    private JTable table;
    private DefaultTableModel tableModel;
    private JComboBox<String> roleCombo;
    
    public ApprobationMembresPanel() {
        membreService = new MembreService();
        setLayout(new BorderLayout());
        setBackground(BEIGE_COLOR);
        
        // Panel d'info
        JPanel infoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        infoPanel.setBackground(BEIGE_COLOR);
        JLabel infoLabel = new JLabel("Approuver les membres après vote du bureau (Président, Censeur, Secrétaire)");
        infoLabel.setForeground(BLUE_COLOR);
        infoPanel.add(infoLabel);
        
        // Panel de contrôle
        JPanel controlPanel = new JPanel(new FlowLayout());
        controlPanel.setBackground(BEIGE_COLOR);
        roleCombo = new JComboBox<>(new String[]{"Membre", "President", "Secretaire", "Censeur", "Gestionnaire"});
        CustomButton approveBtn = new CustomButton("Approuver/Modifier Rôle");
        CustomButton refreshBtn = new CustomButton("Actualiser");
        
        approveBtn.addActionListener(e -> approuverMembre());
        refreshBtn.addActionListener(e -> actualiserTable());
        
        controlPanel.add(new JLabel("Rôle:"));
        controlPanel.add(roleCombo);
        controlPanel.add(approveBtn);
        controlPanel.add(refreshBtn);
        
        // Table des membres
        String[] columns = {"ID", "Nom", "Email", "Rôle", "Date Inscription"};
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
    
    private void approuverMembre() {
        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Sélectionnez un membre!");
            return;
        }
        try {
            int id = (Integer) tableModel.getValueAt(row, 0);
            String newRole = (String) roleCombo.getSelectedItem();
            int result = membreService.updateMembreRole(id, newRole);
            if (result > 0) {
                JOptionPane.showMessageDialog(this, "Rôle mis à jour!");
                actualiserTable();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erreur: " + e.getMessage());
        }
    }
    
    private void actualiserTable() {
        try {
            tableModel.setRowCount(0);
            List<Membre> membres = membreService.getAllMembres();
            for (Membre m : membres) {
                String roleStr = m.getRole().toString();
                tableModel.addRow(new Object[]{
                    m.getIdMembre(),
                    m.getNom(),
                    m.getEmail(),
                    roleStr,
                    m.getDateInscription()
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erreur: " + e.getMessage());
        }
    }
}

