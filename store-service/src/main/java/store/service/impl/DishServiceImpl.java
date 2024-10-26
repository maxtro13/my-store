package store.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;
import store.dto.DishRequestDto;
import store.dto.DishResponseDto;
import store.dto.mapper.DishMapper;
import store.repositories.DishRepository;
import store.service.DishService;

@Service
@RequiredArgsConstructor
public class DishServiceImpl implements DishService {

    private final DishRepository dishRepository;
    private final DishMapper dishMapper;

    @Override
    public ResponseEntity<DishResponseDto> create(DishRequestDto requestDto) {
        if (dishRepository.existsByName(requestDto.getName())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Позиция с таким названием уже существует");
        }
        DishResponseDto responseDto = dishMapper.toDto(
                dishRepository.save(dishMapper.toEntity(requestDto)));

        return ResponseEntity.created(UriComponentsBuilder.newInstance()
                        .replacePath("store-api/v1/dishes/{dishId]")
                        .build(responseDto.getId()))
                .body(responseDto);
    }

}
