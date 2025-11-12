package asalaz41;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/***
 * GameController listens to mouse and actions by the buttons
 * created in the Main class.
 *
 * @author Andrea Salazar Santos, asalaz41
 * @version 1
 */
public class GameController implements MouseListener, ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        System.out.println("StateSelected: "+ command);
        switch (command) {
            case "Start":
                BlackBoard.getInstance().setRunning();
                BlackBoard.getInstance().start();
                break;
            case "Next":
                BlackBoard.getInstance().setNext();
                BlackBoard.getInstance().start();
                break;
            case "Stop":
                BlackBoard.getInstance().setStop();
                break;
            case "Clear":
                BlackBoard.getInstance().clear();
                break;
            default:
                break;
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        Object source = e.getSource();
        Point point = e.getPoint();

        if(source instanceof GameBoard){
            Point index = ((GameBoard) source).cellAt(point);
            BlackBoard.getInstance().cellSelected(index);
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
