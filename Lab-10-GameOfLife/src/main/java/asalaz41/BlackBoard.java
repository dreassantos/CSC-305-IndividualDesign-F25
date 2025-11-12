package asalaz41;

import java.awt.*;
import java.beans.PropertyChangeSupport;
import java.util.Vector;

/***
 * BlackBoard holds Cell data for the Gameboard to observe,
 * and uses the GameLogic in a thread to process any game state and logic changes.
 *
 * @author Andrea Salazar Santos, asalaz41
 * @version 1
 */
public class BlackBoard extends PropertyChangeSupport {
    private static BlackBoard instance;

    public static final String CELLS_UPDATED_PROP = "CellsUpdated";
    public static final int DEFAULT_GRID_SIZE = 10;
    public static final int DEFAULT_CELL_COUNT = DEFAULT_GRID_SIZE*DEFAULT_GRID_SIZE;

    private boolean runningFlag = false;
    private boolean nextFlag = false;
    private boolean startFlag = true;

    private Vector<Cell> cells;

    private BlackBoard() {
        super(new Object());
        defaultCells();
    }

    private void defaultCells(){
        cells = new Vector<Cell>();
        for (int i = 0; i < DEFAULT_CELL_COUNT; i++) {
            Cell cell = new Cell(false);
            cells.add(cell);
        }
    }

    public static BlackBoard getInstance() {
        if(instance == null) {
            instance = new BlackBoard();
        }
        return instance;
    }

    public void cellSelected(Point point) {
        int row = point.x;
        int col = point.y;
        int index = row * DEFAULT_GRID_SIZE + col;

        if(row < 0 || row >= DEFAULT_GRID_SIZE
                || col < 0 || col >= DEFAULT_GRID_SIZE){
            return;
        }
        Cell selectedCell = cells.get(index);
        if(selectedCell.isAlive()){
            selectedCell.setAliveState(false);
        }else{
            selectedCell.setAliveState(true);
        }
        cellsUpdated();
    }

    public void cellsUpdated(){
        firePropertyChange(CELLS_UPDATED_PROP, null, cells);
    }

    public void start() {
        GameLogic gameLogic = new GameLogic();
        if (startFlag) {
            startFlag = false;
            Thread thread = new Thread(gameLogic);
            thread.start();
        }
        if(nextFlag){
            nextFlag = false;
            gameLogic.nextIterationStep();
        }
    }

    public void clear() {
        cells.clear();
        defaultCells();
        cellsUpdated();
    }

    public void setRunning(){
        runningFlag = true;
    }

    public boolean isRunning(){
        return runningFlag;
    }

    public void setNext(){
         nextFlag = true;
    }

    public void setStop() {
        runningFlag = false;
        startFlag = true;
    }

    public void commitState(){
        for(Cell cell : cells){
            cell.commitNextState();
        }
    }

    public void updateNextAliveState(int index, boolean state){
        cells.get(index).setNextAliveState(state);
    }

    public boolean isCellAlive(int index){
        return cells.get(index).isAlive();
    }
}
