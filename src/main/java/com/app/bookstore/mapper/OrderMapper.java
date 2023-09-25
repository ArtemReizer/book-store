package com.app.bookstore.mapper;

import com.app.bookstore.config.MapperConfig;
import com.app.bookstore.dto.order.OrderDto;
import com.app.bookstore.dto.order.PlaceOrderRequestDto;
import com.app.bookstore.model.Order;
import com.app.bookstore.model.ShoppingCart;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class, uses = OrderItemMapper.class)
public interface OrderMapper {
    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "orderItems", source = "orderItems")
    OrderDto toDto(Order order);

    @Mapping(target = "id")
    @Mapping(target = "status", constant = "PENDING")
    @Mapping(target = "total", ignore = true)
    @Mapping(target = "orderItems", source = "shoppingCart.cartItems")
    @Mapping(target = "orderDate", expression = "java(java.time.LocalDateTime.now())")
    Order toOrder(ShoppingCart shoppingCart, PlaceOrderRequestDto request);
}
