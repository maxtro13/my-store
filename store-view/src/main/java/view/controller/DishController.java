package view.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import view.client.StoreRestClient;
import view.dto.DishDtoRequest;
import view.entity.Dish;

import java.util.HashMap;
import java.util.NoSuchElementException;

@Controller
@RequestMapping(("/store/dishes/{dishId:\\d+}"))
@RequiredArgsConstructor
public class DishController {

    private final StoreRestClient storeRestClient;
    @ModelAttribute("dish")
    public Dish dish(@PathVariable("dishId") Long dishId, Model model) {
        Dish dish = this.storeRestClient.findDishById(dishId)
                .orElseThrow(() -> new NoSuchElementException("Not found"));
        if (dish.getImageId() !=null) {
            model.addAttribute("image", this.storeRestClient.getImageById(dish.getImageId()));
        }else{
            dish.setImageUrl("/images/default.jpg");
        }
        return dish;
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
        this.storeRestClient.updateDish(dishId, dtoRequest, image);
        return "redirect:/store/dishes/%d".formatted(dishId);
    }

    @PostMapping("/delete")
    public String deleteDish(Dish dish) {
        this.storeRestClient.deleteDish(dish.getId());
        return "redirect:/store/dishes/category";
    }
}
