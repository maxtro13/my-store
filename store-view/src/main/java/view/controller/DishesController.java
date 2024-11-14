package view.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import view.client.DishRestClient;
import view.dto.DishDtoResponse;
import view.entity.Dish;

@RequiredArgsConstructor
@Controller
@RequestMapping("store/dishes")
public class DishesController {

    private final DishRestClient dishRestClient;


    @GetMapping
    public String getDishListPage() {
        return "redirect:/store/dishes/category";
    }

    @GetMapping("category")
    public String getDishListByCategory(Model model, @RequestParam(name = "category", required = false) String category) {
        if (category != null) {
            if (category.equals("all")) {
                model.addAttribute("dishes", this.dishRestClient.getAllDishes());
                return "store/dishes/list";

            } else {
                model.addAttribute("dishes", this.dishRestClient.getAllDishesByCategory(category));
                model.addAttribute("category", category);
                return "store/dishes/list";

            }
        } else {
            return "store/dishes/list";
        }
    }


    @PostMapping("create")
    public String createNewDish(DishDtoResponse dishDto, Model model) {
        Dish dish = this.dishRestClient
                .createDish(dishDto.name(), dishDto.description(),
                        dishDto.category(), dishDto.availability(), dishDto.price());
        return "redirect:/store/dishes/%d".formatted(dish.id());
    }

    @GetMapping("create")
    public String createNewDish(Model model) {
        Dish dish = new Dish(null, "", "", "", true, null);
        model.addAttribute(dish);
        return "store/dishes/create_dish";
    }
}
