package asalaz41;
/***
 * Cell is the data structure that holds the cells states
 *
 * @author Andrea Salazar Santos, asalaz41
 * @version 1
 */
public class Cell {
    private boolean aliveFlag;
    private boolean nextAliveState;

    public Cell(boolean aliveFlag) {
        this.aliveFlag = aliveFlag;
    }

    public boolean isAlive() {
        return aliveFlag;
    }

    public void setNextAliveState(boolean state) {
        this.nextAliveState = state;
    }

    public void setAliveState(boolean state) {
        this.aliveFlag = state;
    }

    public void commitNextState(){
        this.aliveFlag = this.nextAliveState;
    }
}
