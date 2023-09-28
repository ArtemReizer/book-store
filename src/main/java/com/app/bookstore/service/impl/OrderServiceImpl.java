package com.app.bookstore.service.impl;

import com.app.bookstore.dto.order.OrderDto;
import com.app.bookstore.dto.order.OrderItemDto;
import com.app.bookstore.dto.order.PlaceOrderRequestDto;
import com.app.bookstore.dto.order.UpdateOrderStatusRequestDto;
import com.app.bookstore.exceptions.EntityNotFoundException;
import com.app.bookstore.mapper.OrderItemMapper;
import com.app.bookstore.mapper.OrderMapper;
import com.app.bookstore.mapper.ShoppingCartMapper;
import com.app.bookstore.model.CartItem;
import com.app.bookstore.model.Order;
import com.app.bookstore.model.OrderItem;
import com.app.bookstore.model.ShoppingCart;
import com.app.bookstore.repository.cart.CartItemRepository;
import com.app.bookstore.repository.order.OrderRepository;
import com.app.bookstore.service.OrderService;
import com.app.bookstore.service.ShoppingCartService;
import com.app.bookstore.service.UserService;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final ShoppingCartService shoppingCartService;
    private final OrderItemMapper orderItemMapper;
    private final OrderMapper orderMapper;
    private final OrderRepository orderRepository;
    private final UserService userService;
    private final CartItemRepository cartItemRepository;
    private final ShoppingCartMapper shoppingCartMapper;

    @Override
    @Transactional
    public OrderDto createOrder(PlaceOrderRequestDto request) {
        ShoppingCart cart = shoppingCartMapper.toCart(shoppingCartService.getShoppingCart());
        Set<CartItem> cartItems = new HashSet<>(
                cartItemRepository.findCartItemsByShoppingCartId(cart.getId()));
        Order order = orderMapper.toOrder(cart, request);
        order.setShippingAddress(request.getShippingAddress());
        setOrderItems(cartItems, order);
        order.setTotal(calculateTotal(order));
        orderRepository.save(order);
        shoppingCartService.clear(cart);
        return orderMapper.toDto(order);
    }

    @Override
    public OrderDto updateOrderStatus(Long orderId, UpdateOrderStatusRequestDto request) {
        Order order = orderRepository.findOrderByOrderId(orderId,
                userService.getAuthenticatedUser().getId());
        order.setStatus(request.getStatus());
        return orderMapper.toDto(orderRepository.save(order));
    }

    @Override
    public OrderItemDto getItemById(Long orderId, Long itemId) {
        Order order = orderRepository.findOrderByOrderId(orderId,
                userService.getAuthenticatedUser().getId());
        OrderItem orderItem = order.getOrderItems().stream()
                .filter(item -> item.getId().equals(itemId))
                .findFirst()
                .orElseThrow(
                        () -> new EntityNotFoundException("Can't find order item with id "
                                + itemId));
        return orderItemMapper.toDto(orderItem);
    }

    @Override
    public Set<OrderItemDto> getAllOrderItems(Long orderId) {
        Order order = orderRepository.findOrderByOrderId(orderId,
                userService.getAuthenticatedUser().getId());
        Set<OrderItem> orderItems = order.getOrderItems();
        return orderItems.stream()
                .map(orderItemMapper::toDto)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<OrderDto> getAllOrders(Pageable pageable, Long userId) {
        return orderRepository.findAllByUserId(pageable, userId)
                .stream()
                .map(orderMapper::toDto)
                .collect(Collectors.toSet());
    }

    private BigDecimal calculateTotal(Order order) {
        Set<OrderItem> orderItems = order.getOrderItems();
        BigDecimal total = BigDecimal.ZERO;
        for (OrderItem orderItem : orderItems) {
            BigDecimal itemCost = orderItem.getBook()
                    .getPrice().multiply(BigDecimal.valueOf(orderItem.getQuantity()));
            total = total.add(itemCost);
        }
        return total;
    }

    private void setOrderItems(Set<CartItem> cartItems, Order order) {
        cartItems.stream()
                .map(orderItemMapper::cartItemToOrderItem)
                .peek(orderItem -> orderItem.setOrder(order))
                .forEach(order.getOrderItems()::add);
    }
}
