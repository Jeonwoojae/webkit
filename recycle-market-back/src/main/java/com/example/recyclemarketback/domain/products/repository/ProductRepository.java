package com.example.recyclemarketback.domain.products.repository;

import com.example.recyclemarketback.domain.products.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
}
