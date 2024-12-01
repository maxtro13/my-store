package store.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;
import store.dto.DishRequestDto;
import store.dto.DishResponseDto;
import store.dto.mapper.DishMapper;
import store.entity.Category;
import store.entity.Dish;
import store.entity.Image;
import store.repositories.DishRepository;
import store.service.DishService;
import store.service.ImageService;

import java.util.List;
import java.util.stream.StreamSupport;


@Service
@RequiredArgsConstructor
public class DishServiceImpl implements DishService {

    private final DishRepository dishRepository;
    private final DishMapper dishMapper;
    private final ImageService imageService;


    @Transactional
    @Override
    public ResponseEntity<?> create(DishRequestDto requestDto) {
        if (dishRepository.existsByNameContainingIgnoreCase(requestDto.getName().trim())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Позиция с таким названием уже существует");
        }
        DishResponseDto responseDto;
        Dish dish = dishMapper.toEntity(requestDto);
        if (requestDto.getImage() == null || requestDto.getImage().isEmpty()) {
            responseDto = dishMapper.toDto(
                    dishRepository.save(dish));
        } else {
            Image image = imageService.saveImage(requestDto.getImage());
            dish.setImage(image);
            responseDto = dishMapper.toDto(
                    dishRepository.save(dish));
        }
        return ResponseEntity.created(UriComponentsBuilder.newInstance()
                        .replacePath("/store-api/v1/dishes/{dishId}")
                        .build(responseDto.getId()))
                .body(responseDto);
    }

    @Override
    @Transactional(readOnly = true)
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
        if (requestDto.getImage() == null || requestDto.getImage().isEmpty()) {
            dishMapper.updateEntity(dish, requestDto);
            return ResponseEntity.ok(dishMapper.toDto(dishRepository.save(dish)));
        } else {
            Image image = imageService.saveImage(requestDto.getImage());
            dish.setImage(image);
            return ResponseEntity.ok(dishMapper.toDto(dishRepository.save(dish)));
        }
    }

    @Transactional
    @Override
    public void deleteDishById(Long dishId) {
        dishRepository.findById(dishId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Dish not found"));
        dishRepository.deleteById(dishId);
    }

    @Override
    public ResponseEntity<List<DishResponseDto>> findAllDishesByCategory(Category category) {

        return ResponseEntity.ok(
                dishRepository.findAllByCategory(category)
                        .stream()
                        .map(dishMapper::toDto)
                        .toList());
    }

    @Override
    public ResponseEntity<List<DishResponseDto>> findAll() {
        return ResponseEntity.ok(
                StreamSupport.stream(dishRepository.findAll().spliterator(), false)
                        .map(dishMapper::toDto)
                        .toList()
        );
    }


}
