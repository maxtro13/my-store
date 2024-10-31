package view.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import view.client.DishRestClient;
import view.entity.Dish;

@Controller
@RequestMapping(("/store/dishes/{dishId:\\d+}"))
@RequiredArgsConstructor
public class DishController {

    private final DishRestClient dishRestClient;

    @ModelAttribute("dish")
    public Dish dish(@PathVariable("dishId") Long dishId) {
        return this.dishRestClient.findDishById(dishId);
    }

}
