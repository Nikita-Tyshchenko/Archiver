package kitchen;

import java.util.Arrays;

public enum Dish {

    FISH,
    STEAK,
    SOUP,
    JUICE,
    WATER;

    public static String allDishesToString() {
        StringBuilder menu = new StringBuilder();
        Arrays.stream(Dish.values()).forEach(dish -> menu.append(dish + " "));
        return menu.toString();
    }
}
