package asalaz41;

import java.awt.*;
import java.beans.PropertyChangeSupport;
import java.util.Vector;

/***
 * BlackBoard
 *
 * @author Andrea Salazar Santos, asalaz41
 * @version 1
 */
public class BlackBoard extends PropertyChangeSupport {
    public static final String PROPERTY = "BlackBoard";
    public static final int DEFAULT_GRID_SIZE = 10;
    public static final int DEFAULT_CELL_COUNT = DEFAULT_GRID_SIZE*DEFAULT_GRID_SIZE;
    private static BlackBoard instance;

    private boolean running = false;
    private boolean nextFlag = false;

    private Vector<Cell> cells;
    boolean startFlag = false;
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
        cells.get(index).setAliveState(true);
        firePropertyChange(PROPERTY, null, cells);
    }

    public void cellsUpdated(){
        firePropertyChange(PROPERTY, null, cells);
    }

    public void start() {
        GameLogic gameLogic = new GameLogic();
        if (startFlag == false) {
            startFlag = true;
            Thread thread = new Thread(gameLogic);
            thread.start();
        }
        if(nextFlag){
            gameLogic.nextIterationStep();
        }
    }

    public void clear() {
        cells.clear();
        defaultCells();
        firePropertyChange(PROPERTY, null, cells);
    }

    public void setRunning(){
        System.out.println("running set to true");
        running = true;
    }

    public boolean isRunning(){
        return running;
    }

    public void setNext(){
        System.out.println("next set to true");
         nextFlag = true;
    }

    public void setStop() {
        System.out.println("stop set to true");
        running = false;
        startFlag = false;
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

    public void updateCellNeighbors(int index, int neighborCount) {
        cells.get(index).setNeighbors(neighborCount);
    }

    public int getCellNeighbors(int index) {
        return cells.get(index).getNeighbors();
    }
}
