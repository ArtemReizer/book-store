package com.app.bookstore.mapper;

import com.app.bookstore.config.MapperConfig;
import com.app.bookstore.dto.ShoppingCartDto;
import com.app.bookstore.model.ShoppingCart;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(config = MapperConfig.class)
public interface ShoppingCartMapper {
    @Mapping(target = "cartItems", ignore = true)
    ShoppingCartDto toDto(ShoppingCart shoppingCart);
}
