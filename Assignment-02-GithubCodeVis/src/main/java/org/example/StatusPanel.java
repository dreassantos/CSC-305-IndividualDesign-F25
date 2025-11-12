package asalaz41;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
/**
 * StatusPanel creates a panel and listens for changes in the FileUtility to update
 * any status changes.
 *
 * @author asalaz41, Andrea Salazar Santos
 * @version 1
 */
public class StatusPanel extends JPanel implements PropertyChangeListener {
    private JLabel label;

    public StatusPanel(){
        setBackground(Color.ORANGE);
        label = new JLabel();
        add(label);
        FileUtility.getInstance().addPropertyChangeListener(this);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if(FileUtility.getInstance().FILE_LOG_PROP.equals(evt.getPropertyName())){
            label.setText((String)evt.getNewValue());
        }
        if(FileUtility.getInstance().FILE_CELL_PROP.equals(evt.getPropertyName())){
            label.setText("");
        }
    }
}
