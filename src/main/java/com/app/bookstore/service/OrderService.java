package com.app.bookstore.service;

import com.app.bookstore.dto.order.OrderDto;
import com.app.bookstore.dto.order.OrderItemDto;
import com.app.bookstore.dto.order.PlaceOrderRequestDto;
import com.app.bookstore.dto.order.UpdateOrderStatusRequestDto;
import java.util.Set;
import org.springframework.data.domain.Pageable;

public interface OrderService {
    OrderDto createOrder(PlaceOrderRequestDto request);

    OrderDto updateOrderStatus(Long orderId, UpdateOrderStatusRequestDto request);

    OrderItemDto getItemById(Long orderId, Long itemId);

    Set<OrderItemDto> getAllOrderItems(Long orderId);

    Set<OrderDto> getAllOrders(Pageable pageable, Long userId);
}
