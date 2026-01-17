package com.CBTapp.ui;

import com.CBTapp.models.VoteResults;
import com.CBTapp.services.VoteService;
import com.CBTapp.ui.components.CustomButton;
import javax.swing.*;
import java.awt.*;

public class VoteResultsPanel extends JPanel {
    private static final Color BLUE_COLOR = new Color(70, 130, 180);
    private static final Color BEIGE_COLOR = new Color(245, 245, 220);
    
    private VoteService voteService;
    private JTextField idPropositionField;
    private JLabel votesOuiLabel, votesNonLabel, totalVotesLabel, pourcentageOuiLabel;
    
    public VoteResultsPanel() {
        voteService = new VoteService();
        setLayout(new BorderLayout());
        setBackground(BEIGE_COLOR);
        
        // Panel de formulaire
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(BEIGE_COLOR);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("ID Proposition:"), gbc);
        gbc.gridx = 1;
        idPropositionField = new JTextField(20);
        formPanel.add(idPropositionField, gbc);
        
        CustomButton searchBtn = new CustomButton("Obtenir Résultats");
        searchBtn.addActionListener(e -> obtenirResultats());
        gbc.gridx = 2;
        formPanel.add(searchBtn, gbc);
        
        // Panel de résultats
        JPanel resultPanel = new JPanel(new GridBagLayout());
        resultPanel.setBackground(BEIGE_COLOR);
        resultPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(BLUE_COLOR, 2), "Résultats des Votes"));
        GridBagConstraints gbc2 = new GridBagConstraints();
        gbc2.insets = new Insets(5, 5, 5, 5);
        gbc2.anchor = GridBagConstraints.WEST;
        
        gbc2.gridx = 0; gbc2.gridy = 0;
        resultPanel.add(new JLabel("Votes Oui:"), gbc2);
        gbc2.gridx = 1;
        votesOuiLabel = new JLabel("0");
        resultPanel.add(votesOuiLabel, gbc2);
        
        gbc2.gridx = 0; gbc2.gridy = 1;
        resultPanel.add(new JLabel("Votes Non:"), gbc2);
        gbc2.gridx = 1;
        votesNonLabel = new JLabel("0");
        resultPanel.add(votesNonLabel, gbc2);
        
        gbc2.gridx = 0; gbc2.gridy = 2;
        resultPanel.add(new JLabel("Total Votes:"), gbc2);
        gbc2.gridx = 1;
        totalVotesLabel = new JLabel("0");
        resultPanel.add(totalVotesLabel, gbc2);
        
        gbc2.gridx = 0; gbc2.gridy = 3;
        JLabel pourcentageTitle = new JLabel("Pourcentage Oui:");
        pourcentageTitle.setFont(new Font(pourcentageTitle.getFont().getName(), Font.BOLD, 12));
        resultPanel.add(pourcentageTitle, gbc2);
        gbc2.gridx = 1;
        pourcentageOuiLabel = new JLabel("0.0%");
        pourcentageOuiLabel.setFont(new Font(pourcentageOuiLabel.getFont().getName(), Font.BOLD, 12));
        pourcentageOuiLabel.setForeground(BLUE_COLOR);
        resultPanel.add(pourcentageOuiLabel, gbc2);
        
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(BEIGE_COLOR);
        topPanel.add(formPanel, BorderLayout.NORTH);
        
        add(topPanel, BorderLayout.NORTH);
        add(resultPanel, BorderLayout.CENTER);
    }
    
    private void obtenirResultats() {
        try {
            int idProposition = Integer.parseInt(idPropositionField.getText());
            VoteResults results = voteService.getVoteResults(idProposition);
            if (results != null) {
                votesOuiLabel.setText(String.valueOf(results.getVotesOui()));
                votesNonLabel.setText(String.valueOf(results.getVotesNon()));
                totalVotesLabel.setText(String.valueOf(results.getTotalVotes()));
                pourcentageOuiLabel.setText(String.format("%.2f%%", results.getPourcentageOui()));
            } else {
                JOptionPane.showMessageDialog(this, "Aucun résultat trouvé!");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erreur: " + e.getMessage());
        }
    }
}

