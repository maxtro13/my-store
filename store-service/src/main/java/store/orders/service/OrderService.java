package store.orders.service;

import org.springframework.http.ResponseEntity;
import store.orders.dto.CreateOrderRequest;
import store.orders.dto.OrderEntityResponse;
import store.orders.dto.UpdateOrderRequest;
import store.orders.entity.OrderStatus;

public interface OrderService {

    ResponseEntity<OrderEntityResponse> createOrder(CreateOrderRequest orderRequest);


    ResponseEntity<OrderEntityResponse> getOrderById(Long id);

    ResponseEntity<OrderEntityResponse> updateOrderStatusById(OrderStatus orderStatus, Long id);

    ResponseEntity<OrderEntityResponse> updateFullOrderById(Long id, UpdateOrderRequest request);

    void deleteOrderById(Long id);

}
