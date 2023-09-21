package com.app.bookstore.service.impl;

import com.app.bookstore.dto.CartItemDto;
import com.app.bookstore.dto.CreateCartItemRequestDto;
import com.app.bookstore.dto.PutCartItemRequestDto;
import com.app.bookstore.dto.ShoppingCartDto;
import com.app.bookstore.exceptions.EntityNotFoundException;
import com.app.bookstore.mapper.CartItemMapper;
import com.app.bookstore.mapper.ShoppingCartMapper;
import com.app.bookstore.model.CartItem;
import com.app.bookstore.model.ShoppingCart;
import com.app.bookstore.model.User;
import com.app.bookstore.repository.book.BookRepository;
import com.app.bookstore.repository.cart.CartItemRepository;
import com.app.bookstore.repository.cart.ShoppingCartRepository;
import com.app.bookstore.service.ShoppingCartService;
import com.app.bookstore.service.UserService;
import java.util.Optional;
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
    private final UserService userService;

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
    public PutCartItemRequestDto updateCartItem(Long cartItemId,
                                                PutCartItemRequestDto cartItemRequestDto) {
        CartItem cartItem = cartItemRepository.findById(cartItemId).orElseThrow(
                () -> new EntityNotFoundException("Can't find cart item by its id: " + cartItemId)
        );
        cartItem.setQuantity(cartItemRequestDto.getQuantity());
        return cartItemMapper.toPutRequestDto(cartItemRepository.save(cartItem));
    }

    @Override
    public ShoppingCartDto getShoppingCart() {
        return shoppingCartMapper.toDto(findShoppingCartOfUser()
                .orElseGet(this::createShoppingCart));
    }

    @Override
    public void deleteCartItem(Long cartItemId) {
        cartItemRepository.deleteById(cartItemId);
    }

    private Optional<ShoppingCart> findShoppingCartOfUser() {
        User currentUser = userService.getAuthenticatedUser();
        Optional<ShoppingCart> cart = shoppingCartRepository.findByUserId(currentUser.getId());
        cart.ifPresent(cartEntity -> {
            cartEntity.getCartItems().size();
        });
        return cart;
    }

    private ShoppingCart createShoppingCart() {
        User user = userService.getAuthenticatedUser();
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setUser(user);
        return shoppingCartRepository.save(shoppingCart);
    }
}
