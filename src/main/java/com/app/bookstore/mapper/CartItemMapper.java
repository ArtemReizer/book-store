package com.app.bookstore.mapper;

import com.app.bookstore.config.MapperConfig;
import com.app.bookstore.dto.CartItemDto;
import com.app.bookstore.model.CartItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class)
public interface CartItemMapper {
    @Mapping(target = "bookId", source = "cartItem.book.id")
    @Mapping(target = "bookTitle", source = "cartItem.book.title")
    CartItemDto toDto(CartItem cartItem);
}
