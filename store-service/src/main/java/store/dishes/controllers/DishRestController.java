package store.dishes.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import store.dishes.dto.DishRequestDto;
import store.dishes.dto.DishResponseDto;
import store.dishes.service.DishService;

@RestController
@RequestMapping("/store-api/v1/dishes/{dishId}")
@RequiredArgsConstructor
public class DishRestController {
    private final DishService dishService;

    @GetMapping
    public ResponseEntity<?> getDishById(@PathVariable("dishId") Long dishId) {
        return dishService.findDishById(dishId);
    }

    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<DishResponseDto> updateDishById(
            @PathVariable("dishId") Long dishId,
            @ModelAttribute @Valid DishRequestDto dto
    )throws Exception {
        return dishService.updateDishById(dishId, dto);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteDishById(@PathVariable("dishId") Long dishId) {
        dishService.deleteDishById(dishId);
        return ResponseEntity.noContent()
                .build();
    }
}
