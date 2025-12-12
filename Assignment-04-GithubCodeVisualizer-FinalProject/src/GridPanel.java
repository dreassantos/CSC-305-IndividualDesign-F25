package asalaz41;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * GridPanel creates a grid of rectangles for each file int a selected folder
 * and colors each rectangle based on their FileStat data.
 * This class listens to the Blackboard for all file stat changes.
 *
 * @author asalaz41, Andrea Salazar Santos
 * @version 1
 */
public class GridPanel extends JPanel implements PropertyChangeListener {
    private Rectangle[] cells;
    private Color[] cellColors;
    private java.util.List<FileStat>  fileStats;
    private boolean readyDrawGrid = false;
    private boolean loading = false;

    public GridPanel(){
        BlackBoard.getInstance().addPropertyChangeListener(this);
    }

    private void buildGrid( ){
        int totalCells = fileStats.size();
        int gridWidth = getWidth();
        int gridHeight = getHeight();

        int gridCol = (int)Math.ceil(Math.sqrt(totalCells));
        int gridRow = (int)Math.ceil((double) totalCells/gridCol);

        cells = new Rectangle[gridCol*gridRow];

        int cellWidth = (gridWidth/gridCol);
        int cellHeight = (gridHeight/gridRow);
        int currentCell = 0;
        int cellPadding = 10;

        int yPos = 0;
        for(int row = 0; row < gridRow; row++){
            int xPos = 0;
            for(int col = 0; col < gridCol; col++){
                cells[currentCell] = new Rectangle(xPos+cellPadding,yPos+cellPadding,
                        cellWidth-cellPadding,cellHeight-cellPadding);
                xPos+=cellWidth;
                currentCell++;
            }
            yPos += cellHeight;
        }
    }

    private void paintCells(Graphics g){
        if(cells == null){
            return;
        }

        int colorLen = 0;
        int curCell = 0;

        if(cellColors != null){
            colorLen = cellColors.length;
        }

        int index = 0;
        for(Rectangle cell : cells){
            if(curCell < colorLen){
                g.setColor(cellColors[curCell++]);
                g.fillRect(cell.x, cell.y, cell.width, cell.height);
                g.setColor(Color.black);
                g.drawString(fileStats.get(index).getFileName(),cell.x+cell.width/4, cell.y+cell.height/2);
            }else{
                g.setColor(Color.lightGray);
                g.drawRect(cell.x, cell.y, cell.width, cell.height);
            }

            index++;
        }
    }

    private void paintMsg(Graphics g, String msg){
        g.drawString(msg,getWidth()/2, getHeight()/2);
    }

    private void generateColors() {
        cellColors = new Color[fileStats.size()];
        Color baseColor;
        int curStat = 0;
        for(FileStat stat : fileStats){
            Complexity complexityRecord = stat.getComplexityRecord();
            int complexity = complexityRecord.getComplexity();

            if(complexity > 10){
                baseColor = Color.RED;
            }else if (complexity > 5){
                baseColor = Color.YELLOW;
            }else{
                baseColor = Color.GREEN;
            }
            cellColors[curStat] = calculateAlphaColor(baseColor, complexityRecord.getSize(), complexityRecord.getTotalSize());
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

    public int cellAt(Point point) {
        int cell = -1;
        if(cellColors == null){
            return cell;
        }

        int xPos = point.x;
        int yPos = point.y;

        for(int i = 0; i<cellColors.length; i++){
            if(cells[i].contains(xPos,yPos)){
                cell = i;
            }
        }

        return cell;
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        if(readyDrawGrid){
            paintCells(g);
        }else if(loading){
            paintMsg(g,"Loading");
        }else{
            paintMsg(g, "");
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch(evt.getPropertyName()){
            case BlackBoard.FILE_READY_PROP:
                loading = false;
                readyDrawGrid = false;
                break;
            case BlackBoard.FILE_NODE_SELECTED_PROP:
                readyDrawGrid = true;
                if(evt.getNewValue() == null){
                    return;
                }
                fileStats = (java.util.List<FileStat>)evt.getNewValue();
                generateColors();
                buildGrid();
                break;
            case BlackBoard.FILE_LOADING_PROP:
                loading = true;
                readyDrawGrid = false;
                break;
            default:
                break;
        }
        repaint();
    }

}
