package store.orders.mapper;

import org.springframework.stereotype.Component;
import store.orders.dto.OrderEntityResponse;
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
}
