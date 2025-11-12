package asalaz41;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/***
 * GameController
 *
 * @author Andrea Salazar Santos, asalaz41
 * @version 1
 */
public class GameController implements MouseListener, ActionListener {


    public GameController() {
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if(command.equals("Start")) {
            System.out.println("Starting Game");
            BlackBoard.getInstance().setRunning();
            BlackBoard.getInstance().start();
        }

        if(command.equals("Next")) {
            BlackBoard.getInstance().setNext();
            BlackBoard.getInstance().start();
        }
        if(command.equals("Stop")) {
            BlackBoard.getInstance().setStop();
        }
        if(command.equals("Clear")) {
            System.out.println("Cleared Game");
            BlackBoard.getInstance().clear();
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
