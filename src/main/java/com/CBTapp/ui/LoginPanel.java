package com.CBTapp.ui;

import com.CBTapp.services.AuthService;
import com.CBTapp.ui.components.CustomButton;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class LoginPanel extends JPanel {
    private static final Color BLUE_COLOR = new Color(70, 130, 180);
    private static final Color BEIGE_COLOR = new Color(245, 245, 220);
    
    private AuthService authService;
    private JTextField emailField;
    private JPasswordField passwordField;
    private JLabel statusLabel;
    private LoginCallback callback;
    
    public interface LoginCallback {
        void onLoginSuccess();
    }
    
    public LoginPanel(LoginCallback callback) {
        this.authService = new AuthService();
        this.callback = callback;
        setLayout(new BorderLayout());
        setBackground(BEIGE_COLOR);
        
        // Panel principal centré
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setBackground(BEIGE_COLOR);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        
        // Titre
        JLabel titleLabel = new JLabel("Connexion - Application CBT");
        titleLabel.setFont(new Font(titleLabel.getFont().getName(), Font.BOLD, 18));
        titleLabel.setForeground(BLUE_COLOR);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        centerPanel.add(titleLabel, gbc);
        
        // Email
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.gridy = 1;
        gbc.gridx = 0;
        centerPanel.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        emailField = new JTextField(20);
        centerPanel.add(emailField, gbc);
        
        // Mot de passe
        gbc.gridy = 2;
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        centerPanel.add(new JLabel("Mot de passe:"), gbc);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        passwordField = new JPasswordField(20);
        centerPanel.add(passwordField, gbc);
        
        // Bouton de connexion
        gbc.gridy = 3;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        CustomButton loginButton = new CustomButton("Se connecter");
        loginButton.addActionListener(e -> performLogin());
        centerPanel.add(loginButton, gbc);
        
        // Status label
        gbc.gridy = 4;
        statusLabel = new JLabel(" ");
        statusLabel.setForeground(Color.RED);
        centerPanel.add(statusLabel, gbc);
        
        // Ajouter un listener pour Enter
        KeyListener enterListener = new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    performLogin();
                }
            }
            
            @Override
            public void keyReleased(KeyEvent e) {}
        };
        
        emailField.addKeyListener(enterListener);
        passwordField.addKeyListener(enterListener);
        
        add(centerPanel, BorderLayout.CENTER);
    }
    
    private void performLogin() {
        String email = emailField.getText().trim();
        String password = new String(passwordField.getPassword());
        
        if (email.isEmpty() || password.isEmpty()) {
            statusLabel.setText("Veuillez remplir tous les champs");
            return;
        }
        
        if (authService.login(email, password)) {
            statusLabel.setText("Connexion réussie!");
            statusLabel.setForeground(new Color(0, 150, 0));
            SwingUtilities.invokeLater(() -> {
                try {
                    Thread.sleep(500);
                    if (callback != null) {
                        callback.onLoginSuccess();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        } else {
            statusLabel.setText("Email ou mot de passe incorrect");
            statusLabel.setForeground(Color.RED);
            passwordField.setText("");
        }
    }
}

