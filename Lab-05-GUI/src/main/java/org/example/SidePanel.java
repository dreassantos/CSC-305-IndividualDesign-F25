package org.example;

import javax.swing.*;
import java.awt.*;

public class SidePanel extends JPanel {

    public SidePanel(){
        GridLayout panelLayout = new GridLayout(10,1);
        setLayout(panelLayout);

        JLabel label01 = new JLabel("File 1");
        JLabel label02 = new JLabel("File 2");
        JLabel label03 = new JLabel("File 3");

        add(label01);
        add(label02);
        add(label03);
    }
}
