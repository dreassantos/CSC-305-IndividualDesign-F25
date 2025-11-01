package asalaz41;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class DrawPanel extends JPanel implements PropertyChangeListener {

    private JPanel cellPanel;
    int grid_col, grid_row;
    private FileCell[] fileCells;
    public DrawPanel(int grid_col, int grid_row){
        this.grid_col = grid_col;
        this.grid_row = grid_row;
        FileUtility.GetInstance().addPropertyChangeListener(this);
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        System.out.println("Painting DrawPanel");
        int grid_start_x = 45;
        int grid_start_y = 10;
        int grid_width = 700;
        int grid_height = 425;

        //g.setColor(Color.white);
        //g.fillRect(grid_start_x,grid_start_y, grid_width, grid_height);
        //g.setColor(Color.black);
        //g.drawRect(grid_start_x,grid_start_y, grid_width, grid_height);

        int cell_vgap = 0;
        int cell_hgap = 0;
        int cell_width = (grid_width-(grid_col*cell_vgap))/grid_col;
        int cell_height = (grid_height-(grid_row*cell_hgap))/grid_row;

        int currentCell = 0;
        int count = 0;
        int x_pos = 0;
        int y_pos = 0;

        if(FileUtility.GetInstance().GetFileCells() != null){
            count = fileCells.length;
        }

        y_pos += grid_start_y + cell_vgap;
        for(int row = 0; row < grid_row; row++){
            x_pos = grid_start_x + cell_hgap;
            for(int col = 0; col < grid_col; col++){
                if(currentCell < count){
                    g.setColor(fileCells[currentCell].getColor());
                    g.fillRect(x_pos, y_pos, cell_width, cell_height);
                    currentCell++;
                }
                g.setColor(Color.black);
                g.drawRect(x_pos, y_pos, cell_width, cell_height);
                x_pos+=cell_width + cell_hgap;
            }
            y_pos += cell_height+cell_vgap;
        }
    }


    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if(FileUtility.GetInstance().FILE_CELL_PROP.equals(evt.getPropertyName())){
            fileCells = (FileCell[]) evt.getNewValue();
            System.out.println("DrawPanel: property changed");
        }
       repaint();
    }
}
