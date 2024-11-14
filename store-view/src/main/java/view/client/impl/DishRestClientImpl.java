package view.client.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClient;
import view.client.DishRestClient;
import view.dto.DishDtoRequest;
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
                .body(new DishDtoRequest(name, description, category, availability, price))
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
    public void updateDish(Long dishId,
                           String name, String description,
                           String category, Boolean availability, Double price) {
        this.restClient
                .put()
                .uri("/store-api/v1/dishes/{dishId}", dishId)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new DishDtoRequest(name, description, category, availability, price))
                .retrieve()
                .toBodilessEntity();
        //Продолжить, ты изменил обновление на ресте нужно понять как сделать тут все грамотно
    }

    @Override
    public List<Dish> getAllDishes() {
        return this.restClient
                .get()
                .uri("/store-api/v1/dishes/all")
                .retrieve()
                .body(PRODUCTS_TYPE_REFERENCE);
    }

    @Override
    public void deleteDish(Long dishId) {
        this.restClient
                .delete()
                .uri("/store-api/v1/dishes/{dishId}", dishId)
                .retrieve()
                .toBodilessEntity();
    }


}
