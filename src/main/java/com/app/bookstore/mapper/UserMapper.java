package com.app.bookstore.mapper;

import com.app.bookstore.config.MapperConfig;
import com.app.bookstore.dto.UserLoginResponseDto;
import com.app.bookstore.dto.UserRegistrationRequestDto;
import com.app.bookstore.dto.UserRegistrationResponseDto;
import com.app.bookstore.model.User;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface UserMapper {
    UserRegistrationResponseDto toUserResponse(User user);

    UserLoginResponseDto toDto(User user);

    User toModel(UserRegistrationRequestDto requestDto);
}
