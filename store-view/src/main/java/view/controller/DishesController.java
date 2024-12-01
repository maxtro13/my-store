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
<<<<<<< HEAD
    public String getDishListByCategory(Model model, @RequestParam(name = "category", required = false) String category,
                                        @RequestParam(required = false, name = "reset") String reset) {
//        if(reset !=null && reset.equals("true")){
//            category=null;
//            return "redirect:/"
//        }
        if (category == null || category.isEmpty()) {
            return "redirect:/store/dishes/list";
        } else if (category.equals("all")) {
            model.addAttribute("dishes", this.dishRestClient.getAllDishes());

        }
=======
    public String getDishListByCategory(Model model, @RequestParam(name = "category", required = false) String category) {
        if (category != null) {
            if (category.equals("all")) {
                model.addAttribute("dishes", this.dishRestClient.getAllDishes());
                return "store/dishes/list";
>>>>>>> feature/img

            } else {
                model.addAttribute("dishes", this.dishRestClient.getAllDishesByCategory(category));
                model.addAttribute("category", category);
                return "store/dishes/list";

<<<<<<< HEAD
        return "store/dishes/list";
    }

    //todo Сделать так чтобы один ендпоинт возвращал мог обработать разные значения получение с категорией и без нее
    // путем задания значения all для кнопки, тогда в таблице будет возвращаться все товары
    // либо как-то еще
    @PostMapping("create")
    public String createNewDish(DishDtoResponse dishDto, Model model) {
        Dish dish = this.dishRestClient
                .createDish(dishDto.name(), dishDto.description(),
                        dishDto.category(), dishDto.availability(), dishDto.price());
        return "redirect:/store/dishes/%d".formatted(dish.id());
=======
            }
        } else {
            return "store/dishes/list";
        }
>>>>>>> feature/img
    }


    @PostMapping(value = "create")
    public String createNewDish(@ModelAttribute DishDtoRequest requestDto,
                                @RequestParam(name = "image", required = false) MultipartFile image, Model model) {
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
