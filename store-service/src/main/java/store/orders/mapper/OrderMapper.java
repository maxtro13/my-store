package store.orders.mapper;

import org.springframework.stereotype.Component;
import store.orders.dto.OrderEntityResponse;
import store.orders.dto.UpdateOrderRequest;
import store.orders.entity.OrderDetails;
import store.orders.entity.OrderEntity;
import store.orders.entity.OrderStatus;

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


//
//        List<OrderDetails> newDetails = request.getOrderDetails()
//                .stream()
//                .map(detail -> {
//                    orderDetails = orderDetails.get(detail.getDishId());
//                    if (orderDetails != null) {
//                        orderDetails.setQuantity(detail.getQuantity());
//                        orderDetails.setFixedPrice(detail.getPrice());
//                        orderDetails.setName(detail.getName());
//                        return orderDetails;
//                    } else {
//                        return OrderDetails.builder()
//                                .dishId(detail.getDishId())
//                                .quantity(detail.getQuantity())
//                                .fixedPrice(detail.getPrice())
//                                .name(detail.getName())
//                                .orderId(order.getOrderId())
//                                .build();
//                    }
//                })
//                .collect(Collectors.toList());

    }
//todo Доделать обновление заказа
    public void updateOrderDetails(OrderDetails orderDetails, UpdateOrderRequest request) {

    }
}
