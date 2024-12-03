package view.client.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;
import org.springframework.web.multipart.MultipartFile;
import view.client.DishRestClient;
import view.dto.DishDtoRequest;
import view.entity.Dish;
import view.utils.exceptions.BadRequestException;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class DishRestClientImpl implements DishRestClient {

    private static final ParameterizedTypeReference<List<Dish>> PRODUCTS_TYPE_REFERENCE =
            new ParameterizedTypeReference<List<Dish>>() {
            };

    private final RestClient restClient;


    @Override
    public Dish createDish(DishDtoRequest requestDto, MultipartFile image) {
        try {
            return this.restClient
                    .post()
                    .uri("/store-api/v1/dishes")
                    .contentType(MediaType.MULTIPART_FORM_DATA)
                    .body(bodyForMethod(requestDto, image))
                    .retrieve()
                    .body(Dish.class);
        } catch (HttpClientErrorException.BadRequest exception) {
            ProblemDetail problemDetail = exception.getResponseBodyAs(ProblemDetail.class);
            throw new BadRequestException((List<String>) problemDetail.getProperties().get("errors"));

        }
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
    public Dish updateDish(Long dishId,
                           DishDtoRequest requestDto, MultipartFile image) {
        try {

          return this.restClient
                    .put()
                    .uri("/store-api/v1/dishes/{dishId}", dishId)
                    .contentType(MediaType.MULTIPART_FORM_DATA)
                    .body(bodyForMethod(requestDto,image))
                    .retrieve()
                    .body(Dish.class);
        } catch (HttpClientErrorException.BadRequest exception) {
            ProblemDetail problemDetail = exception.getResponseBodyAs(ProblemDetail.class);
            throw new BadRequestException((List<String>) problemDetail.getProperties().get("errors"));
        }
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

    private MultiValueMap<String, Object> bodyForMethod(DishDtoRequest requestDto, MultipartFile image) {
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("name", requestDto.name());
        body.add("description", requestDto.description());
        body.add("category", requestDto.category()); // .name() если enum
        body.add("availability", requestDto.availability().toString());
        body.add("price", requestDto.price() == null ? null : String.valueOf(requestDto.price()));
        body.add("image", image.getResource());
        return body;
    }


}
