package com.app.bookstore.repository.cart;

import com.app.bookstore.model.CartItem;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    @Query("""
            FROM CartItem ci
            LEFT JOIN FETCH ci.book
            WHERE ci.shoppingCart.id = :id
            """)
    List<CartItem> findCartItemsByShoppingCartId(Long id);
}
