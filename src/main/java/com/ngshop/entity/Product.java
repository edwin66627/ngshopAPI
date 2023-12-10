package com.ngshop.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
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
    @Column(name = "rich_description", length = 5000)
    private String richDescription;
    private String brand;
    private String image;
    private List<String> images;
    @Column(name = "price", nullable = false)
    private float price;
    private int countInStock;
    private int rating;
    private int numReviews;
    private boolean isFeatured;
    private Date createdDate;
    // Products is the owning side of the relationship and Category is the inverse since it includes 'mappedBy' attribute
    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

}
