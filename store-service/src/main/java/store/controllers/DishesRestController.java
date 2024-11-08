package store.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpServerErrorException;
import store.dto.DishRequestDto;
import store.dto.DishResponseDto;
import store.entity.Category;
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
    public ResponseEntity<List<DishResponseDto>> getAllDishesByCategory(
            @RequestParam(name = "category", required = false) Category category) {

        return dishService.findAllDishesByCategory(category);
    }

    @GetMapping("/all")
    public ResponseEntity<List<DishResponseDto>> getAllDishes() {
        return dishService.findAll();
    }
}
