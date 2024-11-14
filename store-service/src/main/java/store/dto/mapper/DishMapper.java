package store.dto.mapper;

import org.springframework.stereotype.Component;
import store.dto.DishRequestDto;
import store.dto.DishResponseDto;
import store.entity.Dish;

@Component
public class DishMapper {

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
        return new DishResponseDto(
                dish.getId(),
                dish.getName(),
                dish.getDescription(),
                dish.getCategory().name(),
                dish.getAvailability(),
                dish.getPrice());
    }

    public void updateEntity(Dish dish, DishRequestDto requestDto) {
        dish.setName(requestDto.getName());
        dish.setAvailability(requestDto.getAvailability());
        dish.setCategory(requestDto.getCategory());
        dish.setPrice(requestDto.getPrice());
        dish.setDescription(requestDto.getDescription());
    }
}
