package com.ngshop.entity;

import com.ngshop.entity.security.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name="address")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", nullable = false)
    private Long id;

    @Column(name="street", nullable = false)
    private String street;

    @Column(name="city", nullable = false)
    private String city;

    @Column(name="state", nullable = false)
    private String state;

    @Column(name="country", nullable = false)
    private String country;

    @Column(name="zip_code")
    private String zipCode;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

}
