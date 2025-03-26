package store.orders.service;

import org.springframework.stereotype.Service;
import store.dishes.dto.DishRequestDto;
import store.orders.dto.CreateOrderRequest;
import store.orders.entity.OrderEntity;

@Service
public interface OrderService {

    public OrderEntity createOrder(CreateOrderRequest orderRequest);
}
