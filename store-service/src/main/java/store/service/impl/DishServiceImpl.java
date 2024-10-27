package store.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;
import store.domain.entity.Dish;
import store.dto.DishRequestDto;
import store.dto.DishResponseDto;
import store.dto.mapper.DishMapper;
import store.repositories.DishRepository;
import store.service.DishService;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DishServiceImpl implements DishService {

    private final DishRepository dishRepository;
    private final DishMapper dishMapper;

    @Transactional
    @Override
    public ResponseEntity<?> create(DishRequestDto requestDto) {
        if (dishRepository.existsByNameContainingIgnoreCase(requestDto.getName())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Позиция с таким названием уже существует");
        }
        DishResponseDto responseDto = dishMapper.toDto(
                dishRepository.save(dishMapper.toEntity(requestDto)));

        return ResponseEntity.created(UriComponentsBuilder.newInstance()
                        .replacePath("/store-api/v1/dishes/{dishId}")
                        .build(responseDto.getId()))
                .body(String.format("Id:%s", responseDto.getId()));
    }

    @Override
    public ResponseEntity<DishResponseDto> findDishById(Long dishId) {

        return ResponseEntity.ok(
                dishMapper.toDto(
                        dishRepository.findById(dishId)
                                .orElseThrow(
                                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Dish not found"))
                )
        );
    }

    @Transactional
    @Override
    public ResponseEntity<DishResponseDto> updateDishById(Long dishId, DishRequestDto requestDto) {
        Dish dish = dishRepository.findById(dishId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Dish not found"));
        dishMapper.updateEntity(dish, requestDto);
        dishRepository.save(dish);
        return null;
    }

}
