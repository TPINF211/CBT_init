package com.CBTapp;

import com.CBTapp.db.DatabaseConnection;
import com.CBTapp.ui.MainFrame;
import javax.swing.SwingUtilities;
import java.util.logging.Logger;

/**
 * Main entry point for the CBT Application
 */
public class Main {
    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());
    
    public static void main(String[] args) {
        // Test database connection
        LOGGER.info("Starting CBT Application...");
        
        if (!DatabaseConnection.testConnection()) {
            LOGGER.severe("Database connection failed. Please check your configuration.");
            System.exit(1);
        }
        
        LOGGER.info("Database connection successful!");
        
        // Launch GUI on Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            try {
                new MainFrame().setVisible(true);
                LOGGER.info("Application started successfully");
            } catch (Exception e) {
                LOGGER.severe("Failed to start application: " + e.getMessage());
                e.printStackTrace();
                System.exit(1);
            }
        });
    }
}

