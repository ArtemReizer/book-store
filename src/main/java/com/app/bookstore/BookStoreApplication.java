package com.app.bookstore;

import com.app.bookstore.model.Book;
import com.app.bookstore.service.BookService;
import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BookStoreApplication {
    @Autowired
    private BookService bookService;

    public static void main(String[] args) {
        SpringApplication.run(BookStoreApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner() {
        return args -> {
            Book designPatterns = new Book();
            designPatterns.setTitle("Design Patterns");
            designPatterns.setAuthor("Refactoring Guru");
            designPatterns.setPrice(BigDecimal.valueOf(500));
            designPatterns.setIsbn("965954132654");

            bookService.save(designPatterns);
            bookService.findAll().forEach(System.out::println);
        };
    }
}
