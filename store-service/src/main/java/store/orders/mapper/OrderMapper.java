package store.orders.mapper;

import org.springframework.stereotype.Component;
import store.orders.dto.OrderEntityResponse;
import store.orders.dto.UpdateOrderRequest;
import store.orders.entity.OrderDetails;
import store.orders.entity.OrderEntity;
import store.orders.entity.OrderStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
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

        Map<Long, OrderDetails> existingDetails = order.getOrderDetails()
                .stream()
                .collect(Collectors.toMap(OrderDetails::getDishId, Function.identity()));

        List<OrderDetails> newDetails = request.getOrderDetails()
                .stream()
                .map(detail -> {
                    OrderDetails orderDetail = existingDetails.get(detail.getDishId());
                    if (orderDetail != null) {
                        orderDetail.setQuantity(detail.getQuantity());
                        orderDetail.setFixedPrice(detail.getPrice());
                        orderDetail.setName(detail.getName());
                        return orderDetail;
                    } else {
                        return OrderDetails.builder()
                                .dishId(detail.getDishId())
                                .quantity(detail.getQuantity())
                                .fixedPrice(detail.getPrice())
                                .name(detail.getName())
                                .order(order)
                                .build();
                    }
                })
                .collect(Collectors.toList());

        order.getOrderDetails().retainAll(newDetails);
        order.getOrderDetails().addAll(newDetails);
    }
}
