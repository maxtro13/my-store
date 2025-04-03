package store.orders.service;

import org.springframework.stereotype.Service;
import store.orders.dto.CreateOrderRequest;
import store.orders.dto.OrderEntityResponse;
import store.orders.entity.OrderStatus;

@Service
public interface OrderService {

    OrderEntityResponse createOrder(CreateOrderRequest orderRequest);


    OrderEntityResponse getOrderById(Long id);

    OrderEntityResponse updateOrderStatusById(OrderStatus orderStatus, Long id);
}
