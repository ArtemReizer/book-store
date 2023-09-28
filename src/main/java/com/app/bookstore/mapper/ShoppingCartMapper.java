package com.app.bookstore.mapper;

import com.app.bookstore.config.MapperConfig;
import com.app.bookstore.dto.ShoppingCartDto;
import com.app.bookstore.model.ShoppingCart;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class, uses = {CartItemMapper.class})
public interface ShoppingCartMapper {
    @Mapping(target = "cartItems")
    @Mapping(target = "userId", source = "shoppingCart.user.id")
    ShoppingCartDto toDto(ShoppingCart shoppingCart);

    @Mapping(target = "id")
    @Mapping(target = "user.id", source = "shoppingCartDto.userId")
    @Mapping(target = "cartItems", source = "shoppingCartDto.cartItems", ignore = true)
    ShoppingCart toCart(ShoppingCartDto shoppingCartDto);
}
