package store.orders.repository;

import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import store.orders.entity.OrderEntity;

@Repository
public interface OrderRepository extends R2dbcRepository<OrderEntity, Long> {
}
