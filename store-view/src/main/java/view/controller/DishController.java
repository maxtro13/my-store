package view.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import view.client.DishRestClient;
import view.dto.DishDtoRequest;
import view.dto.DishDtoResponse;
import view.entity.Dish;

import java.util.NoSuchElementException;

@Controller
@RequestMapping(("/store/dishes/{dishId:\\d+}"))
@RequiredArgsConstructor
public class DishController {

    private final DishRestClient dishRestClient;

    @ModelAttribute("dish")
    public Dish dish(@PathVariable("dishId") Long dishId) {
        return this.dishRestClient.findDishById(dishId)
                .orElseThrow(() -> new NoSuchElementException("Not found"));
    }

    @GetMapping
    public String getDish() {
        return "store/dishes/dish";
    }


}
