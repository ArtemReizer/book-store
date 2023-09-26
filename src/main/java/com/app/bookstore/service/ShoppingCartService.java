package com.app.bookstore.service;

import com.app.bookstore.dto.CartItemDto;
import com.app.bookstore.dto.CreateCartItemRequestDto;
import com.app.bookstore.dto.PutCartItemRequestDto;
import com.app.bookstore.dto.ShoppingCartDto;

public interface ShoppingCartService {
    CartItemDto create(CreateCartItemRequestDto createCartItemRequestDto, Long userId);

    PutCartItemRequestDto updateCartItem(Long cartItemId, PutCartItemRequestDto cartItemRequestDto);

    ShoppingCartDto getShoppingCart();

    void deleteCartItem(Long cartItemId);
}
