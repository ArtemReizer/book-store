package com.app.bookstore.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CategoryRequestDto {
    @NotNull
    @Size(min = 3)
    private String name;
    private String description;
}
