package org.example;

import javax.swing.*;
import java.awt.*;

/***
 * Main creates a frame and adds three panels to it.
 *
 * @author asalaz41, Andrea Santos
 */
public class Main extends JFrame {
    static void main() {
        Main frame = new Main();
        frame.setSize(800,600);
        frame.setTitle("Listeners Lab");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setVisible(true);
    }

    public Main() {
        GridLayout layout = new GridLayout(1, 3);
        setLayout(layout);

        JPanel leftPanel = new ToolPanel();
        JPanel centerPanel = new DrawArea();
        JPanel rightPanel = new JPanel();

        add(leftPanel);
        add(centerPanel);
        add(rightPanel);
    }
}
