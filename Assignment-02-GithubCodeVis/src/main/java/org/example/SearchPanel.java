package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class SearchPanel extends JPanel implements ActionListener {
    public SearchPanel(){
        setBackground(Color.CYAN);

        JTextField field = new JTextField("GitHub Folder URL");
        field.setForeground(Color.GRAY);
        field.setPreferredSize(new Dimension(600,50));

        JButton button = new JButton("OK");

        button.addActionListener(this);
        add(field);
        add(button);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("OK")){
            System.out.println("OK Clicked");
        }
    }
}
