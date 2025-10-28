package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/***
 * Panel used to hold tool buttons, when pressed, a dialogue box displays the tool name.
 */
public class ToolPanel extends JPanel implements ActionListener {
    public ToolPanel() {
        GridLayout leftPanelLayout = new GridLayout(2, 1);
        this.setLayout(leftPanelLayout);

        JButton okBtn = new JButton("Ok");
        JButton cancelBtn = new JButton("Cancel");

        styleBtn(okBtn);
        styleBtn(cancelBtn);

        this.add(okBtn);
        this.add(cancelBtn);

        okBtn.addActionListener(this);
        cancelBtn.addActionListener(this);
    }

    private void styleBtn(JButton btn) {
        btn.setOpaque(true);
        btn.setBorder(BorderFactory.createLineBorder(Color.lightGray, 2));
        btn.setBackground(Color.gray);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JOptionPane.showMessageDialog(this,
                e.getActionCommand());
    }
}
