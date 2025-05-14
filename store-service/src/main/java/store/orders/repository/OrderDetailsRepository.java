package store.orders.repository;

import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import store.orders.entity.OrderDetails;

@Repository
public interface OrderDetailsRepository extends R2dbcRepository<OrderDetails, Long> {
    Flux<OrderDetails> findAllByOrderId(Long orderId);

    Mono<Void> deleteAllByOrderId(Long orderId);
}
