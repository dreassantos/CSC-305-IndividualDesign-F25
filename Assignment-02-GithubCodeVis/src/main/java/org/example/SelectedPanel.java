package asalaz41;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
/**
 * SelectedPanel creates a styled panel and listens to the FileUtiltiy to update the
 * text field when a file has been selected.
 *
 * @author asalaz41, Andrea Salazar Santos
 * @version 1
 */
public class SelectedPanel extends JPanel implements PropertyChangeListener {
    private JTextField textField;
    private String DEFAULT_TEXT = " No file selected";
    public SelectedPanel(  ){
        BorderLayout borderLayout = new BorderLayout(20,20);
        this.setLayout(borderLayout);
        JLabel label = new JLabel("Selected File Name: ");
        label.setHorizontalAlignment(SwingConstants.RIGHT);
        label.setPreferredSize(new Dimension(200,30));

        textField = new JTextField(DEFAULT_TEXT);
        textField.setBackground(Color.WHITE);
        textField.setHorizontalAlignment(SwingConstants.LEFT);
        textField.setBorder(BorderFactory.createLineBorder(Color.black));

        textField.setEditable(false);
        textField.getCaret().deinstall(textField);
        textField.setHighlighter(null);

        JPanel spacer = new JPanel();
        spacer.setPreferredSize(new Dimension(60,0));

        add(label, BorderLayout.WEST);
        add(textField, BorderLayout.CENTER);
        add(spacer,BorderLayout.EAST);

        FileUtility.getInstance().addPropertyChangeListener(this);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if(FileUtility.getInstance().FILE_SELECTED_PROP.equals(evt.getPropertyName())){
            String text = (String) evt.getNewValue();
            textField.setText(text);
        }
        if(FileUtility.getInstance().FILE_CELL_PROP.equals(evt.getPropertyName())){
            textField.setText(DEFAULT_TEXT);
        }
    }
}
