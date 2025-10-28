package org.example;

import javax.swing.*;

/***
 *  Menu creates a menu with the following file tree
 *  -File: Open
 *  -Action
 *  -Help
 *
 * @author asalaz41, Andrea Salazar Santos
 * @version 1
 */
public class Menu extends JMenuBar{
    public Menu() {
        add(fileMenu());
        add(new JMenu("Action"));
        add(new JMenu("Help"));
    }

    private JMenu fileMenu() {
        JMenu fileMenu = new JMenu("File");
        JMenuItem openMenuItem = new JMenuItem("Open");
        fileMenu.add(openMenuItem);
        MenuListener menuListener = new MenuListener();
        openMenuItem.addActionListener(menuListener);
        return fileMenu;
    }
}
