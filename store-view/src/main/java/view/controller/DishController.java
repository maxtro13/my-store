package view.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import view.client.DishRestClient;
import view.dto.DishDtoRequest;
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

    @GetMapping("/edit")
    public String editDish() {
        return "store/dishes/edit_dish";
    }


    @PostMapping("/edit")
    public String updateDish(@ModelAttribute(value = "dish", binding = false) Dish dish,
                             DishDtoRequest dtoRequest) {
        this.dishRestClient.updateDish(dish.id(), dtoRequest.name(),
                dtoRequest.description(), dtoRequest.category(),
                dtoRequest.availability(), dtoRequest.price());
        return "redirect:/store/dishes/%d".formatted(dish.id());
    }

    @PostMapping("/delete")
    public String deleteDish(Dish dish) {
        this.dishRestClient.deleteDish(dish.id());
        return "redirect:/store/dishes/category";
    }
}
