package org.example;

import javax.swing.*;
import java.awt.*;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main extends JFrame {
    static void main() {
        JFrame frame = new Main();
        frame.setSize(800,600);
        frame.setTitle("Assignment 02");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public Main(){
        GridLayout layout = new GridLayout(4,1);
        setLayout(layout);

        SearchPanel searchPanel = new SearchPanel();
        DrawPanel drawPanel = new DrawPanel();
        SelectedPanel selectedPanel = new SelectedPanel();
        StatusPanel statusPanel = new StatusPanel();

        add(searchPanel);
        add(drawPanel);
        add(selectedPanel);
        add(statusPanel);
    }
}
