package asalaz41;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

/***
 * GameBoard
 *
 * @author Andrea Salazar Santos, asalaz41
 * @version 1
 */
public class GameBoard extends JPanel implements PropertyChangeListener {
    private int cellHeight;
    private int cellWidth;

    GameBoard(){
        BlackBoard.getInstance().addPropertyChangeListener(this);
        Border border = BorderFactory.createLineBorder(Color.black, 10);
        setBorder(border);
    }

    private void drawCells(Graphics g){
        int default_column = BlackBoard.DEFAULT_GRID_SIZE;

        int cellPadding = 2;
        cellWidth = getWidth()/default_column;
        cellHeight = getHeight()/default_column;

        for(int i= 0; i<BlackBoard.DEFAULT_CELL_COUNT; i++){
            int row = i/default_column;
            int col = i%default_column;
            int x = col * cellWidth;
            int y = row * cellHeight;

            g.setColor(calculateColor(i));
            g.fillRect(x, y, cellWidth-cellPadding, cellHeight-cellPadding);
            g.setColor(Color.BLACK);
            g.drawRect(x, y, cellWidth-cellPadding, cellHeight-cellPadding);
            g.drawString(""+BlackBoard.getInstance().getCellNeighbors(i), x+(cellWidth/2),y+(cellHeight/2));
        }
    }

    private Color calculateColor(int index) {
        boolean alive = BlackBoard.getInstance().isCellAlive(index);
        Color color = alive ? Color.YELLOW : Color.LIGHT_GRAY;
        return color;
    }

    public Point cellAt(Point mousePoint){
        int col = mousePoint.x / cellWidth;
        int row = mousePoint.y / cellHeight;
        System.out.println("Point[row: " + row + " col: " + col+"]");
        return new Point(row,col);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if(evt.getPropertyName().equals(BlackBoard.PROPERTY)){
            repaint();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawCells(g);
    }
}
