package com.example.recyclemarketback.domain.products.repository;

import com.example.recyclemarketback.domain.products.entity.ProductEntity;

import java.util.List;

public interface ProductRepositoryCustom {
    List<ProductEntity> findDynamicQueryAdvance(String category, String name);
}
