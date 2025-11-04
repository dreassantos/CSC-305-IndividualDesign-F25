package org.example;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class ValuePanel extends JPanel implements PropertyChangeListener {

    private final JLabel label = new JLabel("0",SwingConstants.CENTER);
    private int id;

    public ValuePanel(int id, Color color){
        this.id = id;
        setBackground(color);
        setBorder(BorderFactory.createLineBorder(Color.BLACK));

        label.setFont(label.getFont().deriveFont(Font.BOLD, 64f));
        add(label,BorderLayout.CENTER);

        Blackboard.getInstance().addPropertyChangeListener(this);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        int value;
        switch(evt.getPropertyName()){
            case Blackboard.VALUE_INCREMENT_PROP :
                if(id == Blackboard.VALUE_INCREMENT_ID){
                    value = (int)evt.getNewValue();
                    label.setText(String.valueOf(value));
                }
                break;
            case Blackboard.VALUE_DECREMENT_PROP:
                if(id == Blackboard.VALUE_DECREMENT_ID){
                    value = (int)evt.getNewValue();
                    label.setText(String.valueOf(value));
                }
                break;
            case Blackboard.VALUE_RANDOM_PROP:
                if(id == Blackboard.VALUE_RANDOM_ID){
                    value = (int)evt.getNewValue();
                    label.setText(String.valueOf(value));
                }
                break;
            default:
                break;
        }
    }
}
