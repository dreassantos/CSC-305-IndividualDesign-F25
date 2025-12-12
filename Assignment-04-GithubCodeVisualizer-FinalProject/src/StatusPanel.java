package asalaz41;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * StatusPanel creates a panel and listens for changes in the BlackBoard to update
 * any status changes.
 *
 * @author asalaz41, Andrea Salazar Santos
 * @version 1
 */
public class StatusPanel extends JPanel implements PropertyChangeListener{
    private JLabel label;

    public StatusPanel(){
        setBackground(new Color(109, 150, 201));
        Border border = BorderFactory.createLineBorder(Color.lightGray, 1);
        setBorder(border);

        label = new JLabel();
        add(label);

        setPreferredSize(new Dimension(100, 48));
        BlackBoard.getInstance().addPropertyChangeListener(this);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        String name = evt.getPropertyName();
        switch (name) {
            case BlackBoard.FILE_READY_PROP:
                label.setText("");
                break;
            case BlackBoard.FILE_LOG_PROP:
                label.setText((String) evt.getNewValue());
            default:
        }
    }
}
