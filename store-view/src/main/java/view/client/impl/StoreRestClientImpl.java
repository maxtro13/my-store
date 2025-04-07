package view.client.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import view.client.StoreRestClient;
import view.dto.DishDtoRequest;
import view.entity.Dish;
import view.entity.Image;
import view.utils.exceptions.BadRequestException;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class StoreRestClientImpl implements StoreRestClient {

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

//    @Override
//    public Mono<Dish> createDish(DishDtoRequest requestDto, MultipartFile image) {
//        return this.webClient
//                .post()
//                .uri("/store-api/v1/dishes")
//                .contentType(MediaType.MULTIPART_FORM_DATA)
//                .body(BodyInserters.fromMultipartData(bodyForMethod(requestDto, image)))
//                .retrieve()
//                .onStatus(HttpStatusCode::is4xxClientError, response ->
//                        response.bodyToMono(ProblemDetail.class)
//                                .flatMap(problemDetail ->
//                                        Mono.error(new BadRequestException((List<String>) problemDetail.getProperties().get("errors")))
//                                )
//                )
//                .bodyToMono(Dish.class);
//    }

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
                           DishDtoRequest requestDto, MultipartFile image) {
        try {

            this.restClient
                    .put()
                    .uri("/store-api/v1/dishes/{dishId}", dishId)
                    .contentType(MediaType.MULTIPART_FORM_DATA)
                    .body(bodyForMethod(requestDto, image))
                    .retrieve()
                    .toBodilessEntity();
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

    @Override
    public Image getImageById(Long imageId) {
        return this.restClient
                .get()
                .uri("/store-api/v1/images/{imageId}", imageId)
                .retrieve()
                .body(Image.class);
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
