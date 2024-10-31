package view.client.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClient;
import view.client.DishRestClient;
import view.dto.NewDishDto;
import view.entity.Dish;

import java.util.List;

@RequiredArgsConstructor
public class DishRestClientImpl implements DishRestClient {

    private static final ParameterizedTypeReference<List<Dish>> PRODUCTS_TYPE_REFERENCE =
            new ParameterizedTypeReference<List<Dish>>() {
            };

    private final RestClient restClient;


    @Override
    public Dish createDish(String name, String description, String category, Boolean availability, Double price) {
        return this.restClient
                .post()
                .uri("/store-api/v1/dishes")
                .contentType(MediaType.APPLICATION_JSON)
                .body(new NewDishDto(name, description, category, availability, price))
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
    public Dish findDishById(Long dishId) {
        return this.restClient
                .get()
                .uri("/store-api/v1/dishes/{dishId}", dishId)
                .retrieve()
                .body(Dish.class);
    }


}
