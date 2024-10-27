package store.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import store.dto.DishRequestDto;
import store.service.DishService;

@RestController
@RequestMapping("/store-api/v1/dishes")
@RequiredArgsConstructor
public class DishesRestController {

    private final DishService dishService;

    @PostMapping
    public ResponseEntity<?> addDish(@RequestBody @Valid DishRequestDto requestDto) {
        return dishService.create(requestDto);
    }
}
