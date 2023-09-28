package com.app.bookstore.mapper;

import com.app.bookstore.config.MapperConfig;
import com.app.bookstore.dto.CartItemDto;
import com.app.bookstore.dto.PutCartItemRequestDto;
import com.app.bookstore.model.CartItem;
import com.app.bookstore.model.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class)
public interface CartItemMapper {
    @Mapping(target = "bookId", source = "cartItem.book.id")
    @Mapping(target = "bookTitle", source = "cartItem.book.title")
    CartItemDto toDto(CartItem cartItem);

    PutCartItemRequestDto toPutRequestDto(CartItem cartItem);

    @Mapping(target = "id", ignore = true)
    OrderItem toOrderItem(CartItem cartItem);
}
