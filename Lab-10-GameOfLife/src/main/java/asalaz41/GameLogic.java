package asalaz41;

/***
 * GameLogic helps the blackboard by using a thread to calculate
 * the neighbors on the bord then set the states depending on the
 * rules outlined by https://playgameoflife.com/
 *
 * @author Andrea Salazar Santos, asalaz41
 * @version 1
 */
public class GameLogic implements Runnable {
    private int[][] neighbors;
    int gridSize;
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
        gridSize = BlackBoard.DEFAULT_GRID_SIZE;
        processAllCells();
        BlackBoard.getInstance().cellsUpdated();
        BlackBoard.getInstance().commitState();
    }

    private void processAllCells(){
        neighbors = new int[gridSize][gridSize];
        for(int row = 0; row < gridSize; row++){
            for(int col = 0; col < gridSize; col++){
                neighbors[row][col] = calculateNeighbors(row,col);
                setNextState(row,col);
            }
        }
    }

    private int calculateNeighbors(int curRow, int curCol) {
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
        return neighborCount;
    }

    private void setNextState(int row, int col){
        int index = row * gridSize + col;
        boolean alive = BlackBoard.getInstance().isCellAlive(index);

        if(alive && (neighbors[row][col] == 2 || neighbors[row][col] == 3)){
            BlackBoard.getInstance().updateNextAliveState(index, true);
        }else if(!alive && (neighbors[row][col] == 3)){
            BlackBoard.getInstance().updateNextAliveState(index, true);
        }else {
            BlackBoard.getInstance().updateNextAliveState(index, false);
        }
    }
}
