package asalaz41;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.swing.*;
import java.awt.*;

/**
 *Main creates the applications frame with a
 * search panel, draw panel, file panel and status panel.
 *
 * @author asalaz41, Andrea Salazar Santos
 * @version 1
 */
public class Main extends JFrame {
    private Logger logger;

    public static void main(String[] args){
        JFrame frame = new Main();
        frame.setSize(1200,800);
        frame.setResizable(true);
        frame.setTitle("Assignment 04");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public Main(){
        logger = LoggerFactory.getLogger(Main.class);
        logger.info("Starting Program");

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