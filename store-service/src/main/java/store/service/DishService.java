package store.service;

import org.springframework.http.ResponseEntity;
import store.dto.DishRequestDto;
import store.dto.DishResponseDto;

public interface DishService {

    ResponseEntity<?> create(DishRequestDto requestDto);

    ResponseEntity<DishResponseDto> findDishById(Long dishId);

    ResponseEntity<DishResponseDto> updateDishById(Long dishId, DishRequestDto requestDto);
}
