package com.ngshop.repository.impl;

import com.ngshop.dto.ProductSearchCriteriaDTO;
import com.ngshop.entity.Product;
import com.ngshop.repository.ProductRepositoryCustom;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductRepositoryCustomImpl implements ProductRepositoryCustom {

    @Autowired
    private EntityManager entityManager;
    @Override
    public List<Product> searchProducts(ProductSearchCriteriaDTO productSearchCriteriaDTO) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery criteriaQuery = criteriaBuilder.createQuery(Product.class);
        Root<Product> root = criteriaQuery.from(Product.class);
        List<Predicate> predicates = new ArrayList<>();

        // If true, then return only featured products. If false, then return featured and not featured products
        if(productSearchCriteriaDTO.isFeatured()){
            predicates.add(criteriaBuilder.isTrue(root.get("isFeatured").as(Boolean.class)));
        }

        criteriaQuery.where((predicates.toArray(new Predicate[0])));
        return entityManager.createQuery(criteriaQuery).getResultList();
    }
}
