package store.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;
import store.entity.Category;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DishRequestDto {

    @NotBlank(message = "{dish.error.validation.field.null}")
    @NotNull
    @Size(min = 6, max = 55, message = "{dish.error.validation.max_min}")
    private String name;


    @Size(min = 10, max = 1024, message = "{dish.error.validation.max_min}")
    @NotBlank(message = "{dish.error.validation.field.null}")
    @NotNull
    private String description;

    @NotNull(message = "{dish.error.validation.field.null}")
    private Category category;

    @NotNull(message = "{dish.error.validation.field.null}")
    private Boolean availability;

    @Digits(integer = 5, fraction = 2)
    @Positive
    private Double price;

    private MultipartFile image;
}
