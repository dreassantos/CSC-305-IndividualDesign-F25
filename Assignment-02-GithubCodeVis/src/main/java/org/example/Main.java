package asalaz41;

import javax.swing.*;
import java.awt.*;

/**
 * Main creates a frame, adds a menu and panels.
 * @author Andrea Santos, asalaz41
 * @version 1
 */
public class Main extends JFrame {

    public static void main(String[] args) {
        JFrame frame = new Main();
        frame.setSize(800,600);
        frame.setResizable(false);
        frame.setTitle("Assignment 02");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public Main(){
        BorderLayout borderLayout = new BorderLayout();
        setLayout(borderLayout);

        JMenuBar menuBar = new MenuBar();
        setJMenuBar(menuBar);

        SearchPanel searchPanel = new SearchPanel();
        DrawPanel drawPanel = new DrawPanel();

        Panel bottomPanel = new Panel();
        GridLayout gridLayout = new GridLayout(2,1);
        bottomPanel.setLayout(gridLayout);
        SelectedPanel selectedPanel = new SelectedPanel();
        StatusPanel statusPanel = new StatusPanel();
        bottomPanel.add(selectedPanel);
        bottomPanel.add(statusPanel);

        add(searchPanel, BorderLayout.NORTH);
        add(drawPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    }

}
