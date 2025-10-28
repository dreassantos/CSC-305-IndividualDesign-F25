package org.example;

import javax.swing.*;

public class MyMenu extends JMenuBar {
    public MyMenu() {
        add(buildFileMenu());
        add(buildActionMenu());
        add(buildHelpMenu());
    }

    private JMenu buildFileMenu(){
        JMenu fileMenu = new JMenu("MenuFile");
        JMenuItem openItem = new JMenuItem("Open");
        JMenuItem exitItem = new JMenuItem("Exit");
        fileMenu.add(openItem);
        fileMenu.addSeparator();
        fileMenu.add(exitItem);
        return fileMenu;
    }
    private JMenu buildActionMenu(){
        JMenu actionMenu = new JMenu("MenuAction");
        JMenuItem runItem = new JMenuItem("Run");
        actionMenu.add(runItem);
        return actionMenu;
    }
    private JMenu buildHelpMenu(){
        JMenu helpMenu = new JMenu("MenuHelp");
        JMenuItem aboutItem = new JMenuItem("About");
        helpMenu.add(aboutItem);
        return helpMenu;
    }
}
