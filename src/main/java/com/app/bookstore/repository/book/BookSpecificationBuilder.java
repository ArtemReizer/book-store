package com.app.bookstore.repository.book;

import com.app.bookstore.dto.BookSearchParametersDto;
import com.app.bookstore.model.Book;
import com.app.bookstore.repository.SpecificationBuilder;
import com.app.bookstore.repository.SpecificationProviderManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class BookSpecificationBuilder implements SpecificationBuilder<Book,
        BookSearchParametersDto> {
    private final SpecificationProviderManager<Book> bookSpecificationProviderManager;

    @Override
    public Specification<Book> build(BookSearchParametersDto searchParams) {
        Specification<Book> specification = Specification.where(null);
        if (isParameterPresent(searchParams.authors())) {
            specification = addSpecification(specification, "author", searchParams.authors());
        }
        if (isParameterPresent(searchParams.titles())) {
            specification = addSpecification(specification, "title", searchParams.titles());
        }
        return specification;
    }

    private boolean isParameterPresent(String[] param) {
        return param != null && param.length > 0;
    }

    private Specification<Book> addSpecification(Specification<Book> specification,
                                                 String key, String[] params) {
        return specification.and(bookSpecificationProviderManager
                .getSpecificationProvider(key).getSpecification(params));
    }
}
