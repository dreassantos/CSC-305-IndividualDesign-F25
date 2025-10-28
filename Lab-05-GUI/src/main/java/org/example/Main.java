package org.example;

import javax.swing.*;
import java.awt.*;

public class Main extends JFrame {
    static void main() {
        Main frame = new Main();
        frame.setTitle("Title");
        frame.setSize(800,800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
    public Main(){
        BorderLayout layout = new BorderLayout();
        MyMenu myMenu = new MyMenu();
        SidePanel sidePanel = new SidePanel();
        FooterPanel footerPanel = new FooterPanel();
        InfoPanel infoPanel = new InfoPanel();

        setLayout(layout);
        setJMenuBar(myMenu);

        add(infoPanel, BorderLayout.CENTER);
        add(sidePanel, BorderLayout.WEST);
        add(footerPanel, BorderLayout.SOUTH);
    }
}
