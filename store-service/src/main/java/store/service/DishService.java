package store.service;

import org.springframework.http.ResponseEntity;
import store.dishes.dto.DishRequestDto;
import store.dishes.dto.DishResponseDto;
import store.dishes.entity.Category;

import java.util.List;

public interface DishService {

    ResponseEntity<?> create(DishRequestDto requestDto) throws Exception;

    ResponseEntity<DishResponseDto> findDishById(Long dishId);

    ResponseEntity<DishResponseDto> updateDishById(Long dishId, DishRequestDto requestDto) throws Exception;

    void deleteDishById(Long dishId);

    ResponseEntity<List<DishResponseDto>> findAllDishesByCategory(Category category);

    ResponseEntity<List<DishResponseDto>> findAll();
}
