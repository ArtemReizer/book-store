package com.app.bookstore.service.impl;

import com.app.bookstore.dto.order.OrderDto;
import com.app.bookstore.dto.order.OrderItemDto;
import com.app.bookstore.dto.order.PlaceOrderRequestDto;
import com.app.bookstore.dto.order.UpdateOrderStatusRequestDto;
import com.app.bookstore.exceptions.EntityNotFoundException;
import com.app.bookstore.mapper.CartItemMapper;
import com.app.bookstore.mapper.OrderItemMapper;
import com.app.bookstore.mapper.OrderMapper;
import com.app.bookstore.model.CartItem;
import com.app.bookstore.model.Order;
import com.app.bookstore.model.OrderItem;
import com.app.bookstore.model.ShoppingCart;
import com.app.bookstore.repository.cart.ShoppingCartRepository;
import com.app.bookstore.repository.order.OrderItemRepository;
import com.app.bookstore.repository.order.OrderRepository;
import com.app.bookstore.service.OrderService;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final ShoppingCartRepository cartRepository;
    private final OrderItemMapper orderItemMapper;
    private final OrderItemRepository orderItemRepository;
    private final OrderMapper orderMapper;
    private final OrderRepository orderRepository;
    private final CartItemMapper cartItemMapper;

    @Override
    public OrderDto createOrder(Long userId, PlaceOrderRequestDto request) {
        ShoppingCart cart = getShoppingCart(userId);
        Order order = createNewOrder(cart, request.getShippingAddress());
        orderRepository.save(order);
        orderItemRepository.saveAll(order.getOrderItems());
        cartRepository.deleteAll();
        return orderMapper.toDto(order);
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

    private ShoppingCart getShoppingCart(Long userId) {
        return cartRepository.findByUserId(userId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Can't find shopping cart by user id: " + userId));
    }

    private Order createNewOrder(ShoppingCart shoppingCart, String shippingAddress) {
        Order order = new Order();
        order.setUser(shoppingCart.getUser());
        order.setShippingAddress(shippingAddress);
        order.setOrderDate(LocalDateTime.now());
        order.setStatus(Order.Status.PENDING);
        order.setTotal(calculateTotal(shoppingCart.getCartItems()));
        order.setOrderItems(convertToOrderItems(shoppingCart, order));
        return order;
    }

    private Set<OrderItem> convertToOrderItems(ShoppingCart shoppingCart, Order newOrder) {
        Set<OrderItem> orderItems = new HashSet<>();
        for (CartItem cartItem : shoppingCart.getCartItems()) {
            OrderItem orderItem = cartItemMapper.toOrderItem(cartItem);
            orderItem.setOrder(newOrder);
            orderItems.add(orderItem);
        }
        return orderItems;
    }

    private BigDecimal calculateTotal(Set<CartItem> cartItems) {
        BigDecimal total = BigDecimal.ZERO;
        for (CartItem cartItem : cartItems) {
            BigDecimal itemCost = cartItem.getBook()
                    .getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity()));
            total = total.add(itemCost);
        }
        return total;
    }

    private Order findOrderById(Long orderId) {
        return orderRepository.findById(orderId).orElseThrow(
                () -> new EntityNotFoundException("Can't find order by id " + orderId));
    }
}
