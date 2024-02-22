package com.ngshop.repository.impl;

import com.ngshop.dto.ProductSearchCriteriaDTO;
import com.ngshop.entity.Product;
import com.ngshop.repository.ProductRepositoryCustom;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductRepositoryCustomImpl implements ProductRepositoryCustom {

    @Autowired
    private EntityManager entityManager;
    @Override
    public Page<Product> searchProducts(ProductSearchCriteriaDTO productSearchCriteriaDTO) {
        Pageable pageable = PageRequest.of(productSearchCriteriaDTO.getPageNumber(), productSearchCriteriaDTO.getPageSize());
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery productsQuery = criteriaBuilder.createQuery(Product.class);
        Root<Product> productsRoot = productsQuery.from(Product.class);
        List<Predicate> productsPredicates = new ArrayList<>();

        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        Root<Product> countRoot = countQuery.from(Product.class);
        countQuery.select(criteriaBuilder.count(countRoot));
        List<Predicate> countPredicates = new ArrayList<>();

        // If true, then return only featured products. If false, then return featured and not featured products
        if(productSearchCriteriaDTO.isFeatured()){
            productsPredicates.add(criteriaBuilder.isTrue(productsRoot.get("isFeatured").as(Boolean.class)));
            countPredicates.add(criteriaBuilder.isTrue(countRoot.get("isFeatured").as(Boolean.class)));
        }
        if(!productSearchCriteriaDTO.getCategories().isEmpty()){
            productsPredicates.add(criteriaBuilder.isTrue(productsRoot.get("category").get("id").in(productSearchCriteriaDTO.getCategories())));
            countPredicates.add(criteriaBuilder.isTrue(countRoot.get("category").get("id").in(productSearchCriteriaDTO.getCategories())));
        }
        productsQuery.where((productsPredicates.toArray(new Predicate[0])));
        productsQuery.where((countPredicates.toArray(new Predicate[0])));

        List<Product> products = paginateQuery(entityManager.createQuery(productsQuery), pageable).getResultList();
        long count = entityManager.createQuery(countQuery).getSingleResult();

        return new PageImpl<>(products, pageable, count);
    }

    public static <T> TypedQuery<T> paginateQuery(TypedQuery<T> query, Pageable pageable) {
        if (pageable.isPaged()) {
            query.setFirstResult((int) pageable.getOffset());
            query.setMaxResults(pageable.getPageSize());
        }
        return query;
    }

}
