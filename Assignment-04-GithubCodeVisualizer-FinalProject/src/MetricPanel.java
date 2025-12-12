package asalaz41;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * MatricPanel creates a panel and places one circle per file based on its metrics.
 * This class listens to the BlackBoard and updates on file changes.
 *
 * @author asalaz41, Andrea Salazar Santos
 * @version 1
 */
public class MetricPanel extends JPanel implements PropertyChangeListener {
    private boolean drawMetrics = false;

    MetricPanel(){
        setBackground(new Color(196, 216, 204));
        BlackBoard.getInstance().addPropertyChangeListener(this);
    }

    private void drawBackground(Graphics g){
        int diameter = Math.min(getWidth(),getWidth())/2;
        int radius = diameter / 2;
        int startAngle = 0;
        int arcAngle = 90;
        int x =-radius;
        int y = getHeight()-radius;

        g.setColor(Color.white);
        g.fillArc(x,y, diameter,diameter,startAngle, arcAngle);

        x = getWidth()-radius;
        y = -radius;
        startAngle = 180;
        g.fillArc(x,y, diameter,diameter,startAngle, arcAngle);

        g.setColor(Color.black);
        g.drawString("Useless", getWidth()-(radius/2),radius/2);
        g.drawString("Painful",radius/4, getHeight()-(radius/4));

        g.setColor(Color.GRAY);
        g.drawLine(0,0,getWidth(),getHeight());
    }

    private void drawMetrics(Graphics g){
        java.util.List<FileStat> fileStats = BlackBoard.getInstance().getSelectedFileStats();
        for (FileStat fileStat : fileStats) {
            int width = getWidth();
            int height = getHeight();
            double abstMetric = fileStat.getMetricRecord().getAbstract();
            double instability = fileStat.getMetricRecord().getInstability();

            int diameter = 40;
            int padding = 10;
            int px = (int)(instability * width);
            int py = (int)((1-abstMetric) * height);
            int labelY = py;

            if(py == 0){
                labelY += diameter+padding;
            }else if(py + diameter >= height){
                py -= diameter;
                labelY = py;
            }

            if(px + diameter >= width){
                px-=diameter;
            }

            g.fillOval(px,py, diameter,diameter);
            g.drawString(fileStat.getFileName(),px,labelY);
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawBackground(g);
        if(drawMetrics){
            drawMetrics(g);
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()){
            case BlackBoard.FILE_NODE_SELECTED_PROP:
                drawMetrics = true;
                break;
            case BlackBoard.FILE_READY_PROP:
                drawMetrics = false;
                break;
            default:
                break;
        }
        repaint();
    }
}
