package view.client.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClient;
import view.client.DishRestClient;
import view.dto.DishDtoResponse;
import view.entity.Dish;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class DishRestClientImpl implements DishRestClient {

    private static final ParameterizedTypeReference<List<Dish>> PRODUCTS_TYPE_REFERENCE =
            new ParameterizedTypeReference<List<Dish>>() {
            };

    private final RestClient restClient;


    @Override
    public Dish createDish(String name, String description,
                           String category, Boolean availability, Double price) {
        return this.restClient
                .post()
                .uri("/store-api/v1/dishes")
                .contentType(MediaType.APPLICATION_JSON)
                .body(new DishDtoResponse(name, description, category, availability, price))
                .retrieve()
                .body(Dish.class);
    }

    @Override
    public List<Dish> getAllDishesByCategory(String category) {

        return this.restClient
                .get()
                .uri("/store-api/v1/dishes?category={category}", category)
                .retrieve()
                .body(PRODUCTS_TYPE_REFERENCE);
    }

    @Override
    public Optional<Dish> findDishById(Long dishId) {
        return Optional.ofNullable(this.restClient
                .get()
                .uri("/store-api/v1/dishes/{dishId}", dishId)
                .retrieve()
                .body(Dish.class));
    }

    @Override
    public void updateDish(Dish dish, Long dishId) {
        this.restClient
                .put()
                .uri("/store-api/v1/dishes/{dishId}", dish)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new DishDtoResponse(dish.name(), dish.description(),
                        dish.category(), dish.availability(), dish.price()))
                .retrieve()
                .toBodilessEntity();


    }

    @Override
    public List<Dish> getAllDishes() {
        return this.restClient
                .get()
                .uri("/store-api/v1/dishes/all")
                .retrieve()
                .body(PRODUCTS_TYPE_REFERENCE);
    }


}
