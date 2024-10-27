package store.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import store.service.DishService;

@RestController
@RequestMapping("/store-api/v1/dishes/{dishId}")
@RequiredArgsConstructor
public class DishRestController {
    private final DishService dishService;

    @GetMapping
    public ResponseEntity<?> getDishById(@PathVariable("dishId") Long dishId){
        return   dishService.findDishById(dishId);
    }
}
