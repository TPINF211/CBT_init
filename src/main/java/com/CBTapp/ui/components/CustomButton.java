package com.CBTapp.ui.components;

import javax.swing.*;
import java.awt.*;

public class CustomButton extends JButton {
    private static final Color BLUE_COLOR = new Color(70, 130, 180);
    private static final Color BLUE_HOVER = new Color(100, 149, 237);
    
    public CustomButton(String text) {
        super(text);
        setBackground(BLUE_COLOR);
        setForeground(Color.WHITE);
        setFocusPainted(false);
        setBorderPainted(false);
        setOpaque(true);
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                setBackground(BLUE_HOVER);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                setBackground(BLUE_COLOR);
            }
        });
    }
}

