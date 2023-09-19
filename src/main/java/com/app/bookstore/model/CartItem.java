package com.app.bookstore.model;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Data
@Table(name = "cart_item")
@SQLDelete(sql = "UPDATE cart_item SET is_deleted=true WHERE id=?")
@Where(clause = "is_deleted=false")
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "cart_id", referencedColumnName = "id")
    private ShoppingCart shoppingCart;
    @NotNull
    @OneToOne(fetch = FetchType.LAZY)
    private Book book;
    @NotNull
    private int quantity;
    @NotNull
    private boolean isDeleted = false;
}
