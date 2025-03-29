package store.orders.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import store.orders.entity.OrderDetails;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderDetails, Long> {
}
