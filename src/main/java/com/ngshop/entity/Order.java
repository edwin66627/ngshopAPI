package com.ngshop.entity;

import com.ngshop.entity.security.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name="orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "status", nullable = false)
    private String status;
    @Column(name = "total_price", nullable = false)
    private float totalPrice;
    @Column(name = "order_date", nullable = false)
    private Date orderDate;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "order")
    private Set<OrderItem> orderItems = new HashSet<>();
    @ManyToOne
    @JoinColumn(name = "address_id", nullable = false)
    private Address address;

}
