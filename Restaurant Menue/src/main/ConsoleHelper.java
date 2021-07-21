package main;

import kitchen.Dish;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ConsoleHelper {
    private static BufferedReader readerFromConsole = new BufferedReader(new InputStreamReader(System.in));

    private ConsoleHelper(){
        throw new IllegalStateException("Utility class.");
    }

    public static void writeMessage(String message){
        System.out.println(message);
    }

    public static String readString() throws IOException {
        return readerFromConsole.readLine();
    }

    public static List<Dish> getAllDishesForOrder() throws IOException {
        List<Dish> dishes = new ArrayList<>();
        ConsoleHelper.writeMessage("Please choose a dish from the list: " + Dish.allDishesToString() + "\nType 'exit' to complete the order");
        while (true) {

            String dishName = ConsoleHelper.readString().trim();
            if ("exit".equals(dishName)) {
                break;
            }

            try {
                Dish dish = Dish.valueOf(dishName);
                dishes.add(dish);
            } catch (Exception e) {
                writeMessage(dishName + " hasn't been detected");
            }
        }

        return dishes;
    }

}
