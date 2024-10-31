package store.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import store.entity.Category;
import store.dto.DishRequestDto;
import store.dto.DishResponseDto;
import store.service.DishService;

import java.util.List;

@RestController
@RequestMapping("/store-api/v1/dishes")
@RequiredArgsConstructor
public class DishesRestController {

    private final DishService dishService;

    @PostMapping
    public ResponseEntity<?> addDish(@RequestBody @Valid DishRequestDto requestDto) {
        return dishService.create(requestDto);
    }

    @GetMapping
    public ResponseEntity<List<DishResponseDto>> getAllDishesByCategory(@RequestParam(name = "category", required = false) Category category) {
        return dishService.findAllDishesByCategory(category);
    }

}
