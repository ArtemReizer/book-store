package com.app.bookstore.dto;

import com.app.bookstore.validation.FieldsValueMatch;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
@FieldsValueMatch(field = "password", fieldMatch = "repeatedPassword")
public class UserRegistrationRequestDto {
    @NotEmpty
    @Email
    private String email;
    @NotEmpty
    @Length(min = 6, max = 20)
    private String password;
    @NotEmpty
    @Length(min = 6, max = 20)
    private String repeatedPassword;
    @NotEmpty
    private String firstName;
    @NotEmpty
    private String lastName;
    private String shippingAddress;
}
