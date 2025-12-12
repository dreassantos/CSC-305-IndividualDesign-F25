package asalaz41;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;
/**
 *DiagramPanel creates a UMLpanel and set it to a scrollable panel.
 * Listens to the Blackboard for changes to the selected filestats.
 *
 * @author Andrea Santos, asalaz41
 * @version 2
 */
public class DiagramPanel extends JPanel implements PropertyChangeListener {
    private String umlString = "@startuml\n!pragma layout smetana\n@enduml";
    JScrollPane paneWithScrollBars;

    DiagramPanel() {
        BlackBoard.getInstance().addPropertyChangeListener(this);
        paneWithScrollBars = new JScrollPane();
        paneWithScrollBars.setBackground(Color.WHITE);
        setLayout(new GridLayout(1,1));
        add(paneWithScrollBars);
    }

    private void buildUMLString(){
        List<FileStat> fileStat = BlackBoard.getInstance().getSelectedFileStats();

        StringBuilder sb = new StringBuilder();
        sb.append("@startuml\n!pragma layout smetana\n");

        for(FileStat fs : fileStat){
            Metric metric = fs.getMetricRecord();
            sb.append(metric.relationStringDeclarator(fs.getClasName()));
        }

        for(FileStat fs : fileStat){
            Metric metric = fs.getMetricRecord();
            sb.append(metric.relationString(fs.getClasName()));
        }
        sb.append("@enduml");
        umlString = sb.toString();
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if(evt.getPropertyName().equals(BlackBoard.FILE_NODE_SELECTED_PROP)) {
            buildUMLString();
            JPanel newPanel = new UMLPanel(umlString);
            paneWithScrollBars.setViewportView(newPanel);
        }
    }
}
