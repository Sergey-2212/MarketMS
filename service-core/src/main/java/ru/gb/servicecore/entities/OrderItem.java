package ru.gb.servicecore.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
//import org.springframework.data.annotation.Id;

@AllArgsConstructor
@Data
@NoArgsConstructor
@Entity
@Table(name = "ordered_items")
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "price_per_item")
    private BigDecimal pricePerItem;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "total_price")
    private BigDecimal totalPrice;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    public OrderItem(Product product, BigDecimal pricePerItem, Integer quantity, BigDecimal totalPrice, Order order) {
        this.product = product;
        this.pricePerItem = pricePerItem;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
        this.order = order;
    }
}
