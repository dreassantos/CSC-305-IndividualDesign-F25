package asalaz41;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuBar extends JMenuBar implements ActionListener {
    public MenuBar() {
        buildFileMenu();
        buildActionMenu();
        buildHelpMenu();
    }

    private void buildFileMenu(){
        JMenu fileMenu = new JMenu("File");

        JMenuItem open = new JMenuItem("Open From URL");
        open.addActionListener(this);
        JMenuItem exit = new JMenuItem("Exit");
        exit.addActionListener(this);

        fileMenu.add(open);
        fileMenu.add(exit);
        add(fileMenu);
    }

    private void buildHelpMenu(){
        JMenu helpMenu = new JMenu("Help");
        JMenuItem about = new JMenuItem("About");
        about.addActionListener(this);
        helpMenu.add(about);
        add(helpMenu);
    }

    private void buildActionMenu(){
        JMenu actionsMenu = new JMenu("Action");
        JMenuItem reload = new JMenuItem("Reload");
        JMenuItem clear = new JMenuItem("Clear");
        actionsMenu.add(reload);
        actionsMenu.add(clear);
        add(actionsMenu);
    }

    private void openDialogue(){
        JDialog dialog = buildDialogBox("Open From URL");
        JPanel dialogueSearch = new SearchPanel();
        dialog.add(dialogueSearch);
    }
    private void aboutDialogue(){
        JDialog dialog = buildDialogBox("About");
        JPanel aboutPanel = new JPanel();
        JLabel title = new JLabel("Program: Assignment 02, CSC-305-Fall 2025");
        aboutPanel.add(title);
        dialog.add(aboutPanel);
    }

    private JDialog buildDialogBox(String title){
        JDialog dialog = new JDialog();
        dialog.setTitle(title);
        dialog.setPreferredSize(new Dimension(700, 150));
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.pack();
        dialog.setVisible(true);
        return dialog;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch(e.getActionCommand()){
            case "Open From URL":
                openDialogue();
                break;
            case "Exit":
                System.exit(0);
                break;
            case "About":
                aboutDialogue();
                break;
        }
    }
}
