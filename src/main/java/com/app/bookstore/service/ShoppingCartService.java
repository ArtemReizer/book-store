package com.app.bookstore.service;

import com.app.bookstore.dto.CartItemDto;
import com.app.bookstore.dto.CreateCartItemRequestDto;
import com.app.bookstore.dto.PostCartItemRequestDto;
import com.app.bookstore.dto.ShoppingCartDto;

public interface ShoppingCartService {
    CartItemDto create(CreateCartItemRequestDto createCartItemRequestDto, Long userId);

    CartItemDto updateCartItem(Long cartItemId, PostCartItemRequestDto cartItemRequestDto);

    ShoppingCartDto getShoppingCart(Long userId);

    void deleteCartItem(Long cartItemId);
}
