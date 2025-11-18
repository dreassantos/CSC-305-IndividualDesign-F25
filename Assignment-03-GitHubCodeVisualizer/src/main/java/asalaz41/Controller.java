package asalaz41;

import javax.swing.*;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Controller implements ActionListener, MouseListener {
    private JTextField urlTextField;
    private JTree root;

    public void setTextArea(JTextField urlTextField){
        this.urlTextField = urlTextField;
    }

    public void setRoot(JTree root){
        this.root = root;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(urlTextField == null){
            return;
        }

        if (e.getActionCommand().equals("OK")){
            String url = urlTextField.getText().trim();
            if(!url.isEmpty()) {
                BlackBoard.getInstance().updateStatus(BlackBoard.FILE_LOADING_PROP);
                BlackBoard.getInstance().processURL(urlTextField.getText());
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        //System.out.println("Mouse Clicked " + e.getSource());
        Object source = e.getSource();
        Point point = e.getPoint();

        if (source instanceof GridPanel) {
            int index = ((GridPanel) source).cellAt(point);
            BlackBoard.getInstance().fileSelected(index);
        }


        if(source instanceof JTree) {
            JTree tree = (JTree) source;
            TreePath path = tree.getSelectionPath();
            String line = path.toString()
                    .replace("[root, ", "")
                    .replace(", ","/")
                    .replace("]","");

            if(line.contains(".java")){
                line = line.substring(0, line.lastIndexOf("/"));
            }

            BlackBoard.getInstance().FolderSelected(line);
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
