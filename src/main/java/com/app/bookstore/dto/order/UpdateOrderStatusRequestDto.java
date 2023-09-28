package com.app.bookstore.dto.order;

import com.app.bookstore.model.Order;
import lombok.Data;

@Data
public class UpdateOrderStatusRequestDto {
    private Order.Status status;
}
