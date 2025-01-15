package store.dishes.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    private String imageUrl;
    private Long imageId;
}
