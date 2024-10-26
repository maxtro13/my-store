package store.dto;

import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import store.domain.entity.Category;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DishResponseDto {
    private Long id;

    private String name;

    private String description;

    @Enumerated(EnumType.STRING)
    private Category category;

    private Boolean availability;
    private Double price;
}
