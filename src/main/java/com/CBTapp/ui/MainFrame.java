package com.CBTapp.ui;

import com.CBTapp.ui.components.CustomButton;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Main application window
 */
public class MainFrame extends JFrame {
    private ClientPanel clientPanel;
    
    public MainFrame() {
        initializeFrame();
        createComponents();
        layoutComponents();
        setupEventHandlers();
    }
    
    private void initializeFrame() {
        setTitle("CBT Application");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 800);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        
        // Apply violet theme (based on user preference)
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeel());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void createComponents() {
        // Create menu bar
        JMenuBar menuBar = new JMenuBar();
        
        JMenu fileMenu = new JMenu("File");
        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(e -> System.exit(0));
        fileMenu.add(exitItem);
        
        JMenu helpMenu = new JMenu("Help");
        JMenuItem aboutItem = new JMenuItem("About");
        aboutItem.addActionListener(e -> showAboutDialog());
        helpMenu.add(aboutItem);
        
        menuBar.add(fileMenu);
        menuBar.add(helpMenu);
        setJMenuBar(menuBar);
        
        // Create main panel
        clientPanel = new ClientPanel();
        
        // Create toolbar
        JToolBar toolBar = new JToolBar();
        CustomButton refreshBtn = new CustomButton("Refresh");
        CustomButton addBtn = new CustomButton("Add");
        CustomButton editBtn = new CustomButton("Edit");
        CustomButton deleteBtn = new CustomButton("Delete");
        
        toolBar.add(refreshBtn);
        toolBar.addSeparator();
        toolBar.add(addBtn);
        toolBar.add(editBtn);
        toolBar.add(deleteBtn);
        
        add(toolBar, BorderLayout.NORTH);
        add(clientPanel, BorderLayout.CENTER);
        
        // Status bar
        JPanel statusPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel statusLabel = new JLabel("Ready");
        statusPanel.add(statusLabel);
        add(statusPanel, BorderLayout.SOUTH);
    }
    
    private void layoutComponents() {
        // Components are already laid out in createComponents
    }
    
    private void setupEventHandlers() {
        // Event handlers are set up in createComponents
    }
    
    private void showAboutDialog() {
        JOptionPane.showMessageDialog(
            this,
            "CBT Application\nVersion 1.0.0\n\nA Java SQL Application",
            "About",
            JOptionPane.INFORMATION_MESSAGE
        );
    }
}

