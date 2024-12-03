package view.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
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
    public String updateDish(@PathVariable Long dishId, @ModelAttribute DishDtoRequest dtoRequest,
                             @RequestParam(name = "image", required = false) MultipartFile image) {
        this.dishRestClient.updateDish(dishId, dtoRequest, image);
        return "redirect:/store/dishes/%d".formatted(dishId);
    }

    @PostMapping("/delete")
    public String deleteDish(Dish dish) {
        this.dishRestClient.deleteDish(dish.id());
        return "redirect:/store/dishes/category";
    }
}
