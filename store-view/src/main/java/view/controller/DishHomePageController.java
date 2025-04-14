package view.controller;

import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import view.client.StoreRestClient;
import view.entity.Dish;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/")
@Controller
public class DishHomePageController {
    private final StoreRestClient storeRestClient;

    @GetMapping
    public String showHomePage(Model model) {
//        List<Dish> dishes = this.storeRestClient.getAllDishes();
//        model.addAttribute("dishes", Lists.partition(dishes, 3).stream()
//                .flatMap(List::stream)
//                .toList());

        List<Dish> rolls = this.storeRestClient.getAllDishesByCategory("ROLLS");
        model.addAttribute("rolls", Lists.partition(rolls, 3).stream()
                .flatMap(List::stream)
                .toList());

        List<Dish> soups = this.storeRestClient.getAllDishesByCategory("SOUPS");
        model.addAttribute("soups", Lists.partition(soups, 3).stream()
                .flatMap(List::stream)
                .toList());

        List<Dish> desserts = this.storeRestClient.getAllDishesByCategory("DESSERTS");
        model.addAttribute("desserts", Lists.partition(desserts, 3).stream()
                .flatMap(List::stream)
                .toList());
        List<Dish> pizzas = this.storeRestClient.getAllDishesByCategory("PIZZAS");
        model.addAttribute("pizzas", Lists.partition(pizzas, 3).stream()
                .flatMap(List::stream)
                .toList());

        return "store/dishes/main";
    }

}
