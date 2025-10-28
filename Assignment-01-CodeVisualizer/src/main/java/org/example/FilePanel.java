package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
/**
 * FilePanel holds a scrollable panel of files.
 * Files are selected in the menu and loaded from the FileUtility.
 *
 * @author asalaz41, Andrea Salazar Santos
 * @version 1
 */
public class FilePanel extends JPanel{
    private JScrollPane scrollPane;
 
    public FilePanel() {
        setPreferredSize(new Dimension(150, 600));
        setLayout(new BorderLayout());
        setBackground(Color.decode("#86ebe6"));

        JLabel label = new JLabel("Select a file");
        scrollPane = new JScrollPane();
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        add(label, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }

    public void updateFilePanel(){
        JList<String> files = FileUtility.getFileList();
        MenuListener menuListener = new MenuListener();
        files.addMouseListener(menuListener);
        scrollPane.setViewportView(files);
    }
}
