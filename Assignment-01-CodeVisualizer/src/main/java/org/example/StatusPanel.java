package org.example;

import javax.swing.*;
import java.awt.*;

/**
 * StatusPanel creates a panel with a label.
 * @author asalaz41, Andrea Salazar Santos
 * @version 1
 */
public class StatusPanel extends JPanel {

    public StatusPanel() {
        setBackground(Color.decode("#eacbf5"));
        JLabel statusLabel = new JLabel("Status Bar");
        statusLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(statusLabel);
    }
}
