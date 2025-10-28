package org.example;

import javax.swing.*;
import java.awt.*;

/***
 * Main creates a frame and adds a menu and three panels.
 *
 * @author asalaz41, Andrea Santos
 * @version 1
 */
public class Main extends JFrame {

    public static void main(String[] args) {
        JFrame frame = new Main();
        frame.setSize(800, 600);
        frame.setMinimumSize(new Dimension(800,600));
        frame.setTitle("Assignment 01");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public Main() {
        setLayout(new BorderLayout());
        JMenuBar menuBar = new Menu();
        setJMenuBar(menuBar);

        JPanel filePanel = new FilePanel();
        JPanel drawPanel = new DrawPanel();
        JPanel statusPanel = new StatusPanel();

        FileUtility.SetDrawPanel(drawPanel);
        FileUtility.SetFilePanel(filePanel);

        add(filePanel, BorderLayout.WEST);
        add(drawPanel, BorderLayout.CENTER);
        add(statusPanel, BorderLayout.SOUTH);
    }
}
