package com.ngshop.repository;

import com.ngshop.entity.Product;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {

    //@Query("SELECT p FROM Product p LEFT JOIN p.category WHERE p.id = :productId")
    //Product getProductWithCategory(@Param("productId") Long productId);

    @EntityGraph(attributePaths = {"category"})
    Optional<Product> findById(Long productId);

}
