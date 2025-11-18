package asalaz41;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Map;

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
        FileStat[] fileStats = BlackBoard.getInstance().getSelectedFileStats().toArray(new FileStat[0]);
        Point centerPt = new Point(getWidth()/2,getHeight()/2);
        int diameter = 50;
        for (FileStat fileStat : fileStats) {
            int x = centerPt.x;
            int y = centerPt.y;
            g.fillOval(x,y, diameter,diameter);
            g.drawString(fileStat.getName(),x,y);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(drawMetrics){
            drawMetrics(g);
        }
        drawBackground(g);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()){
            case BlackBoard.FILE_NODE_SELECTED_PROP:
                System.out.println("repainting metric");
                drawMetrics = true;
                break;
            case BlackBoard.FILE_READY_PROP:
                System.out.println("repainting metric");
                drawMetrics = false;
                break;
            default:
                break;
        }
        repaint();
    }
}
