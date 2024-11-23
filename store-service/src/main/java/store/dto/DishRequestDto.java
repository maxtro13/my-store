package store.dto;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;
import store.entity.Category;

import java.util.Optional;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DishRequestDto {

    @NotBlank(message = "")
    @Size(min = 3, max = 150)
    private String name;

    @Size(min = 10, max = 660)
    @NotBlank
    private String description;

    @NotNull
    private Category category;

    @NotNull
    private Boolean availability;

    @Digits(integer = 5, fraction = 2)
    @Positive
    @NotNull
    private Double price;

    @Nullable
    private MultipartFile image;
}
