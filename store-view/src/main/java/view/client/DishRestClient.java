package view.client;

import org.springframework.web.multipart.MultipartFile;
import view.dto.DishDtoRequest;
import view.entity.Dish;

import java.util.List;
import java.util.Optional;

public interface DishRestClient {

    Dish createDish(DishDtoRequest dtoRequest, MultipartFile image);

    List<Dish> getAllDishesByCategory(String category);

    Optional<Dish> findDishById(Long dishId);

    Dish updateDish(Long dishId, DishDtoRequest requestDto, MultipartFile image);

    List<Dish> getAllDishes();

    void deleteDish(Long dishId);
}
