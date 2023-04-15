package com.example.recyclemarketback.domain.products.repository;

import com.example.recyclemarketback.domain.products.entity.ProductEntity;
import com.example.recyclemarketback.domain.products.entity.ProductState;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
    List<ProductEntity> findAllByEndDateBeforeAndProductState(LocalDateTime now, ProductState auction);
}
