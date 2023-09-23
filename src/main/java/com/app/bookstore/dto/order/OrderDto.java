package com.app.bookstore.dto.order;

import com.app.bookstore.model.Order;
import com.app.bookstore.model.OrderItem;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;
import lombok.Data;

@Data
public class OrderDto {
    private Long userId;
    private BigDecimal total;
    private LocalDateTime orderDate;
    private String shippingAddress;
    private Set<OrderItem> orderItems;
    private Order.Status status;
}
