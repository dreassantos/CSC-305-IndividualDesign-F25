package org.example;

import java.beans.PropertyChangeSupport;

public class Blackboard extends PropertyChangeSupport {
    private static Blackboard instance;

    private static final int VALUE_COUNT = 3;
    public static final int VALUE_INCREMENT_ID = 0;
    public static final int VALUE_DECREMENT_ID = 1;
    public static final int VALUE_RANDOM_ID = 2;

    public static final String VALUE_INCREMENT_PROP =  "value_increment";
    public static final String VALUE_DECREMENT_PROP = "value_decrement";
    public static final String VALUE_RANDOM_PROP =  "value_random";

    private int[] values;

    private Blackboard(int valueCount) {
        super(new Object());
        values = new int[valueCount];
    }

    public static synchronized Blackboard getInstance() {
        if(instance == null){
            instance = new Blackboard(VALUE_COUNT);
        }
        return instance;
    }

    public int get(int index){
        switch(index){
            case VALUE_INCREMENT_ID:
            case VALUE_DECREMENT_ID:
            case VALUE_RANDOM_ID:
                return values[index];
            default:
                return -1;
        }
    }

    public void set(int valueIndex, int value){
        int oldValue;
        String propertyName;

        switch(valueIndex){
            case VALUE_INCREMENT_ID:
                oldValue = values[VALUE_INCREMENT_ID];
                propertyName = VALUE_INCREMENT_PROP;
                break;
            case VALUE_DECREMENT_ID:
                oldValue = values[VALUE_DECREMENT_ID];
                propertyName = VALUE_DECREMENT_PROP;
                break;
            case VALUE_RANDOM_ID:
                oldValue = values[VALUE_RANDOM_ID];
                propertyName = VALUE_RANDOM_PROP;
                break;
            default:
                return;
        }
        if(oldValue != value){
            firePropertyChange(propertyName, oldValue, value);
        }
    }
}
