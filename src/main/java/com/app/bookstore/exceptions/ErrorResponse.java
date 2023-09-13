package com.app.bookstore.exceptions;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.http.HttpStatus;

public record ErrorResponse(LocalDateTime timestamp, HttpStatus status, List<String> errors) {
}
