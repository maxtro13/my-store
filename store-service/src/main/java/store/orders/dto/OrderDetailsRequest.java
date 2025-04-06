package store.orders.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class OrderDetailsRequest {

    @NotNull(message = "${orderDetails.error.validation.dishId.null}")
    @Positive(message = "${orderDetails.error.validation.dishId.positive}")
    private Long dishId;

    @NotNull(message = "${orderDetails.error.validation.quantity.null}")
    @Positive(message = "${orderDetails.error.validation.quantity.positive}")
    private Integer quantity;

    @NotNull(message = "${orderDetails.error.validation.price.null}")
    @Positive(message = "${orderDetails.error.validation.price.positive}")
    private Double price;

    @NotNull(message = "${orderDetails.error.validation.name.null}")
    @NotBlank(message = "${orderDetails.error.validation.name.null}")
    @Size(min = 6, max = 32, message = "${orderDetails.error.validation.name.size}")
    private String name;
}
