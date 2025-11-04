package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;

import javiergs.tulip.GitHubHandler;
//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main extends JFrame implements ActionListener {
    JTextField urlField = new JTextField();
    JTextArea textArea = new JTextArea();

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("OK")) {
            System.out.println("Ok selected");
            textArea.setText(processURL());
        }
    }

    static void main() {
        JFrame frame = new Main();
        frame.setSize(800, 600);
        frame.setTitle("Title");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public Main() {
        GridLayout layout = new GridLayout(2, 1);
        setLayout(layout);

        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new GridLayout(1, 2));

        JButton submitBtn = new JButton("OK");
        searchPanel.add(urlField);
        searchPanel.add(submitBtn);
        submitBtn.addActionListener(this);

        //JScrollPane scrollPane = new JScrollPane(textArea);

        JPanel filePanel = new FilePanel();
        add(searchPanel);
        add(textArea);

    }

    String processURL()  {
        //github.com/csc3100/
        String token = "";
        GitHubHandler gitHubHandler = new GitHubHandler(token);
        String url = urlField.getText();
        ArrayList<String> allFromURL = new ArrayList<>();
        try {
            allFromURL = (ArrayList<String>) gitHubHandler.listFilesRecursive(url);
        } catch (Exception e) {
            return "";
        }
        return allFromURL.toString();
    }
}
