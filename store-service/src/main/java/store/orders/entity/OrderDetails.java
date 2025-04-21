package store.orders.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Table(schema = "store", name = "order_details")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder(toBuilder = true)
public class OrderDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private Long id;

    @JoinColumn(name = "order_id", nullable = false)
    @JsonBackReference
    private Long orderId;


    @Column(name = "dish_id", nullable = false)
    private Long dishId;

    @Column(nullable = false)
    private Integer quantity;

    @Column(name = "fixed_price")
    private Double fixedPrice;

    @Column(name = "name")
    private String name;

}
