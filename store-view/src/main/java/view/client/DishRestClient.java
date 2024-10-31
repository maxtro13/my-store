package view.client;

import view.entity.Dish;

import java.util.List;

public interface DishRestClient {

    Dish createDish(String name, String description, String category, Boolean availability, Double price);

    List<Dish> getAllDishesByCategory(String category);

    Dish findDishById(Long dishId);

}
