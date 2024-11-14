package view.client;

import view.entity.Dish;

import java.util.List;
import java.util.Optional;

public interface DishRestClient  {

    Dish createDish(String name, String description, String category, Boolean availability, Double price);

    List<Dish> getAllDishesByCategory(String category);

    Optional<Dish> findDishById(Long dishId);

    void updateDish(Long dishId, String name, String description, String category, Boolean availability, Double price);
    List<Dish> getAllDishes();
    void deleteDish(Long dishId);
}
