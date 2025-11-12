package asalaz41;
/***
 * Cell
 *
 * @author Andrea Salazar Santos, asalaz41
 * @version 1
 */
public class Cell {
    private boolean aliveFlag;
    private boolean nextAliveState;
    int neighbors = 0;
    public Cell(boolean aliveFlag) {
        this.aliveFlag = aliveFlag;
    }

    public boolean isAlive() {
        return aliveFlag;
    }

    public void commitNextState(){
        this.aliveFlag = this.nextAliveState;
    }

    public void setNeighbors(int neighbors){
        this.neighbors = neighbors;
    }

    public int getNeighbors() {
        return neighbors;
    }

    public void setNextAliveState(boolean state) {
        this.nextAliveState = state;
    }
    public void setAliveState(boolean state) {
        this.aliveFlag = state;
    }
}
