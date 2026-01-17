package com.CBTapp;

import com.CBTapp.db.DatabaseConnection;
import com.CBTapp.ui.MainFrame;
import javax.swing.*;
import java.util.logging.Logger;

/**
 * Main entry point for the CBT Application
 */
public class Main {
    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());
    
    public static void main(String[] args) {
        // Test database connection
        LOGGER.info("Starting CBT Application...");
        
        // Launch GUI on Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            try {
                // Test connection in background
                boolean connected = DatabaseConnection.testConnection();
                
                if (!connected) {
                    int option = JOptionPane.showConfirmDialog(
                        null,
                        "La connexion à la base de données a échoué.\n\n" +
                        "Vérifiez que:\n" +
                        "1. MySQL est démarré\n" +
                        "2. La base de données 'cbt_db' existe\n" +
                        "3. Les identifiants dans config.properties sont corrects\n\n" +
                        "Voulez-vous continuer quand même? (L'application ne fonctionnera pas correctement)",
                        "Erreur de connexion",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE
                    );
                    
                    if (option != JOptionPane.YES_OPTION) {
                        System.exit(0);
                        return;
                    }
                } else {
                    LOGGER.info("Database connection successful!");
                }
                
                MainFrame frame = new MainFrame();
                frame.setVisible(true);
                LOGGER.info("Application started successfully");
                
            } catch (Exception e) {
                LOGGER.severe("Failed to start application: " + e.getMessage());
                e.printStackTrace();
                JOptionPane.showMessageDialog(
                    null,
                    "Erreur lors du démarrage de l'application:\n" + e.getMessage(),
                    "Erreur",
                    JOptionPane.ERROR_MESSAGE
                );
                System.exit(1);
            }
        });
    }
}

