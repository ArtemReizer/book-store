package com.app.bookstore.dto;

import com.app.bookstore.model.CartItem;
import java.util.Set;
import lombok.Data;

@Data
public class ShoppingCartDto {
    private Long id;
    private Long userId;
    private Set<CartItem> cartItems;
}
