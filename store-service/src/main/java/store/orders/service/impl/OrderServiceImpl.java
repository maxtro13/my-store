package store.orders.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import store.dishes.entity.Dish;
import store.dishes.repositories.DishRepository;
import store.orders.dto.CreateOrderRequest;
import store.orders.dto.OrderItemRequest;
import store.orders.entity.OrderEntity;
import store.orders.entity.OrderItem;
import store.orders.repository.OrderItemRepository;
import store.orders.repository.OrderRepository;
import store.orders.service.OrderService;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final DishRepository dishRepository;
    private final OrderItemRepository orderItemRepository;

    @Override
    @Transactional
    public OrderEntity createOrder(CreateOrderRequest orderRequest) {
        OrderEntity order = new OrderEntity();
        order.setDeliveryAddress(orderRequest.getDeliveryAddress());

        List<Long> dishIds = orderRequest.getItems()
                .stream()
                .map(OrderItemRequest::getDishId)
                .toList();
        Map<Long, Dish> dishes = dishRepository.findAllById(dishIds)
                .stream()
                .collect(Collectors.toMap(Dish::getId, Function.identity()));
        List<OrderItem> orderItems = orderRequest.getItems()
                .stream()
                .map(orderItemRequest -> {
                    Dish dish = Optional.ofNullable(dishes.get(orderItemRequest.getDishId()))
                            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Dish not found"));
                    OrderItem orderItem = new OrderItem();
                    orderItem.setDish(dish);
                    orderItem.setQuantity(orderItemRequest.getQuantity());
                    orderItem.setFixedPrice(dish.getPrice());
                    orderItem.setOrder(order);
                    return orderItemRepository.save(orderItem);
                })
                .toList();

//todo разобраться в причине ошибки
        order.setItems(orderItems);
        return orderRepository.save(order);
    }
}
