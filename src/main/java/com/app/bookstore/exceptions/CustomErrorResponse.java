package com.app.bookstore.exceptions;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class CustomErrorResponse {
    private LocalDateTime timestamp;
    private HttpStatus status;
    private List<String> errors;
}
