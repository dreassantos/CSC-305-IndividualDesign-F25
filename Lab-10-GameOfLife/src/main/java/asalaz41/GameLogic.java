package asalaz41;

import java.awt.*;

/***
 * GameLogic
 *
 * @author Andrea Salazar Santos, asalaz41
 * @version 1
 */
public class GameLogic implements Runnable {
    private int[][] neighbors;

    @Override
    public void run() {
        try {
            while (BlackBoard.getInstance().isRunning()) {
                nextIterationStep();
                Thread.sleep(1000);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void nextIterationStep(){
        System.out.println("Next Iteration Step");
        checkNeighborsAllCells();
        setNextState();
        BlackBoard.getInstance().cellsUpdated();
        BlackBoard.getInstance().commitState();
    }

    private void checkNeighborsAllCells(){
        neighbors = new int[BlackBoard.DEFAULT_GRID_SIZE][BlackBoard.DEFAULT_GRID_SIZE];
        for(int i = 0; i < BlackBoard.DEFAULT_GRID_SIZE; i++){
            for(int j = 0; j < BlackBoard.DEFAULT_GRID_SIZE; j++){
                neighbors[i][j] = checkNeighbors(i,j);
            }
        }
    }
    private int checkNeighbors(int curRow, int curCol) {
        int gridSize = BlackBoard.DEFAULT_GRID_SIZE;
        int neighborCount = 0;

        for(int rowOffset = -1; rowOffset <= 1; rowOffset++){
            for(int colOffset = -1; colOffset <= 1; colOffset++){
                if(rowOffset == 0 && colOffset == 0){
                    continue;
                }
                int neighborRow = curRow + rowOffset;
                int neighborCol = curCol + colOffset;
                if(neighborRow < 0 || neighborRow >= gridSize
                        || neighborCol < 0 || neighborCol >= gridSize){
                    continue;
                }
                int neighborIndex = neighborRow * gridSize + neighborCol;
                boolean alive = BlackBoard.getInstance().isCellAlive(neighborIndex);
                if(alive){
                    neighborCount++;
                }
            }
        }
        int index = curRow * gridSize + curCol;
        BlackBoard.getInstance().updateCellNeighbors(index, neighborCount);
        return neighborCount;
    }

    private void setNextState(){
        int gridsize = BlackBoard.DEFAULT_GRID_SIZE;
        for(int row = 0; row < gridsize; row++){
            for(int col = 0; col < gridsize; col++){
                int index = row * gridsize + col;
                boolean alive = BlackBoard.getInstance().isCellAlive(index);
                if(alive && (neighbors[row][col] == 2 || neighbors[row][col] == 3)){
                    BlackBoard.getInstance().updateNextAliveState(index, true);
                }else if(!alive && (neighbors[row][col] == 3)){
                    BlackBoard.getInstance().updateNextAliveState(index, true);
                }else{
                    BlackBoard.getInstance().updateNextAliveState(index, false);
                }
            }
        }
    }
}
