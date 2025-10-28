package org.example;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
/**
 * MenuListener Listens for clicks in the menu and selected files.
 *
 * @author asalaz41, Andrea Salazar Santos
 * @version 1
 */
public class MenuListener implements ActionListener, MouseListener {
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if(command == "Open"){
            FileUtility.folderSelected();
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        Object source = e.getSource();
        if(source instanceof JList){
            JList<String> files = (JList<String>)source;
            int index = files.locationToIndex(e.getPoint());
            FileUtility.fileSelected(index);
        }
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
