package store.orders.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;
import store.dishes.repositories.DishRepository;
import store.orders.dto.CreateOrderRequest;
import store.orders.dto.OrderDetailsRequest;
import store.orders.dto.OrderEntityResponse;
import store.orders.dto.UpdateOrderRequest;
import store.orders.entity.OrderDetails;
import store.orders.entity.OrderEntity;
import store.orders.entity.OrderStatus;
import store.orders.mapper.OrderMapper;
import store.orders.repository.OrderRepository;
import store.orders.service.OrderService;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final DishRepository dishRepository;
    private final OrderMapper orderMapper;

    @Override
    @Transactional
    public ResponseEntity<OrderEntityResponse> createOrder(CreateOrderRequest orderRequest) {
        Set<Long> ids = orderRequest.getOrderDetails()
                .stream()
                .map(OrderDetailsRequest::getDishId)
                .collect(Collectors.toSet());

        if (!dishRepository.existsAllByIds(ids, ids.size())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Блюда не найдены");
        }

        OrderEntity order = new OrderEntity();
        order.setDeliveryAddress(Optional.ofNullable(orderRequest.getDeliveryAddress())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Адрес доставки обязателен")));

        List<OrderDetails> orderDetails = orderRequest.getOrderDetails()
                .stream()
                .map(item -> {
                    OrderDetails detail = new OrderDetails();
                    detail.setDishId(item.getDishId());
                    detail.setQuantity(item.getQuantity());
                    detail.setFixedPrice(item.getPrice());
                    detail.setName(item.getName());
                    detail.setOrder(order);
                    log.info("----Order details :{}", detail);
                    return detail;
                })
                .toList();
        order.setOrderDetails(orderDetails);
        return ResponseEntity.created(UriComponentsBuilder.newInstance()
                        .replacePath("/store-api/v1/orders/{orderId}")
                        .build(order.getOrderId()))
                .body(orderMapper.toOrderEntityResponse(orderRepository.save(order)));
    }

    @Transactional(readOnly = true)
    @Override
    public ResponseEntity<OrderEntityResponse> getOrderById(Long id) {
        return ResponseEntity.ok(orderMapper.toOrderEntityResponse(orderRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Блюдо с таким айди не найдено"))));
    }


    @Override
    @Transactional
    public ResponseEntity<OrderEntityResponse> updateOrderStatusById(OrderStatus orderStatus, Long id) {
        OrderEntity orderEntity = orderRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Блюдо с таким айди не найдено"));
        orderMapper.updateStatusOrderEntity(orderEntity, orderStatus);
        return ResponseEntity.ok(orderMapper.toOrderEntityResponse(orderRepository.save(orderEntity)));
    }

    @Override
    @Transactional
    public ResponseEntity<OrderEntityResponse> updateFullOrderById(Long id, UpdateOrderRequest request) {
        OrderEntity orderEntity = orderRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Заказ с ID " + id + " не найден"
                ));

        orderMapper.updateFullOrderById(orderEntity, request);

        return ResponseEntity.ok(
                orderMapper.toOrderEntityResponse(
                        orderRepository.save(orderEntity)
                )
        );
    }

    @Override
    public void deleteOrderById(Long id) {
        if (!this.orderRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Заказ с ID " + id + " не найден");
        }
        this.orderRepository.deleteById(id);
    }
}
