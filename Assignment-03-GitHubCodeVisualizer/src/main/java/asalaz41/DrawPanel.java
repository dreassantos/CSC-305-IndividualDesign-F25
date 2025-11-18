package asalaz41;

import javax.swing.*;
import java.awt.*;

public class DrawPanel extends JPanel {

    DrawPanel(Controller controller){
        GridLayout gridLayout = new GridLayout(1,1);
        setLayout(gridLayout);

        JTabbedPane tabbedPane = new JTabbedPane();

        JPanel gridPanel = new GridPanel();
        gridPanel.addMouseListener(controller);
        JPanel metricsPanel = new MetricPanel();
        JPanel diagramPanel = new JPanel();

        tabbedPane.add("Grid", gridPanel);
        tabbedPane.add("Metrics", metricsPanel);
        tabbedPane.add("Diagrams", diagramPanel);
        add(tabbedPane);
    }
}
