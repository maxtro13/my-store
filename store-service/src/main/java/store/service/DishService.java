package store.service;

import org.springframework.http.ResponseEntity;
import store.dto.DishRequestDto;
import store.dto.DishResponseDto;

public interface DishService {

    ResponseEntity<DishResponseDto> create(DishRequestDto requestDto);
}
