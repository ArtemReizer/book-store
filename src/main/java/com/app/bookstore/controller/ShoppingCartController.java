package com.app.bookstore.controller;

import com.app.bookstore.dto.CartItemDto;
import com.app.bookstore.dto.CreateCartItemRequestDto;
import com.app.bookstore.dto.PutCartItemRequestDto;
import com.app.bookstore.dto.ShoppingCartDto;
import com.app.bookstore.service.ShoppingCartService;
import com.app.bookstore.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Shopping cart management", description =
        "Managing shopping cart by its endpoints")
@RequiredArgsConstructor
@RequestMapping("/cart")
public class ShoppingCartController {
    private final ShoppingCartService shoppingCartService;
    private final UserService userService;

    @GetMapping
    @PreAuthorize("hasRole('ROLE_USER')")
    @Operation(summary = "Get a shopping cart")
    public ShoppingCartDto getShoppingCart() {
        return shoppingCartService.getShoppingCart();
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_USER')")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Add a cart item to the shopping cart")
    public CartItemDto addCartItem(
            @RequestBody @Valid CreateCartItemRequestDto cartItemRequestDto) {
        return shoppingCartService.create(cartItemRequestDto,
                userService.getAuthenticatedUser().getId());
    }

    @PutMapping("/cart-items/{cartItemId}")
    @PreAuthorize("hasRole('ROLE_USER')")
    @Operation(summary = "Update the quantity of cart items in cart")
    public PutCartItemRequestDto updateCartItem(
            @RequestBody @Valid PutCartItemRequestDto cartItemRequestDto,
            @PathVariable Long cartItemId) {
        return shoppingCartService.updateCartItem(cartItemId, cartItemRequestDto);
    }

    @DeleteMapping("/cart-items/{cartItemId}")
    @PreAuthorize("hasRole('ROLE_USER')")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete cart item by its id")
    public void deleteCartItem(@PathVariable Long cartItemId) {
        shoppingCartService.deleteCartItem(cartItemId);
    }
}
