package org.example;

public class DataSource implements Runnable {
    private static final int REALLY_BIG_NUMBER = 1000000;
    private int id;

    public DataSource(int id) {
        this.id = id;
    }

    @Override
    public void run() {
        int i = 0;
        int j = REALLY_BIG_NUMBER;
        try{
            while(!Thread.currentThread().isInterrupted()){
                switch(id){
                    case Blackboard.VALUE_INCREMENT_ID:
                        Blackboard.getInstance().set(id, i++);
                        break;
                    case Blackboard.VALUE_DECREMENT_ID:
                        Blackboard.getInstance().set(id, j--);
                        break;
                    case Blackboard.VALUE_RANDOM_ID:
                        Blackboard.getInstance().set(id, (int)(Math.random()*100));
                        break;
                }
                Thread.sleep(100);
            }
        }catch (InterruptedException ie){
            System.out.println(ie.getMessage());
        }
    }
}
