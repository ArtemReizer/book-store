package com.app.bookstore.controller;

import com.app.bookstore.dto.order.OrderDto;
import com.app.bookstore.dto.order.OrderItemDto;
import com.app.bookstore.dto.order.PlaceOrderRequestDto;
import com.app.bookstore.dto.order.UpdateOrderStatusRequestDto;
import com.app.bookstore.service.OrderService;
import com.app.bookstore.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/order")
@Tag(name = "Order management")
public class OrderController {
    private final OrderService orderService;
    private final UserService userService;

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping
    @Operation(summary = "Place a new order")
    public OrderDto placeOrder(@RequestBody @Valid PlaceOrderRequestDto placeOrderRequestDto) {
        return orderService.createOrder(placeOrderRequestDto);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping
    @Operation(summary = "Returns all user's orders")
    public Set<OrderDto> getAllUserOrders(Pageable pageable) {
        Long userId = userService.getAuthenticatedUser().getId();
        return orderService.getAllOrders(pageable, userId);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PatchMapping("/{id}")
    @Operation(summary = "Update status of order by its id")
    public OrderDto updateStatus(@PathVariable Long id,
            @RequestBody UpdateOrderStatusRequestDto requestDto) {
        return orderService.updateOrderStatus(id, requestDto);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/{orderId}/items")
    @Operation(summary = "Returns order items from an order by its id")
    public Set<OrderItemDto> getAllByOrderId(@PathVariable Long orderId) {
        return orderService.getAllOrderItems(orderId);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/{orderId}/items/{itemId}")
    @Operation(summary = "Return an item from an order by its id")
    public OrderItemDto getByIdAndItemId(@PathVariable Long orderId,
            @PathVariable Long itemId) {
        return orderService.getItemById(orderId, itemId);
    }
}
