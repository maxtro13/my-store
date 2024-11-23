package store.dto.mapper;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.RequestContextFilter;
import store.dto.DishRequestDto;
import store.dto.DishResponseDto;
import store.entity.Dish;

@Component
public class DishMapper {

    private final RequestContextFilter requestContextFilter;

    public DishMapper(RequestContextFilter requestContextFilter) {
        this.requestContextFilter = requestContextFilter;
    }

    public Dish toEntity(DishRequestDto requestDto) {
        Dish dish = new Dish();
        dish.setName(requestDto.getName());
        dish.setCategory(requestDto.getCategory());
        dish.setDescription(requestDto.getDescription());
        dish.setPrice(requestDto.getPrice());
        dish.setAvailability(requestDto.getAvailability());

        return dish;
    }

    public DishResponseDto toDto(Dish dish) {
        if (dish.getImage() == null) {
            return new DishResponseDto(
                    dish.getId(),
                    dish.getName(),
                    dish.getDescription(),
                    dish.getCategory().name(),
                    dish.getAvailability(),
                    dish.getPrice(),
                    0L);
        } else {
            return new DishResponseDto(
                    dish.getId(),
                    dish.getName(),
                    dish.getDescription(),
                    dish.getCategory().name(),
                    dish.getAvailability(),
                    dish.getPrice(),
                    dish.getImage().getId());
        }
    }

    public void updateEntity(Dish dish, DishRequestDto requestDto) {
        dish.setName(requestDto.getName());
        dish.setAvailability(requestDto.getAvailability());
        dish.setCategory(requestDto.getCategory());
        dish.setPrice(requestDto.getPrice());
        dish.setDescription(requestDto.getDescription());
    }
}
