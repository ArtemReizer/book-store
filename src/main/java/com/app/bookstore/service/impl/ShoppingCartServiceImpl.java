package com.app.bookstore.service.impl;

import com.app.bookstore.dto.CartItemDto;
import com.app.bookstore.dto.CreateCartItemRequestDto;
import com.app.bookstore.dto.PostCartItemRequestDto;
import com.app.bookstore.dto.ShoppingCartDto;
import com.app.bookstore.exceptions.EntityNotFoundException;
import com.app.bookstore.mapper.CartItemMapper;
import com.app.bookstore.mapper.ShoppingCartMapper;
import com.app.bookstore.model.CartItem;
import com.app.bookstore.model.ShoppingCart;
import com.app.bookstore.repository.book.BookRepository;
import com.app.bookstore.repository.cart.CartItemRepository;
import com.app.bookstore.repository.cart.ShoppingCartRepository;
import com.app.bookstore.service.ShoppingCartService;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {
    private final ShoppingCartRepository shoppingCartRepository;
    private final CartItemRepository cartItemRepository;
    private final ShoppingCartMapper shoppingCartMapper;
    private final CartItemMapper cartItemMapper;
    private final BookRepository bookRepository;

    @Override
    public CartItemDto create(CreateCartItemRequestDto createCartItemRequestDto, Long userId) {
        CartItem cartItem = new CartItem();
        cartItem.setBook(bookRepository.getBookById(createCartItemRequestDto.getBookId()));
        ShoppingCart shoppingCart = shoppingCartRepository.findByUserId(userId).orElseThrow(
                () -> new EntityNotFoundException("Can't find shopping cart by user id: " + userId)
        );
        cartItem.setShoppingCart(shoppingCart);
        cartItem.setQuantity(createCartItemRequestDto.getQuantity());
        return cartItemMapper.toDto(cartItemRepository.save(cartItem));
    }

    @Override
    public CartItemDto updateCartItem(Long cartItemId, PostCartItemRequestDto cartItemRequestDto) {
        CartItem cartItem = cartItemRepository.findById(cartItemId).orElseThrow(
                () -> new EntityNotFoundException("Can't find cart item by its id: " + cartItemId)
        );
        cartItem.setQuantity(cartItemRequestDto.getQuantity());
        cartItemRepository.save(cartItem);
        return cartItemMapper.toDto(cartItem);
    }

    @Override
    public ShoppingCartDto getShoppingCart(Long userId) {
        ShoppingCart shoppingCart = shoppingCartRepository.findByUserId(userId).orElseThrow(
                () -> new EntityNotFoundException("Can't find shopping cart by user id: " + userId)
        );
        ShoppingCartDto shoppingCartDto = shoppingCartMapper.toDto(shoppingCart);
        shoppingCartDto.setCartItems(getCartItems(userId));
        shoppingCartDto.setUserId(userId);
        return shoppingCartDto;
    }

    @Override
    public void deleteCartItem(Long cartItemId) {
        cartItemRepository.deleteById(cartItemId);
    }

    private Set<CartItem> getCartItems(Long id) {
        return cartItemRepository.findById(id).stream()
                .collect(Collectors.toSet());
    }
}
