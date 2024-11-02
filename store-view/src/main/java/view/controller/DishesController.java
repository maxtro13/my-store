package view.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
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
        return "redirect:/store/dishes/list";
    }

    @GetMapping("category")
    public String getDishListByCategory(Model model, @RequestParam(name = "category", required = false) String category,
                                        @RequestParam(required = false, name = "reset") String reset) {
//        if(reset !=null && reset.equals("true")){
//            category=null;
//            return "redirect:/"
//        }
        model.addAttribute("dishes", this.dishRestClient.getAllDishesByCategory(category));
        model.addAttribute("category", category);

        return "store/dishes/list";
    }

    @PostMapping("create")
    public String createNewDish(DishDtoResponse dishDto, Model model) {
        Dish dish = this.dishRestClient
                .createDish(dishDto.name(), dishDto.description(),
                        dishDto.category(), dishDto.availability(), dishDto.price());
        return "redirect:/store/dishes/%d".formatted(dish.id());
    }

}
