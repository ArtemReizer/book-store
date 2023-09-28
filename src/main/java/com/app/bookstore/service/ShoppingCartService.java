package com.app.bookstore.service;

import com.app.bookstore.dto.CartItemDto;
import com.app.bookstore.dto.CreateCartItemRequestDto;
import com.app.bookstore.dto.PutCartItemRequestDto;
import com.app.bookstore.dto.ShoppingCartDto;
import com.app.bookstore.model.ShoppingCart;

public interface ShoppingCartService {
    CartItemDto create(CreateCartItemRequestDto createCartItemRequestDto, Long userId);

    PutCartItemRequestDto updateCartItem(Long cartItemId, PutCartItemRequestDto cartItemRequestDto);

    ShoppingCartDto getShoppingCart();

    void deleteCartItem(Long cartItemId);

    void clear(ShoppingCart cart);
}
