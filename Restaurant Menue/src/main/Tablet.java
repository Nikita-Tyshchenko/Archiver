package main;

import kitchen.Order;

import java.io.IOException;
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Tablet  extends Observable {

    private final int number;
    private static Logger logger = Logger.getLogger(Tablet.class.getName());

    public Tablet(int number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "Tablet{number=" + number + '}';
    }


    public Order createOrder(){
        Order order;
        try{
            order = new Order(this);
            ConsoleHelper.writeMessage(order.toString());
            if(!order.isEmpty()){
                setChanged();
                notifyObservers(order);
            }
            return order;
        } catch (IOException ioException) {
            logger.log(Level.SEVERE, "Console is unavailable.");
            throw new RuntimeException();
        }
    }
}
