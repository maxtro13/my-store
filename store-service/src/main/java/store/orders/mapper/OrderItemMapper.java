package store.orders.mapper;

import org.springframework.stereotype.Component;
import store.dishes.dto.DishRequestDto;
import store.orders.entity.OrderDetails;

@Component
public class OrderItemMapper {

    public OrderDetails toOrderItemFromDishRequestDto(DishRequestDto requestDto) {
        return null;
    }

//    public OrderItem toOrderItemFromDish(Dish dish) {
//        OrderItem orderItem = new OrderItem();
//        orderItem.setDish(dish);
//        orderItem.setFixedPrice(dish.getPrice());
//        return null;
//    }
}
