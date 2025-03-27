package store.dishes.service.impl;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;
import store.dishes.dto.DishRequestDto;
import store.dishes.dto.DishResponseDto;
import store.dishes.dto.mapper.DishMapper;
import store.dishes.entity.Category;
import store.dishes.entity.Dish;
import store.dishes.entity.Image;
import store.dishes.repositories.DishRepository;
import store.dishes.service.DishService;
import store.dishes.service.ImageService;
import store.dishes.service.YandexDiskService;

import java.util.List;
import java.util.stream.StreamSupport;


@Service
@RequiredArgsConstructor
public class DishServiceImpl implements DishService {

    private static final Logger log = LoggerFactory.getLogger(DishServiceImpl.class);
    private final DishRepository dishRepository;
    private final DishMapper dishMapper;
    private final ImageService imageService;
    private final YandexDiskService yandexDiskService;

    @Transactional
    @Override
    public ResponseEntity<?> create(DishRequestDto requestDto) throws Exception {
        if (dishRepository.existsByNameContainingIgnoreCase(requestDto.getName().trim())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Позиция с таким названием уже существует");
        }
        DishResponseDto responseDto;
        Dish dish = dishMapper.toEntity(requestDto);
        if (requestDto.getImage() == null || requestDto.getImage().isEmpty()) {
            responseDto = dishMapper.toDto(
                    dishRepository.save(dish));

        } else if (!(requestDto.getImage().getContentType().equals("image/png") || requestDto.getImage().getContentType().equals("image/jpeg"))) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Тип файла должен быть jpeg или png");

        } else {
            uploadFile(requestDto, dish);
            responseDto = dishMapper.toDto(dishRepository.save(dish));
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
                        dishRepository.findByIdWithImage(dishId)
                                .orElseThrow(
                                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Dish not found"))
                )
        );
    }

    @Transactional
    @Override
    public ResponseEntity<DishResponseDto> updateDishById(Long dishId, DishRequestDto requestDto) throws Exception {
        Dish dish = dishRepository.findByIdWithImage(dishId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Dish not found"));
        if (requestDto.getImage() == null || requestDto.getImage().isEmpty()) {
            dishMapper.updateEntity(dish, requestDto);
            return ResponseEntity.ok(dishMapper.toDto(dishRepository.save(dish)));
        } else {
            if (!(requestDto.getImage().getOriginalFilename().equals(dish.getImage().getOriginalFileName()))) {
                uploadFile(requestDto, dish);
            }
            dishMapper.updateEntity(dish, requestDto);
            return ResponseEntity.ok(dishMapper.toDto(dishRepository.save(dish)));
        }
    }


    @Transactional
    @Override
    public void deleteDishById(Long dishId) {
        dishRepository.findByIdWithImage(dishId)
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

    private void uploadFile(DishRequestDto requestDto, Dish dish) throws Exception {
        String filePathOnDisk = "food_image_" + requestDto.getImage().getOriginalFilename();
        String imageUrl = this.yandexDiskService.uploadFile(filePathOnDisk,
                requestDto.getImage().getBytes(),
                requestDto.getImage().getContentType());
        dish.setImageUrl(imageUrl);
        Image image = imageService.saveImage(requestDto.getImage(), imageUrl);
        dish.setImage(image);
    }


}
