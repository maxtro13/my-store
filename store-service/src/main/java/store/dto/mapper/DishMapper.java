package store.dto.mapper;

import org.springframework.stereotype.Component;
import store.entity.Dish;
import store.dto.DishRequestDto;
import store.dto.DishResponseDto;

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

    public void updateEntity(Dish dish, DishRequestDto dto) {
        dish.setName(dto.getName());
        dish.setAvailability(dto.getAvailability());
        dish.setCategory(dto.getCategory());
        dish.setPrice(dto.getPrice());
        dish.setDescription(dto.getDescription());
    }
}
