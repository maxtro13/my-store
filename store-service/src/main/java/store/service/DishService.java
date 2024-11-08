package store.service;

import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import store.entity.Category;
import store.dto.DishRequestDto;
import store.dto.DishResponseDto;

import java.util.List;

public interface DishService {

    ResponseEntity<?> create(DishRequestDto requestDto);

    ResponseEntity<DishResponseDto> findDishById(Long dishId);

    ResponseEntity<DishResponseDto> updateDishById(Long dishId, DishRequestDto requestDto);
    ResponseEntity<String> deleteDishById(Long dishId);

    ResponseEntity<List<DishResponseDto>> findAllDishesByCategory(Category category);
    ResponseEntity<List<DishResponseDto>> findAll();
}
