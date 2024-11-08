package view.client;

import view.dto.DishDtoRequest;
import view.dto.DishDtoResponse;
import view.entity.Dish;

import java.util.List;
import java.util.Optional;

public interface DishRestClient {

    Dish createDish(String name, String description, String category, Boolean availability, Double price);

    List<Dish> getAllDishesByCategory(String category);

    Optional<Dish> findDishById(Long dishId);

    void updateDish(Dish dishDto, Long dishId);
    List<Dish> getAllDishes();
}
