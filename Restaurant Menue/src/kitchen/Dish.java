package kitchen;

import java.util.Arrays;

public enum Dish {

    FISH(25),
    STEAK(30),
    SOUP(15),
    JUICE(5),
    WATER(3);

    private int duration;

    Dish(int duration) {
        this.duration = duration;
    }

    public int getDuration() {
        return duration;
    }

    public static String allDishesToString() {
        StringBuilder menu = new StringBuilder();
        Arrays.stream(Dish.values()).forEach(dish -> menu.append(dish + " "));
        return menu.toString();
    }
}
