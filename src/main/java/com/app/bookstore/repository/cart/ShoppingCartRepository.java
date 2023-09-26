package com.app.bookstore.repository.cart;

import com.app.bookstore.model.ShoppingCart;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {
    @Query("""
            FROM ShoppingCart sc
            INNER JOIN FETCH sc.cartItems i
            INNER JOIN FETCH i.book
            WHERE sc.user.id = :userId
            """)
    Optional<ShoppingCart> findByUserId(Long userId);
}
