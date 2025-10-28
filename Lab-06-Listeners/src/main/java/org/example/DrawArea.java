package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/***
 * Panel used for drawing, When mouse is clicked a dialogue box displays the x and y coordinates.
 */
public class DrawArea extends JPanel implements MouseListener {

    public DrawArea() {
        addMouseListener(this);
        setBorder(BorderFactory.createLineBorder(Color.lightGray, 2));
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        String msg = "("+ e.getX()+ ","+ e.getY()+ ")   ";
        JOptionPane.showMessageDialog(this, msg);
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
