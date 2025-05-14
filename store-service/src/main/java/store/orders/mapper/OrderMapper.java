package store.orders.mapper;

import org.springframework.stereotype.Component;
import store.orders.dto.OrderEntityResponse;
import store.orders.dto.UpdateOrderRequest;
import store.orders.entity.OrderDetails;
import store.orders.entity.OrderEntity;
import store.orders.entity.OrderStatus;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrderMapper {

    public OrderEntityResponse toOrderEntityResponse(OrderEntity orderEntity) {
        return new OrderEntityResponse(
                orderEntity.getOrderId(),
                orderEntity.getTotalPrice(),
                orderEntity.getOrderStatus(),
                orderEntity.getDeliveryAddress());
    }


    public void updateStatusOrderEntity(OrderEntity orderEntity, OrderStatus orderStatus) {
        orderEntity.setOrderStatus(orderStatus);
    }

    public void updateFullOrderById(OrderEntity order, UpdateOrderRequest request) {
        order.setDeliveryAddress(request.getDeliveryAddress());
        order.setOrderStatus(request.getOrderStatus());
        order.setTotalPrice(request.getOrderDetails().stream()
                .mapToDouble(orderDetails1 ->
                        orderDetails1.getPrice() * orderDetails1.getQuantity())
                .sum());
    }

    public List<OrderDetails> updateOrderDetails(UpdateOrderRequest request, Long orderId) {
        return request.getOrderDetails()
                .stream()
                .map(orderRequest -> {
                    OrderDetails orderDetails = new OrderDetails();
                    orderDetails.setOrderId(orderId);
                    orderDetails.setName(orderRequest.getName());
                    orderDetails.setQuantity(orderRequest.getQuantity());
                    orderDetails.setFixedPrice(orderRequest.getPrice());
                    orderDetails.setDishId(orderRequest.getDishId());
                    return orderDetails;
                })
                .collect(Collectors.toList());
    }
}
