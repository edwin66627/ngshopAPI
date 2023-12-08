package com.ngshop.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name="products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "description", nullable = false)
    private String description;
    private String richDescription;
    private String image;
    private List<String> images;
    @Column(name = "price", nullable = false)
    private float price;
    private int countInStock;
    private int rating;
    private boolean isFeatured;
    private Date createdDate;
    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

}
