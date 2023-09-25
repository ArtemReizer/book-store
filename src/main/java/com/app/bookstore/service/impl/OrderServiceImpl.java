package com.app.bookstore.service.impl;

import com.app.bookstore.dto.order.OrderDto;
import com.app.bookstore.dto.order.OrderItemDto;
import com.app.bookstore.dto.order.PlaceOrderRequestDto;
import com.app.bookstore.dto.order.UpdateOrderStatusRequestDto;
import com.app.bookstore.exceptions.EntityNotFoundException;
import com.app.bookstore.mapper.OrderItemMapper;
import com.app.bookstore.mapper.OrderMapper;
import com.app.bookstore.model.CartItem;
import com.app.bookstore.model.Order;
import com.app.bookstore.model.OrderItem;
import com.app.bookstore.model.ShoppingCart;
import com.app.bookstore.model.User;
import com.app.bookstore.repository.cart.CartItemRepository;
import com.app.bookstore.repository.cart.ShoppingCartRepository;
import com.app.bookstore.repository.order.OrderItemRepository;
import com.app.bookstore.repository.order.OrderRepository;
import com.app.bookstore.service.OrderService;
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
    private final ShoppingCartRepository cartRepository;
    private final OrderItemMapper orderItemMapper;
    private final OrderMapper orderMapper;
    private final OrderRepository orderRepository;
    private final UserService userService;
    private final OrderItemRepository orderItemRepository;
    private final CartItemRepository cartItemRepository;

    @Override
    @Transactional
    public OrderDto createOrder(PlaceOrderRequestDto request) {
        ShoppingCart cart = getShoppingCart();
        User user = userService.getAuthenticatedUser();
        Set<CartItem> cartItems = new HashSet<>(
                cartItemRepository.findCartItemsByShoppingCartId(cart.getId()));
        cart.setCartItems(cartItems);
        Order order = orderMapper.toOrder(cart, request);
        order.setUser(user);
        order.setShippingAddress(request.getShippingAddress());
        order.setTotal(calculateTotal(order.getOrderItems()));
        Order savedOrder = saveOrderWithItems(cartItems, order);
        return orderMapper.toDto(savedOrder);
    }

    @Override
    public OrderDto updateOrderStatus(Long orderId, UpdateOrderStatusRequestDto request) {
        Order order = findOrderById(orderId);
        order.setStatus(request.getStatus());
        return orderMapper.toDto(orderRepository.save(order));
    }

    @Override
    public OrderItemDto getItemById(Long orderId, Long itemId) {
        Order order = findOrderById(orderId);
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
        Order order = findOrderById(orderId);
        Set<OrderItem> orderItems = order.getOrderItems();
        return orderItems.stream()
                .map(orderItemMapper::toDto)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<OrderDto> getAllOrders(Pageable pageable, Long userId) {
        return orderRepository.findAllByUserId(pageable, userId).stream()
                .map(orderMapper::toDto)
                .collect(Collectors.toSet());
    }

    private ShoppingCart getShoppingCart() {
        User user = userService.getAuthenticatedUser();
        return cartRepository.findByUserId(user.getId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Can't find shopping cart by user id: " + user.getId()));
    }

    private BigDecimal calculateTotal(Set<OrderItem> orderItems) {
        BigDecimal total = BigDecimal.ZERO;
        for (OrderItem orderItem : orderItems) {
            BigDecimal itemCost = orderItem.getBook()
                    .getPrice().multiply(BigDecimal.valueOf(orderItem.getQuantity()));
            total = total.add(itemCost);
        }
        return total;
    }

    private Order findOrderById(Long orderId) {
        return orderRepository.findById(orderId).orElseThrow(
                () -> new EntityNotFoundException("Can't find order by id " + orderId));
    }

    private Order saveOrderWithItems(Set<CartItem> cartItems, Order order) {
        Set<OrderItem> orderItems = cartItems.stream()
                .map(orderItemMapper::cartItemToOrderItem)
                .collect(Collectors.toSet());
        order.setOrderItems(orderItems);
        orderRepository.save(order);
        orderItems.forEach(orderItem -> orderItem.setOrder(order));
        orderItemRepository.saveAll(orderItems);
        return order;
    }
}
