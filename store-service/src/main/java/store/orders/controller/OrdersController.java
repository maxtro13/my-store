package store.orders.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import store.orders.dto.CreateOrderRequest;
import store.orders.dto.OrderEntityResponse;
import store.orders.service.OrderService;

@RestController
@RequestMapping("/store-api/v1/orders")
@RequiredArgsConstructor
public class OrdersController {
    private final OrderService orderService;

    @PostMapping("/create")
    public Mono<ResponseEntity<OrderEntityResponse>> createOrder(@RequestBody @Valid CreateOrderRequest createOrderRequest) {
        return orderService.createOrder(createOrderRequest);
    }
}
