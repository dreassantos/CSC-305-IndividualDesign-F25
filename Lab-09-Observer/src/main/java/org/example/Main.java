package org.example;

import javax.swing.*;
import java.awt.*;

public class Main extends JFrame {
    static void main(String[] args) {
        Main frame = new Main();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Lab 09");
        frame.setSize(400, 250);
        frame.setVisible(true);
    }

    public Main(){

        DataSource producer_1 = new DataSource(Blackboard.VALUE_INCREMENT_ID);
        DataSource producer_2 = new DataSource(Blackboard.VALUE_DECREMENT_ID);
        DataSource producer_3 = new DataSource(Blackboard.VALUE_RANDOM_ID);

        Thread thread_1 = new Thread(producer_1);
        Thread thread_2 = new Thread(producer_2);
        Thread thread_3 = new Thread(producer_3);

        thread_1.setDaemon(true);
        thread_2.setDaemon(true);
        thread_3.setDaemon(true);

        thread_1.start();
        thread_2.start();
        thread_3.start();

        buildPanel();
    }

    private void buildPanel(){
        GridLayout layout = new GridLayout(3,1);
        setLayout(layout);

        ValuePanel panel_1 = new ValuePanel(Blackboard.VALUE_INCREMENT_ID, new Color(173, 216, 230));
        ValuePanel panel_2 = new ValuePanel(Blackboard.VALUE_DECREMENT_ID, new Color(144, 238, 144));
        ValuePanel panel_3 = new ValuePanel(Blackboard.VALUE_RANDOM_ID, new Color(252, 252, 145));

        add(panel_1);
        add(panel_2);
        add(panel_3);
    }
}
