package com.CBTapp.ui;

import com.CBTapp.dao.CotisationDAO;
import com.CBTapp.models.Cotisation;
import com.CBTapp.ui.components.CustomButton;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class CotisationsPanel extends JPanel {
    private static final Color BLUE_COLOR = new Color(70, 130, 180);
    private static final Color BEIGE_COLOR = new Color(245, 245, 220);
    
    private CotisationDAO cotisationDAO;
    private JTable table;
    private DefaultTableModel tableModel;
    
    public CotisationsPanel() {
        cotisationDAO = new CotisationDAO();
        setLayout(new BorderLayout());
        setBackground(BEIGE_COLOR);
        
        // Panel d'info
        JPanel infoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        infoPanel.setBackground(BEIGE_COLOR);
        JLabel infoLabel = new JLabel("En tant que Caissier, vous gérez les cotisations");
        infoLabel.setForeground(BLUE_COLOR);
        infoPanel.add(infoLabel);
        
        // Panel de contrôle
        JPanel controlPanel = new JPanel(new FlowLayout());
        controlPanel.setBackground(BEIGE_COLOR);
        CustomButton refreshBtn = new CustomButton("Actualiser");
        refreshBtn.addActionListener(e -> actualiserTable());
        controlPanel.add(refreshBtn);
        
        // Table
        String[] columns = {"ID", "ID Séance", "ID Membre", "Montant", "Type", "Parts", "Date"};
        tableModel = new DefaultTableModel(columns, 0);
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
    
    private void actualiserTable() {
        try {
            tableModel.setRowCount(0);
            List<Cotisation> cotisations = cotisationDAO.getAll();
            for (Cotisation c : cotisations) {
                tableModel.addRow(new Object[]{
                    c.getIdCotisation(),
                    c.getIdSeance(),
                    c.getIdMembre(),
                    c.getMontant(),
                    c.getType() != null ? c.getType().toString() : "",
                    c.getParts(),
                    c.getDate()
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erreur: " + e.getMessage());
        }
    }
}

