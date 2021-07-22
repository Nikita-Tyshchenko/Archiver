package kitchen;

import main.ConsoleHelper;
import main.Tablet;

import java.io.IOException;
import java.util.List;
import java.util.Observable;

public class Order {

    private final Tablet tablet;
    protected List<Dish> dishes;

    public Order(Tablet tablet) throws IOException {
        this.tablet = tablet;
        this.dishes = ConsoleHelper.getAllDishesForOrder();
    }

    @Override
    public String toString() {
        String result = "";
        if (dishes.size() == 0) {
            return result;
        }
        result += "Your order: [" + dishes.get(0);

        for (int i = 1; i < dishes.size(); i++) {
            result += ", " + dishes.get(i).name();
        }
        result += "] of " + tablet + ", cooking time " + getTotalCookingTime() + "min";
        return result;
    }

    public boolean isEmpty(){
        return dishes.isEmpty();
    }

    public int getTotalCookingTime(){
        return dishes.stream().map(dish -> dish.getDuration()).reduce(0, Integer::sum);
    }
}
