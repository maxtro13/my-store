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
        List<Dish> dishes = this.storeRestClient.getAllDishes();
//        Map<String, List<Dish>> dishesByCategory = dishes.stream()
//                .collect(Collectors.groupingBy(Dish::getCategory));
//        model.addAttribute("dishesByCategory", dishesByCategory);
        model.addAttribute("dishes", Lists.partition(dishes, 3).stream()
                .flatMap(List::stream)
                .toList());
        return "store/dishes/main";
    }
}
