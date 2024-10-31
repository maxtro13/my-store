package store.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import store.dto.DishRequestDto;
import store.dto.DishResponseDto;
import store.service.DishService;

@RestController
@RequestMapping("/store-api/v1/dishes/{dishId}")
@RequiredArgsConstructor
public class DishRestController {
    private final DishService dishService;

    @GetMapping
    public ResponseEntity<?> getDishById(@PathVariable("dishId") Long dishId) {
        return dishService.findDishById(dishId);
    }

    @PutMapping
    public ResponseEntity<DishResponseDto> updateDishById(
            @PathVariable("dishId") Long dishId,
            @RequestBody DishRequestDto requestDto
    ) {
        return dishService.updateDishById(dishId, requestDto);
    }

    @DeleteMapping
    public ResponseEntity<String> deleteDishById(@PathVariable("dishId") Long dishId) {

        return dishService.deleteDishById(dishId);
    }
}
