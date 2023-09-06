package com.app.bookstore.repository;

import com.app.bookstore.dto.BookSearchParametersDto;
import org.springframework.data.jpa.domain.Specification;

public interface SpecificationBuilder<T> {

    Specification<T> build(BookSearchParametersDto searchParameters);
}
