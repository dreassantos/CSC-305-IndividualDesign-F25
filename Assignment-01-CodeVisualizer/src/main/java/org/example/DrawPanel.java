package org.example;

import javax.swing.*;
import java.awt.*;
/**
 * DrawPanel creates a panel where the drawing is done.
 * Information loaded from the FileUtility.
 *
 * @author asalaz41, Andrea Salazar Santos
 * @version 1
 */
public class DrawPanel extends JPanel {
    private final int HAPPY = 1;
    private JLabel complexityLabel;
    private JLabel overallLabel;
    private JLabel sizeLabel;

    public DrawPanel(){
        setBackground(Color.white);
        BorderLayout borderLayout = new BorderLayout();
        setLayout(borderLayout);

        JPanel labelPanel = new JPanel();
        labelPanel.setBackground(Color.white);
        GridLayout gridLayout = new GridLayout(1,5);
        gridLayout.setHgap(20);
        labelPanel.setLayout(gridLayout);

        sizeLabel = styleLabel("Size");
        complexityLabel = styleLabel("Complexity");
        overallLabel = styleLabel("Overall");

        labelPanel.add(complexityLabel);
        labelPanel.add(sizeLabel);
        labelPanel.add(overallLabel);

        add(labelPanel, BorderLayout.NORTH);
    }

    private JLabel styleLabel(String text){
        JLabel styleLabel = new JLabel(text);
        styleLabel.setHorizontalAlignment(JLabel.CENTER);
        styleLabel.setPreferredSize(new Dimension(200, 100));
        return styleLabel;
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);

        int yPadding = 200;
        int padding = 100;
        int fileSize = FileUtility.getSize();

        if(fileSize != 0) {
            String sizeText = fileSize + "";
            g.drawString(sizeText, complexityLabel.getX(),
                    complexityLabel.getY() + complexityLabel.getHeight() + padding);

            g.fillRect(sizeLabel.getX() + (sizeLabel.getWidth() / 4), sizeLabel.getY() + yPadding,
                    sizeLabel.getWidth() - padding, fileSize);

            g.fillRect(complexityLabel.getX() + (complexityLabel.getWidth() / 4),
                    complexityLabel.getY() + yPadding + fileSize - FileUtility.getComplexity(),
                    complexityLabel.getWidth() - padding, FileUtility.getComplexity());

            g.fillOval(overallLabel.getX() + (overallLabel.getWidth() / 4), overallLabel.getY() + yPadding,
                    padding, padding);

            yPadding += 20;
            int eyeSize = 25;
            g.setColor(Color.WHITE);
            g.fillOval(overallLabel.getX() + 60, overallLabel.getY() + yPadding, eyeSize, eyeSize);
            g.fillOval(overallLabel.getX() + 100, overallLabel.getY() + yPadding, eyeSize, eyeSize);

            yPadding += 30;
            if (FileUtility.getOverall() == HAPPY) {
                g.drawArc(overallLabel.getX() + 75, overallLabel.getY() + yPadding, 50, 25, 180, 180);
            } else {
                g.drawArc(overallLabel.getX() + 75, overallLabel.getY() + yPadding, 50, 25, 0, 180);
            }
        }
    }
}
