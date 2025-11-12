package asalaz41;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * DrawPanel listens to the FileUtiltiy for changes and redraws when the data changes.
 * A Grid is drawn with rectangles representing the files size and complexity.
 *
 * @author asalaz41, Andrea Salazar Santos
 * @version 1
 */
public class DrawPanel extends JPanel implements PropertyChangeListener, MouseListener {
    private final int GRID_COL_DEFAULT = 9;
    private final int GRID_ROW_DEFAULT = 6;
    private Rectangle[] cells;
    private Color[] cellColors;

    public DrawPanel(){
        FileUtility.getInstance().addPropertyChangeListener(this);
        addMouseListener(this);
        buildGrid(GRID_COL_DEFAULT*GRID_ROW_DEFAULT);
    }

    private void buildGrid(int totalCells){
        int grid_start_x = 45;
        int grid_start_y = 10;
        int grid_width = 700;
        int grid_height = 425;
        int grid_col = GRID_COL_DEFAULT;
        int grid_row = GRID_ROW_DEFAULT;

        if(totalCells > (grid_row*grid_col)){
            grid_row = (totalCells/grid_col);
            if(totalCells % grid_col > 0){
                grid_row++;
            }
        }

        cells = new Rectangle[grid_col*grid_row];

        int cell_width = grid_width/grid_col;
        int cell_height = grid_height/grid_row;
        int y_pos;
        int x_pos;
        int currentCell = 0;

        y_pos = grid_start_y;
        for(int row = 0; row < grid_row; row++){
            x_pos = grid_start_x;
            for(int col = 0; col < grid_col; col++){
                cells[currentCell] = new Rectangle(x_pos,y_pos,cell_width,cell_height);
                x_pos+=cell_width;
                currentCell++;
            }
            y_pos += cell_height;
        }
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);

        int colorLen = 0;
        int curCell = 0;

        if(cellColors != null){
            colorLen = cellColors.length;
        }

        for(Rectangle cell : cells){
            if(curCell < colorLen){
                g.setColor(cellColors[curCell++]);
                g.fillRect(cell.x, cell.y, cell.width, cell.height);
            }
            g.setColor(Color.BLACK);
            g.drawRect(cell.x, cell.y, cell.width, cell.height);
        }
    }

    private void propertyHandler(FileStat[] fileStats){
        buildGrid(fileStats.length);
        generateColors(fileStats);
        System.out.println("DrawPanel: property updated");
        this.repaint();
    }

    private void generateColors(FileStat[] fileStat) {
        cellColors = new Color[fileStat.length];
        Color baseColor;
        int curStat = 0;
        for(FileStat stat : fileStat){
            int complexity = stat.getComplexity();
            int size = stat.getSize();

            if(complexity > 10){
                baseColor = Color.RED;
            }else if (complexity > 5){
                baseColor = Color.YELLOW;
            }else{
                baseColor = Color.GREEN;
            }
            cellColors[curStat] = calculateAlphaColor(baseColor, size, stat.getTotalSize());
            curStat++;
        }
    }

    private Color calculateAlphaColor(Color baseColor,int size, int totalSize ){
        int alpha;
        int MAX_ALPHA = 255;
        if(size == 0 || totalSize == 0){
            alpha = 0;
        }else{
            double sizeRatio = (double)size / (double)totalSize;
            alpha = (int) (MAX_ALPHA * sizeRatio);
            alpha = Math.min(MAX_ALPHA,Math.max(0,alpha));
        }
        return new Color(baseColor.getRed(),baseColor.getGreen(), baseColor.getBlue(),alpha);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if(FileUtility.getInstance().FILE_CELL_PROP.equals(evt.getPropertyName())){
            propertyHandler((FileStat[]) evt.getNewValue());
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(cellColors == null){
            return;
        }
        int x_pos = e.getX();
        int y_pos = e.getY();

        System.out.println("Mouse Clicked");

        for(int i = 0; i<cellColors.length; i++){
            if(cells[i].contains(x_pos,y_pos)){
                FileUtility.getInstance().fileSelected(i);
                return;
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
