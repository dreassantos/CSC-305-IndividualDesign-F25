package asalaz41;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
/**
 * SearchPanel creates a panel with a textfield
 * that accepts a url and passes it to the FileUtility.
 *
 * @author asalaz41, Andrea Salazar Santos
 * @version 1
 */
public class SearchPanel extends JPanel implements ActionListener{

    private final String SEARCH_PLACEHOLDER = "GitHub Folder URL";
    private final String TEST_URL = "https://github.com/CSC3100/Pacman";
    private JTextField textField;
    public SearchPanel(){
        textField = new JTextField(SEARCH_PLACEHOLDER);
        textField.setForeground(Color.GRAY);
        textField.setPreferredSize(new Dimension(640,25));

        JButton button = new JButton("OK");
        button.addActionListener(this);

        add(textField);
        add(button);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("OK")){
            FileUtility.getInstance().processURL(textField.getText());
        }
    }
}
