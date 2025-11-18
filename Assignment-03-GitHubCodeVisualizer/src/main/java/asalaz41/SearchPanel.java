package asalaz41;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * SearchPanel creates a panel with a textfield
 * that accepts a url and passes it to the FileUtility.
 *
 * @author asalaz41, Andrea Salazar Santos
 * @version 2
 */
public class SearchPanel extends JPanel{
    private final String SEARCH_PLACEHOLDER = "GitHub Folder URL";
    private final String TEST_URL = "https://github.com/CSC3100/Pacman";
    private final String TEST_URL2= "https://github.com/CSC3100/App-Paint";
    private JTextField textField;

    public SearchPanel(Controller controller){
        setBackground(new Color(109, 150, 201));
        textField = new JTextField(TEST_URL);
        textField.setForeground(Color.GRAY);
        textField.setPreferredSize(new Dimension(640,25));

        JButton button = new JButton("OK");
        button.addActionListener(controller);
        controller.setTextArea(textField);

        add(textField);
        add(button);
    }
}
