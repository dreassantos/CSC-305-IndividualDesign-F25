package org.example;

import javax.swing.*;
import java.awt.*;

public class InfoPanel extends JPanel {
    public InfoPanel() {

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawComplexity(g);
        drawSize(g);
        drawOverall(g);
    }

    private void drawComplexity(Graphics g) {
        g.fillRect(100,200,150,400);
        g.drawString("Complexity",100,100);
    }

    private void drawSize(Graphics g) {
        g.fillRect(300,200,150,400);
        g.drawString("Size",300,100);
    }

    private void drawOverall(Graphics g) {
        g.fillRect(500, 300,150,150);
        g.drawString("Overall",500,100);
    }
}
