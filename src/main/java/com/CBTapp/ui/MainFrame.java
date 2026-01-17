package com.CBTapp.ui;

import com.CBTapp.models.UserSession;
import com.CBTapp.services.AuthService;
import com.CBTapp.ui.components.CustomButton;
import javax.swing.*;
import java.awt.*;

/**
 * Main application window
 */
public class MainFrame extends JFrame {
    private static final Color BLUE_COLOR = new Color(70, 130, 180);
    private static final Color BEIGE_COLOR = new Color(245, 245, 220);
    
    private JPanel mainPanel;
    private AuthService authService;
    private JLabel userInfoLabel;
    
    public MainFrame() {
        authService = new AuthService();
        initializeFrame();
        showLogin();
    }
    
    private void initializeFrame() {
        setTitle("CBT Application - Gestion de Tontine");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 800);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        
        // Apply blue and beige theme
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            UIManager.put("Panel.background", BEIGE_COLOR);
            UIManager.put("TabbedPane.background", BEIGE_COLOR);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void showLogin() {
        getContentPane().removeAll();
        
        LoginPanel loginPanel = new LoginPanel(() -> {
            showMainInterface();
        });
        
        add(loginPanel, BorderLayout.CENTER);
        revalidate();
        repaint();
    }
    
    private void showMainInterface() {
        getContentPane().removeAll();
        
        // Create menu bar
        JMenuBar menuBar = new JMenuBar();
        menuBar.setBackground(BEIGE_COLOR);
        
        JMenu fileMenu = new JMenu("Fichier");
        JMenuItem logoutItem = new JMenuItem("Déconnexion");
        logoutItem.addActionListener(e -> {
            authService.logout();
            showLogin();
        });
        JMenuItem exitItem = new JMenuItem("Quitter");
        exitItem.addActionListener(e -> System.exit(0));
        fileMenu.add(logoutItem);
        fileMenu.addSeparator();
        fileMenu.add(exitItem);
        
        JMenu helpMenu = new JMenu("Aide");
        JMenuItem aboutItem = new JMenuItem("À propos");
        aboutItem.addActionListener(e -> showAboutDialog());
        helpMenu.add(aboutItem);
        
        menuBar.add(fileMenu);
        menuBar.add(helpMenu);
        setJMenuBar(menuBar);
        
        // User info panel
        JPanel userPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        userPanel.setBackground(BEIGE_COLOR);
        userInfoLabel = new JLabel();
        updateUserInfo();
        userPanel.add(userInfoLabel);
        
        // Create main panel based on role
        mainPanel = new RoleBasedPanel();
        
        add(userPanel, BorderLayout.NORTH);
        add(mainPanel, BorderLayout.CENTER);
        
        revalidate();
        repaint();
    }
    
    private void updateUserInfo() {
        if (UserSession.getInstance().isLoggedIn()) {
            var user = UserSession.getInstance().getCurrentUser();
            String roleName = getRoleDisplayName(user.getRole());
            userInfoLabel.setText("Connecté en tant que: " + user.getNom() + " (" + roleName + ")");
            userInfoLabel.setForeground(BLUE_COLOR);
            userInfoLabel.setFont(new Font(userInfoLabel.getFont().getName(), Font.BOLD, 12));
        }
    }
    
    private String getRoleDisplayName(com.CBTapp.models.Membre.Role role) {
        switch (role) {
            case PRESIDENT: return "Président";
            case SECRETAIRE: return "Secrétaire";
            case CENSEUR: return "Censeur";
            case CASSIERE: return "Caissier";
            default: return "Membre";
        }
    }
    
    private void showAboutDialog() {
        JOptionPane.showMessageDialog(
            this,
            "Application CBT - Gestion de Tontine\nVersion 1.0.0\n\n" +
            "Système de gestion avec contrôle d'accès basé sur les rôles",
            "À propos",
            JOptionPane.INFORMATION_MESSAGE
        );
    }
}

