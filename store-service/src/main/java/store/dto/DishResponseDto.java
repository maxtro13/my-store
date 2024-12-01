package store.dto;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import store.entity.Category;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DishResponseDto {
    private Long id;

    private String name;

    private String description;

    @Enumerated(EnumType.STRING)
    private String category;

    private Boolean availability;
    private Double price;

    private Long imageId;
}
