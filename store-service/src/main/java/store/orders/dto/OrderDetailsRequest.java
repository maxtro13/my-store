package store.orders.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class OrderDetailsRequest {
    private Long dishId;
    private Integer quantity;
    private Double price;
}
//todo добавить название к содержимому заказа, решить проблему почему не сохраняется содержимое