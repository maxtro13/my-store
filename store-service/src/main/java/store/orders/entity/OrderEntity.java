package store.orders.entity;


import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.relational.core.mapping.Table;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table("orders")
@Builder(toBuilder = true)
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long orderId;

    @Column(name = "total_price")
    private Double totalPrice;

    @Column(name = "order_status")
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus = OrderStatus.PENDING;

    @Column(name = "delivery_address", length = 500)
    private String deliveryAddress;

}
