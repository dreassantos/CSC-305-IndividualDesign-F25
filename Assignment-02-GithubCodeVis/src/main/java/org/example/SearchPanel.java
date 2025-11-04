package asalaz41;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SearchPanel extends JPanel implements ActionListener{

    private final String SEARCH_PLACEHOLDER = "GitHub Folder URL";
    private JTextField textField;
    private String TEXT_URL = "https://github.com/CSC3100/Pacman";
    public SearchPanel(){
        textField = new JTextField(TEXT_URL);
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
