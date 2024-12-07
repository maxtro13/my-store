package view.client;

import org.springframework.web.multipart.MultipartFile;
import view.dto.DishDtoRequest;
import view.entity.Dish;
import view.entity.Image;

import java.util.List;
import java.util.Optional;

public interface StoreRestClient {

    Dish createDish(DishDtoRequest dtoRequest, MultipartFile image);

    List<Dish> getAllDishesByCategory(String category);

    Optional<Dish> findDishById(Long dishId);

    void updateDish(Long dishId, DishDtoRequest requestDto, MultipartFile image);

    List<Dish> getAllDishes();

    void deleteDish(Long dishId);

    Image getImageById(Long imageId);
}

