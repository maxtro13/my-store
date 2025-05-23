package store.orders.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import store.orders.dto.OrderEntityResponse;
import store.orders.dto.UpdateOrderRequest;
import store.orders.entity.OrderStatus;
import store.orders.service.OrderService;

@RestController
@RequestMapping("/store-api/v1/orders/{orderId}")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @GetMapping
    public ResponseEntity<OrderEntityResponse> getOrderById(@PathVariable("orderId") Long orderId) {
        return this.orderService.getOrderById(orderId);
    }

    @PutMapping
    public ResponseEntity<OrderEntityResponse> updateOrderStatusById(@PathVariable("orderId") Long orderId,
                                                                     @RequestParam OrderStatus orderStatus) {
        return this.orderService.updateOrderStatusById(orderStatus, orderId);
    }

    @PutMapping("/full")
    public ResponseEntity<OrderEntityResponse> updateFullOrderById(@PathVariable("orderId") Long orderId,
                                                                   @RequestBody UpdateOrderRequest request) {
        return this.orderService.updateFullOrderById(orderId, request);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteOrderById(@PathVariable("orderId") Long orderId) {
        this.orderService.deleteOrderById(orderId);
        return ResponseEntity.noContent().build();
    }
}

