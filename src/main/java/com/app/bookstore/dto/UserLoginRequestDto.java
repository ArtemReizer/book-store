package com.app.bookstore.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class UserLoginRequestDto {
    @NotEmpty
    @Email
    private String email;
    @NotEmpty
    @Length(min = 6, max = 20)
    private String password;
}
