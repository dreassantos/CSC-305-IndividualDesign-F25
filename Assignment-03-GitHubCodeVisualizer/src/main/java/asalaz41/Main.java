package asalaz41;

import javax.swing.*;
import java.awt.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main extends JFrame {
    private Logger logger;
    public static void main(String[] args){
        JFrame frame = new Main();
        frame.setSize(1200,800);
        frame.setResizable(false);
        frame.setTitle("Assignment 03");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public Main(){

        BorderLayout layout = new BorderLayout();
        setLayout(layout);

        Controller controller = new Controller();

        JPanel searchPanel = new SearchPanel(controller);
        JPanel filePanel = new FilePanel(controller);
        JPanel drawPanel = new DrawPanel(controller);
        JPanel statusPanel = new StatusPanel();

        add(searchPanel, BorderLayout.NORTH);
        add(filePanel, BorderLayout.WEST);
        add(drawPanel, BorderLayout.CENTER);
        add(statusPanel, BorderLayout.SOUTH);
    }
}