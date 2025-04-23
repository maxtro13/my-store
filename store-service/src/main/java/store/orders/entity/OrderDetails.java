package store.orders.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;

@org.springframework.data.relational.core.mapping.Table("store.order_details")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder(toBuilder = true)
public class OrderDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column("item_id")
    private Long id;

    @Column("order_id")
    @JsonBackReference
    private Long orderId;

    @Column("dish_id")
    private Long dishId;

    @Column("quantity")
    private Integer quantity;

    @Column("fixed_price")
    private Double fixedPrice;

    @Column("name")
    private String name;

}
