package store.orders.service;

import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;
import store.orders.dto.CreateOrderRequest;
import store.orders.dto.OrderEntityResponse;
import store.orders.dto.UpdateOrderRequest;
import store.orders.entity.OrderStatus;

public interface OrderService {

    Mono<ResponseEntity<OrderEntityResponse>> createOrder(CreateOrderRequest orderRequest);


    Mono<ResponseEntity<OrderEntityResponse>> getOrderById(Long id);

    Mono<ResponseEntity<OrderEntityResponse>> updateOrderStatusById(OrderStatus orderStatus, Long id);

    Mono<ResponseEntity<OrderEntityResponse>> updateFullOrderById(Long id, UpdateOrderRequest request);

    Mono<ResponseEntity<Void>> deleteOrderById(Long id);

}
