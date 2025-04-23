package store.orders.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;
import store.dishes.repositories.DishRepository;
import store.orders.dto.CreateOrderRequest;
import store.orders.dto.OrderDetailsRequest;
import store.orders.dto.OrderEntityResponse;
import store.orders.dto.UpdateOrderRequest;
import store.orders.entity.OrderDetails;
import store.orders.entity.OrderEntity;
import store.orders.entity.OrderStatus;
import store.orders.mapper.OrderMapper;
import store.orders.repository.OrderDetailsRepository;
import store.orders.repository.OrderRepository;
import store.orders.service.OrderService;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(transactionManager = "connectionFactoryTransactionManager")
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final DishRepository dishRepository;
    private final OrderDetailsRepository orderDetailsRepository;
    private final OrderMapper orderMapper;


    @Override
    public Mono<ResponseEntity<OrderEntityResponse>> createOrder(CreateOrderRequest orderRequest) {
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
                    detail.setOrderId(order.getOrderId());
                    log.info("----Order details :{}", detail);
                    return detail;
                })
                .collect(Collectors.toList());
        order.setTotalPrice(orderDetails.stream()
                .mapToDouble(orderDetails1 ->
                        orderDetails1.getFixedPrice() * orderDetails1.getQuantity())
                .sum());
        return this.orderRepository.save(order)
                .flatMap(savedOrder -> {
                    orderDetails.forEach(detail -> detail.setOrderId(savedOrder.getOrderId()));
                    return orderDetailsRepository.saveAll(orderDetails)
                            .collectList()
                            .map(details -> {
                                URI uri = UriComponentsBuilder.newInstance()
                                        .replacePath("/store-api/v1/orders/{orderId}")
                                        .build(savedOrder.getOrderId());
                                return ResponseEntity.created(uri)
                                        .body(orderMapper.toOrderEntityResponse(savedOrder));
                            });
                });
    }


    @Override
    @Transactional(transactionManager = "connectionFactoryTransactionManager", readOnly = true)
    public Mono<ResponseEntity<OrderEntityResponse>> getOrderById(Long id) {

        return orderRepository.findById(id)
                .map(orderMapper::toOrderEntityResponse)
                .map(ResponseEntity::ok)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Заказ с ID " + id + " не найден")));
    }


    @Override
    public Mono<ResponseEntity<OrderEntityResponse>> updateOrderStatusById(OrderStatus orderStatus, Long id) {

        return orderRepository.findById(id)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Заказ с ID " + id + " не найден")))
                .flatMap(orderEntity -> {
                    orderMapper.updateStatusOrderEntity(orderEntity, orderStatus);
                    return orderRepository.save(orderEntity)
                            .map(savedOrder -> ResponseEntity.ok(orderMapper.toOrderEntityResponse(savedOrder)));
                });
    }

    @Override
    public Mono<ResponseEntity<OrderEntityResponse>> updateFullOrderById(Long id, UpdateOrderRequest request) {
        return orderRepository.findById(id)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Заказ с ID " + id + " не найден")))
                .flatMap(orderEntity -> {
                    orderMapper.updateFullOrderById(orderEntity, request);
                    return orderRepository.save(orderEntity)
                            .map(savedOrder -> ResponseEntity.ok(orderMapper.toOrderEntityResponse(savedOrder)));
                })
                ;
    }

    @Override
    public Mono<ResponseEntity<Void>> deleteOrderById(Long id) {
        return orderRepository.existsById(id)
                .flatMap(exist -> {
                    if (!exist) {
                        return Mono.error((new ResponseStatusException(HttpStatus.NOT_FOUND,
                                "Заказ с ID " + id + " не найден")));
                    }
                    return this.orderRepository.deleteById(id)
                            .then(Mono.just(ResponseEntity.noContent().build()));
                });
    }
}