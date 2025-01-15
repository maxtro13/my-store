package store.dishes.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;
import store.dishes.entity.Category;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DishRequestDto {

    @NotBlank(message = "{dish.error.validation.name.null}")
    @NotNull
    @Size(min = 6, max = 55, message = "{dish.error.validation.name.max_min}")
    private String name;


    @Size(min = 10, max = 1024, message = "{dish.error.validation.description.max_min}")
    @NotBlank(message = "{dish.error.validation.description.null}")
    @NotNull
    private String description;

    @NotNull(message = "{dish.error.validation.category.null}")
    private Category category;

    @NotNull(message = "{dish.error.validation.availability.null}")
    private Boolean availability;

    @Digits(integer = 5, fraction = 2)
    @Positive
    @NotNull(message = "{dish.error.validation.price.null}")
    private Double price;

    private MultipartFile image;
}
