package store.orders.entity;


import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table("store.orders")
@Builder(toBuilder = true)
public class OrderEntity {

    @Id
    @Column("id")
    private Long orderId;

    @Column("total_price")
    private Double totalPrice;

    @Column("order_status")
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus = OrderStatus.PENDING;

    @Column("delivery_address")
    private String deliveryAddress;

}
