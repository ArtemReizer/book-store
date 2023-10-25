package com.app.bookstore.repository.order;

import com.app.bookstore.model.Order;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query("""
            FROM Order o
            LEFT JOIN FETCH o.user u
            LEFT JOIN FETCH o.orderItems i
            LEFT JOIN FETCH i.book
            WHERE u.id = :userId
            """)
    List<Order> findAllByUserId(Pageable pageable, Long userId);

    @Query("""
            FROM Order o
            LEFT JOIN FETCH o.orderItems oi
            LEFT JOIN FETCH o.user u
            WHERE u.id = :userId
            AND o.id = :orderId
            """)
    Order findOrderByOrderId(Long orderId, Long userId);
}
