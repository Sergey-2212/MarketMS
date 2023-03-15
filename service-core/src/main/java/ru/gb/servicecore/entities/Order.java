package ru.gb.servicecore.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "address")
    private String address;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "total_price")
    private BigDecimal totalPrice;

    @Column(name = "ordered_at")
    @CreationTimestamp
    private LocalDateTime ordered_at;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> items;

    public Order(String username, String address, String phoneNumber, BigDecimal totalPrice) {
        this.username = username;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.totalPrice = totalPrice;

    }
}
