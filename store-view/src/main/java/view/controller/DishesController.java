package view.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import view.client.DishRestClient;
import view.dto.DishDtoRequest;
import view.entity.Dish;
import view.utils.exceptions.BadRequestException;

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


    @PostMapping(value = "create")
    public String createNewDish(@ModelAttribute DishDtoRequest requestDto,
                                @RequestParam(required = false) MultipartFile image, Model model) {
        try {
            Dish dish = this.dishRestClient
                    .createDish(requestDto, image);
            return "redirect:/store/dishes/%d".formatted(dish.id());
        } catch (BadRequestException exception) {
            model.addAttribute("errors", exception.getErrors());
            return "/store/dishes/create_dish";
        }
    }

    @GetMapping(value = "create")
    public String createNewDish(Model model) {
        Dish dish = new Dish(null, "", "", "", true, null, null);
        model.addAttribute(dish);
        return "store/dishes/create_dish";
    }

}
