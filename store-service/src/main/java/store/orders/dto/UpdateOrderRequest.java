package store.orders.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import store.orders.entity.OrderStatus;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class UpdateOrderRequest {

    @NotBlank(message = "${order.error.validation.deliveryAddress.null}")
    @NotNull(message = "${order.error.validation.deliveryAddress.null}")
    @Size(min = 5, max = 128, message = "${order.error.validation.deliveryAddress.size}")
    private String deliveryAddress;
    @NotNull(message = "${order.error.validation.orderStatus.null}")
    private OrderStatus orderStatus;

    @Size(max = 15)
    private List<OrderDetailsRequest> orderDetails = new ArrayList<>();

}
