package asalaz41;

import javax.swing.*;
import java.awt.*;

/**
 * SearchPanel creates a panel with a textfield
 * that accepts a url and passes it to the BlackBoard.
 *
 * @author asalaz41, Andrea Salazar Santos
 * @version 2
 */
public class SearchPanel extends JPanel{
    private final String SEARCH_PLACEHOLDER = "GitHub Folder URL";
    private JTextField textField;

    public SearchPanel(Controller controller){
        setBackground(new Color(109, 150, 201));

        textField = new JTextField(SEARCH_PLACEHOLDER);
        textField.setForeground(Color.GRAY);
        textField.setPreferredSize(new Dimension(640,25));

        JButton button = new JButton("OK");
        button.addActionListener(controller);
        controller.setTextArea(textField);

        add(textField);
        add(button);
    }
}
