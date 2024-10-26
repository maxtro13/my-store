package store.dto;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import store.domain.entity.Category;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DishRequestDto {

    private String name;

    private String desString;

    private Category category;

    private Boolean availability;

    @Digits(integer = 5, fraction = 2)
    @Positive
    private Double price;

}
