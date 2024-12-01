package store.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import store.dto.DishRequestDto;
import store.dto.DishResponseDto;
import store.entity.Category;

import java.util.List;

public interface DishService {

    ResponseEntity<?> create(DishRequestDto requestDto);

    ResponseEntity<DishResponseDto> findDishById(Long dishId);

    ResponseEntity<DishResponseDto> updateDishById(Long dishId, DishRequestDto requestDto);

    void deleteDishById(Long dishId);

    ResponseEntity<List<DishResponseDto>> findAllDishesByCategory(Category category);

    ResponseEntity<List<DishResponseDto>> findAll();
}
